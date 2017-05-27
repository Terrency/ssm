package com.ssm.core.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ssm.core.page.Page;
import com.ssm.core.page.PageImpl;
import com.ssm.core.page.PaginationSqlExecutor;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractDaoSupport {

    @Resource(name = "sqlMapClientTemplate")
    protected SqlMapClientTemplate sqlMapClientTemplate;

    @Resource(name = "sqlExecutor")
    private SqlExecutor sqlExecutor;

    protected Page queryForPage(String statementId, Object parameter, int offset, int length) {
        List<?> list = sqlMapClientTemplate.queryForList(statementId, parameter, offset, length);
        Long recordCount = ((PaginationSqlExecutor) sqlExecutor).getRecordCount();
        return new PageImpl<>(offset / length, length, list, recordCount != null ? recordCount.intValue() : 0);
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        if (sqlExecutor != null) {
            SqlMapClient sqlMapClient = sqlMapClientTemplate.getSqlMapClient();
            if (sqlMapClient instanceof ExtendedSqlMapClient) {
                Field field = ReflectionUtils.findField(SqlMapExecutorDelegate.class, "sqlExecutor");
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, ((ExtendedSqlMapClient) sqlMapClient).getDelegate(), sqlExecutor);
            }
        }
    }

}
