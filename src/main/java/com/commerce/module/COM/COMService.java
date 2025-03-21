package com.commerce.module.COM;


import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.COM.vo.SCOM001RVO;
import com.commerce.module.COM.vo.SCOM001SVO;
import com.commerce.service.HCO.vo.AdminVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("ComService")
public class COMService extends UtilMapper {
    @Autowired
    private HttpSession session;

    public List<CamelKeyMap> SCOM001(SCOM001SVO req) throws UserException {
        Map map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("COM","selectCmmnCode",map);
        return result ;
    }

    public List<CamelKeyMap> SCOM002(SCOM001SVO req) throws UserException {
        Map map = objectMapper.convertValue(req, Map.class);

        System.out.println("mapmap" + map.toString());
        List<CamelKeyMap> result = generalMapper.selectList("COM","selectCmmnCodeDetail",map);
        return result ;
    }

    public AdminVO getAdminInfo() throws UserException {
        AdminVO userVo = (AdminVO) session.getAttribute("user");
        if(userVo == null){
            throw new UserException("SESSION_EXPIRED");//세션이 만료되었습니다. 재로그인 부탁드립니다.
        }
        return userVo;
    }
}
