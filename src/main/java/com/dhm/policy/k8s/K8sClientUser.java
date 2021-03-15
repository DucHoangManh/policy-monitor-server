package com.dhm.policy.k8s;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public interface K8sClientUser {
    KubernetesClient client = new DefaultKubernetesClient();

}
