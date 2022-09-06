package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM PlaylistMusicas WHERE PlaylistId = :playlistId and musicaid = :musicId", nativeQuery = true)
    public void deleteMusicFromPlaylist(String playlistId, String musicId);

    @Query(value = "SELECT M.id FROM Musicas M LEFT JOIN PlaylistMusicas A on M.id = A.MusicaId \n" +
            "WHERE A.MusicaId = :musicId and A.PlaylistId = :playlistId", nativeQuery = true)
    public String findMusicByPlaylist(String playlistId, String musicId);

}
