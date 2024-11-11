package co.edu.uniquindio.carrito.service.interfaces;

import co.edu.uniquindio.carrito.dto.LoginDTO;
import co.edu.uniquindio.carrito.dto.TokenDTO;

public interface ICuentaService {
    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;
}
