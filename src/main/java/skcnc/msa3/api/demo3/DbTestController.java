package skcnc.msa3.api.demo3;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skcnc.msa3.api.demo3.service.vo.JoinRequestVO;
import skcnc.msa3.api.demo3.service.vo.JoinResponseVO;
import skcnc.msa3.api.demo3.service.DbTestService;
import skcnc.msa3.framework.common.AppCommonController;
import skcnc.msa3.framework.kafka.KafkaProducerService;
import skcnc.msa3.framework.model.AppRequest;
import skcnc.msa3.framework.model.AppResponse;

@Tag(name = "Demo03", description = "테스트 Demo03")
@RestController
@RequestMapping("/demo3/dbtestcontroller")
public class DbTestController extends AppCommonController {

    @Autowired
    DbTestService dbTestService;

    @Operation(summary = "사원등록", description = "사원등록.")
    @PostMapping("/empjoin.do")
    public AppResponse<JoinResponseVO> empJoin(@RequestBody AppRequest<JoinRequestVO> inData) {
        return dbTestService.empJoin(inData);
    }
}
