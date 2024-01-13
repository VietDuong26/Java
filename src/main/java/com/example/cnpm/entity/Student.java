package com.example.cnpm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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
    private String password;
    private String lop;
    private String khoa;
    @OneToMany(mappedBy = "maSV")
    private List<Point> diem;
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="student_role",joinColumns = {@JoinColumn(name="student_id",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="role_id",referencedColumnName = "id")})
    private Set<Role> role= new HashSet<>();
}
