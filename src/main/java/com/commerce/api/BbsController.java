package com.commerce.api;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.commerce.module.COM.COMService;
import com.commerce.module.COM.vo.SCOM001SVO;
import com.commerce.service.BBS.BBSService;
import com.commerce.service.BBS.vo.BBS0101S01S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/service")
public class BbsController{
    @Autowired
    private BBSService bbsService;

    @PostMapping("/BBS0101U01")
    public String uploadPost(@ModelAttribute  BBS0101S01S req) throws UserException {

        // 파일 및 데이터 확인
        System.out.println("Title: " + req.getTitle());
        System.out.println("Is Notice: " + req.isNotice());
        System.out.println("Contents: " + req.getContents());

        if (req.getImages() != null && !req.getImages().isEmpty()) {
            for (MultipartFile file : req.getImages() ) {
                System.out.println("Received file: " + file.getOriginalFilename());
            }
        }

        bbsService.BBS0101U01(req);

        return "Upload successful";
    }
}
