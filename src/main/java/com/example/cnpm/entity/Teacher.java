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
    @OneToMany(mappedBy = "maGV")
    private Set<PhanCong> phancong;
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="teacher_role",joinColumns = {@JoinColumn(name="teacher_id",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="role_id",referencedColumnName = "id")})
    private Set<Role> role= new HashSet<>();
    private String password;
}
