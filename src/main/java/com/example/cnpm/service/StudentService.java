package com.example.cnpm.service;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.dto.SubjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface StudentService {
    Page<StudentDTO> getAll(Pageable pageable);
    Page<StudentDTO> findByName(String name,Pageable pageable);
    Page<StudentDTO> findByCode(String code,Pageable pageable);
    StudentDTO findByCode(String code);
    StudentDTO findById(long id);
    void insert(StudentDTO studentDTO);
    void delete(long id);
    Page<SubjectDTO> findAll(String code);
    void dang_ki_mon_hoc(List<SubjectDTO> list);
    StudentDTO findByName(String name);
    List<StudentDTO> findByLop(String lop);

    Set<String> findAllClasses();

    List<StudentDTO> findStudentByTenmonhoc(String subject);
    List<StudentDTO> findAll();
}
