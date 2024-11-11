package co.edu.uniquindio.carrito.service.interfaces;

import co.edu.uniquindio.carrito.dto.CategoriaDTO;
import co.edu.uniquindio.carrito.dto.ProductoDTO;
import co.edu.uniquindio.carrito.exception.CreacionNoRealizadaException;

import java.util.List;

public interface IProductoService {
    String crearProducto(ProductoDTO productoDTO) throws CreacionNoRealizadaException;

    String eliminarProducto(String id) throws Exception;

    String editarProducto(String id, ProductoDTO productoDTO) throws Exception;

    ProductoDTO obtenerProducto(String id) throws Exception;

    List<ProductoDTO> productos();

    List<CategoriaDTO> obtenerCategorias();
}
