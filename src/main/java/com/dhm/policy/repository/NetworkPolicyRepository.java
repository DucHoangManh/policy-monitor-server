package com.dhm.policy.repository;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NetworkPolicyRepository extends MongoRepository<VersionedNetworkPolicy, String> {
}
