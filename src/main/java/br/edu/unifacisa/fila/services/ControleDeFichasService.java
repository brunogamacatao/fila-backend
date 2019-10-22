package br.edu.unifacisa.fila.services;

import br.edu.unifacisa.fila.entidades.Ficha;
import br.edu.unifacisa.fila.entidades.Fila;

public interface ControleDeFichasService {
  Ficha criarFicha(Fila fila, Ficha dadosFicha);
  Ficha getFichaDaVez(Fila fila);
  void atenderFicha(Ficha ficha);
}
