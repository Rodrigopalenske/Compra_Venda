package rodrigo.palenske.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigo.palenske.model.ItemVenda;
import rodrigo.palenske.repository.ItemVendaRepository;
import rodrigo.palenske.repository.VendaRepository;

import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository repository;
    @Autowired
    private VendaRepository vendaRepository;

    public void salvar(ItemVenda itemVenda) {
        if (itemVenda.getDescricao().isEmpty() || itemVenda.getDescricao().isBlank()) return;
        if (itemVenda.getValor().isInfinite() || itemVenda.getValor().isNaN()) return;
        if (itemVenda.getVenda().getId() == null) return;
        var venda = itemVenda.getVenda();

        repository.save(itemVenda);


        var itensvenda = venda.getItens();
        float valorTotalVenda = 0.0F;

        for(int i = 0; i < itensvenda.size(); i++) {
            valorTotalVenda += itensvenda.get(i).getValor();
        }
        venda.setValorTotal(valorTotalVenda);
        vendaRepository.save(venda);
    }

    public List<ItemVenda> listarTodos(){
        return repository.findAll();
    }

    public ItemVenda buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public void deletarPorId(Long id) {
        var itemVenda = buscarPorId(id);
        if (itemVenda.getId() == null) return;
        var venda = itemVenda.getVenda();

        repository.deleteById(id);

        var itensvenda = venda.getItens();
        float valorTotalVenda = 0.0F;

        for(int i = 0; i < itensvenda.size(); i++) {
            valorTotalVenda += itensvenda.get(i).getValor();
        }
        venda.setValorTotal(valorTotalVenda);
        vendaRepository.save(venda);
    }

}
