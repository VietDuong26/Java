package com.example.cnpm.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "role")
    private Set<Student> student;
    @ManyToMany(mappedBy = "role")
    private Set<Teacher> teacher;
    @ManyToMany(mappedBy = "role")
    private Set<Admin> admin;
}
