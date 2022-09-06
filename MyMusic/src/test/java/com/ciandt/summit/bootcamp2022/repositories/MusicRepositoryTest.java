package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.Playlist;
import com.ciandt.summit.bootcamp2022.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class MusicRepositoryTest {

    @Autowired
    MusicRepository musicRepository;

    @Test
    public void shouldHaveTheBeatlesMusic() {
        List<Music> listMusic = musicRepository.getAllMusicArtist("The Beatles");
        Assertions.assertFalse(listMusic.isEmpty());
    }



}
