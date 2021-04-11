package com.dhm.policy.web;

import com.dhm.policy.k8s.NamespaceServices;
import io.fabric8.kubernetes.api.model.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NamespaceController {
    NamespaceServices namespaceServices;
    @RequestMapping("/namespace")
    public List<String> getAll(){
        return namespaceServices
                .getAll()
                .stream()
                .map((item)->item
                        .getMetadata()
                        .getName())
                .collect(Collectors.toList());
    }

    @Autowired
    public NamespaceController(NamespaceServices namespaceServices){
        this.namespaceServices = namespaceServices;
    }

}
