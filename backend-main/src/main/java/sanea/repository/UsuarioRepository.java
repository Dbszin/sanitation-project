package sanea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sanea.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Métodos customizados podem ser adicionados aqui
} 