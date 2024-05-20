package com.example.cnpm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
   @OneToMany(mappedBy = "maMH")
    private Set<PhanCong> phancong;
    @OneToMany(mappedBy = "maMH")
    private List<Point> diem;

}
