package skcnc.msa3.api.demo3.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skcnc.msa3.api.demo3.service.vo.JoinRequestVO;
import skcnc.msa3.api.demo3.service.vo.JoinResponseVO;
import skcnc.msa3.domain.jpa.primary.entity.EmployeeEntity;
import skcnc.msa3.domain.jpa.primary.repository.EmployeeRepository;
import skcnc.msa3.framework.common.AppCommonService;
import skcnc.msa3.framework.common.ContextStoreHelper;
import skcnc.msa3.framework.model.AppRequest;
import skcnc.msa3.framework.model.AppResponse;
import skcnc.msa3.framework.utils.MapperUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(value = "primaryJpaTxManager", rollbackFor = {Throwable.class})
public class DbTestService extends AppCommonService {

    @Autowired
    EmployeeRepository employeeRep;

    public AppResponse<JoinResponseVO> empJoin(AppRequest<JoinRequestVO> inData) {

        Logger log = ContextStoreHelper.getLog();
        log.debug( "DbTestService - empJoin START" );

        JoinResponseVO outVO = new JoinResponseVO();

        JoinRequestVO inVo = inData.getData();
        Optional<EmployeeEntity> employee =  employeeRep.findById( inVo.getId() );
        log.debug( "JPA select employee : {}", employee.get() );

        String query = "mapper.primary.employeemapper.selectone";
        Map<String, Object> reqMap = MapperUtil.toMap( inVo );

        Map<String,Object> dataone = dbio.select( query, reqMap );
        log.debug( "mybatis select employee : {}", dataone );

        query = "mapper.primary.employeemapper.selectarray";
        List<Map<String,Object>> dataList01 = dbio.selectList(query, reqMap);
        log.debug( "mybatis select list employee : {}", dataList01 );

        return makeResponse(outVO, "MYOK1001", "회원정보 조회가" );
    }
}
