-- teo.admin definition

CREATE TABLE `admin` (
  `id` varchar(50) NOT NULL,
  `pwd` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `idno` varchar(20) DEFAULT NULL,
  `ip` varchar(45) DEFAULT NULL,
  `mobl_telno1` varchar(3) DEFAULT NULL,
  `mobl_telno2` varchar(4) DEFAULT NULL,
  `mobl_telno3` varchar(4) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `use_stat_cd` varchar(10) NOT NULL,
  `use_strt_date` varchar(8) NOT NULL,
  `use_end_date` varchar(8) NOT NULL,
  `menu_auth` text DEFAULT NULL,
  `dept_cd` varchar(10) DEFAULT NULL,
  `dept_nm` varchar(100) DEFAULT NULL,
  `memo` text DEFAULT NULL,
  `otp_skey` varchar(50) DEFAULT NULL,
  `rgtr_user_id` varchar(50) DEFAULT NULL,
  `rgtr_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  `last_user_id` varchar(50) DEFAULT NULL,
  `last_chg_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  `login_err_cnt` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- teo.cmmn_code definition

CREATE TABLE `cmmn_code` (
  `grp_code` varchar(20) NOT NULL,
  `grp_name` varchar(100) DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `code2` varchar(20) DEFAULT NULL,
  `name2` varchar(100) DEFAULT NULL,
  `code3` varchar(20) DEFAULT NULL,
  `name3` varchar(100) DEFAULT NULL,
  `code4` varchar(20) DEFAULT NULL,
  `name4` varchar(100) DEFAULT NULL,
  `code5` varchar(20) DEFAULT NULL,
  `name5` varchar(100) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `use_yn` char(1) DEFAULT NULL,
  `rgtr_user_id` varchar(50) DEFAULT NULL,
  `rgtr_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  `last_user_id` varchar(50) DEFAULT NULL,
  `last_chg_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  PRIMARY KEY (`grp_code`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- teo.cmmn_grp_code definition

CREATE TABLE `cmmn_grp_code` (
  `grp_code` varchar(50) NOT NULL COMMENT '그룹코드',
  `grp_name` varchar(100) NOT NULL COMMENT '그룹코드명',
  `use_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `memo` varchar(500) DEFAULT NULL COMMENT '메모',
  `RGTR_USER_ID` varchar(50) DEFAULT NULL COMMENT '등록자 ID',
  `RGTR_DT` varchar(14) NOT NULL DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s') COMMENT '등록 일시',
  `LAST_USER_ID` varchar(50) DEFAULT NULL COMMENT '마지막 수정자 ID',
  `LAST_CHG_DT` varchar(14) NOT NULL DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s') COMMENT '마지막 수정 일시',
  PRIMARY KEY (`grp_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='공통 그룹코드';


-- teo.`member` definition

CREATE TABLE `member` (
  `mem_id` varchar(50) NOT NULL,
  `mem_div_cd` varchar(10) NOT NULL,
  `nlty_div_cd` varchar(10) NOT NULL,
  `mem_nm` varchar(100) NOT NULL,
  `mem_pw` varchar(255) NOT NULL,
  `mem_brth` varchar(8) NOT NULL,
  `mem_se_cd` varchar(1) NOT NULL,
  `mobl_telno1` varchar(4) DEFAULT NULL,
  `mobl_telno2` varchar(4) DEFAULT NULL,
  `mobl_telno3` varchar(4) DEFAULT NULL,
  `umsmail` varchar(100) DEFAULT NULL,
  `ntcn_chnl_div_cd` varchar(10) DEFAULT NULL,
  `mem_join_stat_cd` varchar(10) DEFAULT NULL,
  `mem_join_date` varchar(8) DEFAULT date_format(current_timestamp(),'%Y%m%d'),
  `mem_cncl_date` varchar(8) DEFAULT '99991231',
  `mem_cert_div_cd` varchar(10) DEFAULT NULL,
  `mem_cncl_resn` text DEFAULT NULL,
  `remark` text DEFAULT NULL,
  `prvc_ogcr_vrify_dt` varchar(14) DEFAULT NULL,
  `last_chnl_cert_dt` varchar(14) DEFAULT NULL,
  `last_login_dt` varchar(14) DEFAULT NULL,
  `login_err_cnt` int(11) DEFAULT 0,
  `rgtr_user_id` varchar(50) DEFAULT NULL,
  `rgtr_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  `last_user_id` varchar(50) DEFAULT NULL,
  `last_chg_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  `mem_pw_chg_date` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`mem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- teo.menu definition

CREATE TABLE `menu` (
  `sys_div_cd` varchar(10) NOT NULL,
  `menu_scr_dev` varchar(1) DEFAULT NULL,
  `menu_code` varchar(10) NOT NULL,
  `menu_name` varchar(100) DEFAULT NULL,
  `menu_depth` int(11) DEFAULT NULL,
  `menu_seq` int(11) DEFAULT NULL,
  `upper_menu_code` varchar(10) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `login_yn` char(1) DEFAULT NULL,
  `scr_ctn` text DEFAULT NULL,
  `menu_css` varchar(255) DEFAULT NULL,
  `use_end_date` varchar(8) DEFAULT NULL,
  `use_strt_date` varchar(8) DEFAULT NULL,
  `use_yn` char(1) DEFAULT NULL,
  `rgtr_user_id` varchar(50) DEFAULT NULL,
  `rgtr_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  `last_user_id` varchar(50) DEFAULT NULL,
  `last_chg_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s'),
  PRIMARY KEY (`sys_div_cd`,`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- teo.menu_temp definition

CREATE TABLE `menu_temp` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(100) NOT NULL COMMENT '메뉴 이름',
  `menu_level` int(11) NOT NULL COMMENT '메뉴 깊이 (1: 대메뉴, 2: 서브메뉴 등)',
  `sort_order` int(11) NOT NULL DEFAULT 1 COMMENT '정렬 순서',
  `parent_menu_id` int(11) DEFAULT NULL COMMENT '상위 메뉴 ID (NULL이면 루트)',
  `screen_id` int(11) DEFAULT NULL COMMENT '연결된 화면 ID',
  `use_yn` char(1) DEFAULT 'Y' COMMENT '사용 여부 (Y/N)',
  `display_yn` char(1) DEFAULT 'Y' COMMENT '메뉴 노출 여부 (Y/N)',
  `rgtr_user_id` varchar(50) DEFAULT NULL COMMENT '등록자 ID',
  `rgtr_dt` varchar(14) DEFAULT NULL COMMENT '등록일시 (YYYYMMDDHHMISS)',
  `last_user_id` varchar(50) DEFAULT NULL COMMENT '최종 수정자 ID',
  `last_chg_dt` varchar(14) DEFAULT NULL COMMENT '최종 수정일시 (YYYYMMDDHHMISS)',
  `system_type` varchar(100) DEFAULT NULL COMMENT '시스템 타입(admin)',
  `menu_grp_code` varchar(100) DEFAULT NULL COMMENT '메뉴 그룹코드(정렬용)',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci COMMENT='메뉴 관리 테이블';


-- teo.posts definition

CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `contents` text NOT NULL,
  `rgtr_user_id` varchar(255) NOT NULL,
  `rgtr_dt` varchar(14) DEFAULT NULL,
  `last_user_id` varchar(255) NOT NULL,
  `last_chg_at` varchar(14) DEFAULT NULL,
  `isnotice` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- teo.screen definition

CREATE TABLE `screen` (
  `screen_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '화면 ID',
  `screen_name` varchar(100) NOT NULL COMMENT '화면 이름',
  `screen_path` varchar(200) NOT NULL COMMENT '화면 경로(URL 또는 라우트)',
  `memo` varchar(500) DEFAULT NULL COMMENT '비고',
  `use_yn` char(1) DEFAULT 'Y' COMMENT '사용 여부 (Y/N)',
  `rgtr_user_id` varchar(50) DEFAULT NULL COMMENT '등록자 ID',
  `rgtr_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s') COMMENT '등록일시',
  `last_user_id` varchar(50) DEFAULT NULL COMMENT '최종 수정자 ID',
  `last_chg_dt` varchar(14) DEFAULT date_format(current_timestamp(),'%Y%m%d%H%i%s') COMMENT '최종 수정일시',
  `system_type` varchar(20) DEFAULT NULL COMMENT '시스템 유형',
  `mapping_code` varchar(100) DEFAULT NULL COMMENT '매핑코드(라우터)',
  PRIMARY KEY (`screen_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci COMMENT='화면 관리 테이블';


-- teo.files definition

CREATE TABLE `files` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `postid` int(11) DEFAULT NULL,
  `file_sequence` int(11) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `fileurl` varchar(255) DEFAULT NULL,
  `rgtr_user_id` varchar(255) NOT NULL,
  `rgtr_dt` varchar(14) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `realpath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `files_bbs_id_fkey` (`postid`),
  CONSTRAINT `files_bbs_id_fkey` FOREIGN KEY (`postid`) REFERENCES `posts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



INSERT INTO teo.screen (screen_name,screen_path,memo,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt,system_type,mapping_code) VALUES
	 ('홈','/LOG/LOG001M00','메인화면','Y','admin','20240823161712','admin','20250609220806','ADM','LOG001M00'),
	 ('로그인','/LOG/LOG001M00','로그인','Y','admin','20240823163637','admin','20250609220806','ADM','LOG001M00'),
	 ('메뉴관리(안씀)','/MNU/MNU001M01','메뉴관리 화면','N','admin','20240823163611','admin','20250618234348','ADM','MNU001M01'),
	 ('공통코드관리','/ADM/ADM107M01','공통코드관리','Y','admin','20240823164038','admin','20250609220806','ADM','ADM107M01'),
	 ('관리자 계정관리','/ADM/ADM100M01','관리자 계정관리','Y','admin','20240823164235','admin','20250609220806','ADM','ADM100M01'),
	 ('게시판관리','/BBS/BBS001M01','','Y','admin','20250324123903','admin','20250609220806','ADM','BBS001M01'),
	 ('사용자 권한 관리','/ADM/ADM101M01','','Y','admin','20250522235821','admin','20250609220806','ADM','ADM101M01'),
	 ('메뉴 권한 관리','/ADM/ADM201M01','','Y','admin','20250523000010','admin','20250609220806','ADM','ADM201M01'),
	 ('화면관리','/SCR/SCR001M01','','Y','admin','20250528122402','admin','20250618232348','ADM','SCR001M01'),
	 ('OCR(온문상)','/ETC/ETC001M01','','Y','admin','20250312122648','admin','20250609220806','ADM','ETC001M01');
INSERT INTO teo.screen (screen_name,screen_path,memo,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt,system_type,mapping_code) VALUES
	 ('OCR(사업자등록증)','/ETC/ETC001M02','','Y','admin','20250312122648','admin','20250312122716','ADM',NULL),
	 ('크림 로그인','/KRM/KRM001M01','','Y','admin','20250517052732','admin','20250609220806','ADM','KRM001M01'),
	 ('정산내역 조회','/KRM/KRM001M02','','Y','admin','20250517052732','admin','20250609220806','ADM','KRM001M02'),
	 ('보관신청 상품관리','/KRM/KRM001M03','','Y','admin','20250517052732','admin','20250609220806','ADM','KRM001M03'),
	 ('입찰보관신청 상품관리','/KRM/KRM001M05','','Y','admin','20250517052732','admin','20250609220806','ADM','KRM001M05'),
	 ('로그 확인','/KRM/KRM001M04','','Y','admin','20250517052732','admin','20250609220806','ADM','KRM001M04'),
	 ('메뉴관리','/MNU/MNU002M01',NULL,'Y','admin','20250615225908','admin','20250618234333','ADM','MNU002M01');
INSERT INTO teo.menu_temp (menu_name,menu_level,sort_order,parent_menu_id,screen_id,use_yn,display_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt,system_type,menu_grp_code) VALUES
	 ('시스템 관리',1,1,NULL,NULL,'Y','Y','admin','20250610055640','admin','20250612064151','ADM','1'),
	 ('크롤링',1,2,NULL,NULL,'Y','Y','admin','20250610130800','admin','20250618230433','ADM','2'),
	 ('관리자 계정관리',2,1,1,5,'Y','Y','admin','20250612064219','admin','20250613155536','ADM','1'),
	 ('크림 로그인',2,1,2,12,'Y','Y','admin','20250612064300',NULL,NULL,'ADM','2'),
	 ('화면관리',2,2,1,9,'Y','Y','admin','20250615225746',NULL,NULL,'ADM','1'),
	 ('메뉴관리',2,3,1,18,'Y','Y','admin','20250615225932','admin','20250618224440','ADM','1'),
	 ('권한그룹별 사용자 관리',2,5,1,8,'Y','Y','admin','20250615225956','admin','20250618234153','ADM','1'),
	 ('권한그룹별 메뉴 관리',2,4,1,7,'Y','Y','admin','20250615230024','admin','20250618234144','ADM','1'),
	 ('공통코드관리',2,6,1,4,'Y','Y','admin','20250615230041',NULL,NULL,'ADM','1'),
	 ('게시판관리',2,7,1,6,'Y','Y','admin','20250615230102',NULL,NULL,'ADM','1');
INSERT INTO teo.menu_temp (menu_name,menu_level,sort_order,parent_menu_id,screen_id,use_yn,display_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt,system_type,menu_grp_code) VALUES
	 ('정산내역 조회',2,2,2,13,'Y','Y','admin','20250615230133',NULL,NULL,'ADM','2'),
	 ('보관신청 상품관리',2,3,2,14,'Y','Y','admin','20250615230154',NULL,NULL,'ADM','2'),
	 ('입찰보관신청 상품관리',2,4,2,15,'Y','Y','admin','20250615230227',NULL,NULL,'ADM','2'),
	 ('로그확인',2,5,2,16,'Y','Y','admin','20250615230248',NULL,NULL,'ADM','2');

INSERT INTO teo.menu (sys_div_cd,menu_scr_dev,menu_code,menu_name,menu_depth,menu_seq,upper_menu_code,file_path,login_yn,scr_ctn,menu_css,use_end_date,use_strt_date,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt) VALUES
	 ('ADM','N','AHOM001','홈',1,1,NULL,'/LOG/LOG001M00','N','메인화면',NULL,'99991223','20240823','Y','admin','20240823161712','admin','20240823161712'),
	 ('ADM','S','ALOG001','로그인',2,1,'AHOM001','/LOG/LOG001M00','N','로그인',NULL,'99991223','20240823','Y','admin','20240823163637','admin','20240823163637'),
	 ('ADM','M','ASYS000','시스템관리',2,1,'AHOM001',NULL,'Y','로그인',NULL,'99991223','20240823','Y','admin','20240823162850','admin','20250312122706'),
	 ('ADM','M','ASYS001','메뉴관리',3,3,'ASYS000','/MNU/MNU001M01','Y','메뉴관리 화면',NULL,'99991223','20240823','Y','admin','20240823163611','admin','20250528122633'),
	 ('ADM','M','ASYS002','공통코드관리',3,6,'ASYS000','/ADM/ADM107M01','Y','공통코드관리',NULL,'99991223','20240823','Y','admin','20240823164038','admin','20250528122601'),
	 ('ADM','M','ASYS003','관리자 계정관리',3,1,'ASYS000','/ADM/ADM100M01','Y','관리자 계정관리',NULL,'99991223','20240823','Y','admin','20240823164235','admin','20250528122633'),
	 ('ADM','S','ASYS004','게시판관리',3,7,'ASYS000','/BBS/BBS001M01','Y',NULL,NULL,'99991231','20250324','Y','admin','20250324123903','admin','20250528122553'),
	 ('ADM','M','ASYS005','사용자 권한 관리',3,5,'ASYS000','/ADM/ADM101M01','Y',NULL,NULL,'99991231','20250522','Y','admin','20250522235821','admin','20250528122633'),
	 ('ADM','M','ASYS006','메뉴 권한 관리',3,4,'ASYS000','/ADM/ADM201M01','Y',NULL,NULL,'99991231','20250522','Y','admin','20250523000010','admin','20250616002653'),
	 ('ADM','M','ASYS007','화면관리',2,2,'ASYS000','/SCR/SCR001M01','Y',NULL,NULL,'99991231','20250528','Y','admin','20250528122402','admin','20250528230304');
INSERT INTO teo.menu (sys_div_cd,menu_scr_dev,menu_code,menu_name,menu_depth,menu_seq,upper_menu_code,file_path,login_yn,scr_ctn,menu_css,use_end_date,use_strt_date,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt) VALUES
	 ('ADM','M','ASYS008','메뉴관리(신)',3,3,'ASYS000','/MNU/MNU002M01','Y',NULL,NULL,'99991231','20250602','Y','admin','20250602125143','admin','20250602125143'),
	 ('ADM','M','ETC000','기타',2,2,'AHOM001',NULL,'Y',NULL,NULL,'99991231','20250312','Y','admin','20250312122648','admin','20250312122706'),
	 ('ADM','S','ETC001','OCR(온문상)',3,1,'ETC000','/ETC/ETC001M01','Y',NULL,NULL,'99991231','20250312','Y','admin','20250312122648','admin','20250312122648'),
	 ('ADM','S','ETC002','OCR(사업자등록증)',3,2,'ETC000','/ETC/ETC001M02','Y',NULL,NULL,'99991231','20250312','Y','admin','20250312122648','admin','20250312122716'),
	 ('ADM','M','KREAM000','크롤링',2,1,'AHOM001',NULL,'Y',NULL,NULL,'99991231','20250517','Y','admin','20250517052732','admin','20250517052759'),
	 ('ADM','S','KREAM001','크림 로그인',3,1,'KREAM000','/KRM/KRM001M01','Y',NULL,NULL,'99991231','20250517','Y','admin','20250517052732','admin','20250517052835'),
	 ('ADM','S','KREAM002','정산내역 조회 ',3,2,'KREAM000','/KRM/KRM001M02','Y',NULL,NULL,'99991231','20250517','Y','admin','20250517052732','admin','20250517052835'),
	 ('ADM','S','KREAM003','보관신청 상품관리',3,3,'KREAM000','/KRM/KRM001M03','Y',NULL,NULL,'99991231','20250517','Y','admin','20250517052732','admin','20250517052835'),
	 ('ADM','S','KREAM004','입찰보관신청 상품관리',3,4,'KREAM000','/KRM/KRM001M05','Y',NULL,NULL,'99991231','20250517','Y','admin','20250517052732','admin','20250517052835'),
	 ('ADM','S','KREAM005','로그 확인',3,5,'KREAM000','/KRM/KRM001M04','Y',NULL,NULL,'99991231','20250517','Y','admin','20250517052732','admin','20250517052835');
INSERT INTO teo.menu (sys_div_cd,menu_scr_dev,menu_code,menu_name,menu_depth,menu_seq,upper_menu_code,file_path,login_yn,scr_ctn,menu_css,use_end_date,use_strt_date,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt) VALUES
	 ('ADM','M','ㅁㄴㅇㄹ','ㅁㄴㄹ',1,1,NULL,NULL,'Y',NULL,NULL,'99991231','20250527','Y','admin','20250527131254','admin','20250527131254');
INSERT INTO teo.`member` (mem_id,mem_div_cd,nlty_div_cd,mem_nm,mem_pw,mem_brth,mem_se_cd,mobl_telno1,mobl_telno2,mobl_telno3,umsmail,ntcn_chnl_div_cd,mem_join_stat_cd,mem_join_date,mem_cncl_date,mem_cert_div_cd,mem_cncl_resn,remark,prvc_ogcr_vrify_dt,last_chnl_cert_dt,last_login_dt,login_err_cnt,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt,mem_pw_chg_date) VALUES
	 ('mandoo1027','1','1','김경','rkskekfk5','19861027','1','010','4732','1808','mandoo1027@gmail.com','1','01','20240813','99991231','','','','','','',0,'','20240813141602','','20240813141602','');
INSERT INTO teo.cmmn_grp_code (grp_code,grp_name,use_yn,memo,RGTR_USER_ID,RGTR_DT,LAST_USER_ID,LAST_CHG_DT) VALUES
	 ('AUTH_GRADE_CD','권한그룹','Y',NULL,'admin','20250521123500','admin','20250521123500'),
	 ('ERROR_CODE','에러코드','Y',NULL,'system','20250521123500','system','20250521123500'),
	 ('MEM_DIV_CD','회원구분','Y',NULL,'system','20250521123500','system','20250521123500'),
	 ('MEM_JOIN_STAT_CD','회원가입상태','Y',NULL,'system','20250521123500','system','20250521123500'),
	 ('MEM_SE_CD','성별 (M/F)','Y',NULL,'system','20250521123500','system','20250521123500'),
	 ('MENU_GRP_CODE','메뉴 그룹코드(관리자)','Y',NULL,'admin','20250604203453','admin','20250604203453'),
	 ('MENU_GRP_S_CD','메뉴 그룹코드(사용자)','Y',NULL,'admin','20250609203656','admin','20250609203656'),
	 ('MENU_SCR_DEV','화면구분코드','Y',NULL,'admin','20250525082407','admin','20250525082407'),
	 ('NLTY_DIV_CD','내외국인구분','Y',NULL,'system','20250521123500','system','20250521123500'),
	 ('SYS_DIV_CD','시스템구분코드','Y',NULL,'system','20250521123500','system','20250521123500');
INSERT INTO teo.cmmn_grp_code (grp_code,grp_name,use_yn,memo,RGTR_USER_ID,RGTR_DT,LAST_USER_ID,LAST_CHG_DT) VALUES
	 ('USE_STAT_CD','회원상태코드','Y',NULL,'admin','20250521233437','admin','20250521233437');
INSERT INTO teo.cmmn_code (grp_code,grp_name,code,name,code2,name2,code3,name3,code4,name4,code5,name5,seq,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt) VALUES
	 ('AUTH_GRADE_CD','권한등급','A','무료회원','ADM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'Y','admin','20250518151354','admin','20250518151354'),
	 ('AUTH_GRADE_CD','권한등급','B','유료회원','ADM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,'Y','admin','20250518151354','admin','20250518151354'),
	 ('AUTH_GRADE_CD','권한등급','C','클래식회원','ADM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,'Y','admin','20250518151354','admin','20250518151354'),
	 ('AUTH_GRADE_CD','권한등급','D','시스템관리자','ADM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,'Y','admin','20250518151354','admin','20250518151354'),
	 ('AUTH_GRADE_CD',NULL,'E','일반회원','ADM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,'Y','admin','20250524141401','admin','20250524141401'),
	 ('ERROR_CODE','에러코드','COM002','상세 코드가 존재합니다. 삭제 후 다시 시도해주세요',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y','admin','20250521233754','admin','20250521233754'),
	 ('ERROR_CODE',NULL,'COM003','이미 등록된 코드가 있습니다. 등록할 수 없습니다.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,'Y','admin','20250522130300','admin','20250522130300'),
	 ('ERROR_CODE','에러코드','LOGIN_ERROR','로그인 에러가 발생했습니다.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'Y','admin','20240822205408','admin','20240822205408'),
	 ('ERROR_CODE','에러코드','MEM001','아이디를 입력하세요.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,12,'Y','system','20240822205408','system','20240822205408'),
	 ('ERROR_CODE','에러코드','MEM002','아이디 또는 비밀번호가 일치하지 않습니다.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,13,'Y','system','20240822205408','system','20240822205408');
INSERT INTO teo.cmmn_code (grp_code,grp_name,code,name,code2,name2,code3,name3,code4,name4,code5,name5,seq,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt) VALUES
	 ('ERROR_CODE','에러코드','SESSION_EXPIRED','세션이 만료되었습니다. 재로그인 부탁드립니다.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,14,'Y','system','20250113163635','system','20250113163635'),
	 ('MEM_DIV_CD','회원구분','01','회원',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'Y','system','20240822205408','system','20240822205408'),
	 ('MEM_DIV_CD','회원구분','02','비회원',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,'Y','system','20240822205408','system','20240822205408'),
	 ('MEM_JOIN_STAT_CD','회원가입상태','01','승인대기',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,'Y','system','20240822205408','system','20240822205408'),
	 ('MEM_JOIN_STAT_CD','회원가입상태','02','승인',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,'Y','system','20240822205408','system','20240822205408'),
	 ('MEM_JOIN_STAT_CD','회원가입상태','03','탈퇴대기',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,9,'Y','system','20240822205408','system','20240822205408'),
	 ('MEM_JOIN_STAT_CD','회원가입상태','04','탈퇴',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,10,'Y','system','20240822205408','system','20240822205408'),
	 ('MEM_SE_CD','성별 (M/F)','F','여자',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,'Y','system','20240822205408','system','20240822205408'),
	 ('MEM_SE_CD','성별 (M/F)','M','남자',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,'Y','system','20240822205408','system','20240822205408'),
	 ('MENU_GRP_CODE',NULL,'CRAWLING','크롤링',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,'Y','admin','20250604203614','admin','20250604203614');
INSERT INTO teo.cmmn_code (grp_code,grp_name,code,name,code2,name2,code3,name3,code4,name4,code5,name5,seq,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt) VALUES
	 ('MENU_GRP_CODE',NULL,'ETC','기타',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,'Y','admin','20250604203614','admin','20250604203614'),
	 ('MENU_GRP_CODE',NULL,'SYSTEM','시스템관리',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'Y','admin','20250604203615','admin','20250604203615'),
	 ('MENU_SCR_DEV',NULL,'M','메뉴',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,'Y','admin','20250525082448','admin','20250525082448'),
	 ('MENU_SCR_DEV',NULL,'N','네비',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,'Y','admin','20250525082448','admin','20250525082448'),
	 ('MENU_SCR_DEV',NULL,'S','화면',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'Y','admin','20250525082448','admin','20250525082448'),
	 ('NLTY_DIV_CD','내외국인구분','01','내국인',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,'Y','system','20240822205408','system','20240822205408'),
	 ('NLTY_DIV_CD','내외국인구분','02','외국인',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,'Y','system','20240822205408','system','20240822205408'),
	 ('SYS_DIV_CD','시스템구분코드','ADM','관리자',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'Y','system','20240822205408','system','20240822205408'),
	 ('SYS_DIV_CD',NULL,'USR','사용자',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,'Y','admin','20250604205033','admin','20250604205033'),
	 ('USE_STAT_CD','회원상태코드','1','승인',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'Y','admin','20250518151236','admin','20250518151236');
INSERT INTO teo.cmmn_code (grp_code,grp_name,code,name,code2,name2,code3,name3,code4,name4,code5,name5,seq,use_yn,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt) VALUES
	 ('USE_STAT_CD','회원상태코드','2','승인요청',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,'Y','admin','20250518151236','admin','20250518151236'),
	 ('USE_STAT_CD','회원상태코드','3','승인거절',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,'Y','admin','20250518151236','admin','20250518151236'),
	 ('USE_STAT_CD','회원상태코드','4','회원탈퇴',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,'Y','admin','20250518151236','admin','20250518151236');
INSERT INTO teo.admin (id,pwd,name,idno,ip,mobl_telno1,mobl_telno2,mobl_telno3,email,use_stat_cd,use_strt_date,use_end_date,menu_auth,dept_cd,dept_nm,memo,otp_skey,rgtr_user_id,rgtr_dt,last_user_id,last_chg_dt,login_err_cnt) VALUES
	 ('admin','1234','김경일',NULL,'',NULL,NULL,NULL,'admin@example.com','02','20240824','99991231','D','','',NULL,'3EMKK74BS5ZAQLOO','admin','20240824201204','admin','20250526233931',0),
	 ('mandoo1027@naver.com','rkskekfk5%','ㅅㄷㄴㅅ',NULL,'','010','4732','1808','ㅅㄷㄴdddd','02','20250513','99991231','A','','','test','Y7HFB6QBLPEEP35E',NULL,'20250519230632','admin','20250526072728',0),
	 ('pack','1234','박희찬',NULL,'','010','4732','1808','admin@example.com','01','20240824','99991231','A','','','tttttt','WOTTHNU4SWL2ULVM','admin','20240824201204','admin','20250526233931',0);
