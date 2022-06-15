package com.ngen.cosys.cscc.constant;

public enum ImportExport {
    IMPORT("I"),
    EXPORT("E");

    private String importExportFlag;

    ImportExport(String importExportFlag) {
        this.importExportFlag = importExportFlag;
    }

    public String getImportExportFlag() {
        return importExportFlag;
    }

    public static boolean isValid(String flag) {
        if (flag == null || "".equals(flag)) return false;

        for (ImportExport val : values()) {
            if (val.getImportExportFlag().equals(flag)) return true;
        }

        return false;
    }
}
