package com.commerce.service.MNU.vo;

import lombok.Data;

import java.util.List;

@Data
public class MNUMenu  implements Comparable<MNUMenu> {
    private String fileCtn;
    private String filePath;
    private String lastChgDt;
    private String lastUserId;
    private String loginYn;
    private String logoCss;
    private String menuCode;
    private String menuCss;
    private String menuDepth;
    private String menuScrDev;
    private List<String> navigator;
    private String menuSeq;
    private String rgtrDt;
    private String rgtrUserId;
    private String sysDivCd;
    private String upperMenuCode;
    private String useEndDate;
    private String useStrtDate;
    private List<MNUMenu> children;
    private String scrCtn;
    private String useInstDiv;


    // 메뉴 신

    private String systemType; // 행상태
    private String menuId; // 메뉴 ID
    private String menuName; // 메뉴 이름
    private String menuLevel; // 메뉴 레벨
    private String sortOrder; // 정렬 순서
    private String parentMenuId; // 상위 메뉴 ID
    private String parentMenuNm; // 상위 메뉴명
    private String screenId; // 화면 ID
    private String screenNm; // 화면 이름
    private String useYn; // 사용 여부
    private String displayYn; // 표시 여부
    private String menuGrpCode; // 메뉴 그룹 코드
    private String mappingCode; // 메뉴 그룹 코드
    private String screenPath; // 화면 경로


    @Override
    public int compareTo(MNUMenu other) {
        // upperMenuCode가 없는 경우를 우선순위로 처리
        if(this.upperMenuCode == null && other.upperMenuCode != null) {
            return -1;
        }

        if(this.upperMenuCode != null && other.upperMenuCode == null) {
            return 1;
        }

        Integer currentSeq = Integer.parseInt(this.menuSeq);
        Integer otherSeq = Integer.parseInt(other.menuSeq);

        return currentSeq.compareTo(otherSeq);
    }
}
