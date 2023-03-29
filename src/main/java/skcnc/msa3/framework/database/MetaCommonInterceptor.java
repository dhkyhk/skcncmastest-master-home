package skcnc.msa3.framework.database;

import java.util.Map;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import skcnc.msa3.framework.common.ContextStoreHelper;

/**
 * mybatis insert update 쿼리에 기본 항목 강제 설정용 Intercept
 * TODO : 항목이 정해진 후 수정 필요
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
public class MetaCommonInterceptor implements Interceptor {
    public final static String FW_COMMON_KEY_OPRT_STFNO = "fw_common_oprt_stfno"; //조작직원번호
    public final static String FW_COMMON_KEY_TR_CD = "fw_common_tr_cd"; //조작거래코드(화면번호 or 배치ID)

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        if(args != null && args.length >= 2) {
            Object parameter = args[1];

            if(parameter instanceof Map) {
                Map map = (Map) parameter;
                String fw_common_oprt_stfno = ContextStoreHelper.getData(FW_COMMON_KEY_OPRT_STFNO, String.class);
                String fw_common_tr_cd = ContextStoreHelper.getData(FW_COMMON_KEY_TR_CD, String.class);
                map.put(FW_COMMON_KEY_OPRT_STFNO, fw_common_oprt_stfno);
                map.put(FW_COMMON_KEY_TR_CD, fw_common_tr_cd);
            }
        }

        return invocation.proceed();
    }
}

