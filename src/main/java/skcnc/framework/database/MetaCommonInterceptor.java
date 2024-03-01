package skcnc.framework.database;

import java.util.Map;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import skcnc.framework.common.ContextStoreHelper;

/**
 * mybatis insert update 쿼리에 기본 항목 강제 설정용 Intercept
 * TODO : 항목이 정해진 후 수정 필요
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
public class MetaCommonInterceptor implements Interceptor {
    public final static String FW_OPR_ID     = "opr_id";     //조작자ID
    public final static String FW_OPR_TRD_ID = "opr_trd_id"; //조작거래ID
    public final static String FW_OPR_TMN_ID = "opr_tmn_id"; //조작터미널ID
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        if(args != null && args.length >= 2) {
            Object parameter = args[1];

            if(parameter instanceof Map) {
                Map map = (Map) parameter;
                map.put(FW_OPR_ID     , ContextStoreHelper.getData(FW_OPR_ID    , String.class) );
                map.put(FW_OPR_TRD_ID , ContextStoreHelper.getData(FW_OPR_TRD_ID, String.class) );
                map.put(FW_OPR_TMN_ID , ContextStoreHelper.getData(FW_OPR_TMN_ID, String.class));
            }
        }

        return invocation.proceed();
    }
}

