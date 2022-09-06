package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.Data;
import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.services.MusicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/music")
public class MusicController {
    @Autowired
    MusicService musicService;
    @ApiOperation(value = "This request reads the values to list the songs and artists")
    @GetMapping
    public ResponseEntity<List<Music>> getAllMusics
            (@RequestParam(value = "name") String name
                    , @RequestHeader(value = "name") String nome
                    , @RequestHeader(value = "token") String token) {
        UsernameDto usernameDto = new UsernameDto(new Data(nome, token));
        List<Music> music = musicService.getMusics(name, usernameDto);
        if (music == null || music.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(music);
    }
}