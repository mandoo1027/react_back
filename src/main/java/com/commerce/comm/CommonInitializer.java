package com.commerce.comm;

import com.commerce.exception.UserException;
import com.commerce.module.COM.COMService;
import com.commerce.module.COM.vo.SCOM001SVO;
import com.commerce.module.MEM.MemService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonInitializer {

    @Autowired
    private CommonSaveManager commonCodeManager;
    @Autowired
    private COMService comService;

    @Autowired
    private MemService memService;
    @PostConstruct
    public void init() throws UserException {
        SCOM001SVO svo = new SCOM001SVO();
        List<CamelKeyMap> list = comService.SCOM002(svo);
        commonCodeManager.setSaveData("codeList",list);
        System.out.println("list = " + list);
//        SMEM001SVO svo = new SMEM001SVO();
//        SMEM006RVO rvo = memService.SMEM001(svo);
//        ResultVO resultVo = new ResultVO();
        //ResultVO resultVo = new ResultVO();

    }
}