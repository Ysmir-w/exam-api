package org.han.examination.log.aspect;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.exception.BusinessException;
import org.han.examination.result.Result;
import org.han.examination.utils.JsonUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Aspect
public class LogAspect {
    @Resource
    private JsonUtil jsonUtil;

    @Pointcut("@annotation(org.han.examination.log.annotation.LogMarker)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取方法签名
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();

        Class<?> clazz = signature.getDeclaringType();
        String controllerName = clazz.getName();
        log.info("===========================start===========================");
        log.info("请求路径: {} {}", request.getRequestURI(), request.getMethod());
        log.info("方法路径: {}.{}", controllerName, methodName);
        log.info("ip: {}", getClientIp(request));
        log.info("请求参数: {}", getParamJson(request, joinPoint));
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (BusinessException e) {
            log.error("业务异常: {}", e.getMessage(), e);
            result = e.getError().getCode() != -1 ? Result.error(e.getError()) : Result.error(e.getMessage());
        } catch (Throwable e) {
            log.error("运行时异常: {}", e.getMessage(), e);
            result = Result.error(ErrorEnum.SERVER_ERROR);
        } finally {
            log.info("返回结果: {}", jsonUtil.serialize(result));
            log.info("耗时: {} ms", System.currentTimeMillis() - startTime);
            log.info("============================end============================");
        }
        return result;
    }

    private String getParamJson(HttpServletRequest request, JoinPoint joinPoint) {
        String requestType = request.getMethod();
        if ("GET".equals(requestType)) {
            return Optional.ofNullable(request.getQueryString()).orElse("");
        }

        Object[] args = joinPoint.getArgs();
        List<Object> arguments = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof ServerRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
                continue;
            }
            arguments.add(arg);
        }
        return jsonUtil.serialize(arguments);
    }

    private String getClientIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .map(xff -> xff.contains(",") ? xff.split(",")[0] : xff)
                .orElse(request.getRemoteAddr());
    }

}