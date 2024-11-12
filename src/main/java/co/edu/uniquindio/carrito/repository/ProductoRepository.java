package co.edu.uniquindio.carrito.repository;

import co.edu.uniquindio.carrito.model.document.Producto;
import co.edu.uniquindio.carrito.model.enums.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {

    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    List<Producto> buscarProductoPorNombre(String nombre);

    @Query("{ 'categoriaPrincipal': ?0 }")
    List<Producto> buscarProductoPorCategoria(Categoria categoriaPrincipal);
    List<Producto> findProductoByNombreIsLikeIgnoreCaseAndCategoriaPrincipalIsLikeIgnoreCase(String nombre, Categoria categoriaPrincipal);
}
