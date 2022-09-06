package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MusicRepository extends JpaRepository<Music, String> {
    @Query(value = "SELECT DISTINCT M FROM Music M INNER JOIN Artist A ON A.id =  M.artistId WHERE Upper(A.name) Like Upper ('%' || :name || '%') OR Upper(M.name) like Upper('%' || :name || '%') ORDER BY M.name, A.name")
    public List<Music> getAllMusicArtist(@Param("name") String name);


}


