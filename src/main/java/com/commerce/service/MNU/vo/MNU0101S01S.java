package com.commerce.service.MNU.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MNU0101S01S {
    private String type; // 메뉴타입
    private String sysDivCd;
    private String menuCode;
    private String menuName;
    private String use_yn;
    private String menuScrDev;

}
