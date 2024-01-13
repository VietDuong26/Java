package com.example.cnpm.repository;

import com.example.cnpm.entity.Student;
import com.example.cnpm.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Page<Subject> findByTenmonhoc(String name, Pageable pageable);

    Page<Subject> findByKihieu(String code, Pageable pageable);
    Subject findByTenmonhoc(String name);
    @Query(value = "select count(*) from subject sb where sb.id=?1",nativeQuery = true)
    long check(long id);
}
