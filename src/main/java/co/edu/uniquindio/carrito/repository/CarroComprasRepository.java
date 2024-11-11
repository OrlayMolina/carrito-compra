package co.edu.uniquindio.carrito.repository;

import co.edu.uniquindio.carrito.model.document.CarroCompras;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarroComprasRepository extends MongoRepository<CarroCompras, String> {

    Optional<CarroCompras> findCarroComprasByIdCuenta(ObjectId idCuenta);
}
