package ipTVShopProject;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipTVShopProject.config.kafka.KafkaProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

@Entity
@Table(name="ManagementCenter_table")
public class ManagementCenter {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String installationAddress;
    private Long engineerId;
    private String engineerName;
    private String status;

    @PostPersist
    public void onPostPersist() {

            //Join Ordered(Accepted 변경) 후 Order Accepted 이벤트 호출(InstallationRequest Policy 전달).
            if("Accepted".equals(getStatus())) {

                OrderAccepted orderAccepted = new OrderAccepted();

                orderAccepted.setId(this.getId());
                orderAccepted.setInstallationAddress(this.getInstallationAddress());
                orderAccepted.setOrderId(this.getId());
                orderAccepted.setStatus(this.getStatus());
                orderAccepted.setEngineerId(this.getEngineerId());
                orderAccepted.setEngineerName(this.getEngineerName());

                BeanUtils.copyProperties(this, orderAccepted);
                orderAccepted.publishAfterCommit();
            }
    }

    @PostUpdate
    public void onPostUpdate() {

        //설치 완료 통보 후 Join Completed 이벤트 호출.
        if (this.getStatus().equals("Completed")) {
            JoinCompleted jc = new JoinCompleted();

            jc.setEngineerId(this.getEngineerId());
            jc.setEngineerName(this.getEngineerName());
            jc.setId(this.getId());
            jc.setInstallationAddress(this.getInstallationAddress());
            jc.setOrderId(this.orderId);
            jc.setStatus(this.getStatus());

            BeanUtils.copyProperties(this, jc);
            jc.publishAfterCommit();

        }
        //주문 취소 요청 승인 및 거절 확인을 위한 동기 호
        else  if (this.getStatus().equals("CancelRequested")){
            ipTVShopProject.external.Installation installation = new ipTVShopProject.external.Installation();
            // mappings goes here
            System.out.println("111111111");

            String result = ManagementCenterApplication.applicationContext.getBean(ipTVShopProject.external.InstallationService.class)
                    .installationCancellation();

            System.out.println(result + ": TEST");
            if (result.equals("Approval")) {

                OrderCancelAccepted orderCancelAccepted = new OrderCancelAccepted();

                orderCancelAccepted.setId(this.getId());
                orderCancelAccepted.setInstallationAddress(this.getInstallationAddress());
                orderCancelAccepted.setOrderId(this.getId());
                orderCancelAccepted.setStatus("CanceledApproved");
                orderCancelAccepted.setEngineerId(this.getEngineerId());
                orderCancelAccepted.setEngineerName(this.getEngineerName());

                BeanUtils.copyProperties(this, orderCancelAccepted);
                orderCancelAccepted.publishAfterCommit();
            } else {
                OrderCancelNotAccepted orderCancelNotAccepted = new OrderCancelNotAccepted();

                orderCancelNotAccepted.setId(this.getId());
                orderCancelNotAccepted.setInstallationAddress(this.getInstallationAddress());
                orderCancelNotAccepted.setOrderId(this.getId());
                orderCancelNotAccepted.setStatus("CanceledNotApproved");
                orderCancelNotAccepted.setEngineerId(this.getEngineerId());
                orderCancelNotAccepted.setEngineerName(this.getEngineerName());

                BeanUtils.copyProperties(this, orderCancelNotAccepted);
                orderCancelNotAccepted.publishAfterCommit();
            }


        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getInstallationAddress() {
        return installationAddress;
    }

    public void setInstallationAddress(String installationAddress) {
        this.installationAddress = installationAddress;
    }
    public Long getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Long engineerId) {
        this.engineerId = engineerId;
    }
    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
