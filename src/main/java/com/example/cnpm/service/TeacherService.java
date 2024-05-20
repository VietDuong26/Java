package com.example.cnpm.service;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.Student;
import com.example.cnpm.entity.Subject;
import com.example.cnpm.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface TeacherService {
    Page<TeacherDTO> getAll(Pageable pageable);
    Page<TeacherDTO> findByName(String name,Pageable pageable);
    Page<TeacherDTO> findByCode(String code,Pageable pageable);
    TeacherDTO findByCode(String code);
    void insert(TeacherDTO teacherDTO);
    void delete(long id);

    TeacherDTO findById(long id);


    Set<TeacherDTO> findAll();
    Teacher findByName(String name);

    void nhapdiem( String sub, String[] msv, Float[] qtr, Float[] thi);

    Set<String> findAllTeacher();

   Set<String>findAllSubject();
    Set<Student>findAllStudent(String classes,String maMH);
}
