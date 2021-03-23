package com.dhm.policy.bootstrap;

import com.dhm.policy.domain.Version;
import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.k8s.K8sClientUser;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.VersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringMongoBootstrap implements ApplicationListener<ContextRefreshedEvent>, K8sClientUser {
    private NetworkPolicyRepository networkPolicyRepository;
    private VersionRepository versionRepository;
    private final Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);

    @Autowired
    public void setNetworkPolicyRepository(NetworkPolicyRepository networkPolicyRepository){
        this.networkPolicyRepository = networkPolicyRepository;
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository){
        this.versionRepository = versionRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        fetchNetworkPolicy();
    }

    public void fetchNetworkPolicy(){
        logger.info("fetch network policies in cluster");
        Version version = new Version("Init version use for test", true);
        versionRepository.save(version);
        client
                .network()
                .networkPolicies()
                .inNamespace("default")
                .list()
                .getItems()
                .forEach(
                        networkPolicy -> {
                            VersionedNetworkPolicy np = new VersionedNetworkPolicy(version,networkPolicy);
                            networkPolicyRepository.save(np);
                        }
                );
    }

}
