package com.example.cnpm.service.impl;


import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.entity.*;
import com.example.cnpm.mapper.StudentMapper;
import com.example.cnpm.repository.*;
import com.example.cnpm.service.AdminService;
import com.example.cnpm.service.StudentService;
import com.example.cnpm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository repository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    RoleRepository roleRepository;
    private PasswordEncoder passwordEncode;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    PhancongRepository phancongRepository;
    public AdminServiceImpl(AdminRepository adminRepository, RoleRepository roleRepository
            , PasswordEncoder passwordEncode
    ){
        this.passwordEncode=passwordEncode;
        this.repository=adminRepository;
        this.roleRepository=roleRepository;
    }
    private Role checkRoleExist1(){
        Role role= new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
    @Override
    public void insert(Admin admin) {
        Role role= roleRepository.findByName("ROLE_ADMIN");
        if(role==null){
            role= checkRoleExist1();
        }
        Set<Role> roles= new HashSet<>();
        roles.add(role);
        admin.setRole(roles);
        admin.setPassword(passwordEncode.encode(admin.getPassword()));
        repository.save(admin);
    }

    @Override
    public void delete(long id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public void dangkimonhoc(String classes, String sub,String teacher) {
        List<StudentDTO> student_list= studentService.findStudentsByClass(classes);
        Subject subject= subjectRepository.findByTenmonhoc(sub);
        for (StudentDTO dto: student_list
             ) {
            if(pointRepository.findByMaMHAndAndMaSV(subject,studentMapper.convertToEntity(dto))==null){
                Point point= new Point();
                point.setMaMH(subject);
                point.setMaSV(studentMapper.convertToEntity(dto));
                pointRepository.save(point);
            }
        }
        PhanCong pclist=phancongRepository.findByMaGVAndMaMH(teacherService.findByName(teacher),subject);
        if(pclist==null){
            PhanCong phanCong= new PhanCong();
            phanCong.setMaGV(teacherService.findByName(teacher));
            phanCong.setMaMH(subject);
            phancongRepository.save(phanCong);
        }
    }
}
