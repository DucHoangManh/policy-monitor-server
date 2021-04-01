package com.dhm.policy.service;

import com.dhm.policy.domain.Version;
import com.dhm.policy.repository.impl.VersionDALImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServices {
    private final VersionDALImpl versionRepository;

    public List<Version> getAll(){
        return versionRepository.getAll();
    }

    @Autowired
    public VersionServices(VersionDALImpl versionRepository){
        this.versionRepository=versionRepository;
    }
}
