package com.startcoding.cvportal.repository;

import com.startcoding.cvportal.models.JuniorWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JuniorWorkRepository extends JpaRepository<JuniorWork,Long> {
    Optional<JuniorWork> findByLink(String name);
}
