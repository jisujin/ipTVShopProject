package ipTVShopProject.config.kafka;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaListener {
    @StreamListener(KafkaProcessor.INPUT)
    public void onEventByString(@Payload String event){
        System.out.println("====== start event message ================");
        System.out.println(event);
        System.out.println("====== end event message ================");
    }
}