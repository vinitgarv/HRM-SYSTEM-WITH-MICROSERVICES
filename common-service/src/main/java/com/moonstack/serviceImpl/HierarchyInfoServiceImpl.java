package com.moonstack.serviceImpl;

import com.moonstack.entity.HierarchyInfo;
import com.moonstack.repository.HierarchyInfoRepository;
import com.moonstack.service.HierarchyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HierarchyInfoServiceImpl implements HierarchyInfoService {

    @Autowired
    private HierarchyInfoRepository repository;

    @Override
    public HierarchyInfo addHierarchyInfo(HierarchyInfo hierarchyInfo) {
        return repository.save(hierarchyInfo);
    }
}
