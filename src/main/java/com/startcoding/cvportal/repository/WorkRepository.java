package com.startcoding.cvportal.repository;

import com.startcoding.cvportal.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface WorkRepository extends JpaRepository<Work,Long> {


    Collection<Work> findAllBycv_id(Long id);
}
