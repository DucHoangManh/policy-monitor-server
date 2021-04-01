package com.dhm.policy.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "policy")

public class VersionedNetworkPolicy {
    @Id
    private String id;
    private Version version;
    @CreatedDate
    private Date createdDate;
    private io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy networkPolicy;

    public VersionedNetworkPolicy(Version version, io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy networkPolicy) {
        this.version = version;
        this.networkPolicy = networkPolicy;
    }

    public String getId() {
        return id;
    }

    public Version getVersion() {
        return version;
    }

    public io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy getNetworkPolicy() {
        return networkPolicy;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void setNetworkPolicy(io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy networkPolicy) {
        this.networkPolicy = networkPolicy;
    }
}
