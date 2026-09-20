package com.example.pedidos.service;

import com.example.pedidos.domain.Pedido;
import com.example.pedidos.dto.UsuarioResponse;
import com.example.pedidos.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service 
@Transactional
public class PedidoService {

    private final PedidoRepository repository;
    private final UsuarioClient usuarioClient;

    public PedidoService(PedidoRepository repository, UsuarioClient usuarioClient) {
        this.repository = repository;
        this.usuarioClient = usuarioClient;
    }

    @Transactional(readOnly = true)
    public List<Pedido> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado: " + id));
    }

    public Pedido crear(Long usuarioId, String producto, Integer cantidad) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El usuarioId es obligatorio");
        }
        if (producto == null || producto.isBlank()) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        // Comunicación entre microservicios:
        // Pedido -> GET /usuarios/{id} en servicio-usuarios.
        try {
            UsuarioResponse usuario = usuarioClient.obtenerUsuario(usuarioId);
            if (usuario == null) {
                throw new IllegalArgumentException("No se pudo validar el usuario");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("El usuario no existe: " + usuarioId);
        } catch (RuntimeException e) {
            if (e instanceof NotFoundException) {
                throw e;
            }
            throw new IllegalStateException(
                    "No fue posible comunicarse con servicio-usuarios en http://localhost:8081", e);
        }

        Pedido pedido = new Pedido(usuarioId, producto.trim(), cantidad, "CREADO");
        return repository.save(pedido);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Pedido no encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
 