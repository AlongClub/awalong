package org.alongclub.file.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.List;

@RestController
public class FileRestfulImpl {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/file/callHello")
    public String callHello() {
        String urp = restTemplate.getForObject("http://urp/user/hello", String.class);
        return urp + "123";
    }

    public List<String> showAllFiles(){
        Region region = Region.US_WEST_2;
        S3Client s3 = S3Client.builder().region(region).build();
    }
}