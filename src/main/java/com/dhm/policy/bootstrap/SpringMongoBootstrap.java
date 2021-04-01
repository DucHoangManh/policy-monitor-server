package com.dhm.policy.bootstrap;

import com.dhm.policy.domain.Version;
import com.dhm.policy.domain.VersionedNetworkPolicy;
import com.dhm.policy.k8s.K8sClientUser;
import com.dhm.policy.k8s.NetworkPolicyServices;
import com.dhm.policy.repository.NetworkPolicyRepository;
import com.dhm.policy.repository.impl.VersionDALImpl;
import com.dhm.policy.repository.VersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringMongoBootstrap implements ApplicationListener<ContextRefreshedEvent>, K8sClientUser {
    private NetworkPolicyRepository networkPolicyRepository;
    private VersionRepository versionRepository;
    private VersionDALImpl versionDALImpl;
    private NetworkPolicyServices networkPolicyServices;
    private final Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);

    @Autowired
    public void setNetworkPolicyRepository(NetworkPolicyRepository networkPolicyRepository){
        this.networkPolicyRepository = networkPolicyRepository;
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository){
        this.versionRepository = versionRepository;
    }

    @Autowired
    public void setVersionDALImpl(VersionDALImpl versionDALImpl){
        this.versionDALImpl = versionDALImpl;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        fetchNetworkPolicy();
    }

    @Transactional
    public void fetchNetworkPolicy(){
        Version latestVersion = versionDALImpl.getLatest();
        Version version;
        if(latestVersion==null){
            logger.info("fetch the first version");
            version = new Version("Init version", true);
            versionRepository.save(version);
        }else{
            networkPolicyServices.removeLatestVersionedNetworkPolicy();
            version = latestVersion;
        }
        logger.info("fetch network policies in cluster");
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

    @Autowired
    public void setNetworkPolicyServices(NetworkPolicyServices networkPolicyServices){
        this.networkPolicyServices = networkPolicyServices;
    }
}
