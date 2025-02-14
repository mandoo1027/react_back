package com.commerce.module.CMN;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.ResultVO;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.COM.COMService;
import com.commerce.service.BBS.vo.BBS0101S01S;
import com.commerce.service.HCO.vo.AdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional
@Service("FileModule")
public class FileModule extends UtilMapper {

    @Value("${file.home-dir}") // 파일 경로
    private String homeDir;

    @Value("${file.tmp-file-upload-dir}") // 임시 저장소에 저장
    private String tmpfileUploadDir;

    @Value("${file.real-file-upload-dir}") // 임시 저장소에 저장
    private String realFileUploadDir;

    @Autowired
    private COMService comService;



    public HashMap fileUpload(MultipartFile file,String type) throws UserException {
        HashMap fileInfo = new HashMap();
            try {
                // 저장할 디렉토리 생성
                String uploadDir = tmpfileUploadDir ;
                LocalDateTime now = LocalDateTime.now();
                String yyyymmdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                Path uploadPath = Paths.get(homeDir + File.separator+ uploadDir+File.separator+yyyymmdd+File.separator);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssnnnnnnnnn");
                String formattedDateTime = now.format(formatter);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);//파일 생성
                }

                // 현재 날짜 및 시간 가져오기


                // 원본 파일 확장자 추출
                String extension = "";
                int dotIndex = file.getOriginalFilename().lastIndexOf(".");
                if (dotIndex > 0) { // 확장자가 존재하는 경우
                    extension = file.getOriginalFilename().substring(dotIndex);
                }

                // 새로운 파일명 생성 (예: "file_20250205_153045.png")
                //return "file_" + formattedDateTime + extension;
                // 파일 저장
                String realName = file.getOriginalFilename();
                String filePath = uploadDir +File.separator +yyyymmdd+File.separator+ formattedDateTime + extension;
                String fileName = formattedDateTime + extension;
                file.transferTo(new File(homeDir+filePath));

                //변경전 : /uploads/tmp-attachments, 변경 후 /uploads/attachments/{0}/{1}
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + filePath;
                String realUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() +realFileUploadDir.replaceAll(Pattern.quote("{0}"),type)+File.separator+ formattedDateTime + extension;;
                String realPath = realFileUploadDir.replaceAll(Pattern.quote("{0}"),type)+File.separator+ formattedDateTime + extension;;

                fileInfo.put("fileUrl",imageUrl);
                fileInfo.put("tmpPath",filePath);
                fileInfo.put("realUrl",realUrl);
                fileInfo.put("realPath",realPath);
                fileInfo.put("yyyymmdd",yyyymmdd);
                fileInfo.put("fileName",fileName);
                fileInfo.put("realName",realName);
                fileInfo.put("rowStatus","C");
        } catch (IOException e) {
                throw new UserException("UPLOAD_ERROR");//파일업로드 실패
        }
        return fileInfo;
    }

    public void relocateFile(List<HashMap> files){

        for(HashMap file : files){
            String sourcePath =homeDir +  (String) file.get("tmpPath");
            String destinationPath = homeDir +(String) file.get("realPath");
            try {
                File destinationFile = new File(destinationPath);
                File destinationDir = destinationFile.getParentFile();

                // 폴더가 존재하지 않으면 생성
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs();
                }

                // 파일 이동
                Files.move(Path.of(sourcePath), Path.of(destinationPath), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("파일이 성공적으로 이동됨: " + destinationPath);
            } catch (IOException e) {
                System.err.println("파일 이동 실패: " + e.getMessage());
            }
        }
    }

    /**
     * 파일 조회
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */

    public FileVO fileList(FileVO req) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);
        List<CamelKeyMap> result = generalMapper.selectList("FILE", "selectFileList", map);
        List<FileVO> files = ObjectMapperUtils.convertToList(result, FileVO.class);
        List<FileVO> attachList = files.stream()
                .filter(item -> "attach".equals(item.getType()))
                .collect(Collectors.toList());
        List<FileVO> imageList = files.stream()
                .filter(item -> "image".equals(item.getType()))
                .collect(Collectors.toList());
        req.setAttachList(attachList);
        req.setImageList(imageList);
        return req;
    }

    public void fileSave(List<HashMap> files,String type,int postsId) throws UserException{
        AdminVO vo = comService.getAdminInfo();
        int seq = 1;
        List<FileVO> fileList = ObjectMapperUtils.convertToList(files, FileVO.class);
        for(FileVO fileVO: fileList){
            fileVO.setRgtrUserId(vo.getId());
            String rowStatus = fileVO.getRowStatus();
            fileVO.setFileSequence(seq++);
            fileVO.setType(type);
            fileVO.setPostId(postsId);
            Map<String, Object> map = objectMapper.convertValue(fileVO, Map.class);
            if ("C".equals(rowStatus)) {
                generalMapper.insert("FILE", "insertFile", map);
            }
        }

    }
}
