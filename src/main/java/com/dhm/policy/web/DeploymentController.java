package com.dhm.policy.web;

import com.dhm.policy.k8s.ControllerResourcesServices;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class DeploymentController {

    @RequestMapping("/deployments")
    public List<Deployment> listDeployment(){
        return ControllerResourcesServices.listDeploymentDefaultNs();
    }
    @RequestMapping("/devs")
    public List<Deployment> listDeploymentByLabel(){
        return ControllerResourcesServices.listDeploymentByLabel("component","postgres");
    }
}
