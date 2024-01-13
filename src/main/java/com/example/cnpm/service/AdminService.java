package com.example.cnpm.service;

import com.example.cnpm.entity.Admin;

public interface AdminService {
    void insert(Admin admin);
    void delete(long id);
    void dangkimonhoc(String classes,String sub,String teacher);
}
