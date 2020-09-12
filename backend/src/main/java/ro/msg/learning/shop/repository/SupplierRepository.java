package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entity.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Integer> {
}
