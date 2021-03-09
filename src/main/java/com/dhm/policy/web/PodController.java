package com.dhm.policy.web;

import io.fabric8.kubernetes.api.model.Pod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dhm.policy.k8s.PodServices;

import java.util.List;

@RestController
public class PodController {
    @RequestMapping("/pods")
    public List<Pod> listPods() {
        return PodServices.listPodsDefaultNs();
    }

}
