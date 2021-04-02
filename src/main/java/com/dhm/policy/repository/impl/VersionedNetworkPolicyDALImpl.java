package com.dhm.policy.repository.impl;

import com.dhm.policy.bootstrap.SpringMongoBootstrap;
import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.repository.VersionedNetworkPolicyDAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VersionedNetworkPolicyDALImpl implements VersionedNetworkPolicyDAL {
    private final Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);
    private MongoTemplate mongoTemplate;

    @Override
    public List<VersionedNetworkPolicy> getAllInNs(String ns) {
        Query query = new Query();
        query.addCriteria(Criteria.where("networkPolicy.metadata.namespace").is(ns));
        return mongoTemplate.find(query,VersionedNetworkPolicy.class);
    }

    @Override
    public List<VersionedNetworkPolicy> getLatestNetworkPolicyInNs(String ns) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("networkPolicy.metadata.namespace").is(ns));
            query.addCriteria(Criteria.where("version.latest").is(true));
            return mongoTemplate.find(query,VersionedNetworkPolicy.class);
        }catch (Exception e){
            e.printStackTrace();
            return getAllInNs(ns);
        }
    }

    @Override
    public List<VersionedNetworkPolicy> getLatestNetworkPolicy() {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("version.latest").is(true));
            return mongoTemplate.find(query,VersionedNetworkPolicy.class);
        }catch (Exception e){
            e.printStackTrace();
            return mongoTemplate.findAll(VersionedNetworkPolicy.class);
        }
    }

    @Override
    public VersionedNetworkPolicy findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("networkPolicy.metadata.name").is(name));
        return mongoTemplate.findOne(query,VersionedNetworkPolicy.class);
    }

    @Override
    public void removeLatestVersion() {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("version.latest").is(true));
            mongoTemplate.remove(query, VersionedNetworkPolicy.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeLatest() {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("version.latest").is(true));
            Update update = new Update();
            update.set("version.latest",false);
            mongoTemplate.updateMulti(query,update,VersionedNetworkPolicy.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public VersionedNetworkPolicy removeByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("networkPolicy.metadata.name").is(name));
        try{
            return mongoTemplate.findAndRemove(query,VersionedNetworkPolicy.class);
        }catch (Exception e){
            e.printStackTrace();
            return mongoTemplate.findOne(query,VersionedNetworkPolicy.class);
        }
    }

    @Override
    public VersionedNetworkPolicy removeById(String Id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(Id));
        try{
            return mongoTemplate.findAndRemove(query,VersionedNetworkPolicy.class);
        }catch (Exception e){
            e.printStackTrace();
            return mongoTemplate.findOne(query,VersionedNetworkPolicy.class);
        }
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }
}
