package com.example.cnpm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name="point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float diemThi;
    private float diemThanhPhan;
    @ManyToOne
    @JoinColumn(name="masinhvien")
    private Student maSV;
    @ManyToOne
    @JoinColumn(name="maMH")
    private Subject maMH;
}
