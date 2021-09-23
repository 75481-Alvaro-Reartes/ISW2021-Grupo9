package com.example.delivereat.model.pedidos;

import static com.example.delivereat.util.Constantes.MIN_HORA_ENTREGA;

import com.example.delivereat.model.otros.ErrorManager;
import com.example.delivereat.util.Constantes;

import java.util.Calendar;

/**
 * Clase del modelo sobre la entrega del pedido.
 */
public class Entrega {

    private Calendar mEntregaProgramada;
    private boolean mEntregaLoAntesPosible = true;

    public Entrega() {
    }

    public Calendar getEntregaProgramada() {
        return mEntregaProgramada;
    }

    public void setEntregaProgramada(Calendar entregaProgramada) {
        mEntregaProgramada = entregaProgramada;
    }

    public boolean isEntregaLoAntesPosible() {
        return mEntregaLoAntesPosible;
    }

    public void setEntregaLoAntesPosible(boolean entregaLoAntesPosible) {
        mEntregaLoAntesPosible = entregaLoAntesPosible;
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    /**
     * avisa por pantalla con el momento de llegada del producto con su mes hora y minutos
     * @return
     */
    public String cuandoLlega() {
        return mEntregaLoAntesPosible
                ? "Llegará lo antes posible"
                : "Llegará el " + mEntregaProgramada.get(Calendar.DATE) + " de "
                + Constantes.MESES[mEntregaProgramada.get(Calendar.MONTH)] + " a las " +
                mEntregaProgramada.get(Calendar.HOUR_OF_DAY) + ":" +
                mEntregaProgramada.get(Calendar.MINUTE) + " horas.";
    }

    /**
     * Clase interna con errores posibles de la clase entrega
     */
    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return eFechaRequerida() || eFechaMinima() || eRangoHoras();
        }

        public boolean eFechaRequerida() {
            return !mEntregaLoAntesPosible && mEntregaProgramada == null;
        }

        public boolean eFechaMinima() {
            Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.HOUR_OF_DAY, MIN_HORA_ENTREGA);

            return !mEntregaLoAntesPosible && mEntregaProgramada != null
                    && minDate.getTime().after(mEntregaProgramada.getTime());
        }

        public boolean eRangoHoras() {
            return !mEntregaLoAntesPosible &&
                    !eFechaRequerida() && !eFechaMinima()
                    && (
                            mEntregaProgramada.get(Calendar.HOUR_OF_DAY) < Constantes.HORA_HABIL_MIN
                                    || (mEntregaProgramada.get(Calendar.HOUR_OF_DAY) == 23
                                    && mEntregaProgramada.get(Calendar.MINUTE) == 59));
        }
    }
}
