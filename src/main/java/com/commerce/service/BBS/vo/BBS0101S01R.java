package com.commerce.service.BBS.vo;

import com.commerce.module.CMN.FileVO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@Component
@Data
public class BBS0101S01R {

    private int postId;
    private String rowStatus;
    private String title;
    private boolean isNotice;
    private String contents;
    private String rgtrUserName;  // 등록자 이름
    private String last_user_name;  // 등록자 이름
    private String rgtrDt; // 등록 일시
    private String lastUserId;   // 마지막 수정자 ID
    private String lastChgAt; // 마지막 수정 일시
    private Integer rowNum;

    private List<FileVO> attachFile;
    private List<FileVO> imgFiles;
}
