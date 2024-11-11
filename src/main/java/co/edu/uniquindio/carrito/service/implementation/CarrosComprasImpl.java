package co.edu.uniquindio.carrito.service.implementation;

import co.edu.uniquindio.carrito.configuration.PlantillasEmailConfig;
import co.edu.uniquindio.carrito.dto.CarroComprasDTO;
import co.edu.uniquindio.carrito.dto.DetalleCarroComprasDTO;
import co.edu.uniquindio.carrito.dto.EmailDTO;
import co.edu.uniquindio.carrito.exception.RecursoNoEncontradoException;
import co.edu.uniquindio.carrito.model.document.CarroCompras;
import co.edu.uniquindio.carrito.model.document.Cuenta;
import co.edu.uniquindio.carrito.model.vo.DetalleCarroCompras;
import co.edu.uniquindio.carrito.repository.CarroComprasRepository;
import co.edu.uniquindio.carrito.repository.CuentaRepository;
import co.edu.uniquindio.carrito.service.interfaces.EmailService;
import co.edu.uniquindio.carrito.service.interfaces.ICarroComprasService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarrosComprasImpl implements ICarroComprasService {

    private final CarroComprasRepository carroComprasRepository;
    private final CuentaRepository cuentaRepository;
    private final EmailService emailService;

    @Override
    public String eliminarItem(String idCarrito, String idDetalleCarrito) {

        Optional<CarroCompras> carrito = carroComprasRepository.findById(idCarrito);
        if(carrito.isPresent()){
            Optional<DetalleCarroCompras> detalleCarrito = carrito.get().getDetalle().stream().filter(x -> x.getCodigoProducto().equals(idDetalleCarrito) ).findFirst();
            if(detalleCarrito.isPresent()){
                carrito.get().getDetalle().remove(detalleCarrito.get());
                carroComprasRepository.save(carrito.get());

                return "Detalle eliminado correctamente.";
            }
        }
        return "Item no ha sido eliminado correctamente";
    }

    @Override
    public void agregarItem(String idCarrito, DetalleCarroComprasDTO detalleDTO) throws Exception {
        Optional<CarroCompras> carrito = carroComprasRepository.findById(idCarrito);

        if (carrito.isPresent()) {
            CarroCompras carritoActual = carrito.get();

            Optional<DetalleCarroCompras> itemExistente = carritoActual.getDetalle().stream()
                    .filter(i -> i.getCodigoProducto().equals(detalleDTO.codigoProducto()))
                    .findFirst();

            if (itemExistente.isPresent()) {

                DetalleCarroCompras detalleExistente = itemExistente.get();
                detalleExistente.setCantidad(detalleExistente.getCantidad() + detalleDTO.cantidad());
            } else {

                DetalleCarroCompras nuevoDetalle = new DetalleCarroCompras();
                nuevoDetalle.setCodigoProducto(detalleDTO.codigoProducto());
                nuevoDetalle.setMonto(detalleDTO.monto());
                nuevoDetalle.setImpuesto(detalleDTO.impuesto());
                nuevoDetalle.setSubtotal(detalleDTO.subtotal());
                nuevoDetalle.setCantidad(detalleDTO.cantidad());

                carritoActual.getDetalle().add(nuevoDetalle);
            }

            carroComprasRepository.save(carritoActual);
        } else {
            throw new Exception("Carrito no encontrado");
        }
    }

    @Override
    public CarroComprasDTO traerCarrito(String idCarrito) throws Exception {
        Optional<CarroCompras> carrito = carroComprasRepository.findById(idCarrito);

        if (carrito.isPresent()) {

            List<DetalleCarroComprasDTO> detalleDTOs = carrito.get().getDetalle().stream()
                    .map(detalle -> new DetalleCarroComprasDTO(
                            detalle.getCodigoProducto(),
                            detalle.getMonto(),
                            detalle.getImpuesto(),
                            detalle.getSubtotal(),
                            detalle.getCantidad()
                    ))
                    .collect(Collectors.toList());

            return new CarroComprasDTO(
                    carrito.get().getId(),
                    carrito.get().getIdCuenta().toString(),
                    carrito.get().getCodigo(),
                    carrito.get().getFecha(),
                    carrito.get().getHora(),
                    carrito.get().getEstado(),
                    carrito.get().getObservaciones(),
                    detalleDTOs,
                    carrito.get().getTotal()
            );
        } else {
            throw new Exception("No se ha encontrado un carrito");
        }
    }

    @Override
    public void editarItem(String idCarrito, DetalleCarroCompras item) throws Exception {
        Optional<CarroCompras> carrito = carroComprasRepository.findById(idCarrito);

        if (carrito.isPresent()) {
            CarroCompras carritoActual = carrito.get();

            Optional<DetalleCarroCompras> itemExistente = carritoActual.getDetalle().stream()
                    .filter(i -> i.getCodigoProducto().equals(item.getCodigoProducto()))
                    .findFirst();

            if (itemExistente.isPresent()) {

                DetalleCarroCompras detalleExistente = itemExistente.get();
                detalleExistente.setCantidad(item.getCantidad());
                detalleExistente.setMonto(item.getMonto());
                detalleExistente.setImpuesto(item.getImpuesto());
                detalleExistente.setSubtotal(item.getSubtotal());

                carroComprasRepository.save(carritoActual);
            } else {
                throw new Exception("Item no encontrado en el carrito");
            }
        } else {
            throw new Exception("Carrito no encontrado");
        }
    }

    @Override
    public CarroComprasDTO traerCarroComprasCiudadano(String idCuenta) throws  Exception{
        Optional<CarroCompras> carrito = carroComprasRepository.findCarroComprasByIdCuenta(new ObjectId(idCuenta));

        if(carrito.isPresent()){

            List<DetalleCarroComprasDTO> detalleDTOs = carrito.get().getDetalle().stream()
                    .map(detalle -> new DetalleCarroComprasDTO(
                            detalle.getCodigoProducto(),
                            detalle.getMonto(),
                            detalle.getImpuesto(),
                            detalle.getSubtotal(),
                            detalle.getCantidad()
                    ))
                    .toList();

            return new CarroComprasDTO(
                    carrito.get().getId(),
                    carrito.get().getIdCuenta().toString(),
                    carrito.get().getCodigo(),
                    carrito.get().getFecha(),
                    carrito.get().getHora(),
                    carrito.get().getEstado(),
                    carrito.get().getObservaciones(),
                    detalleDTOs,
                    carrito.get().getTotal()
            );
        }else {
            throw  new Exception("No se ha encontrado un carrito!.");
        }

    }

    @Override
    public void vaciarCarrito(String idCarrito) throws Exception {
        Optional<CarroCompras> carrito = carroComprasRepository.findById(idCarrito);

        if (carrito.isPresent()) {

            CarroCompras carritoActual = carrito.get();
            carritoActual.getDetalle().clear();

            carroComprasRepository.save(carritoActual);
        } else {
            throw new Exception("Carrito no encontrado.");
        }
    }

    @Override
    public String hacerReserva(String email) throws Exception {

        Optional<Cuenta>  cuentaOptional = cuentaRepository.findCuentaByEmail(email);

        if(cuentaOptional.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró una cuenta con ese email ");
        }

        Cuenta cuenta = cuentaOptional.get();

        String body = PlantillasEmailConfig.bodyReserva.replace("[Nombres]",
                cuenta.getNombre()).replace("[Apellidos]",cuenta.getApellido());

        emailService.enviarCorreo( new EmailDTO("Creacion de Reserva", body, email) );

        return "Reserva creada exitosamente!.";
    }
}
