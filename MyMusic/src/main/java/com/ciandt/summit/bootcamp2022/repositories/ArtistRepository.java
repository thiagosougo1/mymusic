package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {
}
