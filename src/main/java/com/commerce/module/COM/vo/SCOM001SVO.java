package com.commerce.module.COM.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class SCOM001SVO {
    private String rowStatus;
    private String etcCodeOrName;
    private String chk;

    private String grpCode;
    private String grpName;
    private String code;
    private String name;
    private String code2;
    private String name2;
    private String code3;
    private String name3;
    private String code4;
    private String name4;
    private String code5;
    private String name5;
    private String seq;
    private String useYn;

    private List<SCOM001SVO> list;


    private int startNum;
    private int endNum;
}
