package br.edu.unifacisa.fila.repositories;

import br.edu.unifacisa.fila.entidades.Ficha;
import br.edu.unifacisa.fila.entidades.Fila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FichaRepository extends JpaRepository<Ficha, Long> {
  @Query("select count(f) from Ficha f where f.fila = :#{#fila} and f.createdAt >= :#{#createdAt}")
  long countFichasByFilaAnAndCreatedAtAfter(@Param("fila") Fila fila, @Param("createdAt") Date createdAt);
  @Query("select f from Ficha f where f.fila = :#{#fila} and f.preferencial = :#{#preferencial} and f.jaFoiChamada = false order by f.createdAt desc")
  List<Ficha> getFichasByFilaAndPreferencialOrderByCreatedAtDesc(@Param("fila") Fila fila, @Param("preferencial") boolean preferencial);
}
