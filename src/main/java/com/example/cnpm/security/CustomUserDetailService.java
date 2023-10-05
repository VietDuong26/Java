package com.example.cnpm.security;
import com.example.cnpm.repository.StudentRepository;
import com.example.cnpm.entity.Role;
import com.example.cnpm.repository.TeacherRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.session.StandardSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailService implements UserDetailsService {
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    public CustomUserDetailService( StudentRepository studentRepository,TeacherRepository teacherRepository){

        this.studentRepository= studentRepository;
        this.teacherRepository=teacherRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.cnpm.entity.Student student = studentRepository.findByMasinhvien(email);
        com.example.cnpm.entity.Teacher teacher= teacherRepository.findByMagiaovien(email);
        if(student!=null){
            return  new org.springframework.security.core.userdetails.User(student.getMasinhvien(),
                    student.getPassword(),mapRoleToAuthority(student.getRole()));
        }
        if(teacher!=null){
            return  new org.springframework.security.core.userdetails.User(teacher.getMagiaovien(),
                    teacher.getPassword(),mapRoleToAuthority(teacher.getRole()));
        }
        return null;
    }
    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Collection<Role> roles){
        Collection<? extends GrantedAuthority> mapRole =roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return mapRole;
    }
}
