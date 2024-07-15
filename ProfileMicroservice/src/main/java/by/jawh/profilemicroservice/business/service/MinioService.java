package by.jawh.profilemicroservice.business.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioService implements InitializingBean {

    private final MinioClient minioClient;

    public void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
    }

    public String getObjectUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String bucketName = "avatars";
        boolean bucketIsEmpty = !minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (bucketIsEmpty) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }
}

