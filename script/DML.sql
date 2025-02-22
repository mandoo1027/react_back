INSERT INTO MENU (
    SYS_DIV_CD,       -- 시스템구분
    MENU_SCR_DEV,     -- 메뉴화면구분 (N: 네비게이션)
    MENU_CODE,        -- 메뉴코드
    MENU_NAME,        -- 메뉴 또는 화면 이름
    MENU_DEPTH,       -- 메뉴 단계
    MENU_SEQ,         -- 메뉴 노출 순서
    UPPER_MENU_CODE,  -- 상위 메뉴 코드
    FILE_PATH,        -- 화면 경로 (없음)
    LOGIN_YN,         -- 로그인 필요 여부
    SCR_CTN,          -- 화면 설명
    MENU_CSS,         -- 메뉴 CSS
    USE_END_DATE,     -- 사용 종료 일자
    USE_STRT_DATE,    -- 사용 시작 일자
    USE_YN,           -- 사용 여부
    RGTR_USER_ID,     -- 등록자 ID
    RGTR_DT,          -- 등록 일시
    LAST_USER_ID,     -- 마지막 수정자 ID
    LAST_CHG_DT       -- 마지막 수정 일시
) VALUES (
    'ADM',           -- 시스템구분 (예시로 ADM 사용)
    'N',             -- 메뉴화면구분 (N: 네비게이션)
    'AHOM001',     -- 메뉴코드 (홈 : AHOM001)
    '홈',          -- 메뉴 이름
    1,               -- 메뉴 깊이 (최상위 메뉴)
    1,               -- 메뉴 노출 순서
    NULL,            -- 상위 메뉴 코드 (최상위 메뉴이므로 없음)
    NULL,            -- 화면 경로 (네비게이션 메뉴이므로 없음)
    'N',             -- 로그인 필요 여부 (로그인 필요)
    '메인화면', -- 화면 설명
    NULL,            -- 메뉴 CSS (없음)
    '99991223',            -- 사용 종료 일자 (없음)
    TO_CHAR(NOW(), 'YYYYMMDD'),    -- 사용 시작 일자 (현재 날짜)
    'Y',             -- 사용 여부 (사용 중)
    'admin',         -- 등록자 ID (예시로 admin 사용)
    TO_CHAR(NOW(), 'YYYYMMDDHH24MISS'), -- 등록 일시
    'admin',         -- 마지막 수정자 ID (예시로 admin 사용)
    TO_CHAR(NOW(), 'YYYYMMDDHH24MISS')  -- 마지막 수정 일시
);




INSERT INTO admin (
    ID, PWD, NAME, IDNO, IP,
    MOBL_TELNO1, MOBL_TELNO2, MOBL_TELNO3,
    EMAIL, USE_STAT_CD, USE_STRT_DATE, USE_END_DATE, MENU_AUTH,
    DEPT_CD, DEPT_NM, MEMO, OTP_SKEY,
    RGTR_USER_ID, RGTR_DT, LAST_USER_ID, LAST_CHG_DT, LOGIN_ERR_CNT
) VALUES (
    'admin',                                 -- 사용자 ID
    '1234',                                  -- 비밀번호 (실제 운영 환경에서는 해시 사용 필요)
    'Admin',                                 -- 사용자 이름
    NULL,                                    -- 주민등록번호 또는 식별 번호 (NULL)
    NULL,                                    -- 접속 IP 주소 (NULL)
    NULL,                                    -- 모바일 전화번호 앞자리 (NULL)
    NULL,                                    -- 모바일 전화번호 중간자리 (NULL)
    NULL,                                    -- 모바일 전화번호 끝자리 (NULL)
    'admin@example.com',                     -- 이메일 주소
    '1',                                     -- 사용 상태 코드 (1: 승인)
    TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD'),  -- 사용 시작일 (현재 날짜)
    '99991231',                              -- 사용 종료일 (사실상 무기한)
    NULL,                                    -- 메뉴 권한 정보 (NULL)
    NULL,                                    -- 부서 코드 (NULL)
    NULL,                                    -- 부서 이름 (NULL)
    NULL,                                    -- 메모 (NULL)
    NULL,                                    -- OTP 시크릿 키 (NULL)
    'admin',                                 -- 등록자 ID
    TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'), -- 등록 일시
    'admin',                                 -- 마지막 수정자 ID
    TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'), -- 마지막 수정 일시
    0                                        -- 로그인 오류 횟수 (초기값 0)
);

 



