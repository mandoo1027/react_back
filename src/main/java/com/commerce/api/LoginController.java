package com.commerce.api;

import ch.qos.logback.core.util.StringUtil;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.ResultVO;
import com.commerce.comm.UserVO;
import com.commerce.exception.UserException;
import com.commerce.module.MEM.MemService;
import com.commerce.module.MEM.vo.SMEM001SVO;
import com.commerce.module.MEM.vo.SMEM006RVO;
import com.commerce.service.LOG.vo.LOG0101S01S;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class LoginController {

    @Autowired
    private MemService memService;

    @Autowired
    private HttpSession session;

    // 로그인
    @PostMapping(value = "/LOG0101S01")
    public ResultVO login(@RequestBody LOG0101S01S req) throws UserException{

        if(StringUtil.isNullOrEmpty(req.getMemId())){
            throw new UserException("MEM001");//"아이디를 입력하세요.");
        }

        SMEM001SVO svo = ObjectMapperUtils.convertToVo(req,SMEM001SVO.class);
        List<SMEM006RVO> rvo = memService.SMEM001(svo);
        SMEM006RVO getData ;
        if(rvo.size() == 0 ){
            throw new UserException("MEM002");//"아이디 또는 비밀번호가 일치하지 않습니다.");
        }else{
            getData = rvo.get(0);
            if(!getData.getMemPw().equals(svo.getPw())){
                throw new UserException("MEM002");//"아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        }
        ResultVO resultVo = new ResultVO();
        resultVo.setSucessCode();
        resultVo.setResultData(getData);

        //세션에 데이터 담기
        UserVO userVo = ObjectMapperUtils.convertToVo(getData,UserVO.class);

        String newSessionId = session.getId();
        userVo.setCurrentSessionId(newSessionId);
        session.setAttribute("user", userVo);
        return resultVo;
    }

    //로그인 체크
    @PostMapping(value = "/LOG0101S02")
    public ResultVO healthCheck(@RequestBody LOG0101S01S req,HttpSession session) throws UserException{

        UserVO userVo =  (UserVO) session.getAttribute("user");
        ResultVO resultVo = new ResultVO();
        resultVo.setSucessCode();
        resultVo.setResultData(userVo);
        return resultVo;
    }

    // 로그아웃
    @PostMapping(value = "/LOG0101S03")
    public ResultVO logout(@RequestBody LOG0101S01S req,HttpSession session) throws UserException{

        ResultVO resultVo = new ResultVO();
        resultVo.setSucessCode();
        resultVo.setResultData(true);
        session.invalidate();
        return resultVo;
    }
}
