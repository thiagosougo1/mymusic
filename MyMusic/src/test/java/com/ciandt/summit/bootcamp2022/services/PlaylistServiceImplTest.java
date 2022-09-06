package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.Data;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.Playlist;
import com.ciandt.summit.bootcamp2022.entity.User;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.repositories.PlaylistRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicAlreadyExistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.utils.TokenService;
import com.ciandt.summit.bootcamp2022.utils.exceptions.InvalidLogDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class PlaylistServiceImplTest {

    @InjectMocks
    private PlaylistServiceImpl playlistService;

    @InjectMocks
    private MusicServiceImpl musicService;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private TokenService tokenService;

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
        artist = new Artist("32844fdd-bb76-4c0a-9627-e34ddc9fd892", "The Beatles", musicList2);
        musicDto = new MusicDto("67f5976c-eb1e-404e-8220-2c2a8a23be47", "Hippy Hippy Shake", artist);
        usernameDto = new UsernameDto(new Data("joao",
                "ZIIKXbvDLcs30v/7nzGxxwRHW6AHBEp94vEtSCFGZqK8ojfKYv39J92PI5Tw9EIHZLhtGJUaY2KZHwysFlfWvA=="));
        musicReturned = new Music("67f5976c-eb1e-404e-8220-2c2a8a23be47", "Hippy Hippy Shake", artist, playlistMusic);
        Mockito.when(playlistRepository.getById(playlistExistingId)).thenReturn(playlist);
        Mockito.when(musicRepository.getById(musicDto.getId())).thenReturn(musicReturned);
        Mockito.when(tokenService.createToken(usernameDto)).thenReturn("ZIIKXbvDLcs30v/7nzGxxwRHW6AHBEp94vEtSCFGZqK8ojfKYv39J92PI5Tw9EIHZLhtGJUaY2KZHwysFlfWvA==");
        Mockito.doNothing().when(tokenService).validateToken(usernameDto);

    }

    @Test
    public void shuouldSaveMusicToPlaylist() {
        Assertions.assertDoesNotThrow(() -> {
            playlistService.saveMusicToPlaylist(playlistExistingId, musicDto, usernameDto);
        });
    }

    @Test
    public void shouldRertunNotFoundWhenNotExistsPlaylistId() {
        playlist = new Playlist(playlistNotExistId, musicList, usersList);
        Mockito.when(playlistRepository.getById(playlistNotExistId)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            playlistService.saveMusicToPlaylist(playlistNotExistId, musicDto, usernameDto);
        });
    }

    @Test
    public void shouldRertunNotFoundWhenNotExistsMusicDtoId() {
        musicDto.setId("32844fdd-bb76-4c0a-9627-e34ddc9fd892");
        Mockito.when(musicRepository.getById(musicDto.getId())).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            playlistService.saveMusicToPlaylist(playlistExistingId, musicDto, usernameDto);
        });
    }

    @Test
    public void ShouldReturnInvalidLogDataExceptionWhenInvalidToken() {
        usernameDto.getData().setToken("");
        Mockito.doThrow(InvalidLogDataException.class).when(tokenService).validateToken(usernameDto);

        Assertions.assertThrows(InvalidLogDataException.class, () -> {
            playlistService.saveMusicToPlaylist(playlistExistingId, musicDto, usernameDto);
        });
    }

    @Test
    public void ShouldReturnMusicAlreadyExistExceptionWhenMusicIsAlreadyInPlaylist() {
        playlist.getMusicList().add(musicReturned);

        Assertions.assertThrows(MusicAlreadyExistException.class, () -> {
            playlistService.saveMusicToPlaylist(playlistExistingId, musicDto, usernameDto);
        });

        Mockito.verify(musicRepository, Mockito.times(1)).getById(musicDto.getId());
    }

    @Test
    public void deleteMusicWithSuccess() {
        Mockito.doReturn(Optional.ofNullable(playlist))
                .when(playlistRepository)
                .findById(Mockito.anyString());
        Mockito.doReturn(musicReturned.getId())
                .when(playlistRepository)
                .findMusicByPlaylist(Mockito.anyString(), Mockito.anyString());

        Assertions.assertDoesNotThrow(() -> {
            playlistService.deleteMusicFromPlaylist(playlistExistingId, musicDto.getId(), usernameDto);
        });
    }

    @Test
    public void shouldThrowErrorWhenPlaylistIsEmpty() {

        Mockito.doReturn(musicReturned.getId())
                .when(playlistRepository)
                .findMusicByPlaylist(Mockito.anyString(), Mockito.anyString());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            playlistService.deleteMusicFromPlaylist(playlistNotExistId, musicDto.getId(), usernameDto);
        });
    }

    @Test
    public void shouldThrowErrorWhenMusicIsNull() {

        Mockito.doReturn(Optional.ofNullable(playlist))
                .when(playlistRepository)
                .findById(Mockito.anyString());
        Mockito.doReturn(null)
                .when(playlistRepository)
                .findMusicByPlaylist(Mockito.anyString(), Mockito.anyString());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            playlistService.deleteMusicFromPlaylist(playlistExistingId, musicDto.getId(), usernameDto);
        });

    }

    @Test
    public void shoudlThrowInvalidLogDataToken() {
        Mockito.doThrow(InvalidLogDataException.class).when(tokenService).validateToken(Mockito.any(UsernameDto.class));

        Assertions.assertThrows(InvalidLogDataException.class, () -> {
            playlistService.saveMusicToPlaylist(playlistExistingId, musicDto, usernameDto);
        });
    }
}
