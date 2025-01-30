package com.commerce.comm;

import com.commerce.module.MEM.vo.SMEM006RVO;
import jakarta.servlet.http.HttpSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GeneralMapperService {

    private final SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private HttpSession session;

    public GeneralMapperService(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<CamelKeyMap> selectList(String namespace, String queryId, Map<String, Object> paramMap) {
        String statement = namespace + "." + queryId;
        List<Map<String, Object>> resultList = sqlSessionTemplate.selectList(statement, paramMap);
        List<CamelKeyMap> camelKeyMapList = new ArrayList<>();

        for (Map<String, Object> map : resultList) {
            camelKeyMapList.add(new CamelKeyMap(map));
        }

        return camelKeyMapList;
    }

    public CamelKeyMap selectOne(String namespace, String queryId, Map<String, Object> paramMap) {
        String statement = namespace + "." + queryId;
        Map map = sqlSessionTemplate.selectOne(statement, paramMap);
        CamelKeyMap camelKeyMap = new CamelKeyMap(map);
        return camelKeyMap;
    }

    public int insert(String namespace, String queryId, Map<String,Object> paramMap) {
        String statement = namespace + "." + queryId;
        UserVO userVo = (UserVO) session.getAttribute("user");
        String userId = userVo.getCurrentSessionId();
        paramMap.put("rgtrUserId", userId);
        return sqlSessionTemplate.insert(statement, paramMap);
    }

    public int update(String namespace, String queryId, Map<String,Object> paramMap) {
        String statement = namespace + "." + queryId;
        return sqlSessionTemplate.update(statement, paramMap);
    }


}