package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.model.Admin;
import com.azo.ecommerce.repository.AdminRepository;
import com.azo.ecommerce.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImplementation implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImplementation(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminById(Long adminId) {
        return adminRepository.findById(adminId); // No need to wrap with Optional again
    }

    @Override
    public Admin createAdmin(Admin admin) {
        if (admin.getAdminId() != null && adminRepository.existsById(admin.getAdminId())) {
            throw new IllegalArgumentException("Admin already exists with ID: " + admin.getAdminId());
        }
        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> updateAdmin(Long adminId, Admin admin) {

        if (!adminRepository.existsById(adminId)) {
            throw new IllegalArgumentException("Admin does not exist with ID: " + adminId);
        }

        admin.setAdminId(adminId);
        return Optional.of(adminRepository.save(admin));
    }

    @Override
    public void deleteAdmin(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new IllegalArgumentException("Admin does not exist with ID: " + adminId);
        }

        adminRepository.deleteById(adminId);
    }
}



