package com.springmvcjpa.primerproyectospringmvcyjpa.controllers;

import com.springmvcjpa.primerproyectospringmvcyjpa.controllers.util.paginator.PageRender;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Cliente;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.service.IClienteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    @Autowired
    private MessageSource messageSource;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOAD_FOLDER = "uploads";

    @Secured("ROLE_USER")
    @GetMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){
        Path pathFoto = Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
        log.info("filename: " + filename);
        log.info("Pathfoto: " + pathFoto);
        Resource recurso = null;

        //Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
        //File archivo = rootPath.toFile();

        try{
            recurso = new UrlResource(pathFoto.toUri());
            if(!recurso.exists() || !recurso.isReadable()){
                throw new RuntimeException("Error, no se puede cargar la imagen " + pathFoto.toString());
            }
        } catch(MalformedURLException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() +"\"")
                .body(recurso);

    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Cliente cliente = clienteService.fetchByIdWithFactura(id); //clienteService.findOne(id);

        if (cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe en la BBDD");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("facturas", cliente.getFacturas());
        model.put("titulo","Cliente: "+cliente.getNombre() + ' ' + cliente.getApellido());
        return "ver";
    }

    @RequestMapping(value= {"/listar", "/"}, method = RequestMethod.GET)
    public String listar(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                         Authentication authentication, HttpServletRequest request, Locale locale) {

        if (authentication != null){
            log.info("Hola usuario autenticado, tu username es: " + authentication.getName());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            log.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(),  El usuario "+auth.getName() + " ha iniciado sesión con éxito");
        }

        //método 1 , método propio para buscar rol, se puede usar el logger y acceder a cada rol
        /*if(hasRole("ROLE_ADMIN")){
            log.info(("Hola "+ auth.getName()+ " tienes acceso" ) );
        }else{
            log.info(("Hola "+ auth.getName()+ " NO tienes acceso" ) );
        }*/

        //método 2,
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");

        if(securityContext.isUserInRole("ADMIN")){
            log.info(("Usando método SecurityContextHolderAwareRequestWrapper, Hola "+ auth.getName()+ " tienes acceso" ) );
        }else{
            log.info(("Usando método SecurityContextHolderAwareRequestWrapper, Hola "+ auth.getName()+ " NO tienes acceso" ) );
        }

        //método 3, directo con request
        /*
        if(request.isUserInRole("ROLE_ADMIN")){
            log.info(("Usando método HttpServletRequest, Hola "+ auth.getName()+ " tienes acceso" ) );
        }else{
            log.info(("Usando método HttpServletRequest, Hola "+ auth.getName()+ " NO tienes acceso" ) );
        }*/


        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/form", method = RequestMethod.GET)
    public String form(Map<String, Object> model){
        Cliente cliente = new Cliente();
        model.put("titulo", "Formulario de Cliente");
        model.put("cliente", cliente);
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/form", method = RequestMethod.POST)
    public String Storeform(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
        //los argumentos @Valid Cliente cliente, BindingResult result siempre tienen que ir juntos, primero objeto mapeado y despues la validacion
        if (result.hasErrors()){
            model.addAttribute("titulo","Formulario de cliente");
            return "form";
        }
        if (!foto.isEmpty()){
            if(cliente.getId() != null &&
                    cliente.getId()>0 &&
                    cliente.getFoto() != null &&
                    cliente.getFoto().length()>0){
                Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rootPath.toFile();

                if(archivo.exists() && archivo.canRead()){
                    archivo.delete();
                }
            }
            String uniqueFileName = UUID.randomUUID().toString() + "_" +foto.getOriginalFilename();
            Path roothPath = Paths.get(UPLOAD_FOLDER).resolve(uniqueFileName);
            Path absolutePath = roothPath.toAbsolutePath();
            log.info("rootpath:" + roothPath);
            log.info("absolutePath:" + absolutePath);

            try {
                Files.copy(foto.getInputStream(),absolutePath);
                flash.addFlashAttribute("info", "Has subido correctamente la imagen "+uniqueFileName);
                cliente.setFoto(uniqueFileName);
            }catch (IOException e){
                e.printStackTrace();
            }


        }
        clienteService.save(cliente);
        String msjFlash = cliente.getId() != null ? "Cliente editado con éxito" : "Cliente creado con éxito";
        flash.addFlashAttribute("success", msjFlash);
        status.setComplete();
        return "redirect:listar";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/form/{id}")
    public String editarForm(@PathVariable(value = "id") Long id, Map<String, Object> model,RedirectAttributes flash){
        Cliente cliente = null;
        if (id>0){
            cliente = clienteService.findOne(id);
            if (cliente == null){
                flash.addFlashAttribute("error", "Cliente no existe en la BD");
                return "redirect:/listar";
            }
        }else{
            flash.addFlashAttribute("error", "El id del cliente no puede ser zero");
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", "Editar cliente");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash){
        if (id>0){
            Cliente cliente = clienteService.findOne(id);
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito");
            Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
            File archivo = rootPath.toFile();

            if(archivo.exists() && archivo.canRead()){
                if(archivo.delete()){
                    flash.addFlashAttribute("info", "foto "+ cliente.getFoto()+ " eliminada con éxito!");
                }
            }
        }

        return "redirect:/listar";
    }

    public boolean hasRole(String role){
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null){
            return false;
        }

        Authentication auth = context.getAuthentication();
        if(auth == null){
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(role));
        /*
        for(GrantedAuthority authority: authorities){
            if(role.equals(authority.getAuthority())){
                log.info("Hola usuario : " + auth.getName() + ", tu rol es: "+ authority.getAuthority());
                return true;
            }
        }
        return false;*/
    }



}
