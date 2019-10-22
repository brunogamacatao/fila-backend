package br.edu.unifacisa.fila.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Fila {
  @Id
  @GeneratedValue
  private Long id;
  private String nome;
  @OneToMany(mappedBy = "fila", fetch = FetchType.EAGER)
  @JsonBackReference
  private List<Ficha> fichas;
}
