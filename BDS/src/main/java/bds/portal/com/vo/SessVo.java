package bds.portal.com.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 로그인한 관리자 VO
 *
 * @author ihmoon
 * @created 2018. 01. 26.
 */
public class SessVo {

    /**
     * adminId
     */
    private String adminId;

    /**
     * name
     */
    private String name;

    /**
     * @return
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * @param adminId - the adminId to set
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name - the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
