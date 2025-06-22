package com.commerce.comm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class ResultVO {
    private String resultCode;
    private String errorCode;
    private String resultMsg;
    private String errorMsg;
    private boolean success;

    private Object resultData;

    public void setSucessCode() {
        this.resultCode = "0000";
        this.resultMsg = "성공";
        this.success = true;
    }
    public void setFailCode() {
        this.resultCode = "9999";
        this.resultMsg = "실패";
        this.success = false;
    }
}
