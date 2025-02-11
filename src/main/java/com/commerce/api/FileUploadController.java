package com.commerce.api;


import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.commerce.module.CMN.FileModule;
import com.commerce.module.CMN.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin/service")
public class FileUploadController {
    @Autowired
    private FileModule fileModule;

    @PostMapping("/imageUpload")
    public ResultVO uploadFile(List<MultipartFile> files, String type ) throws UserException {

        // MultipartFile 여러개 들어왔을때 아래 구현해줘
        List<HashMap> results = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                HashMap fileInfo = fileModule.fileUpload(file,type);
                results.add(fileInfo);
            }
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setResultData(results);
        resultVO.setSucessCode();
        return resultVO;
    }

    @PostMapping("/fileList")
    public ResultVO fileList(@RequestBody FileVO vo) throws UserException {
        FileVO fileData = fileModule.fileList(vo);
        ResultVO resultVO = new ResultVO();
        resultVO.setResultData(fileData);
        resultVO.setSucessCode();
        return resultVO;
    }


}
