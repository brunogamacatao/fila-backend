package br.edu.unifacisa.fila.controllers;

import br.edu.unifacisa.fila.entidades.Ficha;
import br.edu.unifacisa.fila.entidades.Fila;
import br.edu.unifacisa.fila.repositories.FichaRepository;
import br.edu.unifacisa.fila.repositories.FilaRepository;
import br.edu.unifacisa.fila.services.ControleDeFichasService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ControleDeFichasController {
  private FichaRepository fichaRepository;
  private FilaRepository filaRepository;
  private ControleDeFichasService fichasService;

  @GetMapping("/filas")
  public Collection<Fila> getFilas() {
    return filaRepository.findAll();
  }

  @GetMapping("/filas/{id}")
  public ResponseEntity<Fila> getFila(@PathVariable(value = "id") Long id) {
     Optional<Fila> fila = filaRepository.findById(id);

     if (fila.isPresent()) {
       return new ResponseEntity<Fila>(fila.get(), HttpStatus.OK);
     }

     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/filas")
  public Fila adicionarFila(@RequestBody Fila fila) {
    return filaRepository.save(fila);
  }

  @PutMapping("/filas/{id}")
  public ResponseEntity<Fila> atualizarFila(@PathVariable(value = "id") Long id, @RequestBody Fila newFila) {
    Optional<Fila> oldFila = filaRepository.findById(id);

    if (oldFila.isPresent()) {
      Fila fila = oldFila.get();
      fila.setNome(newFila.getNome());
      filaRepository.save(fila);
      return new ResponseEntity<Fila>(fila, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/filas/{id}")
  public ResponseEntity<Object> removerFila(@PathVariable(value = "id") Long id) {
    Optional<Fila> fila = filaRepository.findById(id);

    if (fila.isPresent()) {
      filaRepository.delete(fila.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/filas/{id_fila}/fichas")
  public ResponseEntity<Collection<Ficha>> getFichas(@PathVariable(value = "id_fila") Long idFila) {
    Optional<Fila> filaResponse = filaRepository.findById(idFila);

    if (filaResponse.isPresent()) {
      Fila fila = filaResponse.get();
      return new ResponseEntity<>(fila.getFichas(), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/filas/{id_fila}/fichas/da_vez")
  public ResponseEntity<Ficha> getFichaDaVez(@PathVariable(value = "id_fila") Long idFila) {
    Optional<Fila> filaResponse = filaRepository.findById(idFila);

    if (filaResponse.isPresent()) {
      Fila fila = filaResponse.get();
      Ficha daVez = fichasService.getFichaDaVez(fila);
      if (daVez != null) {
        return new ResponseEntity<>(daVez, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/filas/{id_fila}/fichas/atender/{id_ficha}")
  public ResponseEntity<Object> atender(@PathVariable(value = "id_fila") Long idFila, @PathVariable(value = "id_ficha") Long idFicha) {
    Optional<Fila> filaResponse = filaRepository.findById(idFila);

    if (filaResponse.isPresent()) {
      Fila fila = filaResponse.get();

      Optional<Ficha> fichaResponse = fichaRepository.findById(idFicha);
      if (fichaResponse.isPresent()) {
        fichasService.atenderFicha(fichaResponse.get());
        return new ResponseEntity<>(HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/filas/{id_fila}/fichas")
  public ResponseEntity<Ficha> adicionarFicha(@PathVariable(value = "id_fila") Long idFila, @RequestBody Ficha ficha) {
    Optional<Fila> filaResponse = filaRepository.findById(idFila);

    if (filaResponse.isPresent()) {
      Fila fila = filaResponse.get();
      return new ResponseEntity<>(fichasService.criarFicha(fila, ficha), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
