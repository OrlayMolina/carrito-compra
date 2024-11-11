package co.edu.uniquindio.carrito.dto;

public record DetalleCarroComprasDTO(
        String codigoProducto,
        float monto,
        float impuesto,
        float subtotal,
        int cantidad) {
}
