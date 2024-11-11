package co.edu.uniquindio.carrito.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProducto {

    private String codigo;
    private int cantidad;
    private String concepto;
    private float monto;
    private float impuesto;
    private float subtotal;
    private LocalDate fechaEntrega;
    private LocalDate fechaPrestamo;
}
