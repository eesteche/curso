package org.example.service;

import org.example.entity.Producto;
import org.example.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_found() {
        Producto producto = new Producto();
        producto.setId("1");
        when(productoRepository.findById("1")).thenReturn(Optional.of(producto));
        Optional<Producto> result = productoService.findById("1");
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
    }

    @Test
    void testFindById_notFound() {
        when(productoRepository.findById("2")).thenReturn(Optional.empty());
        Optional<Producto> result = productoService.findById("2");
        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        Producto producto = new Producto();
        producto.setName("Test");
        producto.setCode(123);
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto result = productoService.save(producto);
        assertEquals("Test", result.getName());
        assertEquals(123, result.getCode());
    }
}


