package com.example.cnpm.controller;

import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.entity.Student;
import com.example.cnpm.service.StudentService;
import com.example.cnpm.service.SubjectService;
import com.example.cnpm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class TeacherController {
    @Autowired
    TeacherService service;
    @Autowired
    SubjectService subjectService;
    @Autowired
    StudentService studentService;
    @GetMapping("/Teacher")
    String getAll(Model model, @RequestParam(name="page") Optional<Integer> page,
                  @RequestParam(name="size") Optional<Integer> size,
                  @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(4);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<TeacherDTO> list =service.getAll(pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_teacher",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "teacher";
    }
    @GetMapping(value="/insertTeacher",name="/insertTeacher")
    String show_insert_form(Model model){
        model.addAttribute("teacher",new TeacherDTO());
        return "insertTeacher";
    }
    @PostMapping(value="/insertTeacher",name="/insertTeacher")
    String insert_S(Model model, @ModelAttribute("teacher")TeacherDTO teacherDTO){
        service.insert(teacherDTO);
        return "redirect:/Teacher";
    }
    @RequestMapping("/deleteTeacher/{id}")
    public String delete(@PathVariable("id")long id){
        service.delete(id);
        return "redirect:/Teacher";
    }
    @GetMapping(name="/editTeacher",value="/editTeacher/{id}")
    public String edit(Model model,@PathVariable("id")long id){
        model.addAttribute("teacher",service.findById(id));
        return "editTeacher";
    }
    @PostMapping(value="/editTeacher",name="/editTeacher")
    public String edit(@ModelAttribute("teacher") TeacherDTO teacherDTO) throws IOException {
        service.insert(teacherDTO);
        return "redirect:/Teacher";
    }
    @RequestMapping ("/findTeacherByName/{name}")
    public String findByName(Model model,@PathVariable("name")String name
                            ,@RequestParam(name="page") Optional<Integer> page,
                             @RequestParam(name="size") Optional<Integer> size,
                             @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(3);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<TeacherDTO> list= service.findByName(name,pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_teacher",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "teacher";
    }
    @RequestMapping ("/findTeacherByCode/{code}")
    public String findByCode(Model model,@PathVariable("code")String code,@RequestParam(name="page") Optional<Integer> page,
                             @RequestParam(name="size") Optional<Integer> size,
                             @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(3);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<TeacherDTO> list= service.findByCode(code,pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_teacher",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "teacher";
    }
    @GetMapping("/teacher_homepage")
    String teacher_hompage(Model model,@RequestParam(value = "class",required = false)String classes,@RequestParam(value = "sub",required = false)String sub){
        Set<String> class_list= studentService.findAllClasses();
        Set<String> sub_list= service.findAllSubject();
        if(classes!=null){
            model.addAttribute("choosed_class",classes);
        }else{
            model.addAttribute("choosed_class",null);
        }
        if(sub!=null){
            model.addAttribute("choosed_sub",sub);
        }else{
            model.addAttribute("choosed_sub",null);
        }
        if(classes!=null && sub!=null){
            Set<Student> student_list= service.findAllStudent(classes,sub);
            model.addAttribute("student_list",student_list);
        }
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        TeacherDTO teacherDTO= service.findByCode(authentication.getName());
        model.addAttribute("teacher",teacherDTO);
        model.addAttribute("class_list",class_list);
        model.addAttribute("sub_list",sub_list);
        return "teacher_homepage";
    }
    @GetMapping("/nhapdiem")
    String nhapdiem( @RequestParam("sub")String sub,
                    @RequestParam("msv")String[] msv,
                    @RequestParam("qtr")Integer[] qtr,
                    @RequestParam("thi")Integer[]thi){
        service.nhapdiem(sub,msv,qtr,thi);
    return "redirect:/teacher_homepage";
    }
}
