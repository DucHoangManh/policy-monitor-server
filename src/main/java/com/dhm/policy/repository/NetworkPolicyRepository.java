package com.dhm.policy.repository;

import com.dhm.policy.domain.NetworkPolicy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NetworkPolicyRepository extends MongoRepository<NetworkPolicy, String> {
}
