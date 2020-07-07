package ipTVShopProject;

import ipTVShopProject.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverJoinCompleted_JoinCompletionNotify(@Payload JoinCompleted joinCompleted){

        if(joinCompleted.isMe()){
            System.out.println("##### listener JoinCompletionNotify : " + joinCompleted.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelNotAccepted_OrderCancelImpossible(@Payload OrderCancelNotAccepted orderCancelNotAccepted){

        if(orderCancelNotAccepted.isMe()){
            System.out.println("##### listener OrderCancelImpossible : " + orderCancelNotAccepted.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelAccepted_OrderCancelAccept(@Payload OrderCancelAccepted orderCancelAccepted){

        if(orderCancelAccepted.isMe()){
            System.out.println("##### listener OrderCancelAccept : " + orderCancelAccepted.toJson());
        }
    }

}
