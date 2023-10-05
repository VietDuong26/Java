package com.example.cnpm.dto;

import lombok.*;

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
}
