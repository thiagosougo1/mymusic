package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.Data;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.Playlist;
import com.ciandt.summit.bootcamp2022.entity.User;
import com.ciandt.summit.bootcamp2022.services.MusicServiceImpl;
import com.ciandt.summit.bootcamp2022.services.exceptions.ValidateSizeNameException;
import com.ciandt.summit.bootcamp2022.utils.exceptions.InvalidLogDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = MusicController.class)
public class MusicControllerTest {


    private Music music ;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicServiceImpl musicService ;

    @Autowired
    private ObjectMapper objectMapper;
    private String playlistExistingId;
    private String playlistNotExistId;


    private String musicDTONotExistId;
    private Playlist playlist;
    private MusicDto musicDto;
    private UsernameDto usernameDto;
    private Artist artist;
    private List<Music> musicList = new ArrayList<>();
    private List<User> usersList = new ArrayList<>();
    private List<Playlist> playlistMusic = new ArrayList<>();
    private List<Music> musicList2 = new ArrayList<>();

    private Music musicReturned;


    @BeforeEach
    void setUp() throws Exception {
        playlistExistingId = "a39926f4-6acb-4497-884f-d4e5296ef652";
        playlistNotExistId = "070d9496-ae38-4587-8ca6-2ed9b36fb198";
        musicDTONotExistId = "32844fdd-bb76-4c0a-9627-e34ddc9fd892";
        playlist = new Playlist(playlistExistingId, musicList, usersList);
        music = new Music("67f5976c-eb1e-404e-8220-2c2a8a23be47", "Hippy Hippy Shake", artist, playlistMusic);
        artist = new Artist("32844fdd-bb76-4c0a-9627-e34ddc9fd892", "The Beatles", musicList2);
        musicDto = new MusicDto("67f5976c-eb1e-404e-8220-2c2a8a23be47", "Hippy Hippy Shake", artist);
        usernameDto = new UsernameDto(new Data("joao",
                "123456"));
        musicReturned = new Music("67f5976c-eb1e-404e-8220-2c2a8a23be47", "Hippy Hippy Shake", artist, playlistMusic);


    }

    @Test
    public void shouldGetMusicAndArtistAndReturn400() throws Exception {


        doThrow(ValidateSizeNameException.class).when(musicService).getMusics(anyString(),any(usernameDto.getClass()));
        this.mockMvc.perform(get("/v1/music").param("name","a")
                .header("name", "joao").header("token", "123456")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(SC_BAD_REQUEST)).andReturn();


    }



    @Test
    public void shouldGetMusicAndArtistAndReturn200() throws Exception {

        artist = new Artist("32844fdd-bb76-4c0a-9627-e34ddc9fd892", "The Beatles", musicList2);
        music = new Music("67f5976c-eb1e-404e-8220-2c2a8a23be47", "Shake", artist, playlistMusic);
        List<Music> allmusic = new ArrayList<>();
        usernameDto = new UsernameDto(new Data("joao",
                "123456"));

        allmusic.add(music);

        when(musicService.getMusics(anyString(),any(usernameDto.getClass()))).thenReturn(allmusic);
        this.mockMvc.perform(get("/v1/music").param("name","Shake")
                        .header("name", "joao").header("token", "123456"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    }



    @Test
    public void shouldGetMusicAndArtistAndReturn204() throws Exception {

        when(musicService.getMusics(anyString(),any(usernameDto.getClass()))).thenReturn(null);
        this.mockMvc.perform(get("/v1/music").param("name","a")
                .header("name", "joao").header("token", "123456")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(SC_NO_CONTENT)).andReturn();


    }

    @Test
    public void shouldGetMusicAndArtistAndReturn401() throws Exception {

        doThrow(InvalidLogDataException.class).when(musicService).getMusics(anyString(),any(usernameDto.getClass()));
        this.mockMvc.perform(get("/v1/music").param("name","a")
                .header("name", "").header("token", "")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(SC_UNAUTHORIZED)).andReturn();

    }





}
