package com.moonstack.serviceImpl;

import com.moonstack.entity.ContactDetail;
import com.moonstack.repository.ContactDetailRepository;
import com.moonstack.service.ContactDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactDetailServiceImpl implements ContactDetailService {

    @Autowired
    private ContactDetailRepository repository;

    @Override
    public ContactDetail addContactDetails(ContactDetail contactDetail) {
        return repository.save(contactDetail);
    }
}
