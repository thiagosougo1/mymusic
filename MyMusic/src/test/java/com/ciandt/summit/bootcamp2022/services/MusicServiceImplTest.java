package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.Data;
import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.ValidateSizeNameException;
import com.ciandt.summit.bootcamp2022.utils.TokenService;
import com.ciandt.summit.bootcamp2022.utils.exceptions.InvalidLogDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
public class MusicServiceImplTest {

    @InjectMocks
    private MusicServiceImpl musicService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private TokenService tokenService;

    private UsernameDto usernameDto;

    static List<Music> nirvanaMusic = Arrays.asList(
            new Music("", "Smells Like Teen Spirit", new Artist("", "Nirvana", new ArrayList<>()), new ArrayList<>())
    );

    @BeforeEach
    public void beforeEach() {

        MockitoAnnotations.openMocks(this);

        usernameDto = new UsernameDto(
                new Data("joao", "ZIIKXbvDLcs30v/7nzGxxwRHW6AHBEp94vEtSCFGZqK8ojfKYv39J92PI5Tw9EIHZLhtGJUaY2KZHwysFlfWvA=="));
    }

    private static Stream<Arguments> getMusicWithSuccess() {
        return Stream.of(
                Arguments.of("1234", nirvanaMusic)
        );
    }

    @ParameterizedTest
    @MethodSource("getMusicWithSuccess")
    void getMusicWithSuccess(String name, List<Music> expected) {

        Mockito.doReturn(nirvanaMusic)
                .when(musicRepository)
                .getAllMusicArtist(Mockito.anyString());

        List<Music> result = musicService.getMusics(name, usernameDto);

        Assertions.assertArrayEquals(expected.toArray(), result.toArray());
    }

    @ParameterizedTest
    @CsvSource({"u", "n", "p"})
    void getArtistLessThanThreeCharacters(String name) {

        Assertions.assertThrows(ValidateSizeNameException.class, () -> {
            musicService.getMusics(name, usernameDto);
        });
    }

    @Test
    void shouldThrowInvalidLogDataException() {

        Mockito.doThrow(InvalidLogDataException.class).when(tokenService).validateToken(Mockito.any(UsernameDto.class));

        Assertions.assertThrows(InvalidLogDataException.class, () -> {
            musicService.getMusics("Nirvana", new UsernameDto());
        });
    }
}
