package rodrigo.palenske.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodrigo.palenske.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
