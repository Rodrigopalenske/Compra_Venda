package rodrigo.palenske.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigo.palenske.model.ItemVenda;
import rodrigo.palenske.model.Venda;
import rodrigo.palenske.repository.ItemVendaRepository;
import rodrigo.palenske.repository.VendaRepository;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;
    @Autowired
    private ItemVendaRepository itemRepository;

    public void salvar(Venda venda){
        if (venda.getCliente().isBlank() || venda.getCliente().isEmpty()) return;
        if (venda.getValorTotal() == null) venda.setValorTotal(0.0F);
        if (venda.getValorTotal().isInfinite() || venda.getValorTotal().isNaN()) return;

        repository.save(venda);
    }

    public List<Venda> listarTodos(){
        return repository.findAll();
    }

    public Venda buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public void deletarPorId(Long id) {
        var venda = buscarPorId(id);
        if (venda.getId() == null) return;

        repository.deleteById(id);
    }

}
