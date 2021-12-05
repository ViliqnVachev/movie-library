package com.vvachev.movielibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vvachev.movielibrary.model.entity.PictureEntity;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

}
