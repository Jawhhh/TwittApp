package by.jawh.postservice.business.service.util;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioService implements InitializingBean {

    private final MinioClient minioClient;

    public void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception {
        try(InputStream inputStream = file.getInputStream()) {
            log.info("Uploading file to MinIO. Bucket: {}, Object: {}, Content-Type: {}",
                    bucketName, objectName, file.getContentType());
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to upload file to MinIO", e);
            throw new Exception("Failed to upload file to MinIO", e);
        }

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
        String bucketName = "picture";
        boolean bucketIsEmpty = !minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (bucketIsEmpty) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


}
