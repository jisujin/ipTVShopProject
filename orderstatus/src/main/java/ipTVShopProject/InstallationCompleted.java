
package ipTVShopProject;

public class InstallationCompleted extends AbstractEvent {

    private Long id;
    private Long EngineerID;
    private String EngineerName;
    private String InstallReservationDate;
    private String InstallCompleteDate;
    private Long OrderId;
    private String Status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getEngineerId() {
        return EngineerID;
    }

    public void setEngineerId(Long EngineerID) {
        this.EngineerID = EngineerID;
    }
    public String getEngineerName() {
        return EngineerName;
    }

    public void setEngineerName(String EngineerName) {
        this.EngineerName = EngineerName;
    }
    public String getInstallReservationDate() {
        return InstallReservationDate;
    }

    public void setInstallReservationDate(String InstallReservationDate) {
        this.InstallReservationDate = InstallReservationDate;
    }
    public String getInstallCompleteDate() {
        return InstallCompleteDate;
    }

    public void setInstallCompleteDate(String InstallCompleteDate) {
        this.InstallCompleteDate = InstallCompleteDate;
    }
    public Long getOrderId() {
        return OrderId;
    }

    public void setOrderId(Long OrderId) {
        this.OrderId = OrderId;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
