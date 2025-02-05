package com.commerce.api;


import com.commerce.comm.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/admin/service")
public class FileUploadController {
    @Value("${file.home-dir}") // 파일 경로
    private String homeDir;

    @Value("${file.images-upload-dir}") // 설정값 가져오기
    private String imageUploadDir;

    @PostMapping("/imageUpload")
    public ResultVO uploadFile(@RequestParam("file") MultipartFile file ) {
        ResultVO vo = new ResultVO();
        try {
            // 저장할 디렉토리 생성
            String uploadDir = imageUploadDir + File.separator + "tempImages";
            Path uploadPath = Paths.get(homeDir+ uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);//파일 생성
            }

            // 현재 날짜 및 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String formattedDateTime = now.format(formatter);

            // 원본 파일 확장자 추출
            String extension = "";
            int dotIndex = file.getOriginalFilename().lastIndexOf(".");
            if (dotIndex > 0) { // 확장자가 존재하는 경우
                extension = file.getOriginalFilename().substring(dotIndex);
            }

            // 새로운 파일명 생성 (예: "file_20250205_153045.png")
            //return "file_" + formattedDateTime + extension;
            // 파일 저장
            //String filePath = uploadDir + File.separator + file.getOriginalFilename();
            String filePath = uploadDir + File.separator + formattedDateTime + extension;
            file.transferTo(new File(homeDir+filePath));


            String siteUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + filePath;


            vo.setResultData(siteUrl);
            vo.setSucessCode();
            vo.setSuccess(true);
        } catch (IOException e) {
            vo.setFailCode();
            vo.setSuccess(false);

        }
        return vo;
    }
}
