package com.example.cnpm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="subject")
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private String tenmonhoc;
    private String kihieu;
    private int sotinchi;
    private int heso;
    private float diemthanhphan;
    private float diemthi;
    private float tbchp;
    private float diemso;
    private String diemchu;
    @ManyToMany(mappedBy = "subjects")
    private List<Student> sinhvien;
   @ManyToMany(mappedBy = "subjects")
    private List<Teacher> giaovien;
}
