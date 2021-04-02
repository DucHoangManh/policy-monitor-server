package com.dhm.policy.repository;

import com.dhm.policy.domain.Version;
import com.dhm.policy.domain.VersionedNetworkPolicy;
import java.util.List;

public interface VersionedNetworkPolicyDAL {
    List<VersionedNetworkPolicy> getLatestNetworkPolicy();
    void removeLatestVersion();
    void removeLatest();
    VersionedNetworkPolicy removeByName(String name);
    VersionedNetworkPolicy findByName(String name);
    VersionedNetworkPolicy removeById(String Id);
}
