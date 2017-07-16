package com.ssm.common.core.page;

import com.ssm.common.base.page.Pageable;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 一、MyBatis插件可以拦截 Executor, StatementHandler, ParameterHandler, ResultSetHandler 这四个对象:
 * (1)Executor: 拦截执行器的方法;
 * (2)StatementHandler: 拦截SQL语法构建的处理;
 * (3)ParameterHandler: 拦截参数的处理;
 * (4)ResultSetHandler: 拦截结果集的处理.
 * <p/>
 * 二、MetaObject的作用是可以帮助我们获取或设置一些属性(包括私有的).
 * MetaObject routingStatementHandler = SystemMetaObject.forObject(statementHandler);
 * routingStatementHandler.setValue("delegate.boundSql.sql", sql);
 * <p/>
 * 三、StatementHandler实现类:
 * (1)RoutingStatementHandler: 这是一个封装类, 它不提供具体的实现, 只是根据Executor的类型创建不同类型的StatementHandler;
 * (2)SimpleStatementHandler: 这个类对应于JDBC的Statement对象, 用于没有预编译参数SQL的执行;
 * (3)PreparedStatementHandler: 这个类用于预编译参数SQL的执行;
 * (4)CallableStatementHandler: 这个类用于存储过程的执行.
 *
 * @author Gavin
 * @see org.apache.ibatis.executor.statement.RoutingStatementHandler#delegate
 */
@Deprecated
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagingInterceptor implements Interceptor {

    private final Dialect dialect;

    public PagingInterceptor(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        Object parameter = boundSql.getParameterObject();
        if (parameter instanceof Pageable) {
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            Pageable pageable = (Pageable) parameter;
            int count = pageable.isCountable() ? getCount((Connection) invocation.getArgs()[0], ms, boundSql) : 0;
            // pageable.setCount(count);
            String limitSql = dialect.getLimitString(boundSql.getSql(), pageable.getOffset(), pageable.getLimit());
            metaObject.setValue("delegate.boundSql.sql", limitSql);
            metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private int getCount(Connection conn, MappedStatement ms, BoundSql boundSql) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String countSql = dialect.getCountString(boundSql.getSql());
            statement = conn.prepareStatement(countSql);
            Object parameter = boundSql.getParameterObject();
            BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
            ParameterHandler handler = new DefaultParameterHandler(ms, parameter, countBoundSql);
            handler.setParameters(statement);
            resultSet = statement.executeQuery();
            int recordCount = 0;
            if (resultSet.next()) {
                recordCount = resultSet.getInt(1);
            }
            return recordCount;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

}
