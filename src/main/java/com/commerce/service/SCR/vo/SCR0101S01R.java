package com.commerce.service.SCR.vo;

import com.commerce.comm.CamelKeyMap;
import com.commerce.service.MNU.vo.MNUMenu;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class SCR0101S01R {
	private Integer screenId;              // 화면 ID
	private String screenName;             // 화면 이름
	private String screenPath;             // 화면 경로(URL 또는 라우트)
	private String memo;                   // 비고
	private String useYn;                  // 사용 여부 (Y/N)
	private String rgtrUserId;             // 등록자 ID
	private String rgtrDt;                 // 등록일시
	private String lastUserId;             // 최종 수정자 ID
	private String lastChgDt;              // 최종 수정일시
	private List<CamelKeyMap> scrList;             // 메뉴 목록
}
