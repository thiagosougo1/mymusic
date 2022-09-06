package com.ciandt.summit.bootcamp2022.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Musicas")
public class Music {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="Id")
    private String id;

    @Column(name="Nome")
    private String name;

    @ManyToOne
    @JoinColumn(name="ArtistaId")
    private Artist artistId;

    @JsonIgnore
    @ManyToMany(mappedBy = "musicList")
    private List<Playlist> playlist = new ArrayList<>();

}
