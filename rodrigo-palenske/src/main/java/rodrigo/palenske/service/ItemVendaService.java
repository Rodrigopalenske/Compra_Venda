package rodrigo.palenske.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigo.palenske.model.ItemVenda;
import rodrigo.palenske.model.Venda;
import rodrigo.palenske.repository.ItemVendaRepository;
import rodrigo.palenske.repository.VendaRepository;

import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository repository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaService vendaService;

    @Transactional
    public void salvar(ItemVenda itemVenda) {
        if (itemVenda.getDescricao().isEmpty() || itemVenda.getDescricao().isBlank()) return;
        if (itemVenda.getValor().isInfinite() || itemVenda.getValor().isNaN()) return;
        if (itemVenda.getVenda().getId() == null) return;
        var venda = itemVenda.getVenda();
        List<ItemVenda> itensVenda = venda.getItens();
        itensVenda.add(itemVenda);

        repository.save(itemVenda);
        venda.setItens(itensVenda);
        venda = vendaService.calcularValorTotal(venda);
        vendaRepository.save(venda);
    }

    public List<ItemVenda> listarTodos(){
        return repository.findAll();
    }

    public ItemVenda buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void deletarPorId(Long id) {
        var itemVenda = buscarPorId(id);
        if (itemVenda.getId() == null) return;
        var venda = itemVenda.getVenda();
        List<ItemVenda> itensVenda = venda.getItens();
        itensVenda.remove(itemVenda);

        repository.deleteById(id);
        venda.setItens(itensVenda);
        venda = vendaService.calcularValorTotal(venda);
        vendaRepository.save(venda);
    }

}
