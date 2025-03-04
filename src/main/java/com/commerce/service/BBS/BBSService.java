package com.commerce.service.BBS;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.CMN.FileModule;
import com.commerce.module.CMN.FileVO;
import com.commerce.module.COM.COMService;
import com.commerce.service.BBS.vo.BBS0101S01R;
import com.commerce.service.BBS.vo.BBS0101S01S;
import com.commerce.service.HCO.vo.AdminVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;


@Transactional
@Service("BBSService")
public class BBSService extends UtilMapper {

    @Autowired
    private HttpSession session;


    @Autowired
    private FileModule fileModule;

    @Autowired
    private COMService comService;

    /**
     * 관리자 로그인
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */

    public List<AdminVO> BBS0101S01(BBS0101S01S req) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("HCO", "selectAdmin", map);

        return ObjectMapperUtils.convertToList(result, AdminVO.class);
    }

    /**
     * 게시판 아이디 가져오기
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */

    public Long selectPostIdNextVal() throws UserException {
         CamelKeyMap result = generalMapper.selectOne("BBS", "selectPostIdNextVal",null);
        BigInteger posts_id_seq = (BigInteger) result.get("postsidseq");
        Long data = Long.parseLong(posts_id_seq.toString());
        return data;
    }
    /**
     * 게시판 아이디 가져오기
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */

    public List<BBS0101S01R>  selectBBSList(BBS0101S01S req) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);
        if(!Objects.isNull(req.getPageSize() )) {
            int offset = (req.getCurrentPage() - 1) * req.getPageSize();
            map.put("offset",offset);
        }


        List<CamelKeyMap> result = generalMapper.selectList("BBS", "selectPosts",map);
        List<BBS0101S01R> returnData = ObjectMapperUtils.convertToList(result, BBS0101S01R.class);

        if(!Objects.isNull(req.getPostId())){
            //파일 정보 가져오기
            FileVO fileVO = ObjectMapperUtils.convertToVo(req,FileVO.class);
            fileModule.fileList(fileVO);
            BBS0101S01R data = returnData.get(0);
            data.setAttachFile(fileVO.getAttachList());
            data.setImgFiles(fileVO.getImageList());
        }

        return returnData;
    }

    /**
     * 관리자 저장
     * @param 
     * @return
     * @throws Exception
     */
    public boolean BBS0101U01(BBS0101S01S req) throws UserException {
        int resultCnt = 0;

        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        AdminVO userVo = comService.getAdminInfo();
        String userId = userVo.getId();
        map.put("rgtrUserId", userId);
        map.put("lastUserId", userId);

        String rowStatus = req.getRowStatus();
        if ("C".equals(rowStatus)) {
            resultCnt += generalMapper.insert("BBS", "insertPosts", map);
        } else if ("U".equals(rowStatus)) {
            resultCnt += generalMapper.insert("BBS", "updatePosts", map);
        } else if ("D".equals((rowStatus))) {
            this.deletsPosts(req);
            resultCnt += generalMapper.insert("BBS", "deletePosts", map);

        }

        //파일 저장
        if(!"D".equals(rowStatus)){
            this.saveFiles(req);
        }



        return resultCnt > 0;
    }

    private boolean saveFiles(BBS0101S01S req) throws UserException {
        int postIs = req.getPostId();
        //2. 파일 위치 변경(게시글 이미지)

        if(!Objects.isNull(req.getImgFiles()) && req.getImgFiles().size() > 0){
            fileModule.relocateFile(req.getImgFiles());
            fileModule.fileSave(req.getImgFiles(),"image",postIs);
        }
        //3. 파일 위치 변경(첨부파일)
        if(!Objects.isNull(req.getAttachFile()) && req.getAttachFile().size() > 0){
            fileModule.relocateFile(req.getAttachFile());
            fileModule.fileSave(req.getAttachFile(),"attach",postIs);
        }
        return true;
    }

    private boolean deletsPosts(BBS0101S01S posts) throws UserException {
        FileVO file = new FileVO();
        file.setPostId(posts.getPostId());
        FileVO fileList = fileModule.fileList(file);
        if(!Objects.isNull(fileList.getImageList())){
            List<FileVO> fileVOList = fileList.getImageList();
            fileModule.deleteFile(fileVOList,true) ;
        }

        if(!Objects.isNull(fileList.getAttachList())){
            List<FileVO> fileVOList = fileList.getAttachList();
            fileModule.deleteFile(fileVOList,true) ;
        }
        return true;
    }
}