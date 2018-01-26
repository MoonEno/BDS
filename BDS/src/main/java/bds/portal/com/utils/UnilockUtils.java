package bds.portal.com.utils;

import java.io.DataOutputStream;
import java.net.Socket;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bds.portal.com.constants.PortalConstants;

/**
 * Unilock 연동
 * 
 * @author openit
 * @created 2017. 11. 1.
 */
public final class UnilockUtils {

    private static int INTERVAL = 100;

    private static final char CHECK = 'A';

    private static final char SET = 'B';

    public static final char ORDER = 'a';

    public static final char STATUS = 'e';

    public static final String PREFIX = "LU";

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(UnilockUtils.class);

    private static Socket socket = null;

    public static void close() {

        try {
            if (dos != null) {
                dos.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        } finally {
            dos = null;
            socket = null;
        }
    }

    public static Socket getSocket() {

        if (socket != null && !socket.isClosed()) {
            return socket;
        }

        socket = null;
        try {
            Configuration config = new PropertiesConfiguration("portal.properties");
            String host = config.getString("unilock.host", "localhost");
            int port = config.getInt("unilock.port", 22223);

            INTERVAL = config.getInt("unilock.interval", 100);

            dos = null;
            socket = new Socket(host, port);

        } catch (Exception e) {
            close();
        }
        return socket;
    }

    private static DataOutputStream dos = null;

    public static DataOutputStream getDataOutputStream() {

        if (socket != null && !socket.isClosed() && dos != null) {
            return dos;
        }

        dos = null;
        try {
            dos = new DataOutputStream(getSocket().getOutputStream());

        } catch (Exception e) {
            close();
        }
        return dos;
    }

    private static byte[] getHead(char type, int length, String lockerId) {
        return ArrayUtils.addAll(getHead(type, length), getLockerId(lockerId));
    }

    private static byte[] getHead(char type, int length) {
        return new byte[] {(byte)160, (byte)type, (byte)0, (byte)0, (byte)0, (byte)length};
    }

    private static byte[] getLockerId(String lockerId) {

        byte[] id = new byte[3];
        for (int i = 0; i < 3; i++) {
            id[i] = (byte)Integer.parseInt(lockerId.substring(i * 2 + 2, i * 2 + 4), 16);
        }

        return id;
    }

    private static byte[] getKey(String key) {

        byte[] keys = new byte[6];
        for (int i = 0; i < 6; i++) {
            keys[i] = (byte)Integer.parseInt(key.substring(i * 2 + 2, i * 2 + 4), 16);
        }

        return keys;
    }

    private static byte[] getPassword(String password) {

        byte[] passwords = new byte[6];
        passwords[0] = (byte)0;
        passwords[1] = (byte)0;
        for (int i = 0; i < 4; i++) {
            passwords[i + 2] = (byte)password.charAt(i);
        }

        return passwords;
    }

    public static int reset(String lockerId) {
        return setKey(lockerId, PortalConstants.Type.BAND, "00000000000000");
    }

    public static int setKey(String lockerId, String type, String key) {

        int res = 0;
        if (lockerId.length() == 8) {
            if (PortalConstants.Type.BAND.equals(type)) {
                if (key.length() != 14) {
                    return res;
                }
            } else {
                if (key.length() != 4) {
                    return res;
                }
            }

            DataOutputStream dos = getDataOutputStream();
            if (dos != null) {
                try {

                    byte[] bytes = ArrayUtils.addAll(getHead(SET, 13, lockerId), Byte.parseByte(type));
                    bytes = ArrayUtils.addAll(bytes, getLockerId(lockerId));

                    if (PortalConstants.Type.BAND.equals(type)) {
                        bytes = ArrayUtils.addAll(bytes, getKey(key));
                    } else {
                        bytes = ArrayUtils.addAll(bytes, getPassword(key));
                    }

                    dos.write(bytes);
                    dos.flush();

                    res++;

                    Thread.sleep(INTERVAL);

                } catch (Exception e) {
                    logger.error("setKey : {}, {}, {}", lockerId, type, key);
                    close();
                }
            }
        }

        return res;
    }

    public static int check(String lockerId, int check) {

        int res = 0;
        if (lockerId.length() == 8) {

            DataOutputStream dos = getDataOutputStream();
            if (dos != null) {
                try {

                    byte[] bytes = ArrayUtils.addAll(getHead(CHECK, 4, lockerId), (byte)check);
                    dos.write(bytes);
                    dos.flush();

                    res++;

                    Thread.sleep(INTERVAL);

                } catch (Exception e) {
                    logger.error("check : {}, {}", lockerId, check);
                    close();
                }
            }
        }

        return res;
    }
}
