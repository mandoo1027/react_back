package com.commerce.api;

import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.commerce.service.BBS.BBSService;
import com.commerce.service.BBS.vo.BBS0101S01R;
import com.commerce.service.BBS.vo.BBS0101S01S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/service")
public class BbsController{
    @Autowired
    private BBSService bbsService;

    @PostMapping("/BBS0101U01")
    public ResultVO uploadPost(@RequestBody  BBS0101S01S req) throws UserException {

        //1. 데이터 저장
        boolean resultData = bbsService.BBS0101U01(req);

        ResultVO resultVO = new ResultVO();
        resultVO.setResultData(resultData);
        resultVO.setSucessCode();
        return resultVO;
    }

    @PostMapping("/selectPostIdNextVal")
    public ResultVO selectPostIdNextVal() throws UserException {
        Long postId = bbsService.selectPostIdNextVal();
        ResultVO resultVO = new ResultVO();
        resultVO.setResultData(postId);
        resultVO.setSuccess(true);
        return resultVO;
    }
    @PostMapping("/BBS0101S01")
    public ResultVO selectBBSList(@RequestBody  BBS0101S01S req) throws UserException {
        List<BBS0101S01R> bbsList = bbsService.selectBBSList(req);
        ResultVO resultVO = new ResultVO();
        resultVO.setResultData(bbsList);
        resultVO.setSucessCode();
        return resultVO;
    }
}
