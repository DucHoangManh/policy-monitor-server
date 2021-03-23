package com.dhm.policy.repository;

import com.dhm.policy.domain.Version;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VersionRepository extends MongoRepository<Version,String> {
}
