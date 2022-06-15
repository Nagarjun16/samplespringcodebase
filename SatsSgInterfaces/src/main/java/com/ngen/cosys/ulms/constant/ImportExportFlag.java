package com.ngen.cosys.ulms.constant;

import org.springframework.util.StringUtils;

public enum ImportExportFlag {
    IMPORT("I"),
    EXPORT("E"),
    BOTH("B");

    private String flag;

    ImportExportFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public static boolean isImport(String flag) {
        if (IMPORT.getFlag().equals(flag) || BOTH.getFlag().equals(flag)) {
            return true;
        }
        return false;
    }

    public static boolean isExport(String flag) {
        if (EXPORT.getFlag().equals(flag) || BOTH.getFlag().equals(flag)) {
            return true;
        }
        return false;
    }

    public static boolean isValidFlag(String flag, boolean excluseBoth) {
        if (flag == null || "".equals(flag)) return false;

        for (ImportExportFlag value : values()) {
            if(excluseBoth && value == BOTH){
                continue;
            }
            if (value.getFlag().equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }
}
