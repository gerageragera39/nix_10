package ua.com.alevel.service.personal.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.repository.users.PersonalRepository;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Optional;

@Service
public class PersonalServiceImpl implements PersonalService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonalRepository personalRepository;
    private final CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelper;

    public PersonalServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder,
                               PersonalRepository personalRepository,
                               CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personalRepository = personalRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public void create(Personal personal) {
        if (personalRepository.existsByEmail(personal.getEmail())) {
            throw new EntityExistException("this personal is exist");
        }
        personal.setPassword(bCryptPasswordEncoder.encode(personal.getPassword()));
        crudRepositoryHelper.create(personalRepository, personal);
    }

    @Override
    public void update(Personal entity) {
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        crudRepositoryHelper.update(personalRepository, entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Personal> findById(Long id) {
        return crudRepositoryHelper.findById(personalRepository, id);
    }

    @Override
    public DataTableResponse<Personal> findAll(DataTableRequest request) {
        DataTableResponse<Personal> dataTableResponse = crudRepositoryHelper.findAll(personalRepository, request);
        long count = personalRepository.countAllByVisibleTrue();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Personal findByEmail(String personalEmail) {
        return personalRepository.findByEmail(personalEmail);
    }
}
