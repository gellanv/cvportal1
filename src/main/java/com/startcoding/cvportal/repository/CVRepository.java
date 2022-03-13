package com.startcoding.cvportal.repository;

import com.startcoding.cvportal.models.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<CV,Long> {
}
