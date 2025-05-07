package rodrigo.palenske.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void salvar(ItemCompra itemCompra) {
        if (itemCompra.getDescricao().isEmpty() || itemCompra.getDescricao().isBlank()) return;
        if (itemCompra.getValor().isInfinite() || itemCompra.getValor().isNaN()) return;
        if (itemCompra.getCompra().getId() == null) return;

        repository.save(itemCompra);

        var compra = itemCompra.getCompra();
        var itensCompra = compra.getItens();
        float valorTotalCompra = 0.0F;

        for(int i = 0; i < itensCompra.size(); i++) {
            valorTotalCompra += itensCompra.get(i).getValor();
        }
        compra.setValorTotal(valorTotalCompra);
        compraRepository.save(compra);
    }

    public List<ItemCompra> listarTodos(){
        return repository.findAll();
    }

    public ItemCompra buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public void deletarPorId(Long id) {
        var itemCompra = buscarPorId(id);
        if (itemCompra.getId() == null) return;
        var compra = itemCompra.getCompra();

        repository.deleteById(id);

        var itensCompra = compra.getItens();
        float valorTotalCompra = 0.0F;

        for(int i = 0; i < itensCompra.size(); i++) {
            valorTotalCompra += itensCompra.get(i).getValor();
        }
        compra.setValorTotal(valorTotalCompra);
        compraRepository.save(compra);
    }

}
