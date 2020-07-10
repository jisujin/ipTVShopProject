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
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverJoinOrdered_OrderRequest(@Payload JoinOrdered joinOrdered){

        if(joinOrdered.isMe()){
            System.out.println("##### listener OrderRequest : " + joinOrdered.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverInstallationCompleted_InstallationCompleteNotify(@Payload InstallationCompleted installationCompleted){

        if(installationCompleted.isMe()){
            System.out.println("##### listener InstallationCompleteNotify : " + installationCompleted.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelOrdered_CancelRequest(@Payload CancelOrdered cancelOrdered){

        if(cancelOrdered.isMe()){
            System.out.println("##### listener CancelRequest : " + cancelOrdered.toJson());
        }
    }

}
