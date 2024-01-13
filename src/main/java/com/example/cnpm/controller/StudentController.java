package com.example.cnpm.controller;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.Admin;
import com.example.cnpm.entity.Point;
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
import java.util.*;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;



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
        model.addAttribute("admin",new Admin());
        return "login";
    }
    @RequestMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
    @GetMapping("/student_homepage")
    String home(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

            List<Point> points= studentService.getAllPoints();
            StudentDTO studentDTO= studentService.findByCode(authentication.getName());
            model.addAttribute("student",studentDTO);
            model.addAttribute("points",points);
            return "student_homepage";

    }


}
