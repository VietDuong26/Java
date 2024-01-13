package com.example.cnpm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="admin_role",joinColumns = {@JoinColumn(name="admin_id",referencedColumnName = "id")}
            ,inverseJoinColumns ={@JoinColumn(name="role_id",referencedColumnName = "id")})
    private Set<Role> role= new HashSet<>();
}
