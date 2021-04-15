package com.dhm.policy.web;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.k8s.NetworkPolicyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/policy")
public class ClusterNetworkPolicyController {
    private final NetworkPolicyServices networkPolicyServices;

    @RequestMapping("/")
    public List<VersionedNetworkPolicy> listAllVersionedPolicy(
            @RequestParam(value = "latest",required = false) boolean latest){
        if (!latest) {
            return networkPolicyServices.getAll();
        }else{
            return networkPolicyServices.getLatestVersionedPolicy();
        }
    }

    @Autowired
    public ClusterNetworkPolicyController(NetworkPolicyServices networkPolicyServices){
        this.networkPolicyServices = networkPolicyServices;
    }
}
