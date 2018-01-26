package bds.portal.com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bds.portal.com.constants.PortalConstants;
import bds.portal.com.vo.SessVo;

/**
 * 권한 확인
 *
 * @author openit
 * @created 2017. 11. 1.
 */
public class PortalInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        requestUri = requestUri.replaceFirst(contextPath, "");
        String menu = requestUri.split("/")[1];

        if (!"login".equals(menu)) {
            HttpSession session = request.getSession();

            if (session.getAttribute(PortalConstants.SESSION_KEY) != null) {

                SessVo sessVo = (SessVo)session.getAttribute(PortalConstants.SESSION_KEY);
                if (sessVo != null && StringUtils.isNotBlank(sessVo.getAdminId())) {
                    return true;
                }
            }

            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}
