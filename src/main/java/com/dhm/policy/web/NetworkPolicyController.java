package com.dhm.policy.web;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.VersionedNetworkPolicyDALImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NetworkPolicyController {
    private final NetworkPolicyRepository networkPolicyRepository;
    private final VersionedNetworkPolicyDALImpl versionedNetworkPolicyDALImpl;
    @RequestMapping("/policy")
    public List<VersionedNetworkPolicy> listAllVersionedPolicy(@RequestParam(value = "latest",required = false) boolean latest){
        if (!latest) {
            return networkPolicyRepository.findAll();
        }else{
            return versionedNetworkPolicyDALImpl.getLatestNetworkPolicy();
        }
    }

    @RequestMapping(value = "/policy/{policyId}", method = RequestMethod.GET)
    public VersionedNetworkPolicy findVersionedNetworkPolicyById(@PathVariable String policyId){
        Optional<VersionedNetworkPolicy> result = networkPolicyRepository.findById(policyId);
        return result.orElse(null);
    }


    @Autowired
    public NetworkPolicyController(NetworkPolicyRepository networkPolicyRepository, VersionedNetworkPolicyDALImpl versionedNetworkPolicyDALImpl){
        this.networkPolicyRepository = networkPolicyRepository;
        this.versionedNetworkPolicyDALImpl = versionedNetworkPolicyDALImpl;
    }
}
