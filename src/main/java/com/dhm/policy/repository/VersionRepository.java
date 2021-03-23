package com.dhm.policy.repository;

import com.dhm.policy.domain.Version;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VersionRepository extends MongoRepository<Version,String> {

}
