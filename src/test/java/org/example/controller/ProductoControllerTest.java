package org.example.controller;

import org.example.entity.Producto;
import org.example.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    void testGetProducto_found() throws Exception {
        Producto producto = new Producto();
        producto.setId("1");
        producto.setName("Test");
        Mockito.when(productoService.findById("1")).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void testGetProducto_notFound() throws Exception {
        Mockito.when(productoService.findById("2")).thenReturn(Optional.empty());
        mockMvc.perform(get("/producto/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId("3");
        producto.setName("Nuevo");
        Mockito.when(productoService.save(any(Producto.class))).thenReturn(producto);

        String json = "{\"name\":\"Nuevo\",\"code\":50}";
        mockMvc.perform(post("/producto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nuevo"));
    }
}

