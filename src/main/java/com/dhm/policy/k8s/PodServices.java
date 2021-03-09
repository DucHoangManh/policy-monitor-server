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
public class PodServices {
    private static final KubernetesClient client = new DefaultKubernetesClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static List<Pod> listPodsDefaultNs(){
        return client
                .pods()
                .inNamespace("default")
                .list()
                .getItems();
    }

}
