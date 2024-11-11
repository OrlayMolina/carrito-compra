package co.edu.uniquindio.carrito.service.interfaces;

import co.edu.uniquindio.carrito.dto.CarroComprasDTO;
import co.edu.uniquindio.carrito.dto.DetalleCarroComprasDTO;
import co.edu.uniquindio.carrito.model.vo.DetalleCarroCompras;

public interface ICarroComprasService {
    String eliminarItem(String idCarrito, String idDetalleCarrito) throws Exception;

    void agregarItem(String idCarrito, DetalleCarroComprasDTO detalleDTO) throws Exception;

    CarroComprasDTO traerCarrito(String idCarrito) throws Exception;

    void editarItem(String idCarrito, DetalleCarroCompras item) throws Exception;

    CarroComprasDTO traerCarroComprasCiudadano(String idCuenta) throws  Exception;

    void vaciarCarrito(String idCarrito) throws Exception;

    String hacerReserva(String email) throws Exception;
}
