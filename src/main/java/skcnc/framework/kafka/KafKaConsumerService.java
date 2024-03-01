package skcnc.framework.kafka;

//@Service
public class KafKaConsumerService {

    /* TODO : 로컬 테스트를 위해 일단 kafka 부분 주석 처리함
     *  https://bsssss.tistory.com/1110 확인하고 되면 수정하자..

    @Value("${kafka.rev.topic}")
    String REV_KAFKA;
    @Value("${kafka.send.topic}")
    String SEND_KAFKA;

    @KafkaListener(topics = "${kafka.rev.topic}",
            groupId = "${kafka.rev.group_id}")
    public void consumeA(String  message) {
        initContext(REV_KAFKA);
        Logger log = ContextStoreHelper.getLog();
        log.debug( "kafka A rev message : " + message );
    }

    @KafkaListener(topics = "${kafka.send.topic}",
            groupId = "${kafka.send.group_id}")
    //public void consumeC(KafkaSampleCMessage  message) {
    public void consumeC(String  message) {
        initContext(SEND_KAFKA);
        Logger log = ContextStoreHelper.getLog();
        log.debug( "kafka C rev message : " + message );
    }

    private void initContext(String kafkaName){
        AppHeader ch = new AppHeader();
        ch.setGuid(RandomStringUtils.randomAlphanumeric(16));

        ContextStoreHelper.setData( ContextStoreHelper.API_URL_KEY, kafkaName );

        //추적 아이디를 logger 공통적으로 사용되도록 설정
        MDC.put("GUID", ch.getGuid());
        MDC.put("TRACE_ID", kafkaName );

        //요청 헤더 보관 처리
        ContextStoreHelper.setData(AppHeader.ATTR_KEY, ch);
        ContextStoreHelper.initLog();
    }

    */
}
