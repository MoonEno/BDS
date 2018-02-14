package bds.portal.com.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bds.portal.com.utils.UnilockUtils;

/**
 * WAS 실행시 기동되는 listener
 *
 * @author ihmoon
 * @created 2018. 01. 15.
 */
public class PortalListener implements ServletContextListener {

    /**
     * 생성자
     */
    public PortalListener() {
        // Default Constructor
    }

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(PortalListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("PortalListener contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("PortalListener contextDestroyed");
        UnilockUtils.close();
    }
}
