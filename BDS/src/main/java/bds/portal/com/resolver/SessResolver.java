package bds.portal.com.resolver;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import bds.portal.com.constants.PortalConstants;
import bds.portal.com.vo.SessVo;

/**
 * session 추가
 *
 * @author ihmoon
 * @created 2018. 01. 15.
 */
public class SessResolver implements HandlerMethodArgumentResolver {

    /**
     * 생성자
     */
    public SessResolver() {
        // default constructor
    }

    private final String RMAP = "rMap";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        if (RMAP.equals(methodParameter.getParameterName())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

        if (RMAP.equals(methodParameter.getParameterName())) {

            Map<String, Object> rMap = new HashMap<String, Object>();

            Enumeration<?> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String key = (String)enumeration.nextElement();
                if (StringUtils.lastIndexOf(key, "[]") > 0) {
                    rMap.put(key.replace("[]", ""), request.getParameterValues(key));
                } else {
                    rMap.put(key, request.getParameter(key));
                }
            }

            HttpSession session = request.getSession();
            if (session.getAttribute(PortalConstants.SESSION_KEY) != null) {
                SessVo sessVo = (SessVo)session.getAttribute(PortalConstants.SESSION_KEY);
                rMap.put("sessAdminId", sessVo.getAdminId());
                rMap.put("sessName", sessVo.getName());
            }

            return rMap;
        }

        return null;
    }
}
