package com.example.cnpm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubjectDTO {
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
    private String note;
}
