package com.dhm.policy.web;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.k8s.NetworkPolicyServices;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.VersionedNetworkPolicyDALImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NetworkPolicyController {
    private final NetworkPolicyServices networkPolicyServices;
    @RequestMapping("/policy")
    public List<VersionedNetworkPolicy> listAllVersionedPolicy(@RequestParam(value = "latest",required = false) boolean latest){
        if (!latest) {
            return networkPolicyServices.getAll();
        }else{
            return networkPolicyServices.getLatestVersionedPolicy();
        }
    }

    @RequestMapping(value = "/policy/{policyId}", method = RequestMethod.GET)
    public VersionedNetworkPolicy findVersionedNetworkPolicyById(@PathVariable String policyId){
        return networkPolicyServices
                .findById(policyId)
                .orElse(null);
    }


    @Autowired
    public NetworkPolicyController(NetworkPolicyServices networkPolicyServices){
        this.networkPolicyServices=networkPolicyServices;
    }
}
