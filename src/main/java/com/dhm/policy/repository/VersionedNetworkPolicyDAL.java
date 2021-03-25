package com.dhm.policy.repository;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import java.util.List;

public interface VersionedNetworkPolicyDAL {
    List<VersionedNetworkPolicy> getLatestNetworkPolicy();
}
