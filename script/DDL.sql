CREATE DATABASE teo;


CREATE TABLE menu (
    sys_div_cd VARCHAR(10) NOT NULL COMMENT '시스템 구분 코드: 예) EFM, ADM',
    menu_scr_dev VARCHAR(1) NOT NULL COMMENT '메뉴 화면 구분: M(메뉴), S(화면), N(네비게이션)',
    menu_code VARCHAR(10) NOT NULL COMMENT '메뉴 코드: M + 업무구분(3자리) + 순번(3자리)',
    menu_name VARCHAR(100) NOT NULL COMMENT '메뉴 또는 화면 이름',
    menu_depth INT NULL COMMENT '메뉴의 깊이 또는 레벨',
    menu_seq INT NULL COMMENT '메뉴 노출 순서: 동일 메뉴 깊이 내에서의 순서',
    upper_menu_code VARCHAR(10) NULL COMMENT '상위 메뉴 코드',
    file_path VARCHAR(255) NULL COMMENT '화면 경로: 예) /COM/COM002M00',
    login_yn CHAR(1) NULL COMMENT '로그인 필요 여부: Y(예), N(아니오)',
    scr_ctn TEXT NULL COMMENT '화면 설명',
    menu_css VARCHAR(255) NULL COMMENT '메뉴에 적용될 CSS 스타일',
    use_end_date VARCHAR(8) NULL COMMENT '메뉴 사용 종료일',
    use_strt_date VARCHAR(8) NULL COMMENT '메뉴 사용 시작일',
    use_yn CHAR(1) NOT NULL COMMENT '메뉴 사용 여부: Y(사용), N(미사용)',
    rgtr_user_id VARCHAR(50) NULL COMMENT '메뉴를 등록한 사용자 ID',
    rgtr_dt VARCHAR(14) NOT NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '메뉴가 등록된 날짜와 시간',
    last_user_id VARCHAR(50) NULL COMMENT '메뉴를 마지막으로 수정한 사용자 ID',
    last_chg_dt VARCHAR(14) NOT NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '메뉴가 마지막으로 수정된 날짜와 시간',
    PRIMARY KEY (sys_div_cd, menu_code)  -- 시스템구분과 메뉴코드를 복합키로 설정
) COMMENT='시스템 메뉴';


