package bds.portal.com.listeners;

import java.io.DataInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bds.portal.com.utils.UnilockUtils;

/**
 * unilock listener
 *
 * @author openit
 * @created 2017. 11. 1.
 */
public class UnilockListener implements Runnable {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(UnilockListener.class);

    private static UnilockListener listener = null;

    public static UnilockListener getInstance() {

        if (listener == null) {
            listener = new UnilockListener();
        }
        return listener;
    }

    private static boolean listen = false;

    public static boolean isListen() {
        return listen;
    }

    @Override
    public void run() {

        if (listen) {
            return;
        }

        listen = true;
        logger.info("UnilockListener start");
        DataInputStream dis = null;
        try {

            dis = new DataInputStream(UnilockUtils.getSocket().getInputStream());

            int b = 0;

            do {
                b = dis.read();
            } while (b > -1);

            logger.info("UnilockListener end");

        } catch (Exception e) {
            logger.info("UnilockListener err");
            listen = false;
        } finally {
            logger.info("UnilockListener fin");
            listen = false;
            if (dis != null) {
                try {
                    dis.close();
                } catch (Exception e) {
                    UnilockUtils.close();
                }
            }
        }
    }
}
