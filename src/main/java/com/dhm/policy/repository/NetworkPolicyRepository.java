package com.dhm.policy.repository;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NetworkPolicyRepository extends MongoRepository<VersionedNetworkPolicy, String> {
    List<VersionedNetworkPolicy> findAll();
}
