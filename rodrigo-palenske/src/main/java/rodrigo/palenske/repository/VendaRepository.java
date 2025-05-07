package rodrigo.palenske.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodrigo.palenske.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
