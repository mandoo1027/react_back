package com.commerce.service.MNU.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class MNU0301S01S {
    private String systemType; // 시스템 타입 : ADM, USR
    private String id; // 코드
    private String code; // 코드
    private String menuId; //메뉴 ID
    private String authGradeCd; // 그룹코드
    List<String> menuIdList; // 메뉴 아이디 리스트

}
