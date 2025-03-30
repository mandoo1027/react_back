package com.commerce.service.MNU.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class MNU0101S01S {
    private String type; // 메뉴타입
    private String sysDivCd;
    private String menuCode;
    private String menuName;
    private String use_yn;
    private String menuScrDev;
    private String children;
    private String fileCtn;
    private String filePath;
    private String lastChgDt;
    private String lastUserId;
    private String loginYn;
    private String logoCss;
    private String menuCss;
    private String menuDepth;
    private String menuSeq;
    private String navigator;
    private String rgtrDt;
    private String rgtrUserId;
    private String rowStatus;
    private String scrCtn;
    private String upperMenuCode;
    private String useEndDate;
    private String useInstDiv;
    private String useStrtDate;
    private String useYn;
    private List<MNU0101S01S> menuList;
    private int startNum;
    private int endNum;
}
