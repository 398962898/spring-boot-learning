package pers.yuiz.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class EncodeUtil {
    /**
     * 循环加密次数
     */
    private final static int amount = 5;
    /**
     * 加密盐值
     */
    private final static String salt = "yuiz";

    public static String MD5Hex(Object object) {
        String md5 = new SimpleHash("md5", object, salt, amount).toHex();
        return md5;
    }
}
