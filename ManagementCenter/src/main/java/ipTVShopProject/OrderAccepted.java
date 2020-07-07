
package ipTVShopProject;

public class OrderAccepted extends AbstractEvent {

    private Long id;
    private Long OrderId;
    private String InstallationAddress;
    private Long EngineerId;
    private String EngineerName;
    private String Status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return OrderId;
    }

    public void setOrderId(Long OrderId) {
        this.OrderId = OrderId;
    }
    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String InstallationAddress) {
        this.InstallationAddress = InstallationAddress;
    }
    public Long getEngineerId() {
        return EngineerId;
    }

    public void setEngineerId(Long EngineerId) {
        this.EngineerId = EngineerId;
    }
    public String getEngineerName() {
        return EngineerName;
    }

    public void setEngineerName(String EngineerName) {
        this.EngineerName = EngineerName;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
