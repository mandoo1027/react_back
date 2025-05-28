package com.commerce.comm;

public enum CODE {
    USE_STAT_CD_01("01", "승인요청"),
    USE_STAT_CD_02("02", "승인"),
    USE_STAT_CD_03("03", "탈퇴대기"),
    USE_STAT_CD_04("04", "탈퇴");

    private final String code;
    private final String name;

    CODE(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    // 🔍 code 값으로 name만 바로 반환
    public static String getNameByCode(String code) {
        for (CODE status : values()) {
            if (status.code.equals(code)) {
                return status.name;
            }
        }
        return ""; // 또는 "알 수 없음", null 등 상황에 따라 처리
    }
}