INSERT INTO menu
(sys_div_cd, menu_scr_dev, menu_code, menu_name, menu_depth, menu_seq, upper_menu_code, file_path, login_yn, scr_ctn, menu_css, use_end_date, use_strt_date, use_yn, rgtr_user_id, rgtr_dt, last_user_id, last_chg_dt)
values
('ADM', 'N', 'AHOM001', '홈', 1, 1, NULL, '/LOG/LOG001M00', 'N', '메인화면', NULL, '99991223', '20240823', 'Y', 'admin', '20240823161712', 'admin', '20240823161712'),
('ADM', 'S', 'ALOG001', '로그인', 2, 1, 'AHOM001', '/LOG/LOG001M00', 'N', '로그인', NULL, '99991223', '20240823', 'Y', 'admin', '20240823163637', 'admin', '20240823163637'),
('ADM', 'M', 'ASYS000', '시스템관리', 2, 1, 'AHOM001', NULL, 'Y', '로그인', NULL, '99991223', '20240823', 'Y', 'admin', '20240823162850', 'admin', '20250312122706'),
('ADM', 'M', 'ASYS001', '메뉴관리', 3, 1, 'ASYS000', '/MNU/MNU001M01', 'Y', '메뉴관리 화면', NULL, '99991223', '20240823', 'Y', 'admin', '20240823163611', 'admin', '20240823163611'),
('ADM', 'M', 'ASYS002', '공통코드관리', 3, 2, 'ASYS000', '/ADM/ADM107M01', 'Y', '공통코드관리', NULL, '99991223', '20240823', 'Y', 'admin', '20240823164038', 'admin', '20240823164038'),
('ADM', 'M', 'ASYS003', '관리자 계정관리', 3, 3, 'ASYS000', '/ADM/ADM100M01', 'Y', '관리자 계정관리', NULL, '99991223', '20240823', 'Y', 'admin', '20240823164235', 'admin', '20240823164235'),
('ADM', 'M', 'ETC000', '기타', 2, 2, 'AHOM001', NULL, 'Y', NULL, NULL, '99991231', '20250312', 'Y', 'admin', '20250312122648', 'admin', '20250312122706'),
('ADM', 'S', 'ETC001', 'OCR(온문상)', 3, 1, 'ETC000', '/ETC/ETC001M01', 'Y', NULL, NULL, '99991231', '20250312', 'Y', 'admin', '20250312122648', 'admin', '20250312122648'),
('ADM', 'S', 'ETC002', 'OCR(사업자등록증)', 3, 2, 'ETC000', '/ETC/ETC001M02', 'Y', NULL, NULL, '99991231', '20250312', 'Y', 'admin', '20250312122648', 'admin', '20250312122716');
CREATE TABLE member (
    mem_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '회원ID',
    mem_div_cd VARCHAR(10) NOT NULL COMMENT '회원구분',
    nlty_div_cd VARCHAR(10) NOT NULL COMMENT '내외국인구분',
    mem_nm VARCHAR(100) NOT NULL COMMENT '회원성명',
    mem_pw VARCHAR(255) NOT NULL COMMENT '비밀번호',
    mem_brth VARCHAR(8) NOT NULL COMMENT '생년월일 (YYYYMMDD 형식)',
    mem_se_cd CHAR(1) NOT NULL COMMENT '성별 (M/F)',
    mobl_telno1 VARCHAR(3) NULL COMMENT '전화번호1',
    mobl_telno2 VARCHAR(4) NULL COMMENT '전화번호2',
    mobl_telno3 VARCHAR(4) NULL COMMENT '전화번호3',
    umsmail VARCHAR(255) NULL COMMENT '이메일주소',
    ntcn_chnl_div_cd VARCHAR(10) NULL COMMENT '알림채널구분',
    mem_join_stat_cd VARCHAR(10) NULL COMMENT '회원가입상태',
    mem_join_date VARCHAR(8) NULL COMMENT '회원가입일자',
    mem_cncl_date VARCHAR(8) NULL COMMENT '회원탈회일자',
    mem_cert_div_cd VARCHAR(10) NULL COMMENT '회원인증방법',
    mem_cncl_resn TEXT NULL COMMENT '탈회사유',
    remark TEXT NULL COMMENT '비고',
    prvc_ogcr_vrify_dt VARCHAR(14) NULL COMMENT '개인인증서검증일시 (YYYYMMDDHH24MISS 형식)',
    last_chnl_cert_dt VARCHAR(14) NULL COMMENT '최종채널인증일시 (YYYYMMDDHH24MISS 형식)',
    last_login_dt VARCHAR(14) NULL COMMENT '최종 로그인 일시 (YYYYMMDDHH24MISS 형식)',
    login_err_cnt INT(11) DEFAULT 0 COMMENT '로그인 오류 횟수',
    rgtr_user_id VARCHAR(50) NULL COMMENT '등록자ID',
    rgtr_dt VARCHAR(14) NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '등록일시',
    last_user_id VARCHAR(50) NULL COMMENT '변경자ID',
    last_chg_dt VARCHAR(14) NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '변경일시',
    mem_pw_chg_date VARCHAR(8) NULL COMMENT '패스워드 변경일시 (YYYYMMDD 형식)'
) COMMENT='회원 테이블';


INSERT INTO member (
    mem_id, mem_div_cd, nlty_div_cd, mem_nm, mem_pw, mem_brth, mem_se_cd,
    mobl_telno1, mobl_telno2, mobl_telno3, umsmail, ntcn_chnl_div_cd,
    mem_join_stat_cd, mem_join_date, mem_cncl_date, mem_cert_div_cd, mem_cncl_resn,
    remark, prvc_ogcr_vrify_dt, last_chnl_cert_dt, last_login_dt, login_err_cnt,
    rgtr_user_id, rgtr_dt, last_user_id, last_chg_dt, mem_pw_chg_date
) VALUES (
    'mandoo1027', '1', '1', '김경', 'rkskekfk5', '19861027', '1',
    '010', '4732', '1808', 'mandoo1027@gmail.com', '1',
    '01', '20240813', '99991231', '', '',
    '', '', '', '', 0,
    '', '20240813141602', '', '20240813141602', ''
);

