package com.moonstack.serviceImpl;

import com.moonstack.entity.RelatedForm;
import com.moonstack.repository.RelatedFormRepository;
import com.moonstack.service.RelatedFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatedFormServiceImpl implements RelatedFormService {
    @Autowired
    private RelatedFormRepository repository;

    @Override
    public RelatedForm addRelatedForm(RelatedForm relatedForm) {
        return repository.save(relatedForm);
    }
}
