package com.example.delivereat.model;

import static com.example.delivereat.util.Constantes.MIN_HORA_ENTREGA;

import com.example.delivereat.util.Constantes;

import java.util.Calendar;

public class Entrega {

    private Calendar entregaProgramada;
    private boolean entregaLoAntesPosible = true;

    public Entrega() {
    }

    public Calendar getEntregaProgramada() {
        return entregaProgramada;
    }

    public void setEntregaProgramada(Calendar entregaProgramada) {
        this.entregaProgramada = entregaProgramada;
    }

    public boolean isEntregaLoAntesPosible() {
        return entregaLoAntesPosible;
    }

    public void setEntregaLoAntesPosible(boolean entregaLoAntesPosible) {
        this.entregaLoAntesPosible = entregaLoAntesPosible;
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public String cuandoLlega() {
        return entregaLoAntesPosible
                ? "Llegará lo antes posible"
                : "Llegará el " + entregaProgramada.get(Calendar.DATE) + " de " + Constantes.MESES[entregaProgramada.get(Calendar.MONTH)] + ".";
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return eFechaRequerida() || eFechaMinima();
        }

        public boolean eFechaRequerida() {
            return !entregaLoAntesPosible && entregaProgramada == null;
        }

        public boolean eFechaMinima() {
            Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.HOUR_OF_DAY, MIN_HORA_ENTREGA);
            return !entregaLoAntesPosible && entregaProgramada != null && minDate.getTime().after(entregaProgramada.getTime());
        }
    }
}
