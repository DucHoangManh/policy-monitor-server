package com.dhm.policy.k8s;

import com.dhm.policy.bootstrap.SpringMongoBootstrap;
import io.fabric8.kubernetes.api.model.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NamespaceServices implements K8sClientUser{
    private final Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);
    public List<Namespace> getAll(){
        return client
                .namespaces()
                .list()
                .getItems();
    }
}
