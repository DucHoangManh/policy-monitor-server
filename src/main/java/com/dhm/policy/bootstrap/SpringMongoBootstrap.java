package com.dhm.policy.bootstrap;

import com.dhm.policy.domain.NetworkPolicy;
import com.dhm.policy.k8s.K8sClientUser;
import com.dhm.policy.repository.NetworkPolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringMongoBootstrap implements ApplicationListener<ContextRefreshedEvent>, K8sClientUser {
    private NetworkPolicyRepository networkPolicyRepository;
    private final Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);

    @Autowired
    public void setNetworkPolicyRepository(NetworkPolicyRepository networkPolicyRepository){
        this.networkPolicyRepository = networkPolicyRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        fetchNetworkPolicy();
    }

    public void fetchNetworkPolicy(){
        logger.info("fetch network policies in cluster");
        client
                .network()
                .networkPolicies()
                .inNamespace("default")
                .list()
                .getItems()
                .forEach(
                        networkPolicy -> {
                            NetworkPolicy np = new NetworkPolicy("test",networkPolicy);
                            networkPolicyRepository.save(np);
                        }
                );
    }

}
