package co.edu.uniquindio.carrito.controller;

import co.edu.uniquindio.carrito.dto.CategoriaDTO;
import co.edu.uniquindio.carrito.dto.MensajeDTO;
import co.edu.uniquindio.carrito.dto.ProductoDTO;
import co.edu.uniquindio.carrito.exception.CreacionNoRealizadaException;
import co.edu.uniquindio.carrito.service.interfaces.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService productoService;

    @PostMapping("/producto/crear")
    public ResponseEntity<MensajeDTO<String>> crearProducto(@RequestBody ProductoDTO productoDTO) throws CreacionNoRealizadaException {

        String respuesta = productoService.crearProducto(productoDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarProducto(@PathVariable String id) {
        try {
            String respuesta = productoService.eliminarProducto(id);
            return ResponseEntity.ok(new MensajeDTO<>(false, respuesta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<MensajeDTO<String>> editarProducto(@PathVariable String id, @RequestBody ProductoDTO productoDTO) {
        try {
            String respuesta = productoService.editarProducto(id, productoDTO);
            return ResponseEntity.ok(new MensajeDTO<>(false, respuesta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<ProductoDTO>> obtenerProducto(@PathVariable String id) throws Exception {
        ProductoDTO producto = productoService.obtenerProducto(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, producto));
    }

    @GetMapping("/todos")
    public ResponseEntity<MensajeDTO<List<ProductoDTO>>> obtenerTodosProductos() {
        List<ProductoDTO> productos = productoService.productos();
        return ResponseEntity.ok(new MensajeDTO<>(false, productos));
    }

    @GetMapping("/producto/obtener-categorias")
    public ResponseEntity<MensajeDTO<List<CategoriaDTO>>> obtenerCategorias(){
        return ResponseEntity.ok().body(new MensajeDTO<>(false, productoService.obtenerCategorias()));
    }
}
