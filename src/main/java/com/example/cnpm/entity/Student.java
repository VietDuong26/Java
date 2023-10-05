package com.example.cnpm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private String ten;
    private String masinhvien;
    private String lop;
    private String khoa;
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="student_teacher",joinColumns = {@JoinColumn(name="student_masinhvien",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="teacher_id",referencedColumnName = "id")})
    private List<Teacher> teachers= new ArrayList<>();
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="student_subject",joinColumns = {@JoinColumn(name="student_masinhvien",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="subject_id",referencedColumnName = "id")})
    private List<Subject> subjects= new ArrayList<>();
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="student_role",joinColumns = {@JoinColumn(name="student_masinhvien",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="role_id",referencedColumnName = "id")})
    private List<Role> role= new ArrayList<>();
    private String password;
}
