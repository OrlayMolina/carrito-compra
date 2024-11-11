package co.edu.uniquindio.carrito.dto;

import co.edu.uniquindio.carrito.model.enums.Categoria;
import co.edu.uniquindio.carrito.model.enums.Estado;

import java.util.List;

public record CrearProductoDTO(
        String codigo,
        String nombre,
        Categoria categoriaPrincipal,
        Categoria categoriaSecundaria,
        String foto,
        String descripcion,
        float precio,
        int stock,
        List<DetalleProductoDTO> detalle,
        List<CaracteristicaDTO>caracteristica,
        boolean garantia,
        Estado estado) {
}
