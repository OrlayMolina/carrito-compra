package co.edu.uniquindio.carrito.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ImagenesService {

    String subirImagen(MultipartFile imagen) throws Exception;
    void eliminarImagen(String nombreImagen) throws Exception;
}
