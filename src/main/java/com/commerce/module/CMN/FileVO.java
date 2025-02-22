package com.commerce.module.CMN;

import lombok.Data;

import java.util.List;

@Data
public class FileVO {
    private Integer id;               // 파일 ID
    private Integer postId;          // 게시글 ID (posts 테이블과 연결)
    private Integer fileSequence;  // 파일 순서
    private String realName; // 원본 파일명
    private String fileName;   // 서버 저장 파일명
    private String fileUrl;         // 파일 경로
    private String realUrl;         // 파일 URL
    private String realPath;         // 파일 경로
    private String type;             // 파일 타입 (예: 이미지, 문서 등)
    private String rgtrUserId;       // 등록 사용자 ID
    private String rgtrUserName;       // 등록 사용자 ID
    private String lastUserName;       // 등록 사용자 ID
    private String lastChgAt;       // 등록 사용자 ID
    private String rgtrDt;    // 등록 일시
    private String rowStatus;
    private List<FileVO> attachList;
    private List<FileVO> imageList;
}
