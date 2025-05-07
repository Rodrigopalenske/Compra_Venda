package rodrigo.palenske.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodrigo.palenske.model.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
}
