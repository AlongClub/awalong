package org.alongclub.file.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FileController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/file/callHello")
    public String callHello() {
        String urp = restTemplate.getForObject("http://urp/user/hello", String.class);
        return urp + "123";
    }
}