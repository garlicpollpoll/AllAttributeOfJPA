package toy.AllAttributeOfJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.AllAttributeOfJPA.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
