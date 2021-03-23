package com.dhm.policy.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "version")
public class Version {
    @MongoId
    private String Id;
    private String content;
    private boolean latest;

    public Version(String content, boolean latest) {
        this.content = content;
        this.latest = latest;
    }

    public String getId() {
        return Id;
    }

    public String getContent() {
        return content;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }
}
