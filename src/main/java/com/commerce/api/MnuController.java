package com.commerce.api;

import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.commerce.service.MNU.MnuService;
import com.commerce.service.MNU.vo.MNU0101S01R;
import com.commerce.service.MNU.vo.MNU0101S01S;
import com.commerce.service.MNU.vo.MNU0201S01S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/service")
public class MnuController {

    @Autowired
    private MnuService mnuService;
    @PostMapping(value = "/MNU0101S01")
    public ResultVO menuSearch(@RequestBody MNU0101S01S req, MNU0101S01R res) throws UserException {
        boolean isResult = mnuService.MNU0101S01(req, res);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(res);
            result.setSucessCode();
        }
        return result;
    }

    @PostMapping(value = "/MNU0101S02")
    public ResultVO MNU0101S02(@RequestBody MNU0101S01S req, MNU0101S01R res) throws UserException {
        boolean isResult = mnuService.MNU0101S02(req, res);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(res);
            result.setSucessCode();
        }
        return result;
    }

    @PostMapping(value = "/MNU0101U02")
    public ResultVO MNU0101U02(@RequestBody MNU0101S01S req) throws UserException {
        boolean isResult = mnuService.MNU0101U02(req);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(isResult);
            result.setSucessCode();
        }
        return result;
    }

    @PostMapping(value = "/MNU0201S01")
    public ResultVO menuSearch2(@RequestBody MNU0201S01S req, MNU0101S01R res) throws UserException {
        boolean isResult = mnuService.MNU0201S01(req, res);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(res);
            result.setSucessCode();
        }
        return result;
    }

    @PostMapping(value = "/MNU0201S02")
    public ResultVO MNU0201S02(@RequestBody MNU0201S01S req, MNU0101S01R res) throws UserException {
        boolean isResult = mnuService.MNU0201S02(req, res);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(res);
            result.setSucessCode();
        }
        return result;
    }

    @PostMapping(value = "/MNU0201U02")
    public ResultVO MNU0201U02(@RequestBody MNU0201S01S req) throws UserException {
        boolean isResult = mnuService.MNU0201U02(req);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(isResult);
            result.setSucessCode();
        }
        return result;
    }


}
