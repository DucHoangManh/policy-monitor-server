package com.dhm.policy.repository;

import com.dhm.policy.domain.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class VersionDALImpl implements VersionDAL{
    private final MongoTemplate mongoTemplate;
    @Override
    public List<Version> getAll() {
        return mongoTemplate.findAll(Version.class);
    }

    @Override
    public Version getLatest() {
        Query query = new Query();
        query.addCriteria(Criteria.where("latest").is(true));
        return mongoTemplate.findOne(query,Version.class);
    }

    @Autowired
    public VersionDALImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }
}
