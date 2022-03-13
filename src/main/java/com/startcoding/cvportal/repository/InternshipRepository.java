package com.startcoding.cvportal.repository;

import com.startcoding.cvportal.models.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternshipRepository extends JpaRepository<Internship,Long> {
    Optional<Internship> findByLink(String name);
}
