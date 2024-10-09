package com.azo.ecommerce.service;

import com.azo.ecommerce.model.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(Long adminId);
    Admin createAdmin(Admin admin);
    Optional<Admin> updateAdmin(Long adminId, Admin admin);
    void deleteAdmin(Long adminId);
}
