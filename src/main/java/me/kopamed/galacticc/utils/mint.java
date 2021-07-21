package me.kopamed.galacticc.utils;

import org.lwjgl.Sys;

public class mint {
    private static long lastMS = System.currentTimeMillis();

    public static void reset() {
        lastMS = System.currentTimeMillis();
    }

    public static boolean hasTimeElapsed(long time, boolean reset) {
        if(System.currentTimeMillis() - lastMS > time) {
            if(reset)
                reset();

            return true;
        }
        return false;
    }
}
