package com.commerce.api;
import com.commerce.service.GoogleCloudVisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ocr")
public class VisionController {

    @Autowired
    private GoogleCloudVisionService visionService;

    @PostMapping
    public ResponseEntity<List<String>> uploadImages(@RequestParam("images") MultipartFile[] files) {
        List<String> extractedTexts = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String text = visionService.detectText(file);
                extractedTexts.add(text);
            }
            return new ResponseEntity<>(extractedTexts, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(List.of("Error processing images"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}