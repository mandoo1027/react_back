package com.commerce.api;
import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.commerce.service.GoogleCloudVisionService;
import com.commerce.service.HCO.HCO0101Service;
import com.commerce.service.HCO.vo.AdminVO;
import com.commerce.service.HCO.vo.HCO0101S01S;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/service")
public class VisionController {

    @Autowired
    private GoogleCloudVisionService visionService;
    @Autowired
    HCO0101Service hco0101Service;

    @Autowired
    private HttpSession session;

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

    @GetMapping("/otp/generate")
    public String generateOtpSecret(@RequestParam String userId) {
        return visionService.generateOtpSecret(userId);
    }

    @GetMapping("/otp/verify")
    public ResultVO verifyOtp(@RequestParam String userId, @RequestParam String otp)  {
        HCO0101S01S req = new HCO0101S01S();
        req.setId(userId);

        List<AdminVO> rvo = hco0101Service.HCO0101S01(req);
        AdminVO adminVo = rvo.get(0);
        String otpSecret = adminVo.getOtpSkey();
        int opt = Integer.parseInt(otp);
        boolean result = visionService.verifyOtp(otpSecret,opt);
        ResultVO vo = new ResultVO();
        if(result) {
             vo.setSucessCode();

            session.setAttribute("user", adminVo);

            //초기화
            adminVo.setPwd("");
            adminVo.setOtpSkey("");
            adminVo.setUseStatCd("");
            vo.setResultData(adminVo);
        } else {
            vo.setFailCode();
        }
        return vo;
    }
}