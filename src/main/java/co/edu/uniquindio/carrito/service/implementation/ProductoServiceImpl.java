package co.edu.uniquindio.carrito.service.implementation;

import co.edu.uniquindio.carrito.dto.*;
import co.edu.uniquindio.carrito.exception.CreacionNoRealizadaException;
import co.edu.uniquindio.carrito.exception.RecursoNoEncontradoException;
import co.edu.uniquindio.carrito.model.document.Producto;
import co.edu.uniquindio.carrito.model.enums.Categoria;
import co.edu.uniquindio.carrito.model.vo.Caracteristica;
import co.edu.uniquindio.carrito.model.vo.DetalleProducto;
import co.edu.uniquindio.carrito.repository.ProductoRepository;
import co.edu.uniquindio.carrito.service.interfaces.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public String crearProducto(ProductoDTO productoDTO) throws CreacionNoRealizadaException {

        Producto producto = new Producto();
        try {
            List<DetalleProducto> detalles = new ArrayList<>();
            for(DetalleProductoDTO detalle : productoDTO.detalle()){
                DetalleProducto detalleProducto = getDetalleProducto(detalle);

                detalles.add( detalleProducto );
            }

            List<Caracteristica> caracteristicas = obtenerListaCarasteristicas(productoDTO);

            producto.setCodigo( UUID.randomUUID().toString() );
            producto.setNombre( productoDTO.nombre() );
            producto.setCategoriaPrincipal( productoDTO.categoriaPrincipal() );
            producto.setCategoriaSecundaria( productoDTO.categoriaSecundaria() );
            producto.setDescripcion( productoDTO.descripcion() );
            producto.setPrecio( productoDTO.precio() );
            producto.setStock( productoDTO.stock() );
            producto.setDetalle( detalles );
            producto.setCaracteristica( caracteristicas );
            producto.setGarantia( productoDTO.garantia() );
            producto.setEstado( productoDTO.estado() );

            productoRepository.save( producto );

            return "Producto creado correctamente!.";
        } catch (Exception e){
            throw new CreacionNoRealizadaException("No fue posible crear el producto!.");
        }
    }

    @Override
    public String eliminarProducto(String id) throws Exception {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado correctamente.";
        } else {
            throw new Exception("Producto no encontrado.");
        }
    }

    @Override
    public String editarProducto(String id, ProductoDTO productoDTO) throws Exception {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado."));

        productoExistente.setNombre(productoDTO.nombre());
        productoExistente.setCategoriaPrincipal(productoDTO.categoriaPrincipal());
        productoExistente.setCategoriaSecundaria(productoDTO.categoriaSecundaria());
        productoExistente.setDescripcion(productoDTO.descripcion());
        productoExistente.setPrecio(productoDTO.precio());
        productoExistente.setStock(productoDTO.stock());
        productoExistente.setGarantia(productoDTO.garantia());
        productoExistente.setEstado(productoDTO.estado());

        List<DetalleProducto> detalles = new ArrayList<>();
        for (DetalleProductoDTO detalle : productoDTO.detalle()) {
            detalles.add(getDetalleProducto(detalle));
        }
        productoExistente.setDetalle(detalles);

        obtenerListaCarasteristicas(productoDTO);
        List<Caracteristica> caracteristicas = new ArrayList<>();
        productoExistente.setCaracteristica(caracteristicas);

        productoRepository.save(productoExistente);

        return "Producto editado correctamente.";
    }

    @Override
    public ProductoDTO obtenerProducto(String id) throws Exception {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado."));

        List<DetalleProductoDTO> detalleDTOs = producto.getDetalle().stream()
                .map(detalle -> new DetalleProductoDTO(
                        detalle.getCodigo(),
                        detalle.getCantidad(),
                        detalle.getConcepto(),
                        detalle.getMonto(),
                        detalle.getImpuesto(),
                        detalle.getSubtotal(),
                        detalle.getFechaEntrega(),
                        detalle.getFechaPrestamo()))
                .toList();

        List<CaracteristicaDTO> caracteristicaDTOs = producto.getCaracteristica().stream()
                .map(caracteristica -> new CaracteristicaDTO(
                        caracteristica.getCaracteristica(),
                        caracteristica.getDescripcion()))
                .toList();

        return new ProductoDTO(
                producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCategoriaPrincipal(),
                producto.getCategoriaSecundaria(),
                producto.getFoto(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                detalleDTOs,
                caracteristicaDTOs,
                producto.isGarantia(),
                producto.getEstado()
        );
    }

    @Override
    public List<ProductoDTO> productos() {
        List<Producto> productos = productoRepository.findAll();

        if (productos.isEmpty()) {
            throw new ResourceAccessException("No se encontraron productos!.");
        }

        List<ProductoDTO> productoDTOS = new ArrayList<>();
        for (Producto producto : productos) {

            List<DetalleProductoDTO> detalleProductoDTOS = producto.getDetalle().stream()
                    .map(detalle -> new DetalleProductoDTO(
                            detalle.getCodigo(),
                            detalle.getCantidad(),
                            detalle.getConcepto(),
                            detalle.getMonto(),
                            detalle.getImpuesto(),
                            detalle.getSubtotal(),
                            detalle.getFechaEntrega(),
                            detalle.getFechaPrestamo()))
                    .toList();

            List<CaracteristicaDTO> caracteristicaDTOS = producto.getCaracteristica().stream()
                    .map(caracteristica -> new CaracteristicaDTO(
                            caracteristica.getCaracteristica(),
                            caracteristica.getDescripcion()))
                    .toList();

            ProductoDTO productoDTO = new ProductoDTO(
                    producto.getId(),
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCategoriaPrincipal(),
                    producto.getCategoriaSecundaria(),
                    producto.getFoto(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getStock(),
                    detalleProductoDTOS,
                    caracteristicaDTOS,
                    producto.isGarantia(),
                    producto.getEstado()
            );

            productoDTOS.add(productoDTO);
        }

        return productoDTOS;
    }

    @Override
    public List<CategoriaDTO> obtenerCategorias(){

        return Arrays.stream(Categoria.values())
                .map(ciudad -> new CategoriaDTO(ciudad.name()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> filtrarEventos(FiltroProductoDTO filtroProductoDTO) throws RecursoNoEncontradoException {

        String nombre = (filtroProductoDTO.nombre() != null && !filtroProductoDTO.nombre().isEmpty()) ? filtroProductoDTO.nombre() : null;
        Categoria categoria = filtroProductoDTO.categoria() != null ? Categoria.valueOf(filtroProductoDTO.categoria()) : null;

        List<Producto> productos;

        if (nombre != null && categoria != null) {
            productos = productoRepository.findProductoByNombreIsLikeIgnoreCaseAndCategoriaPrincipalIsLikeIgnoreCase(nombre, categoria);
        } else if (nombre != null) {
            productos = productoRepository.buscarProductoPorNombre(nombre);
        } else if (categoria != null) {
            productos = productoRepository.buscarProductoPorCategoria(categoria);
        } else {
            throw new RecursoNoEncontradoException("No se proporcionó ningún criterio de búsqueda.");
        }

        List<DetalleProductoDTO> detalles = new ArrayList<>();
        for( Producto producto : productos){

            for( DetalleProducto detalleProducto : producto.getDetalle()){
                DetalleProductoDTO detalleDTO = new DetalleProductoDTO(
                        detalleProducto.getCodigo(),
                        detalleProducto.getCantidad(),
                        detalleProducto.getConcepto(),
                        detalleProducto.getMonto(),
                        detalleProducto.getImpuesto(),
                        detalleProducto.getSubtotal(),
                        detalleProducto.getFechaEntrega(),
                        detalleProducto.getFechaPrestamo()
                );

                detalles.add(detalleDTO);
            }
        }

        List<CaracteristicaDTO> caracteristicas = new ArrayList<>();
        for( Producto producto : productos){
            for(Caracteristica caracteristica: producto.getCaracteristica()){
                CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO(
                        caracteristica.getCaracteristica(),
                        caracteristica.getDescripcion()
                );

                caracteristicas.add(caracteristicaDTO);
            }
        }

        return productos.stream()
                .map(producto -> new ProductoDTO(
                        producto.getId(),
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getCategoriaPrincipal(),
                        producto.getCategoriaSecundaria(),
                        producto.getFoto(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock(),
                        detalles,
                        caracteristicas,
                        producto.isGarantia(),
                        producto.getEstado()))
                .collect(Collectors.toList());
    }

    private List<Caracteristica> obtenerListaCarasteristicas(ProductoDTO productoDTO) {
        List<Caracteristica> caracteristicas = new ArrayList<>();
        for (CaracteristicaDTO caracteristicaDTO : productoDTO.caracteristica()) {
            Caracteristica caracteristica = new Caracteristica();
            caracteristica.setCaracteristica(caracteristicaDTO.caracteristica());
            caracteristica.setDescripcion(caracteristicaDTO.descripcion());
            caracteristicas.add(caracteristica);
        }

        return caracteristicas;
    }


    private static DetalleProducto getDetalleProducto(DetalleProductoDTO detalle) {
        DetalleProducto detalleProducto = new DetalleProducto();
        detalleProducto.setCantidad( detalle.cantidad() );
        detalleProducto.setConcepto( detalle.concepto() );
        detalleProducto.setMonto( detalle.monto() );
        detalleProducto.setImpuesto( detalle.impuesto() );
        detalleProducto.setSubtotal( detalle.subtotal() );
        detalleProducto.setFechaEntrega( detalle.fechaEntrega() );
        detalleProducto.setFechaPrestamo( detalle.fechaPrestamo() );
        return detalleProducto;
    }
}
