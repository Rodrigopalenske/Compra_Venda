package rodrigo.palenske.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String iniciar(Model model){
        model.addAttribute("titulo", "Compra e Venda");
        return "index";
    }
}
