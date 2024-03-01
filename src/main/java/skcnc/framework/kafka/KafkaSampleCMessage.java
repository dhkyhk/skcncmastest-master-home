package skcnc.framework.kafka;

import lombok.Data;

@Data
public class KafkaSampleCMessage {
    private String eventType;
    private String timestamp;
    private String jobType;
    private Long id;
    private Long version;
    private String title;
    private int minEnrollment;
    private int maxEnrollment;
}
