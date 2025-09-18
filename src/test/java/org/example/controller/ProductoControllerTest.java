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
        producto.setCode(50);
        Mockito.when(productoService.findById("1")).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.code").value(50));
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
        producto.setCode(99);
        Mockito.when(productoService.save(any(Producto.class))).thenReturn(producto);

        String json = "{\"name\":\"Nuevo\",\"code\":99}";
        mockMvc.perform(post("/producto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nuevo"))
                .andExpect(jsonPath("$.code").value(99));
    }

    @Test
    void testUpdateProducto_found() throws Exception {
        Producto producto = new Producto();
        producto.setId("1");
        producto.setName("Actualizado");
        producto.setCode(123);
        Mockito.when(productoService.update(eq("1"), any(Producto.class))).thenReturn(Optional.of(producto));
        String json = "{\"name\":\"Actualizado\",\"code\":123}";
        mockMvc.perform(put("/producto/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Actualizado"))
                .andExpect(jsonPath("$.code").value(123));
    }

    @Test
    void testUpdateProducto_notFound() throws Exception {
        Mockito.when(productoService.update(eq("2"), any(Producto.class))).thenReturn(Optional.empty());
        String json = "{\"name\":\"NoExiste\",\"code\":1}";
        mockMvc.perform(put("/producto/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProducto_found() throws Exception {
        Mockito.when(productoService.deleteById("1")).thenReturn(true);
        mockMvc.perform(delete("/producto/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProducto_notFound() throws Exception {
        Mockito.when(productoService.deleteById("2")).thenReturn(false);
        mockMvc.perform(delete("/producto/2"))
                .andExpect(status().isNotFound());
    }
}
