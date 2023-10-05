package com.example.cnpm.dto;

import com.example.cnpm.entity.Subject;
import lombok.*;

import java.util.List;

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
    private List<Subject> subjects;
}
