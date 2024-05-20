package com.example.cnpm.service.impl;

import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.*;
import com.example.cnpm.mapper.StudentMapper;
import com.example.cnpm.mapper.TeacherMapper;
import com.example.cnpm.repository.*;
import com.example.cnpm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncode;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    PhancongRepository phancongRepository;
    @Autowired
    TeacherMapper mapper;
    public TeacherServiceImpl(TeacherRepository teacherRepository, RoleRepository roleRepository
            , PasswordEncoder passwordEncode
    ){
        this.passwordEncode=passwordEncode;
        this.teacherRepository=teacherRepository;
        this.roleRepository=roleRepository;
    }
    private Role checkRoleExist(){
        Role role= new Role();
        role.setName("ROLE_TEACHER");
        return roleRepository.save(role);
    }

    @Override
    public Page<TeacherDTO> getAll(Pageable pageable) {
        return teacherRepository.findAll(pageable).map(x->mapper.convertToDTO(x));
    }
    @Override
    public Page<TeacherDTO> findByName(String name,Pageable pageable ) {
        return teacherRepository.findByTen(name,pageable).map(x->mapper.convertToDTO(x));
    }
    @Override
    public Page<TeacherDTO> findByCode(String code,Pageable pageable) {
        return teacherRepository.findByMagiaovien(code,pageable).map(x->mapper.convertToDTO(x));
    }
    @Override
    public TeacherDTO findByCode(String code) {
        return mapper.convertToDTO(teacherRepository.findByMagiaovien(code));
    }
    @Override
    public void insert(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setTen(teacherDTO.getTen());
        teacher.setMagiaovien(teacherDTO.getMagiaovien());
        teacher.setPassword(passwordEncode.encode(teacherDTO.getPassword()));
        Role role= roleRepository.findByName("ROLE_TEACHER");
        if(role==null){
            role= checkRoleExist();
        }
        Set<Role> roles= new HashSet<>();
        roles.add(role);
        teacher.setRole(roles);
        teacherRepository.save(teacher);
    }
    @Override
    public void delete(long id) {
        teacherRepository.delete(teacherRepository.findById(id).get());
    }
    @Override
    public TeacherDTO findById(long id) {
        return mapper.convertToDTO(teacherRepository.findById(id).get());
    }

    @Override
    public Set<TeacherDTO> findAll() {
        return new HashSet<TeacherDTO>(teacherRepository.findAll().stream().map(x->mapper.convertToDTO(x)).toList());
    }
    @Override
    public Teacher findByName(String name) {
        return teacherRepository.findByTen(name);
    }

    @Override
    public void nhapdiem(String sub, String[] msv, Float[] qtr, Float[] thi) {
        for(int i=0;i<msv.length;i++){
            Point point=pointRepository.findByMaMHAndAndMaSV(subjectRepository.findByTenmonhoc(sub),studentRepository.findByMasinhvien(msv[i]));
            point.setDiemThanhPhan(qtr[i]);
            point.setDiemThi(thi[i]);
            pointRepository.save(point);
        }
    }

    @Override
    public Set<String> findAllTeacher() {
        List<Teacher> list=teacherRepository.findAll();
        Set<String> names=new HashSet<>();
        for (Teacher teacher: list
             ) {
            names.add(teacher.getTen());
        }
        return names;
    }

    @Override
    public Set<String> findAllSubject() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Set<PhanCong> list=phancongRepository.findPhanCongsByMaGV(teacherRepository.findByMagiaovien(authentication.getName())).stream().collect(Collectors.toSet());
        Set<String> list1=new HashSet<>();
        for (PhanCong x: list
             ) {
            list1.add(x.getMaMH().getTenmonhoc());
        }
        return list1;
    }

    @Override
    public Set<Student> findAllStudent(String classes, String maMH) {
        List<Point> list1=pointRepository.findByMaMH(subjectRepository.findByTenmonhoc(maMH));
        Set<Student> list2= new HashSet<>();
        for (Point point:list1
             ) {
            if(point.getMaSV().getLop().equals(classes.toString())){
                list2.add(point.getMaSV());
            }
        }
        return list2;
    }


}
