package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.Playlist;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.repositories.PlaylistRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicAlreadyExistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.utils.TokenService;
import com.ciandt.summit.bootcamp2022.utils.exceptions.InvalidLogDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private TokenService tokenService;

    private static Logger logger = LogManager.getLogger(PlaylistServiceImpl.class);

    @Transactional
    @Override
    public void saveMusicToPlaylist(String id, MusicDto musicDto, UsernameDto usernameDto) {
        try {
            tokenService.validateToken(usernameDto);
            Playlist playlist = playlistRepository.getById(id);
            Music music = musicRepository.getById(musicDto.getId());
            for (Music musicFind : playlist.getMusicList()) {
                if (musicFind.getId() == music.getId()){
                    logger.error("Music Already exist in this playlist.");
                    throw new MusicAlreadyExistException("Music Already exist in this playlist.");
                }
            }
            playlist.getMusicList().add(music);
            music.getPlaylist().add(playlist);
            playlistRepository.save(playlist);
            logger.info("Music add in the playlist");
        } catch (EntityNotFoundException e) {
            logger.error("this Id not exist in database");
            throw new ResourceNotFoundException("this Id not exist in database");
        } catch (InvalidLogDataException e ) {
            logger.error("Invalid Token name");
            throw new InvalidLogDataException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteMusicFromPlaylist(String playlistId, String musicId, UsernameDto usernameDto) {
        try {
            tokenService.validateToken(usernameDto);
            Optional<Playlist> playlist = playlistRepository.findById(playlistId);
            String music = playlistRepository.findMusicByPlaylist(playlistId, musicId);
            if (playlist.isEmpty()) {
                throw new ResourceNotFoundException("Playlist not exits!");
            } else if (music == null) {
                throw new ResourceNotFoundException("Music doesn't exist in this playlist");
            }
            playlistRepository.deleteMusicFromPlaylist(playlistId, musicId);
            logger.info("Music was excluded from playlist");
        } catch (InvalidLogDataException e ) {
            logger.error("Invalid Token name");
            throw new InvalidLogDataException(e.getMessage());
        }
    }


}
