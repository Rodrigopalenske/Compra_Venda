package rodrigo.palenske.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rodrigo.palenske.model.Compra;
import rodrigo.palenske.model.ItemCompra;
import rodrigo.palenske.service.CompraService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CompraController {

    @Autowired
    private CompraService service;

    final private String titulo = "Compras";

    @GetMapping("compra")
    public String abrirFormulario(Model model){
        List<ItemCompra> itens = new ArrayList<>();
        model.addAttribute("titulo", "Nova Compra");
        model.addAttribute("itens", itens);
        return "compra/formulario";
    }

    @GetMapping("/compras")
    public String listar(Model model){
        var compras = service.listarTodos();
        model.addAttribute("listaCompra", compras);
        model.addAttribute("titulo", titulo);
        return "compra/lista";
    }

    @PostMapping("salvarCompra")
    public String salvar(Compra compra, Model model){
        service.salvar(compra);
        return "redirect:/alterarCompra/"+compra.getId().toString();
    }

    @GetMapping("/alterarCompra/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        List<ItemCompra> itens = service.buscarPorId(id).getItens();
        model.addAttribute("titulo", "Editar Compra");
        model.addAttribute("compra", service.buscarPorId(id));
        model.addAttribute("itens", itens);
        return "compra/formulario";
    }

    @GetMapping("/deletarCompra/{id}")
    public String deletar(@PathVariable Long id, Model model) {
        try {
            service.deletarPorId(id);
            return "redirect:/compras";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Não é possível excluir uma compra que possui itens");
            model.addAttribute("tipo", "erro");
            return "utility/mensagem";
        }
    }

}
