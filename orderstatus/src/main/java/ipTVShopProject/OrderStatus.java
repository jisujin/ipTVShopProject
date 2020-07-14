package ipTVShopProject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="OrderStatus_table")
public class OrderStatus {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long orderId;
        private Long productId;
        private String productName;
        private String installationAddress;
        private Long customerId;
        private String orderDate;
        private String engineerName;
        private String installCompleteDate;
        private String status;


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
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }
        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
        public String getInstallationAddress() {
            return installationAddress;
        }

        public void setInstallationAddress(String installationAddress) {
            this.installationAddress = installationAddress;
        }
        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }
        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }
        public String getEngineerName() {
            return engineerName;
        }

        public void setEngineerName(String engineerName) {
            this.engineerName = engineerName;
        }
        public String getInstallCompleteDate() {
            return installCompleteDate;
        }

        public void setInstallCompleteDate(String installCompleteDate) {
            this.installCompleteDate = installCompleteDate;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        
        @Override
        public String toString() {
            return "OrderStatus{" +
                    "id=" + id +
                    ", orderId=" + orderId +
                    ", productId=" + productId +
                    ", productName='" + productName + '\'' +
                    ", installationAddress='" + installationAddress + '\'' +
                    ", customerId=" + customerId +
                    ", orderDate='" + orderDate + '\'' +
                    ", engineerName='" + engineerName + '\'' +
                    ", installCompleteDate='" + installCompleteDate + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

}
