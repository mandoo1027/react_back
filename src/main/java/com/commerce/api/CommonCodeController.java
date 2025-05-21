package com.commerce.api;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.commerce.module.COM.COMService;
import com.commerce.module.COM.vo.SCOM001SVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/service")
public class CommonCodeController {

    @Autowired
    private COMService comService;

    //공통 코드 조회
    @PostMapping(value = "/ADM0202S01")
    public ResultVO ADM0202S01(@RequestBody SCOM001SVO req) throws UserException {

        List<CamelKeyMap> codeList = comService.SCOM001(req);
        ResultVO result = new ResultVO();
        result.setResultData(codeList);
        result.setSucessCode();

        return result;
    }
    // 그룹코드 조회
    @PostMapping(value = "/ADM0202S03")
    public ResultVO ADM0202S03(@RequestBody SCOM001SVO req) throws UserException {

        List<CamelKeyMap> codeList = comService.SCOM000(req);
        ResultVO result = new ResultVO();
        result.setResultData(codeList);
        result.setSucessCode();

        return result;
    }

    @PostMapping(value = "/ADM0202U01")
    public ResultVO ADM0202U01(@RequestBody SCOM001SVO req) throws UserException {

        if (!Objects.isNull(req.getList()) && req.getList().size() > 0) {
            //여러건 날아올 경우 데이터 삭제
            List<SCOM001SVO> scom001SVOS = req.getList();
            //
            scom001SVOS.forEach(scom001SVO -> {
                try {
                    comService.ADM0202U01(scom001SVO);
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            //1. 데이터 저장
            comService.ADM0202U01(req);
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setResultData(true);
        resultVO.setSucessCode();
        return resultVO;
    }
    @PostMapping(value = "/ADM0202S02")
    public ResultVO ADM0202S02(@RequestBody SCOM001SVO req) throws UserException {

        List<CamelKeyMap> codeList = comService.SCOM002(req);
        ResultVO result = new ResultVO();
        result.setResultData(codeList);
        result.setSucessCode();

        return result;
    }
}
