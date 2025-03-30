package com.commerce.module.MEM.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SMEM001SVO {
    private String memId;                // 회원ID
    private String pw;                // pw
    int startNum;
    int endNum;
}
