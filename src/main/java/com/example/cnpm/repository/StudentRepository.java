package com.example.cnpm.repository;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.entity.Student;
import com.example.cnpm.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Page<Student> findByTen(String name,Pageable pageable);

    Page<Student> findByMasinhvien(String code, Pageable pageable);
    Student findByMasinhvien(String code);
    Student findByTen(String ten);
    List<Student> findByLop(String lop);
    @Query("select st from Student st where exists (select sb from Subject sb where sb.tenmonhoc=:name)")
    List<Student> findStudentBySubjectName(@Param("name") String name);


}
