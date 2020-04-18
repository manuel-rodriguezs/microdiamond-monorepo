package org.microdiamond.server.users.utils;

import io.quarkus.elytron.security.common.BcryptUtil;

public class BcryptPasswordUtil {
    public static String bcryptHashPassword(String password) {
        final byte[] SALT = {97, -99, -66, 68, 52, -8, 85, -118, 25, 64, 22, 98, 117, -120, 97, 8};
        return BcryptUtil.bcryptHash(password, 10, SALT);
    }
}
