package com.commerce.module.COM;


import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.COM.vo.SCOM001RVO;
import com.commerce.module.COM.vo.SCOM001SVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("ComService")
public class COMService extends UtilMapper {

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
}
