package co.edu.uniquindio.carrito.dto;

import java.time.LocalDate;

public record CrearDetalleProductoDTO(
        int cantidad,
        String concepto,
        float monto,
        float impuesto,
        float subtotal,
        LocalDate fechaEntrega,
        LocalDate fechaPrestamo) {
}
