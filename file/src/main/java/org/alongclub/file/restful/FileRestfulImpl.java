package org.alongclub.file.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
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

    @RequestMapping(value = "/s3/bucket/listAll", method = RequestMethod.GET)
    public List<String> showAllBuckets() {

        Region region = Region.AP_NORTHEAST_1;
        S3Client s3 = S3Client.builder().region(region).build();
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        List<String> result = new ArrayList<>();
        listBucketsResponse.buckets().stream().forEach(x -> result.add(x.name()));
        return result;
    }

    @RequestMapping(value = "/s3/object/listAll", method = RequestMethod.GET)
    public List<String> showAllObjects(@RequestParam("bucketName") String bucketName) {

        Region region = Region.AP_NORTHEAST_1;
        S3Client s3 = S3Client.builder().region(region).build();
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response listObjectsV2Response = s3.listObjectsV2(request);
        List<String> result = new ArrayList<>();
        listObjectsV2Response.contents().stream().forEach(x -> result.add(x.key()));
        return result;
    }

    @RequestMapping(value = "/s3/object/get", method = RequestMethod.GET)
    public Object showAllObjects(@RequestParam("bucketName") String bucketName,
                                 @RequestParam("key") String key) {

        Region region = Region.AP_NORTHEAST_1;
        S3Client s3 = S3Client.builder().region(region).build();
//        GetObjectResponse getObjectResponse = s3.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build(),
//                ResponseTransformer.toFile(Paths.get("d:\\" + key)));


        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();
        // Create a GetObjectPresignRequest to specify the signature duration
        GetObjectPresignRequest getObjectPresignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();
        // Generate the presigned request
        S3Presigner presigner = S3Presigner.builder().region(Region.AP_NORTHEAST_1).build();
        PresignedGetObjectRequest presignedGetObjectRequest =
                presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url();
    }

}