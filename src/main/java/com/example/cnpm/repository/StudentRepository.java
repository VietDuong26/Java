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
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Page<Student> findByTen(String name,Pageable pageable);

    Page<Student> findByMasinhvien(String code, Pageable pageable);
    Student findByMasinhvien(String code);
    Student findByTen(String ten);
    List<Student> findByLop(String lop);
    @Query("select st from Student st where exists (select sb from Subject sb where sb.tenmonhoc=:name)")
    Set<Student> findStudentBySubjectName(@Param("name") String name);
    @Query("select st from Student st where st.lop=:class and exists(select sb from Subject sb where sb.tenmonhoc=:name)")
    Set<Student> findStudentByClassAndSub(@Param("class")String classes,@Param("name")String subject_name);
    @Query("select sb from Subject sb where exists(select st from Student st where st.masinhvien=?1) and sb.tenmonhoc=?2")
    Set<Subject> findAllSubjectsByStudentCode(String msv,String subject_name);

    @Query(value = "select count(*) from student_subject st_sb where st_sb.student_masinhvien=?1 and student_subject.subject_id=?2",nativeQuery = true)
    long checkIfSubjectExist(String msv,long subject_id);


    List<Student> findStudentsByLop(String classes);
}
