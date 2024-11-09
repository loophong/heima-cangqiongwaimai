package com.sky.service.impl;


import com.sky.minio.MinioProperties;
import com.sky.service.FileService;
import io.minio.MinioClient;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
        if (!exists) {
            // 创建存储桶
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            // 设置存储桶的访问权限为公共读
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .config(createBucketPolicyConfig(minioProperties.getBucketName())).build());
        }
        // 上传文件
        String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .stream(file.getInputStream(), file.getSize(), -1)
                .object(filename)
                .contentType(file.getContentType())
                .build());

        // 返回文件访问路径
        return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + filename;


    }

    private String createBucketPolicyConfig(String bucketName) {

        return """
                {
                  "Statement" : [ {
                    "Action" : "s3:GetObject",
                    "Effect" : "Allow",
                    "Principal" : "*",
                    "Resource" : "arn:aws:s3:::%s/*"
                  } ],
                  "Version" : "2012-10-17"
                }
                """.formatted(bucketName);
    }
}

