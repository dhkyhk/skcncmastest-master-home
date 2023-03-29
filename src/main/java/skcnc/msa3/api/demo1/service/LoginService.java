package skcnc.msa3.api.demo1.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skcnc.msa3.api.demo1.service.vo.JoinRequestVO;
import skcnc.msa3.api.demo1.service.vo.JoinResponseVO;
import skcnc.msa3.domain.jpa.primary.entity.EmployeeEntity;
import skcnc.msa3.domain.jpa.primary.repository.EmployeeRepository;
import skcnc.msa3.api.demo1.service.vo.LoginRequestVO;
import skcnc.msa3.api.demo1.service.vo.LoginResponseVO;
import skcnc.msa3.framework.common.AppCommonService;
import skcnc.msa3.framework.common.ContextStoreHelper;
import skcnc.msa3.framework.model.AppRequest;
import skcnc.msa3.framework.model.AppResponse;

import java.util.Optional;

@Service
@Transactional(value = "primaryJpaTxManager", rollbackFor = {Throwable.class})
public class LoginService extends AppCommonService {

    @Autowired
    EmployeeRepository employeeRep;

    public AppResponse<LoginResponseVO> login(AppRequest<LoginRequestVO> inData) {

        Logger log = ContextStoreHelper.getLog();
        log.debug( "LoginService - login START" );

        LoginResponseVO outVo = new LoginResponseVO();
        //Map<String, Object> reqMap = MapperUtil.toMap(inData.getData());	//Sevice 입력값
        LoginRequestVO inVo = inData.getData();
        log.debug( "INPUT : {}", inVo);

        if ( StringUtils.isEmpty(inVo.getId()) ){
            log.error( "ID를 입력 하세요" );
            throw makeException( "MYER0002", "ID"); //MYER0003=입력값 {0}를 확인하세요.
        }

        if ( StringUtils.isEmpty(inVo.getPassword()) ){
            log.error( "Password를 입력 하세요" );
            throw makeException( "MYER0002", "Password"); //MYER0003=입력값 {0}를 확인하세요.
        }

        Optional<EmployeeEntity> employee =  employeeRep.findById( inVo.getId() );

        if ( employee == null || employee.isEmpty() ) {
            log.error( "존재하지 않는 ID" );
            throw makeException( "MYER0034"); //MYER0034=MYD고객ID가 존재하지 않습니다.
        }

        if ( !inVo.getPassword().equals(employee.get().getPassword()) ) {
            log.error( "Password 입력오류" );
            throw makeException( "MYER0004", "Password"); //MYER0004={0} 값을 확인하세요.
        }

        outVo.setSuccYn( "Y" );
        outVo.setErrMsg( "정상처리 되었습니다." );

        log.debug( "LoginService - login END" );

        return makeResponse(outVo, "MYOK1001", "로그인이" );
    }

    public AppResponse<JoinResponseVO> empJoin(AppRequest<JoinRequestVO> inData) {

        EmployeeEntity entiy = new EmployeeEntity();
        JoinResponseVO outVO = new JoinResponseVO();
        JoinRequestVO inVo = inData.getData();

        try {
            if ( "I".equals(inVo.getEventType()) ) {
                entiy.setId( inVo.getId() );
                entiy.setPassword( inVo.getPassword() );
                employeeRep.save( entiy );
            } else if ( "D".equals(inVo.getEventType()) ) {
                entiy.setId( inVo.getId() );
                employeeRep.delete( entiy );
            }
            //else if ( "U".equals(inData.getEventType()) ) {
            //    entiy.setId( inData.getId() );
            //    entiy.setPassword( inData.getPassword() );
            //    employeeRep. ( entiy );
            //}

            outVO.setSuccYn( "Y" );
            outVO.setErrMsg( "" );
        } catch ( Exception e ){
            outVO.setSuccYn( "N" );
            outVO.setErrMsg( e.getMessage() );

            throw makeException( "MYER0005", "회원정보 등록"); //MYER0005={0} 처리중 오류가 발생했습니다.
        }

        return makeResponse(outVO, "MYOK1001", "회원정보 등록이" );
    }
}
