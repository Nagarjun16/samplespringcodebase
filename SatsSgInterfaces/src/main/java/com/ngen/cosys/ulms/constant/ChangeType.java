package com.ngen.cosys.ulms.constant;

public enum ChangeType {
    NEW("N", "N"),
    UPDATE("U", "Y"),
    DELETE("D", "Y"),
    BOOKINGCHANGED("T", "Y"),
    ALL("A", "Y");

    private String changeType;
    private String changeFlag;

    ChangeType(String changType, String changeNewFlag) {
        this.changeType = changType;
        this.changeFlag = changeNewFlag;
    }

    public String getChangeType() {
        return changeType;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    public static boolean isValid(String changeType) {
        for (ChangeType type : values()) {
            if (type.getChangeType().equalsIgnoreCase(changeType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneValid(String changeType) {
        return !isValid(changeType);
    }

    public static boolean isChanged(String changeType) {
        for (ChangeType type : values()) {
            if (type.getChangeType().equals(changeType) &&
                    type.getChangeFlag().equals("Y")) {
                return true;
            }
        }
        return false;
    }

    public static ChangeType getChangeTypeByValue(String changeTypeValue){
        for (ChangeType type : values()) {
            if (type.getChangeType().equalsIgnoreCase(changeTypeValue)) {
                return type;
            }
        }
        return null;
    }

    public static boolean isNew(String changeType) {
        return NEW.changeType.equals(changeType) || ALL.changeType.equals(changeType) ? true : false;
    }

    public static boolean isDelete(String changeType) {
        return DELETE.changeType.equals(changeType) || ALL.changeType.equals(changeType) ? true : false;
    }

    public static boolean isBookingChanged(String changeType) {
        return BOOKINGCHANGED.changeType.equals(changeType) || ALL.changeType.equals(changeType) ? true : false;
    }

    public static boolean isUpdate(String changeType) {
        return UPDATE.changeType.equals(changeType) || ALL.changeType.equals(changeType) ? true : false;
    }
}
