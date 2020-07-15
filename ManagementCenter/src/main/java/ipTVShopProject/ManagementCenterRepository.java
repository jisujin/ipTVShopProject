package ipTVShopProject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ManagementCenterRepository extends CrudRepository<ManagementCenter, Long> {

    Optional<ManagementCenter> findByOrderId(Long orderId);

}