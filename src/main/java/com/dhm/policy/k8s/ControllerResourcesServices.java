package com.dhm.policy.k8s;
import io.fabric8.kubernetes.api.model.apps.Deployment;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerResourcesServices implements K8sClientUser {
    public static List<Deployment> listDeploymentByLabel(String key, String value){
        List<Deployment> deps = client
                .apps()
                .deployments()
                .inNamespace("default")
                .list()
                .getItems();
        HashMap<String, String> query = new HashMap<>();
        query.put(key,value);
        return deps.stream().filter(deployment -> deployment
                .getSpec()
                .getSelector()
                .getMatchLabels()
                .equals(query)
        ).collect(Collectors.toList());
    }
    public static List<Deployment> listDeploymentDefaultNs(){
        return client
                .apps()
                .deployments()
                .inNamespace("default")
                .list()
                .getItems();
    }
    public static List<Deployment> listDeploymentWithinNamespace(String ns){
        return client
                .apps()
                .deployments()
                .inNamespace(ns)
                .list()
                .getItems();
    }
}
