package com.commerce.service.MNU.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class MNU0201S01S {
    private String systemType; // 시스템 구분 (ADM, USR)
    private Integer menuId;
    private String menuName;
    private Integer menuLevel;
    private Integer sortOrder;
    private Integer parentMenuId;
    private Integer screenId;
    private String useYn;
    private String menuGrpCode;
    private String displayYn;
    private String rgtrUserId;
    private String rgtrDt;
    private String lastUserId;
    private String lastChgDt;
    private List<MNU0201S01S> menuList;
    private int startNum;
    private int endNum;

    private String rowStatus;
}
