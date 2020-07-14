package ipTVShopProject;

import ipTVShopProject.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusViewHandler {


    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenJoinOrdered_then_CREATE_ (@Payload JoinOrdered joinOrdered) {
        try {
            if (joinOrdered.isMe()) {
                // view 객체 생성
                OrderStatus orderStatus = new OrderStatus();
                // view 객체에 이벤트의 Value 를 set 함
                orderStatus.setOrderId(joinOrdered.getId());
                orderStatus.setProductId(joinOrdered.getProductId());
                orderStatus.setProductName(joinOrdered.getProductName());
                orderStatus.setInstallationAddress(joinOrdered.getInstallationAddress());
                orderStatus.setOrderDate(joinOrdered.getOrderDate());
                orderStatus.setCustomerId(joinOrdered.getCustomerId());
                orderStatus.setStatus(joinOrdered.getStatus());
                // view 레파지 토리에 save
                orderStatusRepository.save(orderStatus);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderAccepted_then_UPDATE_(@Payload OrderAccepted orderAccepted) {
        try {
            if (orderAccepted.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(orderAccepted.getOrderId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(orderAccepted.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenInstallationAccepted_then_UPDATE_(@Payload InstallationAccepted installationAccepted) {
        try {
            if (installationAccepted.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(installationAccepted.getOrderId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setEngineerName(installationAccepted.getEngineerName());
                    orderStatus.setStatus(installationAccepted.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenInstallationCompleted_then_UPDATE_(@Payload InstallationCompleted installationCompleted) {
        try {
            if (installationCompleted.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(installationCompleted.getOrderId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(installationCompleted.getStatus());
                    orderStatus.setInstallCompleteDate(installationCompleted.getInstallCompleteDate());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenJoinCompleted_then_UPDATE_(@Payload JoinCompleted joinCompleted) {
        try {
            if (joinCompleted.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(joinCompleted.getOrderId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(joinCompleted.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenCancelOrdered_then_UPDATE_(@Payload CancelOrdered cancelOrdered) {
        try {
            if (cancelOrdered.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(cancelOrdered.getId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(cancelOrdered.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelAccepted_then_UPDATE_(@Payload OrderCancelAccepted orderCancelAccepted) {
        try {
            if (orderCancelAccepted.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(orderCancelAccepted.getOrderId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(orderCancelAccepted.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenInstallationCanceled_then_UPDATE_(@Payload InstallationCanceled installationCanceled) {
        try {
            if (installationCanceled.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(installationCanceled.getOrderId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(installationCanceled.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCanceled_then_UPDATE_(@Payload OrderCanceled orderCanceled) {
        try {
            if (orderCanceled.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(orderCanceled.getId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(orderCanceled.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelRejected_then_UPDATE_(@Payload OrderCancelRejected orderCancelRejected) {
        try {
            if (orderCancelRejected.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(orderCancelRejected.getId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(orderCancelRejected.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenJoinOrderCompleted_then_UPDATE_(@Payload JoinOrderCompleted joinOrderCompleted) {
        try {
            if (joinOrderCompleted.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(joinOrderCompleted.getId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(joinOrderCompleted.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelNotAccepted_then_UPDATE_(@Payload OrderCancelNotAccepted orderCancelNotAccepted) {
        try {
            if (orderCancelNotAccepted.isMe()) {
                // view 객체 조회
                List<OrderStatus> orderStatusList = orderStatusRepository.findByOrderId(orderCancelNotAccepted.getOrderId());
                for(OrderStatus orderStatus : orderStatusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderStatus.setStatus(orderCancelNotAccepted.getStatus());
                    // view 레파지 토리에 save
                    orderStatusRepository.save(orderStatus);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}