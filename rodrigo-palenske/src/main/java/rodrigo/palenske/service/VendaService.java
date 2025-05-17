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
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private ItemVendaRepository itemRepository;

    public Venda calcularValorTotal(Venda venda) {
        var itensVenda = venda.getItens();
        float valorTotalVenda = 0.0F;

        for(int i = 0; i < itensVenda.size(); i++) {
            valorTotalVenda += itensVenda.get(i).getValor();
        }
        venda.setValorTotal(valorTotalVenda);

        return venda;
    }

    @Transactional
    public void salvar(Venda venda){
        if (venda.getCliente().isBlank() || venda.getCliente().isEmpty()) return;

        if (venda.getId() != null) {
            venda.setItens(buscarPorId(venda.getId()).getItens());
            Venda vendaAntiga = calcularValorTotal(venda);
            venda.setValorTotal(vendaAntiga.getValorTotal());
        } else if (venda.getId() == null) {
            if (venda.getValorTotal() == null) venda.setValorTotal(0.0F);
        }

        repository.save(venda);
    }

    public List<Venda> listarTodos(){
        return repository.findAll();
    }

    public Venda buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void deletarPorId(Long id) {
        var venda = buscarPorId(id);
        if (venda.getId() == null) return;

        repository.deleteById(id);
    }

}
