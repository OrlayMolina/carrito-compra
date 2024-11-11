package co.edu.uniquindio.carrito.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta) {
}
