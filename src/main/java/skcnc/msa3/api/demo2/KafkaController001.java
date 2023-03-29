package skcnc.msa3.api.demo2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skcnc.msa3.api.demo1.service.vo.LoginRequestVO;
import skcnc.msa3.api.demo1.service.vo.LoginResponseVO;
import skcnc.msa3.api.demo2.service.vo.KafkaRequestVO;
import skcnc.msa3.api.demo2.service.vo.KafkaResponseVO;
import skcnc.msa3.framework.kafka.KafkaProducerService;
import skcnc.msa3.framework.kafka.KafkaSampleCMessage;

@Tag(name = "Demo02", description = "테스트 Demo02")
@RestController
@RequestMapping("/demo2/kafkacontroller001")
public class KafkaController001 {

    /* TODO : 로컬 테스트를 위해 일단 kafka 부분 주석 처리함
     *  https://bsssss.tistory.com/1110 확인하고 되면 수정하자..

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Operation(summary = "kafka send", description = "kafka 전송 입니다.")
    @PostMapping("/kafka_send.do")
    public ResponseEntity<KafkaResponseVO> kafkaSend(@RequestBody KafkaRequestVO inData) {

        KafkaResponseVO outVo = new KafkaResponseVO();

        try {
            KafkaSampleCMessage data = new KafkaSampleCMessage();

            data.setEventType( inData.getEventType() );
            //private String timestamp;
            data.setJobType( "TEST Type" );
            data.setId( 0l );
            data.setVersion( 0l ); ;
            data.setTitle( "Test Title" );
            data.setMinEnrollment( 0 );
            data.setMaxEnrollment( 10 );

            kafkaProducerService.sendMessage(data);
            outVo.setSuccYn("Y");
            return ResponseEntity.ok().body( outVo );
        } catch ( Exception e ){
            outVo.setSuccYn("N");
            outVo.setErrMsg( e.getMessage() );
            return ResponseEntity.ok().body( outVo );
        }
    }*/
}
