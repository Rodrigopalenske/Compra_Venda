package rodrigo.palenske.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rodrigo.palenske.model.Compra;
import rodrigo.palenske.model.ItemCompra;
import rodrigo.palenske.model.ItemVenda;
import rodrigo.palenske.model.Venda;
import rodrigo.palenske.service.VendaService;

import java.util.List;

@Controller
public class    VendaController {

    
    
    @Autowired
    private VendaService service;

    final private String titulo = "Vendas";

    @GetMapping("venda")
    public String abrirFormulario(Model model){
        model.addAttribute("titulo", "Nova Venda");
        return "venda/formulario";
    }

    @GetMapping("/vendas")
    public String listar(Model model){
        var vendas = service.listarTodos();
        model.addAttribute("listaVenda", vendas);
        model.addAttribute("titulo", titulo);
        return "venda/lista";
    }

    @PostMapping("/salvarVenda")
    public String salvar(Venda venda, Model model){
        try {
            service.salvar(venda);
            return "redirect:/alterarVenda/"+venda.getId().toString();
        } catch (Exception e) {
            model.addAttribute("mensagem", "Não foi possível salvar essa venda");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }
    }

    @GetMapping("/alterarVenda/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        try {
            List<ItemVenda> itens = service.buscarPorId(id).getItens();
            model.addAttribute("titulo", "Editar Venda");
            model.addAttribute("venda", service.buscarPorId(id));
            model.addAttribute("itens", itens);
            return "venda/formulario";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Venda não encontrada");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }
    }

    @GetMapping("/deletarVenda/{id}")
    public String deletar(@PathVariable Long id, Model model) {
        try {
            service.deletarPorId(id);
            return "redirect:/vendas";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Não foi possível deletar essa venda");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }
    }
}
