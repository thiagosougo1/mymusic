package com.ciandt.summit.bootcamp2022.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Artistas")
public class Artist {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="Id")
    private String id;

    @Column(name="Nome")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "artistId",cascade = CascadeType.ALL)
    private List<Music> music;

}
