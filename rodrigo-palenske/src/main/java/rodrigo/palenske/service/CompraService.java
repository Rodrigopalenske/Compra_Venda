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
public class CompraService {

    @Autowired
    private CompraRepository repository;

    @Autowired
    private ItemCompraRepository itemRepository;


    public Compra calcularValorTotal(Compra compra) {
        var itensCompra = compra.getItens();
        float valorTotalCompra = 0.0F;

        for(int i = 0; i < itensCompra.size(); i++) {
            valorTotalCompra += itensCompra.get(i).getValor();
        }
        compra.setValorTotal(valorTotalCompra);

        return compra;
    }

    @Transactional
    public void salvar(Compra compra){
        if (compra.getCliente().isEmpty() || compra.getCliente().isBlank()) return;

        if (compra.getId() != null) {
            compra.setItens(buscarPorId(compra.getId()).getItens());
            Compra compraAntiga = calcularValorTotal(compra);
            compra.setValorTotal(compraAntiga.getValorTotal());
        } else if (compra.getId() == null) {
            if (compra.getValorTotal() == null) compra.setValorTotal(0.0F);
        }
        
        repository.save(compra);
    }

    public List<Compra> listarTodos(){
        return repository.findAll();
    }

    public Compra buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void deletarPorId(Long id) {
        var compra = buscarPorId(id);
        if (compra.getId() == null) return;

        repository.deleteById(id);
    }

}
