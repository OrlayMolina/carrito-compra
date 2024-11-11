package co.edu.uniquindio.carrito.model.document;

import co.edu.uniquindio.carrito.model.enums.Categoria;
import co.edu.uniquindio.carrito.model.enums.Estado;
import co.edu.uniquindio.carrito.model.vo.Caracteristica;
import co.edu.uniquindio.carrito.model.vo.DetalleProducto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("productos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String codigo;
    private String nombre;
    private Categoria categoriaPrincipal;
    private Categoria categoriaSecundaria;
    private String foto;
    private String descripcion;
    private float precio;
    private int stock;
    private List<DetalleProducto> detalle;
    private List<Caracteristica> caracteristica;
    private boolean garantia;
    private Estado estado;
}
