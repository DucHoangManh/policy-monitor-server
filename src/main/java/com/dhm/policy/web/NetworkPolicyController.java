package com.dhm.policy.web;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.k8s.NetworkPolicyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy")
public class NetworkPolicyController {
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

    @RequestMapping(value = "/{policyId}", method = RequestMethod.GET)
    public VersionedNetworkPolicy findVersionedNetworkPolicyById(@PathVariable String policyId){
        return networkPolicyServices
                .findById(policyId)
                .orElse(null);
    }

    @RequestMapping(value = "/{policyId}", method = RequestMethod.DELETE)
    public VersionedNetworkPolicy deleteNetworkPolicyById(@PathVariable String policyId){
        return networkPolicyServices.deleteById(policyId);
    }

    @RequestMapping(value = "/by_name/{name}", method = RequestMethod.DELETE)
    public VersionedNetworkPolicy deleteNetworkPolicyByName(@PathVariable String name){
        return networkPolicyServices.deleteByName(name);
    }


    @Autowired
    public NetworkPolicyController(NetworkPolicyServices networkPolicyServices){
        this.networkPolicyServices=networkPolicyServices;
    }
}
