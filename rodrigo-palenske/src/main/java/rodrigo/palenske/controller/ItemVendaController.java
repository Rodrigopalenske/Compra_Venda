package rodrigo.palenske.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rodrigo.palenske.model.ItemVenda;
import rodrigo.palenske.model.Venda;
import rodrigo.palenske.service.ItemVendaService;
import rodrigo.palenske.service.VendaService;

@Controller
public class ItemVendaController {


    @Autowired
    private ItemVendaService service;

    @Autowired
    private VendaService vendaService;

    final private String titulo = "Itens Vendas";

    @GetMapping("itemVenda/{vendaId}")
    public String abrirFormulario(@PathVariable Long vendaId, Model model){
        try {
            model.addAttribute("titulo", "Novo Item Venda");
            model.addAttribute("vendaId", vendaId);
            return "itemVenda/formulario";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Venda não encontrada");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }
    }

    @GetMapping("/itensVendas")
    public String listar(Model model) {
        var itemVendas = service.listarTodos();
        model.addAttribute("listaItemVenda", itemVendas);
        model.addAttribute("titulo", titulo);
        return "itemVenda/lista";
    }

    @PostMapping("/salvarItemVenda")
    public String salvar(@RequestParam Long vendaId, ItemVenda itemVenda, Model model){
        try {
            Venda venda = vendaService.buscarPorId(vendaId);
            itemVenda.setVenda(venda);
            service.salvar(itemVenda);
            return "redirect:/alterarVenda/" + vendaId.toString();
        } catch (Exception e) {
            model.addAttribute("mensagem", "Não foi possível salvar esse item");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }
    }

    @GetMapping("/alterarItemVenda/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        try {
            var itemVenda = service.buscarPorId(id);
            model.addAttribute("titulo", "Editar Item Venda");
            model.addAttribute("itemVenda", itemVenda);
            model.addAttribute("vendaId", itemVenda.getVenda().getId());
            return "itemVenda/formulario";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Item não encontrado");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }
    }
    @GetMapping("/deletarItemVenda/{id}")
    public String deletar(@PathVariable Long id, Model model) {
        try {
            var vendaId = service.buscarPorId(id).getVenda().getId();
            service.deletarPorId(id);
            return "redirect:/alterarVenda/" + vendaId.toString();
        } catch (Exception e) {
            model.addAttribute("mensagem", "Não foi possível deletar esse item");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }

    }
}
