package co.edu.uniquindio.carrito.controller;

import co.edu.uniquindio.carrito.dto.CarroComprasDTO;
import co.edu.uniquindio.carrito.dto.DetalleCarroComprasDTO;
import co.edu.uniquindio.carrito.dto.MensajeDTO;
import co.edu.uniquindio.carrito.exception.RecursoNoEncontradoException;
import co.edu.uniquindio.carrito.model.vo.DetalleCarroCompras;
import co.edu.uniquindio.carrito.service.interfaces.ICarroComprasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/carrito")
public class CarrosCompraController {

    private final ICarroComprasService carroComprasService;

    @PostMapping("/{idCarrito}/items")
    public ResponseEntity<String> agregarItem(
            @PathVariable String idCarrito,
            @RequestBody DetalleCarroComprasDTO detalleDTO) {
        try {
            carroComprasService.agregarItem(idCarrito, detalleDTO);
            return new ResponseEntity<>("Item agregado al carrito correctamente.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idCarrito}/items/{idDetalleCarrito}")
    public ResponseEntity<MensajeDTO<String>> eliminarItem(
            @PathVariable String idCarrito,
            @PathVariable String idDetalleCarrito) throws Exception {

        String resultado = carroComprasService.eliminarItem(idCarrito, idDetalleCarrito);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, resultado));
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarroComprasDTO> obtenerCarrito(@PathVariable String idCarrito) {
        try {
            CarroComprasDTO carrito = carroComprasService.traerCarrito(idCarrito);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idCarrito}/items")
    public ResponseEntity<String> editarItem(
            @PathVariable String idCarrito,
            @RequestBody DetalleCarroCompras item) {
        try {
            carroComprasService.editarItem(idCarrito, item);
            return new ResponseEntity<>("Item editado correctamente en el carrito.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{idCuenta}")
    public ResponseEntity<MensajeDTO<CarroComprasDTO>> obtenerCarroComprasCiudadano(@PathVariable String idCuenta) {
        try {
            CarroComprasDTO carrito = carroComprasService.traerCarroComprasCiudadano(idCuenta);
            return ResponseEntity.ok().body(new MensajeDTO<>(false, carrito));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idCarrito}/vaciar")
    public ResponseEntity<String> vaciarCarrito(@PathVariable String idCarrito) {
        try {
            carroComprasService.vaciarCarrito(idCarrito);
            return new ResponseEntity<>("Carrito vaciado correctamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tramite-reserva/{email}")
    public ResponseEntity<MensajeDTO<String>> hacerReserva(@PathVariable String email) {
        try {
            String resultado = carroComprasService.hacerReserva(email);
            return ResponseEntity.ok().body(new MensajeDTO<>(false, resultado));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeDTO<>(true, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeDTO<>(true, "Ocurrió un error al crear la reserva."));
        }
    }
}
