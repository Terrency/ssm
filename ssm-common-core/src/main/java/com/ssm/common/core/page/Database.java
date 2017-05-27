package com.ssm.common.core.page;

public enum Database {

    ORACLE, MYSQL, DB2;

    public static Database fromType(final String type) {
        for (Database db : Database.values()) {
            if (db.name().equalsIgnoreCase(type)) {
                return db;
            }
        }
        return null;
    }

}
