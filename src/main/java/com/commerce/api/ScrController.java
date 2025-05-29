package com.commerce.api;

import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import com.commerce.service.MNU.MnuService;
import com.commerce.service.MNU.vo.MNU0101S01R;
import com.commerce.service.MNU.vo.MNU0101S01S;
import com.commerce.service.SCR.ScrService;
import com.commerce.service.SCR.vo.SCR0101S01R;
import com.commerce.service.SCR.vo.SCR0101S01S;
import com.commerce.service.SCR.vo.SCR0101U01S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*화면 관리*/
@RestController
@RequestMapping("/admin/service")
public class ScrController {

    @Autowired
    private ScrService scrService;
    @PostMapping(value = "/SCR0101S01")
    public ResultVO SCR0101S01(@RequestBody SCR0101S01S req, SCR0101S01R res) throws UserException {
        boolean isResult = scrService.SCR0101S01(req, res);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(res);
            result.setSucessCode();
        }
        return result;
    }    @PostMapping(value = "/SCR0101U01")
    public ResultVO SCR0101U01(@RequestBody SCR0101U01S req, SCR0101S01R res) throws UserException {
        boolean isResult = scrService.SCR0101U01(req);
        ResultVO result = new ResultVO();
        if(isResult) {
            result.setResultData(res);
            result.setSucessCode();
        }
        return result;
    }


}
