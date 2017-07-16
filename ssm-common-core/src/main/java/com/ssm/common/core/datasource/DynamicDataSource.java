package com.ssm.common.core.datasource;

import com.ssm.common.base.enums.Action;
import org.aspectj.lang.JoinPoint;

public class DynamicDataSource {

    public void setDataSource(JoinPoint joinPoint) {
        MultipleDataSource.setCurrentLookupKey(MultipleDataSource.CURRENT_LOOKUP_KEY_READ);
        String methodName = joinPoint.getSignature().getName();
        changeDataSource(methodName);
    }

    /**
     * 如果是枚举类中的方法则切换为写的数据源
     */
    private void changeDataSource(String methodName) {
        for (Action type : Action.values()) {
            if (methodName.toUpperCase().startsWith(type.name())) {
                MultipleDataSource.setCurrentLookupKey(MultipleDataSource.CURRENT_LOOKUP_KEY_WRITE);
                break;
            }
        }
    }

}
