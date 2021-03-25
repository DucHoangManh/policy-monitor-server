package com.dhm.policy.k8s;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.VersionedNetworkPolicyDAL;
import com.dhm.policy.repository.VersionedNetworkPolicyDALImpl;
import io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class NetworkPolicyServices implements K8sClientUser {
    VersionedNetworkPolicyDALImpl versionedNetworkPolicyRepository;
    public List<NetworkPolicy> getLatestPolicies(){
        return versionedNetworkPolicyRepository
                .getLatestNetworkPolicy()
                .stream()
                .map(VersionedNetworkPolicy::getNetworkPolicy)
                .collect(Collectors.toList());
    }
    @Autowired
    public void setVersionedNetworkPolicyRepository(VersionedNetworkPolicyDALImpl versionedNetworkPolicyDALImpl){
        this.versionedNetworkPolicyRepository = versionedNetworkPolicyDALImpl;
    }
}
