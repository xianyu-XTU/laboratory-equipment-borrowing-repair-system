package com.xtu.labequipment.common;

public class AuthContext {
    private static final ThreadLocal<LoginUser> HOLDER = new ThreadLocal<>();

    public static void set(LoginUser user) {
        HOLDER.set(user);
    }

    public static LoginUser get() {
        return HOLDER.get();
    }

    public static Long getUserId() {
        LoginUser user = HOLDER.get();
        return user == null ? null : user.getUserId();
    }

    public static String getRoleCode() {
        LoginUser user = HOLDER.get();
        return user == null ? null : user.getRoleCode();
    }

    public static void clear() {
        HOLDER.remove();
    }
}
