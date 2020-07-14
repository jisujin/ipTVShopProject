package ipTVShopProject;

import org.springframework.data.repository.CrudRepository;

public interface InstallationRepository extends CrudRepository<Installation, Long> {
    Installation findByOrderId(Long orderId);

}