package com.commerce.service.SCR;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.COM.COMService;
import com.commerce.service.HCO.vo.AdminVO;
import com.commerce.service.MNU.vo.MNU0101S01R;
import com.commerce.service.MNU.vo.MNU0101S01S;
import com.commerce.service.MNU.vo.MNUMenu;
import com.commerce.service.SCR.vo.SCR0101S01R;
import com.commerce.service.SCR.vo.SCR0101S01S;
import com.commerce.service.SCR.vo.SCR0101U01S;
import com.commerce.service.SCR.vo.ScrInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("ScrService")
public class ScrService extends UtilMapper {

    @Autowired
    private COMService comService;
    public boolean SCR0101S01(SCR0101S01S req, SCR0101S01R rsp) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("SCR","selectScrList",map);
        rsp.setScrList(result);

        return true;
    }
    public boolean SCR0101U01(SCR0101U01S req) throws UserException {
        int resultCnt = 0;
        AdminVO userVo = comService.getAdminInfo();
        for (ScrInfo scr : req.getScrList()) {
            Map<String, Object> map = objectMapper.convertValue(scr, Map.class);
            String userId = userVo.getId();

            map.put("lastUserId", userId);
            map.put("rgtrUserId", userId);

            if ("C".equals(map.get("rowStatus"))) {
                    resultCnt += generalMapper.insert("SCR", "insertScr", map);
            } else if ("U".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("SCR", "updateScr",map );
            } else if ("D".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("SCR", "deleteScr", map);
            }
        }

        return resultCnt > 0;
    }
}
