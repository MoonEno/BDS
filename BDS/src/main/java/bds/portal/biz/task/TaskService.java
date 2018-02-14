package bds.portal.biz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * task service
 *
 * @author ihmoon
 * @created 2018. 01. 10.
 */
@Service
public class TaskService {

    /**
     * 생성자
     */
    public TaskService() {
        // Default Constructor
    }

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 락커 확인
     */
    public void checkLocker() {
        //        if (!UnilockListener.isListen()) {
        //            logger.info("checkLocker unilock listen start!");
        //            new Thread(UnilockListener.getInstance()).start();
        //            logger.info("checkLocker unilock listen end!");
        //        }
    }
}
