package com.commerce.api;

import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/admin/service")
public class OcrController {



    @Value("${file.home-dir}") // 파일 경로
    private String homeDir;

    @Value("${file.tmp-file-upload-dir}") // 임시 저장소에 저장
    private String tmpfileUploadDir;

    @PostMapping("/getOcrData")
    public ResultVO uploadFile(List<MultipartFile> files ) throws UserException {
        ResultVO resultVO = new ResultVO();
        HttpClient client = HttpClients.createDefault();
        String url = "http://mandoo1027.tplinkdns.com:5001/ocr";  // 외부 API URL
        HttpPost post = new HttpPost(url);

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 여러 파일을 처리
            for (MultipartFile file : files) {
                // 임시로 파일 저장
                String uploadDir = tmpfileUploadDir ;

                LocalDateTime now = LocalDateTime.now();
                String yyyymmdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                Path uploadPath = Paths.get(homeDir + File.separator+ uploadDir+File.separator+yyyymmdd+File.separator);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssnnnnnnnnn");
                String formattedDateTime = now.format(formatter);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);//파일 생성
                }

                // 원본 파일 확장자 추출
                String extension = "";
                int dotIndex = file.getOriginalFilename().lastIndexOf(".");
                if (dotIndex > 0) { // 확장자가 존재하는 경우
                    extension = file.getOriginalFilename().substring(dotIndex);
                }

                // 새로운 파일명 생성 (예: "file_20250205_153045.png")
                //return "file_" + formattedDateTime + extension;
                // 파일 저장
                 String filePath = uploadDir +File.separator +yyyymmdd+File.separator+ formattedDateTime + extension;
                 File newFile = new File(homeDir+filePath);
                 file.transferTo(newFile);

                // 파일을 요청의 'files' 파라미터로 추가
                builder.addBinaryBody("files", newFile, ContentType.APPLICATION_OCTET_STREAM, file.getOriginalFilename());
                // 파일 전송 후 삭제
                //tempFile.delete();
            }

            // 요청 본문 설정
            HttpEntity entity = builder.build();
            post.setEntity(entity);
            // HTTP 요청 실행
            try (CloseableHttpResponse response = (CloseableHttpResponse) client.execute(post)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseString = EntityUtils.toString(response.getEntity());


                Gson gson = new Gson();
                Object jsonString = gson.fromJson(responseString,Object.class);
                if (statusCode == 200) {resultVO.setSucessCode();
                    resultVO.setResultData(jsonString);  // 응답 처리
                } else {
                    resultVO.setResultData("Error: " + statusCode);
                }

            }

        } catch (IOException e) {
            throw new UserException("파일 업로드 중 오류가 발생했습니다.");
        }

        return resultVO;
    }
}
