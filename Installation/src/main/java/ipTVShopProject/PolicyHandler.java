package ipTVShopProject;

import ipTVShopProject.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    InstallationRepository installationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderAccepted_InstallationRequest(@Payload OrderAccepted orderAccepted){

        if(orderAccepted.isMe()){
            Installation installationAccept = new Installation();
            installationAccept.setStatus("INSTALLATIONACCEPTED");
            SimpleDateFormat defaultSimpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
            String today = defaultSimpleDateFormat.format(new Date());
            installationAccept.setInstallReservationDate(today);
            installationAccept.setEngineerId(orderAccepted.getEngineerId());
            installationAccept.setEngineerName(orderAccepted.getEngineerName());
            installationAccept.setOrderId(orderAccepted.getOrderId());

            installationRepository.save(installationAccept);
            System.out.println("##### listener InstallationRequest : " + orderAccepted.toJson());
        }
    }

}
