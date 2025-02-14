package com.commerce.service.BBS;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.ResultVO;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.CMN.FileModule;
import com.commerce.module.CMN.FileVO;
import com.commerce.service.BBS.vo.BBS0101S01R;
import com.commerce.service.BBS.vo.BBS0101S01S;
import com.commerce.service.HCO.vo.AdminVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Transactional
@Service("BBSService")
public class BBSService extends UtilMapper {

    @Autowired
    private HttpSession session;


    @Autowired
    private FileModule fileModule;

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
        Long posts_id_seq = (Long) result.get("postsIdSeq");
        return posts_id_seq;
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
        List<CamelKeyMap> result = generalMapper.selectList("BBS", "selectBBS",map);
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

        AdminVO userVo = (AdminVO) session.getAttribute("user");
        String userId = userVo.getId();
        map.put("rgtrUserId", userId);
        map.put("lastUserId", userId);

        String rowStatus = req.getRowStatus();
        if ("C".equals(rowStatus)) {
            resultCnt += generalMapper.insert("BBS", "insertBBS", map);
        } else if ("U".equals(rowStatus)) {
            resultCnt += generalMapper.insert("BBS", "updateBBS", map);
        } else if ("D".equals((rowStatus))) {
            resultCnt += generalMapper.insert("BBS", "deleteBBS", map);
        }

        int postIs = (int) map.get("postId");
        //2. 파일 위치 변경(게시글 이미지)
        if(req.getImgFiles().size() > 0){
            fileModule.relocateFile(req.getImgFiles());
            fileModule.fileSave(req.getImgFiles(),"image",postIs);
        }
        //3. 파일 위치 변경(첨부파일)
        if(req.getAttachFile().size() > 0){
            fileModule.relocateFile(req.getAttachFile());
            fileModule.fileSave(req.getAttachFile(),"attach",postIs);
        }


        return resultCnt > 0;
    }    
}