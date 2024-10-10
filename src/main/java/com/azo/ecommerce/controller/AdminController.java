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

    @GetMapping("/getAllAdmins")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/getAdminById")
    public ResponseEntity<Admin> getAdminById(@RequestParam Long adminId) {
        Optional<Admin> admin = adminService.getAdminById(adminId);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createAdmin")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/updateAdmin")
    public ResponseEntity<Admin> updateAdmin(@RequestParam Long adminId, @RequestBody Admin admin) {
        Optional<Admin> updatedAdmin = adminService.updateAdmin(adminId, admin);
        return updatedAdmin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/test")
    public String testEndpoint() {
        return "Test endpoint is working!";
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<Void> deleteAdmin(@RequestParam Long adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.noContent().build();
    }
}



