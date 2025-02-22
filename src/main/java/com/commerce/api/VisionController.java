package com.commerce.api;
import com.commerce.service.GoogleCloudVisionService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
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

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    @GetMapping("/generate")
    public String generateOtpSecret() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secret = key.getKey();  // 사용자에게 제공할 Secret Key

        // QR 코드 URL 생성 (Google Authenticator 앱에서 스캔 가능)
        String qrCodeUrl = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(
                "MyApp", "user@example.com", key);

        return "Secret Key: " + secret + "<br>QR Code URL: <a href=\"" + qrCodeUrl + "\">Scan QR</a>";
    }

    @PostMapping("/verify")
    public boolean verifyOtp(@RequestParam String secret, @RequestParam int otp) {
        return gAuth.authorize(secret, otp);
    }
}