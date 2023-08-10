package com.springmvcjpa.primerproyectospringmvcyjpa.models.service;

import com.springmvcjpa.primerproyectospringmvcyjpa.models.dao.IClienteDao;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.dao.IFacturaDao;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.dao.IProductoDao;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Cliente;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Factura;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IFacturaDao facturaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findOne(Long id) {
        return clienteDao.findById(id).orElseGet(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente fetchByIdWithFactura(Long id) {
        return clienteDao.fetchByIdWithFactura(id);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByName(String term) {
        //return productoDao.findByName(term);
        return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
        //ambos opciones son igual de válidas

    }

    @Override
    @Transactional
    public void storeFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void removeFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }


}
