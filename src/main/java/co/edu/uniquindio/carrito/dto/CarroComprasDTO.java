package co.edu.uniquindio.carrito.dto;

import co.edu.uniquindio.carrito.model.enums.Estado;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CarroComprasDTO(
        String id,
        String idCuenta,
        String codigo,
        LocalDate fecha,
        LocalTime hora,
        Estado estado,
        String observaciones,
        List<DetalleCarroComprasDTO> detalle,
        float total) {
}
