package rodrigo.palenske.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigo.palenske.model.Compra;
import rodrigo.palenske.model.ItemCompra;
import rodrigo.palenske.repository.CompraRepository;
import rodrigo.palenske.repository.ItemCompraRepository;

import java.util.List;

@Service
public class ItemCompraService {

    @Autowired
    private ItemCompraRepository repository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraService compraService;

    @Transactional
    public void salvar(ItemCompra itemCompra) {
        if (itemCompra.getDescricao().isEmpty() || itemCompra.getDescricao().isBlank()) return;
        if (itemCompra.getValor().isInfinite() || itemCompra.getValor().isNaN()) return;
        if (itemCompra.getCompra().getId() == null) return;
        Compra compra = itemCompra.getCompra();
        List<ItemCompra> itensCompra = compra.getItens();
        itensCompra.add(itemCompra);

        repository.save(itemCompra);
        compra.setItens(itensCompra);
        compra = compraService.calcularValorTotal(compra);
        compraRepository.save(compra);
    }

    public List<ItemCompra> listarTodos(){
        return repository.findAll();
    }

    public ItemCompra buscarPorId(Long id) {
        return repository.findById(id).get();
    }
    @Transactional
    public void deletarPorId(Long id) {
        var itemCompra = buscarPorId(id);
        if (itemCompra.getId() == null) return;
        Compra compra = itemCompra.getCompra();
        List<ItemCompra> itensCompra = compra.getItens();
        itensCompra.remove(itemCompra);

        repository.deleteById(id);
        compra.setItens(itensCompra);
        compra = compraService.calcularValorTotal(compra);
        compraRepository.save(compra);
    }

}
