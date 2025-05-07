package rodrigo.palenske.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodrigo.palenske.model.ItemCompra;

import java.util.List;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {

}
