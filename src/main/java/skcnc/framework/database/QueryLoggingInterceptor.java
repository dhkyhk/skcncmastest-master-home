package skcnc.framework.database;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;

import skcnc.framework.common.ContextStoreHelper;

/**
 * mybatis query 로깅용.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
        ,@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
        ,@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class })
        ,@Signature(type = Executor.class, method = "queryCursor", args = { MappedStatement.class, Object.class, RowBounds.class})
})
public class QueryLoggingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Logger log = ContextStoreHelper.getLog();

        if(!log.isDebugEnabled()) {
            return invocation.proceed();
        }

        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        BoundSql boundSql = null;

        for(Object arg : args) {
            if(arg instanceof BoundSql) {
                boundSql = (BoundSql) arg;
                break;
            }
        }
        if(boundSql == null) {
            boundSql = ms.getBoundSql(parameter);
        }

        try {
            StringBuilder sb = new StringBuilder(boundSql.getSql());
            if( parameter != null) {
                List<ParameterMapping> paramList = boundSql.getParameterMappings();
                if(paramList != null) {
                    List<Object> valueList = new ArrayList<>();

                    //refer : DefaultParameterHandler.setParameters
                    for(ParameterMapping mapping : boundSql.getParameterMappings()) {
                        if (mapping.getMode() != ParameterMode.OUT) {
                            String property = mapping.getProperty();
                            Object value = null;
                            if(boundSql.hasAdditionalParameter(property)) {
                                value = boundSql.getAdditionalParameter(property);
                            } else if(parameter != null) {
                                if(parameter instanceof String || parameter instanceof Number) {
                                    value = parameter;
                                }else {
                                    try {
                                        value = PropertyUtils.getProperty(parameter, property);
                                    }catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                                        value = parameter;
                                    }
                                }
                            }
                            valueList.add(value);
                        }
                    }
                    for(Object value : valueList) {
                        int start = sb.indexOf("?");
                        int end = start + 1;
                        if(value == null) {
                            sb.replace(start, end, "NULL");
                        } else if(value instanceof Number ) {
                            sb.replace(start, end, value.toString());
                        }else {
                            sb.replace(start, end, "'" + value.toString().replaceAll("\'", "\'\'") + "'");
                        }
                    }
                }
            }

            log.debug("### {} ###\n{}", ms.getId(), sb.toString() );
        }catch(Throwable ex) {
            log.warn("### QueryLogging failed", ex);
        }
        return invocation.proceed();
    }
}