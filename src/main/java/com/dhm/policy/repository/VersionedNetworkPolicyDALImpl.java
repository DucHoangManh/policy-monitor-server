package com.dhm.policy.repository;

import com.dhm.policy.domain.VersionedNetworkPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VersionedNetworkPolicyDALImpl implements VersionedNetworkPolicyDAL {
    private MongoTemplate mongoTemplate;
    @Override
    public List<VersionedNetworkPolicy> getLatestNetworkPolicy() {
        Query query = new Query();
        query.addCriteria(Criteria.where("version.latest").is(true));
        return mongoTemplate.find(query,VersionedNetworkPolicy.class);
    }

    @Override
    public void removeLatestVersion() {
        Query query = new Query();
        query.addCriteria(Criteria.where("version.latest").is(true));
        mongoTemplate.remove(query, VersionedNetworkPolicy.class);
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }
}
