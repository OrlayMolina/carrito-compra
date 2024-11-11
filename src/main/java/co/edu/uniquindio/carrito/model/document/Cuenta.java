package co.edu.uniquindio.carrito.model.document;

import co.edu.uniquindio.carrito.model.enums.Estado;
import co.edu.uniquindio.carrito.model.enums.Genero;
import co.edu.uniquindio.carrito.model.enums.TipoCuenta;
import co.edu.uniquindio.carrito.model.vo.CatalagoPreferencias;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("cuentas")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuenta {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private TipoCuenta tipoCuenta;
    private Estado estado;
    private String telefono;
    private List<CatalagoPreferencias> preferencias;
    private String email;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private String password;
}
