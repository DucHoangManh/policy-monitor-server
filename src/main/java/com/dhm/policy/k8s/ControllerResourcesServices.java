package com.dhm.policy.k8s;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerResourcesServices {
    private static final KubernetesClient client = new DefaultKubernetesClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
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
        return client.apps().deployments().inNamespace("default").list().getItems();
    }
}
