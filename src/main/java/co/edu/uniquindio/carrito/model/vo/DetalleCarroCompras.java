package co.edu.uniquindio.carrito.model.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DetalleCarroCompras {

    private String codigoProducto;
    private float monto;
    private float impuesto;
    private float subtotal;
    private int cantidad;
}