-- cmmn_code 테이블
CREATE TABLE cmmn_code (
    grp_code VARCHAR(20) NOT NULL,
    grp_name VARCHAR(100) NULL,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NULL,
    code2 VARCHAR(20) NULL,
    name2 VARCHAR(100) NULL,
    code3 VARCHAR(20) NULL,
    name3 VARCHAR(100) NULL,
    code4 VARCHAR(20) NULL,
    name4 VARCHAR(100) NULL,
    code5 VARCHAR(20) NULL,
    name5 VARCHAR(100) NULL,
    seq INT(11) NULL,
    valid_yn CHAR(1) NULL,
    rgtr_user_id VARCHAR(50) NULL,
    rgtr_dt VARCHAR(14) NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'),
    last_user_id VARCHAR(50) NULL,
    last_chg_dt VARCHAR(14) NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'),
    PRIMARY KEY (grp_code, code)
);


INSERT INTO cmmn_code (
    grp_code, grp_name, code, name, code2, name2, code3, name3, code4, name4, code5, name5, seq, valid_yn, rgtr_user_id, rgtr_dt, last_user_id, last_chg_dt
) VALUES
('MEM_DIV_CD', '회원구분', '01', '회원', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('MEM_DIV_CD', '회원구분', '02', '비회원', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('NLTY_DIV_CD', '내외국인구분', '01', '내국인', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('NLTY_DIV_CD', '내외국인구분', '02', '외국인', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('MEM_SE_CD', '성별 (M/F)', 'M', '남자', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('MEM_SE_CD', '성별 (M/F)', 'F', '여자', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 6, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('MEM_JOIN_STAT_CD', '회원가입상태', '01', '승인대기', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 7, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('MEM_JOIN_STAT_CD', '회원가입상태', '02', '승인', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 8, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('MEM_JOIN_STAT_CD', '회원가입상태', '03', '탈퇴대기', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 9, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('MEM_JOIN_STAT_CD', '회원가입상태', '04', '탈퇴', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 10, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('ERROR_CODE', '에러코드', 'LOGIN_ERROR', '로그인 에러가 발생했습니다.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 11, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('ERROR_CODE', '에러코드', 'MEM001', '아이디를 입력하세요.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 12, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('ERROR_CODE', '에러코드', 'MEM002', '아이디 또는 비밀번호가 일치하지 않습니다.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 13, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('SYS_DIV_CD', '시스템구분코드', 'ADM', '관리자', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Y', 'system', '20240822205408', 'system', '20240822205408'),
('ERROR_CODE', '에러코드', 'SESSION_EXPIRED', '세션이 만료되었습니다. 재로그인 부탁드립니다.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 14, 'Y', 'system', '20250113163635', 'system', '20250113163635');


CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시글 고유 ID',
    title VARCHAR(255) NOT NULL COMMENT '게시글 제목',
    content TEXT NOT NULL COMMENT '게시글 본문 (HTML 포함)',
    rgtr_user_id VARCHAR(255) NOT NULL COMMENT '작성자 ID',
    rgtr_dt VARCHAR(14) NOT NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '등록일자',
    last_user_id VARCHAR(255) NOT NULL COMMENT '마지막 수정자 ID',
    last_chg_at VARCHAR(14) NOT NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '최종 수정일자'
) COMMENT='게시글 테이블';



CREATE TABLE files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '파일 고유 ID',
    post_id BIGINT NOT NULL COMMENT '연결된 게시글 ID (posts 테이블 참조)',
    file_sequence INT NOT NULL COMMENT '파일 시퀀스 (게시글에 첨부된 순서)',
    original_file_name VARCHAR(255) NOT NULL COMMENT '파일 원본 이름',
    server_file_name VARCHAR(255) NOT NULL COMMENT '서버에 저장된 파일 이름',
    file_path VARCHAR(255) NOT NULL COMMENT '파일 저장 경로',
    rgtr_user_id VARCHAR(255) NOT NULL COMMENT '등록자 ID',
    rgtr_dt VARCHAR(14) NOT NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '등록일자',
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
) COMMENT='파일 테이블 (게시글 첨부파일 저장)';

CREATE TABLE ADMIN (
    ID VARCHAR(50) PRIMARY KEY COMMENT '사용자 ID (기본 키)',
    PWD VARCHAR(100) NOT NULL COMMENT '비밀번호 (암호화된 형태로 저장)',
    NAME VARCHAR(100) NOT NULL COMMENT '사용자 이름',
    IDNO VARCHAR(20) NULL COMMENT '주민등록번호 또는 식별 번호',
    IP VARCHAR(45) NULL COMMENT '접속 IP 주소 (IPv6까지 고려해 45자 설정)',
    MOBL_TELNO1 VARCHAR(3) NULL COMMENT '모바일 전화번호 앞자리',
    MOBL_TELNO2 VARCHAR(4) NULL COMMENT '모바일 전화번호 중간자리',
    MOBL_TELNO3 VARCHAR(4) NULL COMMENT '모바일 전화번호 끝자리',
    EMAIL VARCHAR(100) NULL COMMENT '이메일 주소',
    USE_STAT_CD VARCHAR(10) NOT NULL COMMENT '사용 상태 코드 (1: 승인, 9: 승인요청, 3: 거부)',
    USE_STRT_DATE VARCHAR(8) NOT NULL COMMENT '사용 시작일 (YYYYMMDD 형식)',
    USE_END_DATE VARCHAR(8) NOT NULL COMMENT '사용 종료일 (YYYYMMDD 형식, 예: 99991231)',
    MENU_AUTH TEXT NULL COMMENT '메뉴 권한 정보',
    DEPT_CD VARCHAR(10) NULL COMMENT '부서 코드',
    DEPT_NM VARCHAR(100) NULL COMMENT '부서 이름',
    MEMO TEXT NULL COMMENT '메모',
    OTP_SKEY VARCHAR(50) NULL COMMENT 'OTP 시크릿 키',
    RGTR_USER_ID VARCHAR(50) NULL COMMENT '등록자 ID',
    RGTR_DT VARCHAR(14) NOT NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '등록 일시',
    LAST_USER_ID VARCHAR(50) NULL COMMENT '마지막 수정자 ID',
    LAST_CHG_DT VARCHAR(14) NOT NULL DEFAULT DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') COMMENT '마지막 수정 일시',
    LOGIN_ERR_CNT INT DEFAULT 0 COMMENT '로그인 오류 횟수 (초기값 0)'
) COMMENT='관리자 계정 테이블';


INSERT INTO teo.admin
(ID, PWD, NAME, IDNO, IP, MOBL_TELNO1, MOBL_TELNO2, MOBL_TELNO3, EMAIL, USE_STAT_CD, USE_STRT_DATE, USE_END_DATE, MENU_AUTH, DEPT_CD, DEPT_NM, MEMO, OTP_SKEY, RGTR_USER_ID, RGTR_DT, LAST_USER_ID, LAST_CHG_DT, LOGIN_ERR_CNT)
VALUES('admin', '1234', '김경일', NULL, '199.123.1.222', NULL, NULL, NULL, 'admin@example.com', '1', '20240824', '99991231', 'S', NULL, '금감원', NULL, '3EMKK74BS5ZAQLOO', 'admin', '20240824201204', 'admin', '20240824201204', 0);
INSERT INTO teo.admin
(ID, PWD, NAME, IDNO, IP, MOBL_TELNO1, MOBL_TELNO2, MOBL_TELNO3, EMAIL, USE_STAT_CD, USE_STRT_DATE, USE_END_DATE, MENU_AUTH, DEPT_CD, DEPT_NM, MEMO, OTP_SKEY, RGTR_USER_ID, RGTR_DT, LAST_USER_ID, LAST_CHG_DT, LOGIN_ERR_CNT)
VALUES('pack', '1234', '박희찬', NULL, '199.123.1.222', NULL, NULL, NULL, 'admin@example.com', '1', '20240824', '99991231', 'S', NULL, '금감원', NULL, 'WOTTHNU4SWL2ULVM', 'admin', '20240824201204', 'admin', '20240824201204', 0);