package com.example.cnpm.controller;

import com.example.cnpm.dto.StudentDTO;
import com.example.cnpm.dto.SubjectDTO;
import com.example.cnpm.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
public class SubjectController {
    @Autowired
    SubjectService service;
    @GetMapping("/Subject")
    String getAll(Model model, @RequestParam(name="page") Optional<Integer> page,
                  @RequestParam(name="size") Optional<Integer> size,
                  @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(4);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<SubjectDTO> list =service.getAll(pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_subject",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "subject";
    }
    @GetMapping(value="/insertSubject",name="/insertSubject")
    String show_insert_form(Model model){
        model.addAttribute("subject",new SubjectDTO());
        return "insertSubject";
    }
    @PostMapping(value="/insertSubject",name="/insertSubject")
    String insert_S(Model model, @ModelAttribute("subject")SubjectDTO subjectDTO){
        service.insert(subjectDTO);
        return "redirect:/Subject";
    }
    @RequestMapping("/deleteSubject/{id}")
    public String delete(@PathVariable("id")long id){
        service.delete(id);
        return "redirect:/Subject";
    }
    @RequestMapping ("/findSubjectByName/{name}")
    public String findByName(Model model,@PathVariable("name")String name,@RequestParam(name="page") Optional<Integer> page,
                             @RequestParam(name="size") Optional<Integer> size,
                             @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(3);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<SubjectDTO> list= service.findByName(name,pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_subject",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "subject";
    }
    @GetMapping(name="/editSubject",value="/editSubject/{id}")
    public String edit(Model model,@PathVariable("id")long id){
        model.addAttribute("subject",service.findById(id));
        return "editSubject";
    }
    @PostMapping(value="/editSubject",name="/editSubject")
    public String edit(@ModelAttribute("subject") SubjectDTO subjectDTO) throws IOException {
        service.insert(subjectDTO);
        return "redirect:/Student";
    }
    @RequestMapping ("/findSubjectByCode/{code}")
    public String findByCode(Model model,@PathVariable("code")String code,@RequestParam(name="page") Optional<Integer> page,
                             @RequestParam(name="size") Optional<Integer> size,
                             @RequestParam(name="sort",required=false,defaultValue = "ASC")String sort ){
        int  pageIndex = page.orElse(1);
        int pageSize=size.orElse(3);
        Pageable pageable= PageRequest.of(pageIndex-1,pageSize);
        Page<SubjectDTO> list= service.findByCode(code,pageable);
        int totalPage=list.getTotalPages();
        long totalItem= list.getTotalElements();
        model.addAttribute("list_subject",list);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("currentPage",pageIndex);
        return "subject";
    }
}
