package ua.com.alevel.service.admin.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.users.Admin;
import ua.com.alevel.service.admin.AdminService;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public void create(Admin entity) {

    }

    @Override
    public void update(Admin entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Admin> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<Admin> findAll(DataTableRequest request) {
        return null;
    }
}
