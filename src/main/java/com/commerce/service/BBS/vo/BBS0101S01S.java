package com.commerce.service.BBS.vo;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Data
public class BBS0101S01S {

    private String rowStatus;
    private String title;
    private boolean isNotice;
    private String contents;


    private List<MultipartFile> images;
}
