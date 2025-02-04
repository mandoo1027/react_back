package com.commerce.service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import java.nio.file.Paths;
@Service
public class GoogleCloudVisionService {

    public String detectText(MultipartFile file) throws IOException {
        // Google Credentials 직접 로드
        String jsonPath = Paths.get(System.getProperty("user.dir"), "google-credentials.json").toString();
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(jsonPath))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(
                ImageAnnotatorSettings.newBuilder()
                        .setCredentialsProvider(() -> credentials)
                        .build())) {

            // 이미지 파일을 바이트 배열로 변환
            ByteString imgBytes = ByteString.copyFrom(file.getBytes());

            // Vision API 이미지 객체 생성
            Image img = Image.newBuilder().setContent(imgBytes).build();

            // 텍스트 감지 기능 설정
            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();

            // 요청 객체 생성
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();

            // API 호출
            List<AnnotateImageRequest> requests = List.of(request);
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);

            // 텍스트 추출 결과
            TextAnnotation annotation = response.getResponsesList().get(0).getFullTextAnnotation();
            return annotation != null ? annotation.getText() : "No text found";
        }
    }
}