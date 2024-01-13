package com.example.cnpm.service.impl;

import com.example.cnpm.entity.Subject;
import com.example.cnpm.repository.SubjectRepository;
import com.example.cnpm.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    

    @Override
    public Page<Subject> getAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Set<Subject> getAll() {
        return new HashSet<Subject>(subjectRepository.findAll());
    }

    @Override
    public Page<Subject> findByName(String name,Pageable pageable) {
        return subjectRepository.findByTenmonhoc(name,pageable);
    }

    @Override
    public Page<Subject> findByCode(String code,Pageable pageable) {
        return subjectRepository.findByKihieu(code,pageable);
    }

    @Override
    public void insert(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public void delete(long id) {
        subjectRepository.delete(subjectRepository.findById(id).get());
    }

    @Override
    public Subject findById(long id) {
        return subjectRepository.findById(id).get();
    }

    @Override
    public Subject findByName(String name) {
        return subjectRepository.findByTenmonhoc(name);
    }


}
