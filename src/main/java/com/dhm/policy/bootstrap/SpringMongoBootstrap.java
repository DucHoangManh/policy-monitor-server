package com.dhm.policy.bootstrap;

import com.dhm.policy.repository.NetworkPolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class SpringMongoBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private NetworkPolicyRepository networkPolicyRepository;
    private Logger logger = LoggerFactory.getLogger(SpringMongoBootstrap.class);

    @Autowired
    public void setNetworkPolicyRepository(NetworkPolicyRepository networkPolicyRepository){
        this.networkPolicyRepository = networkPolicyRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        fetchNetworkPolicy();
    }

    public void fetchNetworkPolicy(){

    }

}
