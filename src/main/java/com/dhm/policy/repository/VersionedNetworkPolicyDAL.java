package com.dhm.policy.repository;

import com.dhm.policy.domain.Version;
import com.dhm.policy.domain.VersionedNetworkPolicy;
import java.util.List;

public interface VersionedNetworkPolicyDAL {
    List<VersionedNetworkPolicy> getAllInNs(String ns);
    List<VersionedNetworkPolicy> getLatestNetworkPolicy();
    List<VersionedNetworkPolicy> getLatestNetworkPolicyInNs(String ns);
    List<VersionedNetworkPolicy> getSpecificVersionPolicyInNs(String ns, String version);
    void removeLatestVersion();
    void removeLatest();
    VersionedNetworkPolicy removeByName(String name);
    VersionedNetworkPolicy findByName(String name);
    VersionedNetworkPolicy removeById(String Id);
}
