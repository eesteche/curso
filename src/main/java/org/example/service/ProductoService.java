package org.example.service;

import org.example.entity.Producto;
import org.example.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public Optional<Producto> findById(String id) {
        return productoRepository.findById(id);
    }
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
}

