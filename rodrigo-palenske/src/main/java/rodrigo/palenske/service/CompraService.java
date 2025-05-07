package rodrigo.palenske.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigo.palenske.model.Compra;
import rodrigo.palenske.model.ItemCompra;
import rodrigo.palenske.model.ItemVenda;
import rodrigo.palenske.model.Venda;
import rodrigo.palenske.repository.CompraRepository;
import rodrigo.palenske.repository.ItemCompraRepository;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository repository;

    @Autowired
    private ItemCompraRepository itemRepository;

    public void salvar(Compra compra){
        if (compra.getCliente().isEmpty() || compra.getCliente().isBlank()) return;
        if (compra.getValorTotal() == null) compra.setValorTotal(0.0F);
        if (compra.getValorTotal().isInfinite() || compra.getValorTotal().isNaN()) return;

        repository.save(compra);
    }

    public List<Compra> listarTodos(){
        return repository.findAll();
    }

    public Compra buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public void deletarPorId(Long id) {
        var compra = buscarPorId(id);
        if (compra.getId() == null) return;

        repository.deleteById(id);
    }

}
