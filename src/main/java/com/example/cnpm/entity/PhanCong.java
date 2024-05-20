package com.example.cnpm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name="phancong")
public class PhanCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="giangvien")
    private Teacher maGV;
    @ManyToOne
    @JoinColumn(name="monhoc")
    private Subject maMH;
}
