package com.lovememoir.server.api;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.lovememoir.server.domain.diary.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class FileStore {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String getFullPath(String storeFileName) {
        return String.valueOf(amazonS3Client.getUrl(bucket, storeFileName));
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            log.info("storeFiles for-loop");
            if (!multipartFile.isEmpty()) {
                log.info("storeFiles !isEmpty()");
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        log.info("storeFile 1");

        if (multipartFile.isEmpty()) {
            return null;
        }
        log.info("storeFile 2");

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        log.info("storeFile 3");

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        log.info("storeFile 4");
        amazonS3Client.putObject(bucket, storeFileName, multipartFile.getInputStream(), metadata);

        log.info("metadata" + metadata);

        return UploadFile.builder()
            .uploadFileName(originalFilename)
            .storeFileUrl(amazonS3Client.getUrl(bucket, storeFileName).toString())
            .build();
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
