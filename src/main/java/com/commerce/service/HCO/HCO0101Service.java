package com.commerce.service.HCO;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.COM.COMService;
import com.commerce.module.MEM.MemService;
import com.commerce.module.MEM.vo.SMEM001SVO;
import com.commerce.module.MEM.vo.SMEM006RVO;
import com.commerce.service.HCO.vo.*;
import com.commerce.service.MNU.vo.MNU0101S01S;
import com.commerce.service.MNU.vo.MNUMenu;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Transactional
@Service("HcoService")
public class HCO0101Service extends UtilMapper {

    @Autowired
    private HttpSession session;

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

    public List<AdminVO> HCO0101S01(HCO0101S01S req) {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("HCO", "selectAdmin", map);

        return ObjectMapperUtils.convertToList(result, AdminVO.class);
    }

    /**
     * 관리자 목록
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public List<AdminVO>  HCO0101S04(HCO0101S04S req, HCO0101S04R rsp) throws UserException {

        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("HCO","selectAdminList",map);

        List<AdminVO> convertList = ObjectMapperUtils.convertToList(result, AdminVO.class);


        return convertList;
    }

    /**
     * 권한 그룹 사용자  조회
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public List<AdminVO>  HCO0201S01(HCO0201S01L req, HCO0101S04R rsp) throws UserException {

        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("HCO","selectAuthAdmin",map);

        List<AdminVO> convertList = ObjectMapperUtils.convertToList(result, AdminVO.class);


        return convertList;
    }

    /**
     * 관리자 저장
     * @param 
     * @return
     * @throws Exception
     */
    public boolean HCO0101U01(HCO0101S04S req) throws UserException {
        int resultCnt = 0;

        for (HCO0101S04S admin : req.getAdminList()) {

            Map<String, Object> map = objectMapper.convertValue(admin, Map.class);

            AdminVO userVo = comService.getAdminInfo();
            String userId = userVo.getId();

            map.put("lastUserId", userId);

            if ("C".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("HCO", "insertAdmin", map);
            } else if ("U".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("HCO", "updateAdmin", map);
            } else if ("D".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("HCO", "deleteAdmin", map);
            }
        }

        return resultCnt > 0;
    }
    /**
     * 관리자 권한 코드 변경
     * @param
     * @return
     * @throws Exception
     */
    public boolean HCO0201U01(HCO0101S04S req) throws UserException {
        int resultCnt = 0;


            Map<String, Object> map = objectMapper.convertValue(req, Map.class);

            AdminVO userVo = comService.getAdminInfo();
            String userId = userVo.getId();

            map.put("lastUserId", userId);

            resultCnt += generalMapper.update("HCO", "updateAuthCdAdmin", map);
        return resultCnt > 0;
    }
    /**
     * 회원 승인 상태 코드 변경
     * @param
     * @return
     * @throws Exception
     */
    public boolean HCO0101U02(HCO0101S04S req) throws UserException {
        int resultCnt = 0;


            Map<String, Object> map = objectMapper.convertValue(req, Map.class);

            AdminVO userVo = comService.getAdminInfo();
            String userId = userVo.getId();

            map.put("lastUserId", userId);

            resultCnt += generalMapper.update("HCO", "updateUseStatCdAdmin", map);
        return resultCnt > 0;
    }
}