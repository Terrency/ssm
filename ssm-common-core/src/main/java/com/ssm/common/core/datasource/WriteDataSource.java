package com.ssm.common.core.datasource;

import org.apache.commons.dbcp2.BasicDataSource;

public class WriteDataSource extends BasicDataSource {

    private static final long serialVersionUID = 20160117L;

    /**
     * @param password encrypt password
     */
    @Override
    public void setPassword(String password) {
        try {
            password = AESCipher.decrypt(password, "CHINESE SOFTWARE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.setPassword(password);
    }

}
