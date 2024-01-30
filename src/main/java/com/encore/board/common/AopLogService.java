package com.encore.board.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AopLogService {

    //aop의 대상이 되는 controller, service등을 정의
    //com.encore.board.. 모두이기때문에 .. 점 두개
    // 그 밑에 모든 메서드들 ..
    //@Pointcut("excution(* com.encore.board..controller..*.*(..))")
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerPointcut() {
    }

    //위에 controllerPointcut을 쓰겠다.
    //controller 들어가기 전에 하는 작업이다.
//    방식1 before
//    @Before("controllerPointcut()")
//
//    public void beforeController(JoinPoint joinPoint) {
//        log.info("Before Controller");

//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest req = servletRequestAttributes.getRequest();
////        json형태로 사용자의 요청을 조립하기 위한 로직
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode objectNode = objectMapper.createObjectNode();
//        objectNode.put("Method Name", joinPoint.getSignature().getName());
//        objectNode.put("CRUD NAME", req.getMethod());
//        Map<String, String[]> paramMap = req.getParameterMap();
//        ObjectNode objectNodeDetail = objectMapper.valueToTree(paramMap);
//        objectNode.set("user inputs", objectNodeDetail);
//        log.info("user request info" + objectNode);
        //log.info("method가 실행되기 전에 인증, 입력값 검증등을 수행하는 용도로 사용하는 사전 단계");
//    }


//    @After("controllerPointcut()")
////    @Order(3)
//    public void afterController() {
//        log.info("end controller");
//    }
//}

    @Around("controllerPointcut()")


    //joinpoint란 aop 대상으로 하는 컨트롤러의 특정 메서드를 의미
    //컨트롤러로 가는 길목에서 요청을 낚아챘기 때문에 다시 보내줘야한다.
    public Object controllerLogger(ProceedingJoinPoint proceedingJoinPoint) {

        log.info("request method " + proceedingJoinPoint.getSignature().toString());

//      사용자의 요청값을 json형태로 출력하기 위해 httpServletRequest객체를 꺼내는 로직
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = servletRequestAttributes.getRequest();
//        json형태로 사용자의 요청을 조립하기 위한 로직
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("Method Name", proceedingJoinPoint.getSignature().getName());
        objectNode.put("CRUD NAME", req.getMethod());
        Map<String, String[]> paramMap = req.getParameterMap();
        ObjectNode objectNodeDetail = objectMapper.valueToTree(paramMap);
        objectNode.set("user inputs", objectNodeDetail);
        log.info("user request info" + objectNode);


        try {
//                본래의 컨트롤러 메서드 호출하는 부분.
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}