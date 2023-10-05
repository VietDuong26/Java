package com.example.cnpm.service.impl;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.dto.SubjectDTO;
import com.example.cnpm.entity.Role;
import com.example.cnpm.entity.Student;
import com.example.cnpm.entity.Subject;
import com.example.cnpm.mapper.StudentMapper;
import com.example.cnpm.mapper.SubjectMapper;
import com.example.cnpm.repository.RoleRepository;
import com.example.cnpm.repository.StudentRepository;
import com.example.cnpm.repository.SubjectRepository;
import com.example.cnpm.repository.TeacherRepository;
import com.example.cnpm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private SubjectMapper subjectMapper;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentMapper mapper;
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
    private Subject checkSubjectExist(SubjectDTO subjectDTO){
        Subject subject= new Subject();
        subject.setTenmonhoc(subjectDTO.getTenmonhoc());
        subject.setKihieu(subjectDTO.getKihieu());
        subject.setSotinchi(subjectDTO.getSotinchi());
        subject.setHeso(subjectDTO.getHeso());
        return subjectRepository.save(subject);
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
        student.setTen(studentDTO.getTen());
        student.setLop(studentDTO.getLop());
        student.setMasinhvien(studentDTO.getMasinhvien());
        student.setKhoa(studentDTO.getKhoa());
        student.setPassword(passwordEncode.encode(studentDTO.getPassword()));
        Role role=roleRepository.findByName("ROLE_STUDENT");
        if(role==null){
            role=checkRoleExist();
        }
        student.setRole(Arrays.asList(role));
        studentRepository.save(student);
    }

    @Override
    public void delete(long id) {
        Student student=studentRepository.findById(id).get();
        String role1= student.getRole().get(0).getName();
        roleRepository.delete(roleRepository.findByName(role1));
        List<Role>list = roleRepository.findAll();
        studentRepository.delete(student);
        Role role = roleRepository.findByName("ROLE_STUDENT");
        if(role==null){
            role=checkRoleExist();
        }
    }

    @Override
    public Page<SubjectDTO> findAll(String code) {
        List<Subject> subjects= studentRepository.findByMasinhvien(code).getSubjects();
        List<SubjectDTO> list=subjects.stream().map(x->subjectMapper.convertToDTO(x)).toList();
       Page<SubjectDTO> page= new PageImpl<>(list);
       return page;
    }

    @Override
    public void dang_ki_mon_hoc(List<SubjectDTO> list) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student0=studentRepository.findByMasinhvien(authentication.getName());
        List<Subject> subjects = subjectRepository.findAll();
        List<Subject> list1= student0.getSubjects();
        if(subjects.size()!=0) {
            for (int i = 0; i < list.size(); i++) {
                if (subjects.get(i).getKihieu() == null) {
                    list1.add(subjectMapper.convertToEntity(list.get(i)));
                } else {
                    list.remove(i);
                }
            }
        }else{
            for (int i = 0; i < list.size(); i++) {
                    list1.add(subjectMapper.convertToEntity(list.get(i)));
        }
        }
        Student student= new Student();
        student.setId(student0.getId());
        student.setTen(student0.getTen());
        student.setLop(student0.getLop());
        student.setKhoa(student0.getKhoa());
        student.setMasinhvien(student0.getMasinhvien());
        student.setPassword(student0.getPassword());
        student.setRole(student0.getRole());
        student.setSubjects(list1);
        studentRepository.save(student);
    }

    @Override
    public StudentDTO findByName(String name) {
        return mapper.convertToDTO(studentRepository.findByTen(name));
    }

    @Override
    public List<StudentDTO> findByLop(String lop) {
        return studentRepository.findByLop(lop).stream().map(x->mapper.convertToDTO(x)).toList();
    }

    @Override
    public Set<String> findAllClasses() {
        List<StudentDTO> list1= studentRepository.findAll().stream().map(x->mapper.convertToDTO(x)).toList();
        Set<String> list= new HashSet<>();
        for(int i=0;i<list1.size();i++){
            list.add(list1.get(i).getLop());
        }
        return list;
    }

    @Override
    public List<StudentDTO> findStudentByTenmonhoc(String subject) {
        return studentRepository.findStudentBySubjectName(subject).stream().map(x->mapper.convertToDTO(x)).toList();
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(x->mapper.convertToDTO(x)).toList();
    }
}

