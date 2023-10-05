package com.example.cnpm.mapper;

import com.example.cnpm.dto.SubjectDTO;
import com.example.cnpm.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
    public Subject convertToEntity(SubjectDTO subjectDTO){
        return Subject.builder()
                .id(subjectDTO.getId())
                .diemthanhphan(subjectDTO.getDiemthanhphan())
                .diemthi(subjectDTO.getDiemthi())
                .heso(subjectDTO.getHeso())
                .kihieu(subjectDTO.getKihieu())
                .tenmonhoc(subjectDTO.getTenmonhoc())
                .sotinchi(subjectDTO.getSotinchi())
                .tbchp(subjectDTO.getTbchp())
                .diemchu(subjectDTO.getDiemchu())
                .diemso(subjectDTO.getDiemso())
                .build();
    }
    public SubjectDTO convertToDTO(Subject subject){
        return SubjectDTO.builder()
                .id(subject.getId())
                .diemthanhphan(subject.getDiemthanhphan())
                .diemthi(subject.getDiemthi())
                .heso(subject.getHeso())
                .kihieu(subject.getKihieu())
                .tenmonhoc(subject.getTenmonhoc())
                .sotinchi(subject.getSotinchi())
                .tbchp(subject.getTbchp())
                .diemchu(subject.getDiemchu())
                .diemso(subject.getDiemso())
                .build();
    }
}
