package com.springmvcjpa.primerproyectospringmvcyjpa.controllers;

import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Cliente;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Factura;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.ItemFactura;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Producto;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.service.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @GetMapping("/form/{clienteid}")
    public String crearFactura(
            @PathVariable Long clienteid,
            Map<String, Object> model,
            RedirectAttributes flash){

        Cliente cliente = clienteService.findOne(clienteid);

        if (cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe en la BBDD");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.put("factura", factura);
        model.put("titulo", "Crear nueva factura");

        return "factura/form";

    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        return clienteService.findByName(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura, BindingResult result, Model model,
                          @RequestParam(name = "item_id[]",required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]",required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus sesion){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Crear factura");
            return "factura/form";
        }

        if (itemId == null || itemId.length==0){
            model.addAttribute("titulo", "Crear factura");
            model.addAttribute("error", "La factura debe tener como mínimo 1 item");
            return "factura/form";
        }



        for (int i = 0; i < itemId.length; i++) {
            Producto producto = clienteService.findProductById(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i].intValue());
            linea.setProducto(producto);
            factura.addItemFactura(linea);

            log.info("ID:" + itemId[i].toString()+ ", cantidad: "+ cantidad[i].toString());
        }
        clienteService.storeFactura(factura);
        sesion.setComplete();
        flash.addFlashAttribute("success", "Factura generada con éxito");
        return "redirect:/ver/"+factura.getCliente().getId();
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id,Model model, RedirectAttributes flash){
        Factura factura = clienteService.fetchFacturaByIdWithClienteWithItemFacturaWithProducto(id);//clienteService.findFacturaById(id);
        if (factura == null){
            flash.addFlashAttribute("error", "La factura no existe en la BBDD");
            return "redirect:/listar";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Factura: " + factura.getDescripcion());
        return "factura/ver";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash){
        Factura factura = clienteService.findFacturaById(id);
        if(factura != null){
            clienteService.removeFactura(id);
            flash.addFlashAttribute("success", "Factura eliminada correctamente!");
            return "redirect:/ver/" + factura.getId();
        }
        flash.addFlashAttribute("error","Factura no existe en la bd");
        return "redirect:/listar";
    }
}
