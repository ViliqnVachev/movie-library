package com.vvachev.movielibrary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vvachev.movielibrary.model.entity.CategoryEntity;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	Optional<CategoryEntity> findByName(CategoryEnum name);

}
