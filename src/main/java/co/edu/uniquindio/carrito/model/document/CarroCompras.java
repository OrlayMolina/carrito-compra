package co.edu.uniquindio.carrito.model.document;

import co.edu.uniquindio.carrito.model.enums.Estado;
import co.edu.uniquindio.carrito.model.vo.DetalleCarroCompras;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document("carritos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarroCompras {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private ObjectId idCuenta;
    private String codigo;
    private LocalDate fecha;
    private LocalTime hora;
    private Estado estado;
    private String observaciones;
    private List<DetalleCarroCompras> detalle;
    private float total;
}
