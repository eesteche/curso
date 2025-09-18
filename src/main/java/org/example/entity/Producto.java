package org.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "productos")
public class Producto {
    @Id
    private String id;
    private String name;
    private int code;
}
