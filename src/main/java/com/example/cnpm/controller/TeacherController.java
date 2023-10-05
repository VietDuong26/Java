package com.example.cnpm.controller;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.dto.SubjectDTO;
import com.example.cnpm.dto.TeacherDTO;
import com.example.cnpm.service.StudentService;
import com.example.cnpm.service.SubjectService;
import com.example.cnpm.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @RequestMapping ("/findTeacherByName/{name}")
    public String findByName(Model model,@PathVariable("name")String name,@RequestParam(name="page") Optional<Integer> page,
                             @RequestParam(name="size") Optional<Integer> size,
                             @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(3);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<TeacherDTO> list= service.findByName(name,pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_subject",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "teacher";
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
    @GetMapping(value="/insertAdmin",name="/insertAdmin")
    String show_insert_form1(Model model){
        model.addAttribute("admin",new TeacherDTO());
        return "insertAdmin";
    }
    @PostMapping(value="/insertAdmin",name="/insertAdmin")
    String insert_Admin(Model model, @ModelAttribute("admin")TeacherDTO teacherDTO){
        service.insertAdmin(teacherDTO);
        return "redirect:/Teacher";
    }
    @GetMapping("/qtv")
    String qtv(Model model){
        List<SubjectDTO>list= subjectService.getAll();
        Set<String> list1=studentService.findAllClasses();
        List<StudentDTO>list2= studentService.findStudentByTenmonhoc("Toán Đại số");
        List<TeacherDTO> list4=service.findAll();
        List<StudentDTO> list3= studentService.findByLop("21CN3");
        model.addAttribute("subject_list",list);
        model.addAttribute("classes",list1);
        model.addAttribute("student_list",list2);
        model.addAttribute("teacher_list",list4);
        return "qtv";
    }
    @PostMapping("/save")
    String save(Model model,@RequestParam("teacher_name")String name,
                @RequestParam("subject")String subject_name,
                @RequestParam("class")String classes){
        return"";
    }
}
