package br.edu.unifacisa.fila.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
public class Ficha {
  @Id
  @GeneratedValue
  private Long id;
  private String valor; // O valor é construído no ato da criação da ficha
  private String celular;
  private boolean preferencial;
  private boolean jaFoiChamada;
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonManagedReference
  private Fila fila;
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdated;

  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
    jaFoiChamada = false;
  }

  @PreUpdate
  protected void onUpdate() {
    lastUpdated = new Date();
  }
}
