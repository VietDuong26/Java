package com.example.cnpm.service.impl;

import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.Teacher;
import com.example.cnpm.mapper.TeacherMapper;
import com.example.cnpm.repository.RoleRepository;
import com.example.cnpm.repository.StudentRepository;
import com.example.cnpm.repository.TeacherRepository;
import com.example.cnpm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncode;
    @Autowired
    TeacherRepository teacherRepository;
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
    private Role checkRoleExist1(){
        Role role= new Role();
        role.setName("ROLE_ADMIN");
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
        teacher.setRole(Arrays.asList(role));
        teacherRepository.save(teacher);
    }

    @Override
    public void delete(long id) {
        teacherRepository.delete(teacherRepository.findById(id).get());
        Role role= roleRepository.findByName("ROLE_TEACHER");
        Role role1 = roleRepository.findByName("ROLE_ADMIN");
        if(role==null){
            role= checkRoleExist();
        }
        if(role1==null){
            role1=checkRoleExist1();
        }
    }

    @Override
    public TeacherDTO findById(long id) {
        return mapper.convertToDTO(teacherRepository.findById(id).get());
    }
    @Override
    public void insertAdmin(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setTen(teacherDTO.getTen());
        teacher.setMagiaovien(teacherDTO.getMagiaovien());
        teacher.setPassword(passwordEncode.encode(teacherDTO.getPassword()));
        Role role= roleRepository.findByName("ROLE_ADMIN");
        if(role==null){
            role= checkRoleExist1();
        }
        teacher.setRole(Arrays.asList(role));
        teacherRepository.save(teacher);
    }

    @Override
    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll().stream().map(x->mapper.convertToDTO(x)).toList();
    }
}
