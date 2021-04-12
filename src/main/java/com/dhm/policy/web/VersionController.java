package com.dhm.policy.web;

import com.dhm.policy.domain.Version;
import com.dhm.policy.service.VersionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
public class VersionController {
    private final VersionServices versionServices;
    @RequestMapping("/version")
    public List<Version> getAllVersion(){
        return versionServices.getAll();
    }

    @Autowired
    public VersionController(VersionServices versionServices){
        this.versionServices = versionServices;
    }
}
