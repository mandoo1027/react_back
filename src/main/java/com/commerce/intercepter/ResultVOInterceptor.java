package com.commerce.intercepter;

import com.commerce.comm.ResultVO;
import com.commerce.module.COM.COMService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ResultVOInterceptor implements HandlerInterceptor {

    @Autowired
    private HttpSession session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 전 처리

        return true; // false를 반환하면 요청을 중단합니다.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 실행 후, 뷰 렌더링 전에 처리
        if (modelAndView != null) {
            // Model에 접근해서 ResultVO를 수정 가능
            Object result = modelAndView.getModel().get("resultVO");
            if (result instanceof ResultVO) {
                ResultVO resultVO = (ResultVO) result;
               // resultVO.setSomeData("추가된 데이터");
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 완료 후 처리
    }
}