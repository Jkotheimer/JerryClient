package com.robocat.android.rc.base;

// Custom null-safe string class
public class CString {
    public static boolean isBlank(Object o) {
        return o == null || "".equals(String.valueOf(o).trim());
    }
}
