package co.edu.uniquindio.carrito.service.implementation;

import co.edu.uniquindio.carrito.configuration.JWTUtils;
import co.edu.uniquindio.carrito.dto.LoginDTO;
import co.edu.uniquindio.carrito.dto.TokenDTO;
import co.edu.uniquindio.carrito.exception.SesionNoIniciadaException;
import co.edu.uniquindio.carrito.model.document.Cuenta;
import co.edu.uniquindio.carrito.repository.CuentaRepository;
import co.edu.uniquindio.carrito.service.interfaces.ICuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaServiceImpl implements ICuentaService {

    private final CuentaRepository cuentaRepository;
    private final JWTUtils jwtUtils;

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {

        Cuenta cuenta = obtenerPorEmail(loginDTO.email());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if( !passwordEncoder.matches(loginDTO.password(), cuenta.getPassword()) ) {
            throw new SesionNoIniciadaException("La contraseña es incorrecta!");
        }


        Map<String, Object> map = construirClaims(cuenta);
        return new TokenDTO( jwtUtils.generarToken(cuenta.getEmail(), map) );
    }

    private Cuenta obtenerPorEmail(String email) throws Exception{
        try {
            Optional<Cuenta> cuenta = cuentaRepository.findCuentaByEmail( email );

            if( cuenta.isEmpty() ){
                throw new Exception("Cuenta no encontrada.");
            }

            return cuenta.get();
        }catch(Exception e){
            throw new Exception("Error al buscar el correo." + e.getMessage());
        }
    }

    public Map<String, Object> construirClaims(Cuenta cuenta) {
        return Map.of(
                "rol", cuenta.getTipoCuenta(),
                "nombre", cuenta.getNombre(),
                "apellido", cuenta.getApellido(),
                "id", cuenta.getId()
        );
    }
}
