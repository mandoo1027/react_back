package com.commerce.service.BBS.vo;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Component
@Data
public class BBS0101S01S {

    private Integer postId;
    private String rowStatus;
    private String title;
    private Boolean isNotice;
    private String contents;
    private String rgtrUserName;
    private List<HashMap> attachFile;
    private List<HashMap> imgFiles;
    private List<BBS0101S01S> bbsList;

    private Integer currentPage;
    private Integer pageSize;
}
