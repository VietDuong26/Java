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
@Table(name="teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private String ten;
    private String magiaovien;
    @ManyToMany(mappedBy = "teachers")
    private List<Student> students;
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="teacher_subject",joinColumns = {@JoinColumn(name="teacher_id",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="subject_id",referencedColumnName = "id")})
    private List<Subject> subjects= new ArrayList<>();
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="teacher_role",joinColumns = {@JoinColumn(name="teacher_id",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="role_id",referencedColumnName = "id")})
    private List<Role> role= new ArrayList<>();
    private String password;
}
