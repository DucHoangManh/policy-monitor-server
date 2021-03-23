package com.dhm.policy.web;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.VersionRepository;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class NetworkPolicyController {
    private final NetworkPolicyRepository networkPolicyRepository;
    @RequestMapping("/policy")
    public  List<VersionedNetworkPolicy> listAllVersionedPolicy(){
        return networkPolicyRepository.findAll();
    }
    @RequestMapping(value = "/policy/{policyId}", method = RequestMethod.GET)
    public VersionedNetworkPolicy findVersionedNetworkPolicyById(@PathVariable String policyId){
        Optional<VersionedNetworkPolicy> result = networkPolicyRepository.findById(policyId);
        return result.orElse(null);
    }

    @Autowired
    public NetworkPolicyController(NetworkPolicyRepository networkPolicyRepository){
        this.networkPolicyRepository = networkPolicyRepository;
    }
}
