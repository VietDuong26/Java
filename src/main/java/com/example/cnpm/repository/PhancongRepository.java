package com.example.cnpm.repository;

import com.example.cnpm.entity.PhanCong;
import com.example.cnpm.entity.Subject;
import com.example.cnpm.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhancongRepository extends JpaRepository<PhanCong,Long> {
    PhanCong findByMaGVAndMaMH(Teacher byName, Subject subject);
    List<PhanCong> findPhanCongsByMaGV(Teacher teacher);
}
