package com.example.cnpm.dto;

import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.Subject;
import com.example.cnpm.entity.Teacher;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentDTO {
    private long id;
    private String ten;
    private String masinhvien;
    private String lop;
    private String password;
    private String khoa;
    private Set<Subject> subjects;
    private Set<Role> roles;
}
