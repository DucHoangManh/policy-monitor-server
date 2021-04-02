package com.dhm.policy.k8s;

import com.dhm.policy.bootstrap.SpringMongoBootstrap;
import com.dhm.policy.domain.Version;
import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.VersionRepository;
import com.dhm.policy.repository.impl.VersionDALImpl;
import com.dhm.policy.repository.impl.VersionedNetworkPolicyDALImpl;
import io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class NetworkPolicyServices implements K8sClientUser {
    private final Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);
    private final VersionedNetworkPolicyDALImpl versionedNetworkPolicyRepository;
    private final NetworkPolicyRepository networkPolicyRepository;
    private final VersionRepository versionRepository;
    private final VersionDALImpl versionDAL;
    public List<NetworkPolicy> getLatestPolicies(){
        return versionedNetworkPolicyRepository
                .getLatestNetworkPolicy()
                .stream()
                .map(VersionedNetworkPolicy::getNetworkPolicy)
                .collect(Collectors.toList());
    }
    public List<VersionedNetworkPolicy> getLatestVersionedPolicy(){
        return versionedNetworkPolicyRepository
                .getLatestNetworkPolicy();
    }
    public List<VersionedNetworkPolicy> getLatestVersionedPolicyInNs(String ns){
        return versionedNetworkPolicyRepository
                .getLatestNetworkPolicyInNs(ns);
    }
    public List<VersionedNetworkPolicy> getAll(){
        return networkPolicyRepository
                .findAll();
    }
    public List<VersionedNetworkPolicy> getAllInNs(String ns){
        return versionedNetworkPolicyRepository.getAllInNs(ns);
    }
    public Optional<VersionedNetworkPolicy> findById(String id){
        return networkPolicyRepository
                .findById(id);
    }

    public void createNew(String newPolicy, String namespace){
        NetworkPolicy netPolicy = client
                .network()
                .v1().networkPolicies()
                .inNamespace(namespace)
                .load(new ByteArrayInputStream(newPolicy.getBytes(StandardCharsets.UTF_8)))
                .createOrReplace();
        logger.info("new policy created with name: " + netPolicy.getMetadata().getName());
        fetchNewVersion("new policy " + netPolicy.getMetadata().getName() +" created");
    }

    public void update(String newPolicy, String namespace){
        NetworkPolicy netPolicy = client
                .network()
                .v1().networkPolicies()
                .inNamespace(namespace)
                .load(new ByteArrayInputStream(newPolicy.getBytes(StandardCharsets.UTF_8)))
                .createOrReplace();
        logger.info("policy with name: " + netPolicy.getMetadata().getName() + " updated");
        fetchNewVersion("policy " + netPolicy.getMetadata().getName() +" updated");
    }

    @Transactional
    public VersionedNetworkPolicy deleteByName(String name){
        VersionedNetworkPolicy removedPolicy= versionedNetworkPolicyRepository.findByName(name);
        if(removedPolicy!=null){
            client
                    .network()
                    .v1()
                    .networkPolicies()
                    .inNamespace("default")
                    .withName(name)
                    .delete();
            fetchNewVersion("Policy "+ name + " deleted.");
            return removedPolicy;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"policy not found");
    }

    @Transactional
    public void fetchNewVersion(String content){
        versionDAL.removeLatest();
        versionedNetworkPolicyRepository.removeLatest();
        Version newVersion = new Version(content,true);
        versionRepository.save(newVersion);
        logger.info("fetch the new policies after update");
        try{
            client
                    .network()
                    .networkPolicies()
                    .inNamespace("default")
                    .list()
                    .getItems()
                    .forEach(
                            networkPolicy -> {
                                VersionedNetworkPolicy np = new VersionedNetworkPolicy(newVersion,networkPolicy);
                                networkPolicyRepository.save(np);
                            }
                    );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public VersionedNetworkPolicy deleteById(String id){
        VersionedNetworkPolicy removedPolicy =  versionedNetworkPolicyRepository.removeById(id);
        Version newVersion = new Version("Policy "
                +removedPolicy
                .getNetworkPolicy()
                .getMetadata()
                        .getName()
                + " deleted.", true);

        return removedPolicy;
    }



    public void removeLatestVersionedNetworkPolicy(){
        versionedNetworkPolicyRepository.removeLatestVersion();
    }
    @Autowired
    public NetworkPolicyServices(
            VersionedNetworkPolicyDALImpl versionedNetworkPolicyDALImpl
            , NetworkPolicyRepository networkPolicyRepository
            , VersionRepository versionRepository
            , VersionDALImpl versionDAL){
        this.versionedNetworkPolicyRepository = versionedNetworkPolicyDALImpl;
        this.networkPolicyRepository=networkPolicyRepository;
        this.versionRepository=versionRepository;
        this.versionDAL=versionDAL;
    }
}
