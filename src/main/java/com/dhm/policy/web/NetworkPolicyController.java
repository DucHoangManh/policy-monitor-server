package com.dhm.policy.web;

import com.dhm.policy.bootstrap.SpringMongoBootstrap;
import com.dhm.policy.domain.Version;
import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.k8s.NetworkPolicyServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/{namespace}/policy")
public class NetworkPolicyController {
    private final Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);
    private final NetworkPolicyServices networkPolicyServices;
    @RequestMapping("/")
    public List<VersionedNetworkPolicy> listAllVersionedPolicy(
            @RequestParam(value = "version", required = false) String version,
            @PathVariable String namespace){

        if (version != null){
            if (version.equals("latest")){
                return networkPolicyServices.getLatestVersionedPolicyInNs(namespace);
            }else{
                return networkPolicyServices.getSpecificVersionPolicyInNs(namespace, version);
            }
        }else {
            return networkPolicyServices.getAllInNs(namespace);
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

    @RequestMapping(value = "/by_name/{name}", method = RequestMethod.GET)
    public VersionedNetworkPolicy getNetworkPolicyByName(@RequestParam(value = "version", required = true) String version,
                                                                @PathVariable String namespace,
                                                               @PathVariable String name)
    {
        return networkPolicyServices.findByName(name, namespace, version);
    }

    @RequestMapping(value = "/by_name/{name}", method = RequestMethod.DELETE)
    public VersionedNetworkPolicy deleteNetworkPolicyByName(@PathVariable String name, @PathVariable String namespace){
        return networkPolicyServices.deleteByName(name, namespace);
    }


    @Autowired
    public NetworkPolicyController(NetworkPolicyServices networkPolicyServices){
        this.networkPolicyServices=networkPolicyServices;
    }
}
