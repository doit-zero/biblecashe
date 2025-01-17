package hello.biblecashe.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 만약 만료되었으면 session은 null
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
            return;
        }

        long lastAccessedTime = session.getLastAccessedTime();
        long currentTime = System.currentTimeMillis();

        // 세션 생성시간과 현재 시간을 비교해서 timeout보다 클 경우 세션을 만료시킨다.
        long timeout = 10000;
        if(currentTime - lastAccessedTime > timeout){
            session.invalidate();
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
            return;
        }

        chain.doFilter(request,response);
    }
}