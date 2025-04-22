package com.example.myEcommerceAPI.repository;

import org.springframework.stereotype.Repository;

import com.example.myEcommerceAPI.entity.Tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String Name);
}
