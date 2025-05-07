package rodrigo.palenske.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rodrigo.palenske.model.Compra;
import rodrigo.palenske.model.ItemCompra;
import rodrigo.palenske.model.ItemVenda;
import rodrigo.palenske.model.Venda;
import rodrigo.palenske.service.CompraService;
import rodrigo.palenske.service.ItemCompraService;

@Controller
public class ItemCompraController {

    @Autowired
    private ItemCompraService service;

    @Autowired
    private CompraService compraService;

    final private String titulo = "Itens Compras";

    @GetMapping("/itemCompra/{compraId}")
    public String abrirFormulario(@PathVariable Long compraId, Model model){
        model.addAttribute("titulo", "Novo Item Compra");
        model.addAttribute("compraId", compraId);
        return "itemCompra/formulario";
    }

    @GetMapping("/itensCompras")
    public String listar(Model model) {
        var itemCompras = service.listarTodos();
        model.addAttribute("listaItemCompra", itemCompras);
        model.addAttribute("titulo", titulo);
        return "itemCompra/lista";
    }

    @PostMapping("/salvarItemCompra")
    public String salvar(@RequestParam Long compraId, ItemCompra itemCompra, Model model) {

        Compra compra = compraService.buscarPorId(compraId);
        itemCompra.setCompra(compra);
        service.salvar(itemCompra);
        return "redirect:/alterarCompra/" + compraId.toString();
    }

    @GetMapping("/alterarItemCompra/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        var itemCompra = service.buscarPorId(id);
        model.addAttribute("titulo", "Editar Item Compra");
        model.addAttribute("itemCompra", itemCompra);
        model.addAttribute("compraId", itemCompra.getCompra().getId());
        return "itemCompra/formulario";
    }

    @GetMapping("/deletarItemCompra/{id}")
    public String deletar(@PathVariable Long id, Model model) {
        var compraId = service.buscarPorId(id).getCompra().getId();
        service.deletarPorId(id);
        return "redirect:/alterarCompra/" + compraId.toString();

    }
}
