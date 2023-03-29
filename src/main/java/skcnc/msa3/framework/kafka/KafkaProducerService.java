package skcnc.msa3.framework.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

//@Service
public class KafkaProducerService {

    /* TODO : 로컬 테스트를 위해 일단 kafka 부분 주석 처리함
     *  https://bsssss.tistory.com/1110 확인하고 되면 수정하자..
    @Value("${kafka.send.topic}")
    String sendTopic;

    @Autowired
    private KafkaTemplate<String, KafkaSampleCMessage> kafkaTemplate;

    public void sendMessage(KafkaSampleCMessage data){
        //LOGGER.info(String.format("Message sent -> %s", data.toString()));

        Message<KafkaSampleCMessage> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, sendTopic)
                .build();

        kafkaTemplate.send(message);
    }*/
}
