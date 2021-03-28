package com.dhm.policy.k8s;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.VersionedNetworkPolicyDALImpl;
import io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class NetworkPolicyServices implements K8sClientUser {
    private final VersionedNetworkPolicyDALImpl versionedNetworkPolicyRepository;
    private final NetworkPolicyRepository networkPolicyRepository;
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
    public List<VersionedNetworkPolicy> getAll(){
        return networkPolicyRepository
                .findAll();
    }
    public Optional<VersionedNetworkPolicy> findById(String id){
        return networkPolicyRepository
                .findById(id);
    }

    public void removeLatestVersionedNetworkPolicy(){
        versionedNetworkPolicyRepository.removeLatestVersion();
    }
    @Autowired
    public NetworkPolicyServices(
            VersionedNetworkPolicyDALImpl versionedNetworkPolicyDALImpl
            , NetworkPolicyRepository networkPolicyRepository){
        this.versionedNetworkPolicyRepository = versionedNetworkPolicyDALImpl;
        this.networkPolicyRepository=networkPolicyRepository;
    }
}
