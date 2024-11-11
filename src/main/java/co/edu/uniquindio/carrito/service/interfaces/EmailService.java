package co.edu.uniquindio.carrito.service.interfaces;

import co.edu.uniquindio.carrito.dto.EmailDTO;

public interface EmailService {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}
