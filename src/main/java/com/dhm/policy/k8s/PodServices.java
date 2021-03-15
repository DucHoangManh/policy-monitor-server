package com.dhm.policy.k8s;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PodServices implements K8sClientUser{
    public static List<Pod> listPodsDefaultNs(){
        return client
                .pods()
                .inNamespace("default")
                .list()
                .getItems();
    }
    public static List<Pod> listPodsWithinNamespace(String namespace){
        return client
                .pods()
                .inNamespace(namespace)
                .list()
                .getItems();
    }

}
