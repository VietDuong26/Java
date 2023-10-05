package com.example.cnpm.controller;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.dto.SubjectDTO;
import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.Subject;
import com.example.cnpm.entity.Teacher;
import com.example.cnpm.mapper.SubjectMapper;
import com.example.cnpm.service.StudentService;
import com.example.cnpm.service.SubjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    SubjectMapper subjectMapper;
    @GetMapping("/Student")
    String getAll(Model model, @RequestParam(name="page") Optional<Integer> page,
                  @RequestParam(name="size") Optional<Integer> size,
                  @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(4);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<StudentDTO> list =studentService.getAll(pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_student",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "student";
    }
    @GetMapping(value="/insertStudent",name="/insertStudent")
    String show_insert_form(Model model){
        model.addAttribute("student",new StudentDTO());
        return "insertStudent";
    }
    @PostMapping(value="/insertStudent",name="/insertStudent")
    String insert_S(Model model, @ModelAttribute("student")StudentDTO studentDTO){
        studentService.insert(studentDTO);
        return "redirect:/Student";
    }
    @RequestMapping("/deleteStudent/{id}")
    public String delete(@PathVariable("id")long id){
        studentService.delete(id);
        return "redirect:/Student";
    }
    @RequestMapping ("/findStudentByName/{name}")
    public String findByName(Model model,@PathVariable("name")String name,@RequestParam(name="page") Optional<Integer> page,
                             @RequestParam(name="size") Optional<Integer> size,
                             @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(3);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<StudentDTO> student_list= studentService.findByName(name,pageable);
        int totalPage=student_list.getTotalPages();
        long totalItem= student_list.getTotalElements();
        model.addAttribute("list_student",student_list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "student";
    }
    @GetMapping(name="/editStudent",value="/editStudent/{id}")
    public String edit(Model model,@PathVariable("id")long id){
        model.addAttribute("student",studentService.findById(id));
        return "editStudent";
    }
    @PostMapping(value="/editStudent",name="/editStudent")
    public String edit(@ModelAttribute("student") StudentDTO studentDTO) throws IOException {
        studentService.insert(studentDTO);
        return "redirect:/Student";
    }
    @RequestMapping ("/findStudentByCode/{code}")
    public String findByCode(Model model,@PathVariable("code")String code,@RequestParam(name="page") Optional<Integer> page,
                             @RequestParam(name="size") Optional<Integer> size,
                             @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(3);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<StudentDTO> student_list= studentService.findByCode(code,pageable);
        int totalPage=student_list.getTotalPages();
        long totalItem= student_list.getTotalElements();
        model.addAttribute("list_student",student_list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "student";
    }
    @GetMapping("/login")
    String login(Model model){
        model.addAttribute("student",new StudentDTO());
        model.addAttribute("teacher",new TeacherDTO());
        return "login";
    }
    @GetMapping("/student_homepage")
    String home(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        StudentDTO studentDTO= studentService.findByCode(authentication.getName());
        List<Subject> list = studentDTO.getSubjects();
        List<SubjectDTO> list1=list.stream().map(x-> subjectMapper.convertToDTO(x)).toList();
        for (SubjectDTO subjectDTO: list1
        ) {
            float tbchp=subjectDTO.getTbchp();
            if(tbchp>=0 && tbchp<=2.4){
                subjectDTO.setDiemso(1);
            }else if(tbchp>=2.5 && tbchp<=4.9){
                subjectDTO.setDiemso(2);
            }else if(tbchp>=5.0 && tbchp<=7.4){
                subjectDTO.setDiemso(3);
            }else if(tbchp>=7.5 && tbchp <=10.0){
                subjectDTO.setDiemso(4);
            }
            if(tbchp<=3.9){
                subjectDTO.setDiemchu("F");
            }else if(tbchp>=4.0 && tbchp<=5.4){
                subjectDTO.setDiemchu("D");
            }else if(tbchp>=5.5 && tbchp<=6.9){
                subjectDTO.setDiemchu("C");
            }else if(tbchp>=7.0 && tbchp<=8.4){
                subjectDTO.setDiemchu("B");
            }else if(tbchp>=8.5 && tbchp <=10){
                subjectDTO.setDiemchu("A");
            }
            if(tbchp>=4.0){
                subjectDTO.setNote("Passed");
            }else{
                subjectDTO.setNote("Not Passed");
            }
        }
        model.addAttribute("list",list1);
        long tong_so_tin_chi=0;
        long so_tin_chi_tich_luy=0;
        for(int i=0;i<list.size();i++){
            tong_so_tin_chi+=list.get(i).getSotinchi();
        }
        model.addAttribute("student",studentDTO);
        model.addAttribute("tongsotinchi",tong_so_tin_chi);
        return "student1";
    }
    @RequestMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
    @GetMapping("/findAllPoint")
    String index(Model model){
        return "student";
    }
    @GetMapping("/dangkimonhoc")
    String dangki(){
        List<SubjectDTO> subjectDTOS= new ArrayList<>();
        SubjectDTO subject1= new SubjectDTO();
        SubjectDTO subject2= new SubjectDTO();
        SubjectDTO subject3= new SubjectDTO();
        subject1.setTenmonhoc("Triết học Mác - LêNin");
        subject1.setKihieu("CT3901.1");
        subject1.setSotinchi(3);
        subject1.setHeso(1);
        subject2.setTenmonhoc("Toán Đại số");
        subject2.setKihieu("TC2611");
        subject2.setSotinchi(3);
        subject2.setHeso(1);
        subject3.setTenmonhoc("Giáo dục thể chất P1");
        subject3.setKihieu("GD4501");
        subject3.setSotinchi(1);
        subject3.setHeso(1);
        subjectDTOS.add(subject1);
        subjectDTOS.add(subject2);
        subjectDTOS.add(subject3);
        studentService.dang_ki_mon_hoc(subjectDTOS);
        return "";
    }
}
