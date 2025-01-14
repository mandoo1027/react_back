package com.commerce.exception;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.CommonSaveManager;
import com.commerce.comm.ErrorResponse;
import com.commerce.comm.ResultVO;
import com.commerce.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private CommonSaveManager commonCodeManager;
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 상태 코드를 설정할 수 있음
    @ResponseBody
    public ResponseEntity<ResultVO> handleUserException(UserException ex) {
        List<CamelKeyMap> list = commonCodeManager.getSaveData("codeList");
        Optional<CamelKeyMap> matchingItem = list.stream()
                .filter(map -> ex.getMessage().equals(map.get("code")))  // "code" 컬럼의 값과 비교
                .findFirst(); // 일치하는 첫 번째 항목을 찾음
        CamelKeyMap findData = matchingItem.orElse(null);
        ErrorResponse errorResponse = null;
        if(findData != null){
            String codeNm = matchingItem.get().get("name").toString();
             errorResponse = new ErrorResponse(codeNm, ex.getMessage());
        }else{
            errorResponse = new ErrorResponse(ex.getMessage(), "등록된 에러코드가 없습니다.");
        }
        ResultVO result = new ResultVO();
        result.setResultData(errorResponse);
        result.setFailCode();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}