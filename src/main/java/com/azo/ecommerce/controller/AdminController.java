package com.azo.ecommerce.controller;

import com.azo.ecommerce.model.Admin;
import com.azo.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/adminId")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/getAdminById")
    public ResponseEntity<Optional<Admin>> getAdminById(@PathVariable Long adminId) {
        Optional<Admin> admin = adminService.getAdminById(adminId);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("createAdmin")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/updateAdmin")
    public ResponseEntity<Optional<Admin>> updateAdmin(@PathVariable Long adminId, @RequestBody Admin admin) {
        Optional<Admin> updatedAdmin = adminService.updateAdmin(adminId, admin);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.noContent().build();
    }

}

