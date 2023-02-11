package org.han.examination.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LogInterceptor implements HandlerInterceptor {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String sn = getSn(request);
        MDC.put("sn", sn);
        return true;
    }

    private String getSn(HttpServletRequest request) {
        String date = formatter.format(LocalDate.now());
        String requestId = request.getRequestId();
        return date + String.format("%010d", Integer.parseInt(requestId));
    }
}
