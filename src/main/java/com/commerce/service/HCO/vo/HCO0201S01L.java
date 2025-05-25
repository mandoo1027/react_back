package com.commerce.service.HCO.vo;

import com.commerce.comm.RequestPayload;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class HCO0201S01L extends RequestPayload {
    /* id, name으로 검색시 필요*/
    private String searchTxt;
    /* 권한 그룹 코드 */
    private String authGradeCd;
}
