package ipTVShopProject;

import ipTVShopProject.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

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
            oa.setStatus("Accepted");
            oa.setEngineerName("Engineer" + joinOrdered.getId());
            oa.setEngineerId(joinOrdered.getId() + 100);

            managementCenterRepository.save(oa);

        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverInstallationCompleted_InstallationCompleteNotify(@Payload InstallationCompleted installationCompleted){

        //설치 완료 통보 시 기존 Order ID의 설치 완료 상태(Completed) 저장.
        if(installationCompleted.isMe()) {
            System.out.println("22222");
            try
            {
                System.out.println("##### listener installcompleted : " + installationCompleted.toJson());
                managementCenterRepository.findById(installationCompleted.getOrderId())
                            .ifPresent(
                                    managementCenter -> {
                                        managementCenter.setStatus(installationCompleted.getStatus());
                                       // managementCenter.setEngineerId(installationCompleted.getEngineerId());
                                       // managementCenter.setEngineerName(installationCompleted.getEngineerName());
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
                    managementCenterRepository.findById(cancelOrdered.getId()).ifPresent((managementCenter) -> {
                    managementCenter.setStatus("CancelRequested");
                    managementCenterRepository.save(managementCenter);
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
