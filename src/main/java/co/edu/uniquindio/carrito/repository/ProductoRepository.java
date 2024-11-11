package co.edu.uniquindio.carrito.repository;

import co.edu.uniquindio.carrito.model.document.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {
}
