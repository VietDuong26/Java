package com.example.cnpm.mapper;

import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public Teacher convertToEntity(TeacherDTO teacherDTO){
        return Teacher.builder()
                .id(teacherDTO.getId())
                .ten(teacherDTO.getTen())
                .magiaovien(teacherDTO.getMagiaovien())
                .build();
    }
    public TeacherDTO convertToDTO(Teacher teacher){
        return TeacherDTO.builder()
                .id(teacher.getId())
                .ten(teacher.getTen())
                .magiaovien(teacher.getMagiaovien())
                .build();
    }
}
