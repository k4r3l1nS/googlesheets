package io.dtechs.googlesheets.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsService {

    @Value("${storage.bucket}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public boolean fileExists(String key) {

        try {
            return s3Client.doesObjectExist(bucketName, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<String> listIconKeys(String folderPath) {

        var objects = s3Client.listObjects(bucketName, folderPath);

        List<String> keys = new ArrayList<>();
        objects.getObjectSummaries().forEach(s3ObjectSummary -> {
            keys.add(s3ObjectSummary.getKey());
        });
        return keys;
    }

    public URL getIconUrl(String key) {

        try {
            if (fileExists(key)) {
                return s3Client.getUrl(bucketName, key);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
