package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLogger;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;

/**
 * @author gvillal
 * Clase que representa el imprimible de un recibo de pago.
 * Esta clase representa cualquier tipo de recibo que genera la
 * aplicación.
 */
public class ImprimibleFotocopiaComprobanteSolicitud extends ImprimibleRecibo  {
    private static final long serialVersionUID = 1L;
    public ImprimibleFotocopiaComprobanteSolicitud( Pago pago, Circulo circulo, String fechaImpresion ,String tipoImprimible) {
      super( pago, circulo, fechaImpresion,tipoImprimible );

      // prevenir, cuando se edite y llame a otro constructor
      AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
      if( null == this.logger )
        this.logger = loggerImpl;

    }

     protected String getTitulo() {

        String titulo;
        titulo = "Comprobante de solicitud de Documentos a ser Fotocopiados" ;
        return titulo;
    }


    /**
     * Imprime el encabezado del recibo.
     * En particular la parte que es comun a todos los recibos.
     */
    private void imprimirEncabezadoRecibo() {
        //imprimir el numero del recibo
        //this.imprimirLinea(ImprimibleConstantes.titulo2,380,"RECIBO DE CAJA No. ",false);
        String numRecibo = this.pago.getNumRecibo();

        if ((numRecibo == null) ||
                this.solicitud instanceof SolicitudCertificadoMasivo) {
            numRecibo = "";
        }

        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 470, numRecibo);

        //imprimir el tipo de solicitud de recibo.
        String titulo = this.getTitulo();
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
            ImprimibleConstantes.MARGEN_IZQ * 4, titulo);

        //imprime la fecha y hora de impresion del recibo.
        String fechaImp = this.fechaImpresion;
        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 4, fechaImp);

        //imprime el número de radicación.
        if (this.solicitud instanceof SolicitudCertificadoMasivo) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                ImprimibleConstantes.MARGEN_IZQ * 4, "No. DE SOLICITUD: ", false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                (ImprimibleConstantes.MARGEN_IZQ * 6) + 10,
                this.turno.getIdWorkflow());
        } else {
            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                ImprimibleConstantes.MARGEN_IZQ * 4, "No. RADICACIÓN: ", false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                ImprimibleConstantes.MARGEN_IZQ * 6, this.turno.getIdWorkflow());
        }

        //Se ignora esta linea para que no SALGA el recibo turno padre asociado
        /*
        if (this.solicitud instanceof SolicitudCertificado)
        {
                Solicitud solPadre = this.getNumeroRadicacionAsociadoAlTurnoDeRegistro((SolicitudCertificado)this.solicitud);
                if (solPadre!=null)
            {
                        Turno turnoPadre = solPadre.getTurno();
                        String numRadAsociada = turnoPadre.getIdWorkflow();

                        String msgPadre;
                        int cord_x_padre;

                        if(solPadre instanceof SolicitudCertificadoMasivo){
                                msgPadre = "Asociado al turno de certificado masivo: ";
                                cord_x_padre = ImprimibleConstantes.MARGEN_IZQ * 8 + 5;

                        }
                        else{
                                msgPadre = "Asociado al turno de certificado masivo: ";
                                cord_x_padre = ImprimibleConstantes.MARGEN_IZQ * 8 + 5;
                                //cord_x_padre = ImprimibleConstantes.MARGEN_IZQ * 7 + 22;
                        }

                        this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ * 4,msgPadre,false);
                        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,cord_x_padre,numRadAsociada);
            }

        }*/
        //deja una linea en blanco.
        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
    }

}
