package com.example.cnpm.service.impl;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.entity.*;
import com.example.cnpm.mapper.StudentMapper;
import com.example.cnpm.mapper.TeacherMapper;
import com.example.cnpm.repository.*;
import com.example.cnpm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncode;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentMapper mapper;
    @Autowired
    PointRepository pointRepository;
    public StudentServiceImpl(StudentRepository studentRepository,RoleRepository roleRepository
            , PasswordEncoder passwordEncode
    ){
        this.passwordEncode=passwordEncode;
        this.studentRepository=studentRepository;
        this.roleRepository=roleRepository;
    }
    private Role checkRoleExist(){
        Role role= new Role();
        role.setName("ROLE_STUDENT");
        return roleRepository.save(role);
    }
    private Role checkRoleExist1(){
        Role role= new Role();
        role.setName("ROLE_TEACHER");
        return roleRepository.save(role);
    }

    @Override
    public Page<StudentDTO> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable).map(x->mapper.convertToDTO(x));
    }
    @Override
    public Page<StudentDTO> findByName(String name,Pageable pageable) {
        Page<StudentDTO> studentDTOS= studentRepository.findByTen(name,pageable).map(x->mapper.convertToDTO(x));
        return studentDTOS;
    }
    @Override
    public Page<StudentDTO> findByCode(String code,Pageable pageable) {
        Page<StudentDTO> studentDTOS= studentRepository.findByMasinhvien(code,pageable).map(x->mapper.convertToDTO(x));
        return studentDTOS;
    }
    @Override
    public StudentDTO findByCode(String code) {
        return mapper.convertToDTO(studentRepository.findByMasinhvien(code));
    }
    @Override
    public StudentDTO findById(long id) {
        return mapper.convertToDTO(studentRepository.findById(id).get());
    }
    @Override
    public void insert(StudentDTO studentDTO) {
        Student student= new Student();
        student.setId(studentDTO.getId());
        student.setTen(studentDTO.getTen());
        student.setLop(studentDTO.getLop());
        student.setMasinhvien(studentDTO.getMasinhvien());
        student.setKhoa(studentDTO.getKhoa());
        student.setPassword(passwordEncode.encode(studentDTO.getPassword()));
        Role role=roleRepository.findByName("ROLE_STUDENT");
        if(role==null){
            role=checkRoleExist();
        }
        Set<Role> roles= new HashSet<>();
        roles.add(role);
        student.setRole(roles);
        studentRepository.save(student);
        }
    @Override
    public void delete(long id) {
        Student student = studentRepository.findById(id).get();
    }

    @Override
    public StudentDTO findByName(String name) {
        return mapper.convertToDTO(studentRepository.findByTen(name));
    }
    @Override
    public Set<StudentDTO> findByLop(String lop) {
        return new HashSet<StudentDTO>(studentRepository.findByLop(lop).stream().map(x->mapper.convertToDTO(x)).toList());
    }
    @Override
    public Set<String> findAllClasses() {
        List<Student> list1= studentRepository.findAll();
        Set<String> list= new HashSet<>();
        for(int i=0;i<list1.size();i++){
            list.add(list1.get(i).getLop());
        }
        return list;
    }

    @Override
    public Set<StudentDTO> findStudentByTenmonhoc(String subject) {
        return new HashSet<StudentDTO>(studentRepository.findStudentBySubjectName(subject).stream().map(x->mapper.convertToDTO(x)).toList());
    }

    @Override
    public List<StudentDTO> findStudentsByClass(String classes) {
        List<Student> list= studentRepository.findStudentsByLop(classes);
        return list.stream().map(x->mapper.convertToDTO(x)).toList();
    }

    @Override
    public List<Point> getAllPoints() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        List<Point> point= pointRepository.findByMaSV(studentRepository.findByMasinhvien(authentication.getName()));
        return point;
    }


}


