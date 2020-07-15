package ipTVShopProject;

import ipTVShopProject.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    ManagementCenterRepository managementCenterRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverJoinOrdered_OrderRequest(@Payload JoinOrdered joinOrdered){

        //고객에서 요청 시 엔지니어 배치 후 저장.
        if(joinOrdered.isMe()){
            ManagementCenter oa = new ManagementCenter();

            oa.setOrderId(joinOrdered.getId());
            oa.setInstallationAddress(joinOrdered.getInstallationAddress());
            oa.setId(joinOrdered.getId());
            oa.setStatus("JOINORDED");
            oa.setEngineerName("Engineer" + joinOrdered.getId());
            oa.setEngineerId(joinOrdered.getId() + 100);

            managementCenterRepository.save(oa);

        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverInstallationCompleted_InstallationCompleteNotify(@Payload InstallationCompleted installationCompleted){

        //설치 완료 통보 시 기존 Order ID의 설치 완료 상태(Completed) 저장.
        if(installationCompleted.isMe()) {
            System.out.println("ORDERID" + installationCompleted.getOrderId());
            System.out.println("GETID" + installationCompleted.getId());
            try
            {
                System.out.println("##### listener installcompleted : " + installationCompleted.toJson());
                managementCenterRepository.findById(installationCompleted.getOrderId())
                            .ifPresent(
                                    managementCenter -> {
                                        managementCenter.setStatus(installationCompleted.getStatus());
                                        managementCenterRepository.save(managementCenter);
                                    });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelOrdered_CancelRequest(@Payload CancelOrdered cancelOrdered){

        //취소 요청
        if(cancelOrdered.isMe()){
            try {
                        System.out.println("##### listener cancelORderd : " + cancelOrdered.toJson());

                        Optional<ManagementCenter> mc = managementCenterRepository.findByOrderId(cancelOrdered.getId());
                        mc.get().setStatus("CancelRequested");
                        managementCenterRepository.save(mc.get());


                        System.out.println("######  test10");

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
