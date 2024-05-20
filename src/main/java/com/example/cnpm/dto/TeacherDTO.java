package com.example.cnpm.dto;

import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.Student;
import com.example.cnpm.entity.Subject;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeacherDTO {
    private long id;
    private String ten;
    private String magiaovien;
    private String password;
    private Set<Subject> subjects;
    private Set<Role> roles;
}
