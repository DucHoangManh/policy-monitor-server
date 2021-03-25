package com.dhm.policy.repository;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VersionedNetworkPolicyDALImpl implements VersionedNetworkPolicyDAL {
    private MongoTemplate mongoTemplate;
    @Override
    public List<VersionedNetworkPolicy> getLatestNetworkPolicy() {
        Query query = new Query();
        query.addCriteria(Criteria.where("version.latest").is(true));
        return mongoTemplate.find(query,VersionedNetworkPolicy.class);
    }
    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }
}
