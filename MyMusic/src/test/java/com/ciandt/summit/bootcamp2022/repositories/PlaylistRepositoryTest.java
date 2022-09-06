package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class PlaylistRepositoryTest {

    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    public void shouldStorePlayslist() {
        List<Music> musicList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        Playlist playlist = playlistRepository.save(new Playlist(musicList,userList));
        Playlist playlist1 = playlistRepository.getById(playlist.getId());
        Assertions.assertEquals(playlist.getId(),playlist1.getId());
    }



}
