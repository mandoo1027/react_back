package com.commerce.service.BBS;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.service.BBS.vo.BBS0101S01S;
import com.commerce.service.HCO.vo.AdminVO;
import com.commerce.service.HCO.vo.HCO0101S01S;
import com.commerce.service.HCO.vo.HCO0101S04S;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Transactional
@Service("BBSService")
public class BBSService extends UtilMapper {

    @Autowired
    private HttpSession session;



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

        return resultCnt > 0;
    }    
}