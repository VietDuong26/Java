package com.example.cnpm.mapper;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student convertToEntity(StudentDTO studentDTO){
        return Student.builder().id(studentDTO.getId())
                .ten(studentDTO.getTen())
                .lop(studentDTO.getLop())
                .masinhvien(studentDTO.getMasinhvien())
                .khoa(studentDTO.getKhoa())
                .subjects(studentDTO.getSubjects())
                .build();
    }
    public StudentDTO convertToDTO(Student student){
        return StudentDTO.builder()
                .id(student.getId())
                .ten(student.getTen())
                .lop(student.getLop())
                .masinhvien(student.getMasinhvien())
                .khoa(student.getKhoa())
                .subjects(student.getSubjects())
                .build();
    }
}
