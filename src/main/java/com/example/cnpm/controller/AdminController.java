package com.example.cnpm.controller;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.entity.Admin;
import com.example.cnpm.entity.Subject;
import com.example.cnpm.service.AdminService;
import com.example.cnpm.service.StudentService;
import com.example.cnpm.service.SubjectService;
import com.example.cnpm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    @GetMapping(value="/insertAdmin",name="/insertAdmin")
    String show_insert_form1(Model model){
        model.addAttribute("admin",new Admin());
        return "insertAdmin";
    }
    @PostMapping(value="/insertAdmin",name="/insertAdmin")
    String insert_Admin(Model model, @ModelAttribute("admin")Admin admin){
        adminService.insert(admin);
        return "redirect:/Teacher";
    }
   @GetMapping("/index")
    String index(){
        return "index";
   }
   @GetMapping("/studenttest")
    String student(){
        return "student_homepage";
   }
    @GetMapping("/teachertest")
    String teacher(){
        return "teacher_homepage";
    }
    @GetMapping("/admin")
    String admin(Model model, @RequestParam(value = "class",required = false)String classes,@RequestParam(value = "sub",required = false)String sub
    ,@RequestParam(value="teacher",required = false)String teacher){
        Set<String> class_list= studentService.findAllClasses();
        Set<Subject> sub_list= subjectService.getAll();
        Set<String> tea_list=teacherService.findAllTeacher();
        if(classes!=null){
            List<StudentDTO> student_list= studentService.findStudentsByClass(classes);
            model.addAttribute("student_list",student_list);
            model.addAttribute("choosed_class",classes);
        }else{
            model.addAttribute("choosed_class",null);
        }
        if(sub!=null){
            model.addAttribute("choosed_sub",sub);
        }else{
            model.addAttribute("choosed_sub",null);
        }
        if(teacher!=null){
            model.addAttribute("choose_tea",teacher);
        }else{
            model.addAttribute("choose_tea",null);
        }
        model.addAttribute("tea_list",tea_list);
        model.addAttribute("class_list",class_list);
        model.addAttribute("sub_list",sub_list);
        return "admin";
    }
    @GetMapping("/dangkimonhoc")
    String dangkimonhoc(@RequestParam("class")String classes,@RequestParam("sub")String sub,@RequestParam("teacher")String teacher){
        adminService.dangkimonhoc(classes,sub,teacher);
        return "redirect:/admin";
    }
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SubjectService subjectService;
}
