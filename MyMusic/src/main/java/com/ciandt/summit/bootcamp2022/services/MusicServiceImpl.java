package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ValidateSizeNameException;
import com.ciandt.summit.bootcamp2022.utils.TokenService;
import com.ciandt.summit.bootcamp2022.utils.exceptions.InvalidLogDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.status;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    private static Logger logger = LogManager.getLogger(MusicServiceImpl.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public List<Music> getMusics(String name, UsernameDto usernameDto) {
        try {
            tokenService.validateToken(usernameDto);
            if (!checkWordSize(name)) {
                throw new ValidateSizeNameException("Artist's name mustn't have less than 2 characters");
            }
            List<Music> music = musicRepository.getAllMusicArtist(name);
            logger.info("Music/Artist found");
            return music;
        } catch (InvalidLogDataException e) {
            logger.error("Invalid Token name");
            throw new InvalidLogDataException(e.getMessage());
        }
    }

    private boolean checkWordSize(String name) {
        boolean check = true;
        if (name.length() < 2 || name == null) {
            check = false;
        }
        return check;
    }

}

