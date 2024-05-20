package com.example.cnpm.repository;

import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Page<Teacher> findByTen(String name, Pageable pageable);

    Page<Teacher> findByMagiaovien(String code, Pageable pageable);
    Teacher findByMagiaovien(String code);

    Teacher findByTen(String name);
}
