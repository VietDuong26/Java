package com.example.cnpm.service;

import com.example.cnpm.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface SubjectService {
    Page<Subject> getAll(Pageable pageable);
    Set<Subject> getAll();
    Page<Subject> findByName(String name,Pageable pageable);
    Page<Subject> findByCode(String code,Pageable pageable);
    void insert(Subject subject);
    void delete(long id);

    Subject findById(long id);
    Subject findByName(String name);

}
