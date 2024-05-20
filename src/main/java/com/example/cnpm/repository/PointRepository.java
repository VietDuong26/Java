package com.example.cnpm.repository;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.entity.Point;
import com.example.cnpm.entity.Student;
import com.example.cnpm.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point,Long> {
Point findByMaMHAndAndMaSV(Subject mamh, Student masv);

    List<Point> findByMaSV(Student student);


    List<Point> findByMaMH(Subject maMH);
}
