package com.commerce.service.BBS.vo;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Component
@Data
public class BBS0101S01S {

    private Integer postid;
    private String rowStatus;
    private String title;
    private Boolean isnotice;
    private String contents;
    private List<HashMap> attachFile;
    private List<HashMap> imgFiles;
}
