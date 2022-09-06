package com.ciandt.summit.bootcamp2022.dto;

import com.ciandt.summit.bootcamp2022.entity.Artist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MusicDto {

    private String id;
    private String name;
    private Artist artist;
    
}
