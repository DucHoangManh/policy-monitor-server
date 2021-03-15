package com.dhm.policy.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "policy")
public class NetworkPolicy {
    @Id private String Id;
    private String version;
    private io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy networkPolicy;

    public NetworkPolicy(String version, io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy networkPolicy) {
        this.version = version;
        this.networkPolicy = networkPolicy;
    }

    public String getId() {
        return Id;
    }

    public String getVersion() {
        return version;
    }

    public io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy getNetworkPolicy() {
        return networkPolicy;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setNetworkPolicy(io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy networkPolicy) {
        this.networkPolicy = networkPolicy;
    }
}
