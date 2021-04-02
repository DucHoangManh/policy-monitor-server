package com.dhm.policy.web;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.k8s.NetworkPolicyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{namespace}/policy")
public class NetworkPolicyController {
    private final NetworkPolicyServices networkPolicyServices;
    @RequestMapping("/")
    public List<VersionedNetworkPolicy> listAllVersionedPolicy(
            @RequestParam(value = "latest",required = false) boolean latest,
            @PathVariable String namespace){
        if (!latest) {
            return networkPolicyServices.getAllInNs(namespace);
        }else{
            return networkPolicyServices.getLatestVersionedPolicyInNs(namespace);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createNewPolicy(@RequestBody String body, @PathVariable String namespace){
        networkPolicyServices.createNew(body,namespace);
    }

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public void updateExistedPolicy(@RequestBody String body, @PathVariable String namespace){
        networkPolicyServices.update(body,namespace);
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
