package co.edu.uniquindio.carrito.repository;

import co.edu.uniquindio.carrito.model.document.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, String> {
    Optional<Cuenta> findCuentaByEmail(String email);
}
