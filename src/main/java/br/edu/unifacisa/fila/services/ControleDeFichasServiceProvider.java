package br.edu.unifacisa.fila.services;

import br.edu.unifacisa.fila.entidades.Ficha;
import br.edu.unifacisa.fila.entidades.Fila;
import br.edu.unifacisa.fila.repositories.FichaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ControleDeFichasServiceProvider implements ControleDeFichasService {
  private FichaRepository fichaRepository;

  @Override
  public Ficha criarFicha(Fila fila, Ficha dadosFicha) {
    Ficha ficha = new Ficha();
    ficha.setFila(fila);

    ficha.setCelular(dadosFicha.getCelular());

    // Gerar o valor (identificador) da ficha
    Calendar hoje = Calendar.getInstance();
    hoje.setTime(new Date());
    hoje.set(Calendar.HOUR_OF_DAY, 0);
    hoje.set(Calendar.MINUTE, 0);
    hoje.set(Calendar.SECOND, 0);

    long fichasDeHoje = 1 + fichaRepository.countFichasByFilaAnAndCreatedAtAfter(fila, hoje.getTime());
    ficha.setValor(fila.getNome().substring(0, 1) +
                  (ficha.isPreferencial() ? "P" : "") +
                   String.format("%03d" , fichasDeHoje));

    return fichaRepository.save(ficha);
  }

  @Override
  public Ficha getFichaDaVez(Fila fila) {
    List<Ficha> fichasPreferenciais = fichaRepository.getFichasByFilaAndPreferencialOrderByCreatedAtDesc(fila, true);
    List<Ficha> fichasNaoPreferenciais = fichaRepository.getFichasByFilaAndPreferencialOrderByCreatedAtDesc(fila, false);

    if (!fichasPreferenciais.isEmpty()) {
      return fichasPreferenciais.get(0);
    }

    if (fichasNaoPreferenciais.isEmpty()) {
      return fichasNaoPreferenciais.get(0);
    }

    return null;
  }

  @Override
  public void atenderFicha(Ficha ficha) {
    Ficha novaFicha = fichaRepository.findById(ficha.getId()).get();
    novaFicha.setJaFoiChamada(true);
    fichaRepository.save(novaFicha);
  }
}
