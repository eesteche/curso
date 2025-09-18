package org.example;

import org.example.entity.Producto;
import org.example.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ProductoSeeder implements ApplicationRunner {
    @Autowired
    private ProductoService productoService;

    @Override
    public void run(ApplicationArguments args) {
        int cores = Runtime.getRuntime().availableProcessors();
        int productosPorThread = 10; // puedes ajustar la cantidad por hilo
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for (int i = 0; i < cores; i++) {
            executor.submit(() -> {
                Random random = new Random();
                for (int j = 0; j < productosPorThread; j++) {
                    Producto producto = new Producto();
                    producto.setName("Producto-" + UUID.randomUUID());
                    producto.setCode(10 + random.nextInt() * 90);
                    productoService.save(producto);
                }
            });
        }
        executor.shutdown();
    }
}
