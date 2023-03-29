package skcnc.msa3.api.demo1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skcnc.msa3.api.demo1.service.LoginService;
import skcnc.msa3.api.demo1.service.vo.JoinRequestVO;
import skcnc.msa3.api.demo1.service.vo.JoinResponseVO;
import skcnc.msa3.api.demo1.service.vo.LoginRequestVO;
import skcnc.msa3.api.demo1.service.vo.LoginResponseVO;
import skcnc.msa3.framework.common.AppCommonController;
import skcnc.msa3.framework.common.ContextStoreHelper;
import skcnc.msa3.framework.model.AppRequest;
import skcnc.msa3.framework.model.AppResponse;

@Tag(name = "Demo01", description = "테스트 Demo01")
@RestController
@RequestMapping("/demo1/controller001")
public class Controller001 extends AppCommonController {

    @Autowired
    LoginService loginService;

    @Operation(summary = "login", description = "login 입니다.")
    //@ApiResponses(value = {
    //        @ApiResponse(responseCode = "200", description = "successful operation",
    //                content = @Content(schema = @Schema(implementation = LoginResponseVO.class))),
    //        @ApiResponse(responseCode = "400", description = "bad request operation",
    //                content = @Content(schema = @Schema(implementation = LoginResponseVO.class)))
    //})
    @PostMapping("/login.do")
    public AppResponse<LoginResponseVO> login(@RequestBody AppRequest<LoginRequestVO> inData) {
        Logger log = ContextStoreHelper.getLog();
        log.debug( "Controller001 - login START" );
        return loginService.login(inData);
    }

    @Operation(summary = "emp join", description = "사원등록.")
    @PostMapping("/empjoin.do")
    public AppResponse<JoinResponseVO> empJoin(@RequestBody AppRequest<JoinRequestVO> inData) {
        return loginService.empJoin(inData);
    }
}
