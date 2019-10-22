package br.edu.unifacisa.fila.repositories;

import br.edu.unifacisa.fila.entidades.Fila;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilaRepository extends JpaRepository<Fila, Long> {
}
