package com.commerce.service.SCR.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class SCR0101S01S {
    private String screenName;             // 화면 이름
    private String screenPath;             // 화면 경로(URL 또는 라우트)
    private String useYn;                  // 사용 여부 (Y/N)
    private int startNum;                  // 시작
    private int endNum;                 // 종료

}
