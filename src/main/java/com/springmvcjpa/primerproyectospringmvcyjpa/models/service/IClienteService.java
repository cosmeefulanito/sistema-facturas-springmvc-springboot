package com.springmvcjpa.primerproyectospringmvcyjpa.models.service;

import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Cliente;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Factura;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    public void save(Cliente cliente);

    public Cliente findOne(Long id);

    public Cliente fetchByIdWithFactura(Long id);

    public void delete(Long id);

    public List<Producto> findByName(String term);

    public void storeFactura(Factura factura);

    public Producto findProductById(Long id);

    public Factura findFacturaById(Long id);

    public void removeFactura(Long id);

    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id);
}
