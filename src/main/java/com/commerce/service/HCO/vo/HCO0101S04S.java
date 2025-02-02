package com.commerce.service.HCO.vo;

import com.commerce.comm.RequestPayload;
import com.commerce.service.MNU.vo.MNU0101S01S;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class HCO0101S04S extends RequestPayload {
    private String id;              // 사용자 ID
    private String pwd;             // 비밀번호 (암호화된 형태로 저장)
    private String name;            // 사용자 이름
    private String idno;            // 주민등록번호 또는 식별 번호
    private String ip;              // 접속 IP 주소
    private String moblTelno1;      // 모바일 전화번호 앞자리
    private String moblTelno2;      // 모바일 전화번호 중간자리
    private String moblTelno3;      // 모바일 전화번호 끝자리
    private String phon;            // 전화번호 풀네임
    private String email;           // 이메일 주소
    private String useStatCd;       // 사용 상태 코드 (1: 승인, 9: 승인요청, 3: 거부)
    private String useStrtDate;     // 사용 시작일 (YYYYMMDD 형식)
    private String useEndDate;      // 사용 종료일 (YYYYMMDD 형식, 예: 99991231)
    private String menuAuth;        // 메뉴 권한 정보
    private String deptCd;          // 부서 코드
    private String deptNm;          // 부서 이름
    private String memo;            // 메모
    private String otpSkey;         // OTP 시크릿 키
    private String rgtrUserId;      // 등록자 ID
    private String rgtrDt;          // 등록 일시
    private String lastUserId;      // 마지막 수정자 ID
    private String lastChgDt;       // 마지막 수정 일시
    private int loginErrCnt;        // 로그인 오류 횟수 (초기값 0)
    private  String currentSessionId; // 현재 세션 ID
    private String rowStatus;
    private List<HCO0101S04S> adminList;
}
