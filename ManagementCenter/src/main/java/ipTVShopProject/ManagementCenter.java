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
        System.out.println(this.getStatus() + "POST TEST");

        if("JOINORDED".equals(this.getStatus())) {

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
        if (this.getStatus().equals("INSTALLCOMPLETED")) {

            System.out.println("11111111" + this.getStatus());

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

            System.out.println("TEST1");

            ipTVShopProject.external.Installation installation = new ipTVShopProject.external.Installation();

            installation.setOrderId(this.getOrderId());
            ManagementCenterApplication.applicationContext.getBean(ipTVShopProject.external.InstallationService.class)
                    .installationCancellation(installation);

            OrderCancelAccepted orderCancelAccepted = new OrderCancelAccepted();

            orderCancelAccepted.setId(this.getId());
            orderCancelAccepted.setInstallationAddress(this.getInstallationAddress());
            orderCancelAccepted.setOrderId(this.getId());
            orderCancelAccepted.setStatus("ORDERCANCELED");
            orderCancelAccepted.setEngineerId(this.getEngineerId());
            orderCancelAccepted.setEngineerName(this.getEngineerName());

            BeanUtils.copyProperties(this, orderCancelAccepted);
            orderCancelAccepted.publishAfterCommit();



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