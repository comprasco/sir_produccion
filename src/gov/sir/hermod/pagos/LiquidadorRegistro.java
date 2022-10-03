/*
 * LiquidadorRegistro.java
 *
 * Created on 6 de agosto de 2004, 17:14
 */
package gov.sir.hermod.pagos;

import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionPk;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;

import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.dao.impl.jdogenie.JDOTarifasDAO;
import gov.sir.hermod.workflow.Processor;

/**
 * Clase que se encarga de determinar el valor que debe ser liquidado por
 * concepto de una solicitud de registro.
 *
 * @author dlopez
 */
public class LiquidadorRegistro extends Liquidador {

    private double valorTotalDerechos;
    private double valorTotalImpuestos;
    private double valorFinalActos;
    private double valorConservacion;
    private double valorFinalCertificadosAsociados;
    private String nombreTipoCertificado;
    private String idTipoCertificado;
    private double valorTotalMulta;

    JDOLiquidacionesDAO jdoLiquidacionesDAO = new JDOLiquidacionesDAO();

    /**
     * Crea una nueva instancia del liquidador de registro
     */
    public LiquidadorRegistro() {
    }

    /**
     * M?todo que se encarga de determinar el valor que debe ser liquidado por
     * concepto de una solicitud de registro.
     *
     * @param liquidacion, liquidacion a la cual se le va a calcular el valor
     * que debe ser pagado.
     * @return liquidacion cuyo valor ha sido calculado de acuerdo con las
     * reglas del negocio.
     * @throws LiquidarException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    public Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException {
        System.out.println("Desarollo1:: Ingresa a Liquidar Liquidador Registro:");

        LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) liquidacion;
        SolicitudRegistro s = (SolicitudRegistro) liqReg.getSolicitud();
        valorFinalActos = 0;
        valorFinalCertificadosAsociados = 0;

        valorTotalImpuestos = 0;
        valorTotalMulta = 0;
        valorTotalDerechos = 0;
        valorConservacion = 0;

        List listaActos = new ArrayList(5);

        try {

            HermodDAOFactory factory = HermodDAOFactory.getFactory();

            //Se redondea el valor de los otros impuestos de la liquidacion.
            /* JAlcaza caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
             * Se elimina esta linea que no es necesaria para el redondeo de liqReg.getValorOtroImp().
             */
            //valOtrosImpuestosLiq = roundValue (valOtrosImpuestosLiq);
            /* JAlcaza caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
             * Se aplica redondeo a liqReg.getValorOtroImp() que posee el valor de otros impuestos.
             */
            liqReg.setValorOtroImp(roundValue(liqReg.getValorOtroImp()));

            //Se obtiene la lista de ACTOS.
            List actos = liqReg.getActos();

            //Se obtiene el valor liquidado por concepto de actos.
            obtenerLiquidacionActos(actos);

            //Se obtiene la lista de tipos de actos.
            listaActos = this.obtenerListaTiposActo(actos);

            //Se crea una lista con los tipos de actos que est?n sujetos a excepciones:
            //IMPORTANTE EL ORDEN EN EL QUE SE VAN ASOCIANDO LOS ELEMENTOS A LA LISTA, SE DEBEN ASOCIAR
            //DE MENOR A MAYOR VALOR DE LOS ACTOS. 
            List listaActosExcepciones = new ArrayList(5);
            listaActosExcepciones = this.obtenerListaActosExcepciones(listaActos);

            //Se obtiene el valor de los certificados asociados.
            List certAsocs = s.getSolicitudesHijas();
            obtenerLiquidacionCertificadosAsociados(certAsocs, listaActosExcepciones, liqReg, s);

            /**
             * ***************************************************************************
             */
            /*              CALCULO DE MULTAS EN LA LIQUIDACION DE REGISTRO               */
            /**
             * ***************************************************************************
             */
            this.calcularValores(s, liqReg, actos);

            if (!liqReg.isPreliquidacion()) {
                if (s.getIdSolicitud() == null) {
                    SolicitudPk srID = factory.getSolicitudesDAO().crearSolicitudRegistro(s);
                    /*
					//Se coloca un tiemp de delay 
					JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
					boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(srID), 5);
                     */
                    s = (SolicitudRegistro) factory.getSolicitudesDAO().getSolicitudByID(srID);
                    if (s.getLiquidaciones() != null && s.getLiquidaciones().size() > 0) {
                        liqReg = (LiquidacionTurnoRegistro) s.getLiquidaciones().get(0);
                    }
                } else {
                    SolicitudPk srID = new SolicitudPk();
                    srID.idSolicitud = s.getIdSolicitud();
                    liqReg.setSolicitud(s);
                    liqReg = factory.getLiquidacionesDAO().addLiquidacionToSolicitudRegistro(liqReg, srID);
                    LiquidacionPk lId = new LiquidacionPk();
                    lId.idLiquidacion = liqReg.getIdLiquidacion();
                    lId.idSolicitud = liqReg.getIdSolicitud();
                    //LiquidacionTurnoRegistro liqRegr = (LiquidacionTurnoRegistro) factory.getLiquidacionesDAO().getLiquidacionByID(lId);
                    Turno turno = liquidacion.getSolicitud().getTurno();
                    Fase fase = new Fase();
                    fase.setID(turno.getIdFase());
                    Hashtable parametros = new Hashtable();

                    parametros.put(Processor.RESULT, "MAYOR_VALOR");
                    boolean resultadoAvance = true;
                    //resultadoAvance = hermod.avanzarTurno(turno, fase, parametros, 1);
                    if (resultadoAvance == false) {
                        throw new LiquidarException("No se pudo avanzar el turno en el liquidador de registro");
                    }
                }
            }

            return liqReg;

        } catch (LiquidarException e) {
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
        } catch (Throwable e) {

            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
        }

    }

    /**
     * Obtiene el valor total de la liquidacion de un conjunto de actos
     * recibidos como par?metros.
     */
    void obtenerLiquidacionActos(List actos) throws LiquidarException {

        Iterator it = actos.iterator();
        JDOLiquidacionesDAO liqActo = new JDOLiquidacionesDAO();

        try {
            while (it.hasNext()) {
                Acto ac = (Acto) it.next();

                //Se obtiene el valor de la liquidacion para el ACTO.
                Acto actoLiquidado = liqActo.getLiquidacionActo(ac);
                /**
                 * @author Fernando Padilla
                 * @change Caso Mantis 0003831: Acta - Requerimiento No 170 -
                 * Tarifas Resolucion_81_2010-imm. Se utilza la funcion
                 * redondeoCentenaMasCercana (double valor) para el redondeo a
                 * la centena mas cercana.
                 */
                //double valorTotalActo = redondeoCentenaMasCercana(actoLiquidado.getValor()) + actoLiquidado.getValorImpuestos();
                //valorTotalDerechos += redondeoCentenaMasCercana(actoLiquidado.getValor());
                double valorTotalActo = actoLiquidado.getValor() + actoLiquidado.getValorImpuestos();
                valorTotalDerechos += actoLiquidado.getValor();
                // Desarrollo1_TSG modificar para encontrar valor documental por acto

                double conservacion = jdoLiquidacionesDAO.TraerConservacionActo(actoLiquidado);

                valorConservacion += roundValue(actoLiquidado.getValor() * (conservacion));
                valorTotalImpuestos += actoLiquidado.getValorImpuestos();

                if (ac.isCobroImpuestos()) {
                    ac.setValorImpuestos(actoLiquidado.getValorImpuestos());
                }
                //Se incrementa la sumatoria de los valores de los actos.
                valorFinalActos += valorTotalActo;
            }
        } catch (DAOException daoExc) {
            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", daoExc);
        }

    }

    /**
     * Obtiene una lista con los tipos de actos asociados con una liquidacion de
     * registro de documentos.
     */
    List obtenerListaTiposActo(List actos) {

        List listaTiposActo = new ArrayList(10);
        Iterator it = actos.iterator();

        while (it.hasNext()) {
            Acto ac = (Acto) it.next();
            listaTiposActo.add(ac.getTipoActo());

        }
        return listaTiposActo;
    }

    /**
     * @return
     */
    public double getValorFinalActos() {
        return valorFinalActos;
    }

    /**
     * @return
     */
    public double getValorTotalDerechos() {
        return valorTotalDerechos;
    }

    /**
     * @return
     */
    public double getValorTotalImpuestos() {
        return valorTotalImpuestos;
    }

    /**
     * @param d
     */
    public void setValorFinalActos(double d) {
        valorFinalActos = d;
    }

    /**
     * @param d
     */
    public void setValorTotalDerechos(double d) {
        valorTotalDerechos = d;
    }

    /**
     * @param d
     */
    public void setValorTotalImpuestos(double d) {
        valorTotalImpuestos = d;
    }

    /**
     * Obtiene una lista de los actos que estan sujetos a excepciones dentro de
     * la liquidacion. Actos Codigo 80, Actos Codigo 80 y Actos Cesion a titulo
     * gratuito,
     */
    private List obtenerListaActosExcepciones(List listaActos) {
        List listaExcepciones = new ArrayList(10);

        //Se asocian los actos Codigo 80:
        /**
         * @author Ellery D. Robles Gomez.
         * @change Mantis: 8575 - Se hacen comentario las lineas siguientes para
         * que no se tengan en cuenta los actos 80 y 30 en la lista de
         * excepciones.
         */
        /*
                Iterator itCodigo80 = listaActos.iterator(); 
		while (itCodigo80.hasNext())
		{
			TipoActo tipo = (TipoActo) itCodigo80.next();
			if (tipo.getIdTipoActo().equals(CActo.CESION_BIENES_FISCALES))
			{
				listaExcepciones.add(tipo);
			}
		}
		     

		//Se asocian los actos Cesion a titulo gratuito:
		Iterator itCesion = listaActos.iterator(); 
		while (itCesion.hasNext())
		{
			TipoActo tipo = (TipoActo) itCesion.next();
			if (tipo.getIdTipoActo().equals(CActo.CESION_TITULO_GRATUITO))
			{
				listaExcepciones.add(tipo);
			}
		}*/
        //Se asocian los actos Codigo 81
        Iterator itCodigo81 = listaActos.iterator();
        while (itCodigo81.hasNext()) {
            TipoActo tipo = (TipoActo) itCodigo81.next();
            if (tipo.getIdTipoActo().equals(CActo.VENTA_PLANOS_CATASTRALES)) {
                listaExcepciones.add(tipo);
            }
        }

        /**
         * @author Fernando Padilla
         * @change Requeimiento externo 118, Se asocian los actos Codigo 38.
         */
        /*Iterator itCodigo38 = listaActos.iterator();
		while (itCodigo38.hasNext())
		{
			TipoActo tipo = (TipoActo) itCodigo38.next();
			if (tipo.getIdTipoActo().equals(CActo.VIVIENDA_INTERES_SOCIAL))
			{
				listaExcepciones.add(tipo);
			}
		}*/
        return listaExcepciones;
    }

    /**
     * Obtiene el valor total de los certificados asociados con una liquidacion
     * de registro de acuerdo con las reglas del negocio.
     */
    void obtenerLiquidacionCertificadosAsociados(List listaAsociados, List listaExcepciones, LiquidacionTurnoRegistro liqReg, SolicitudRegistro s) throws DAOException, LiquidarException {
        Iterator it2 = listaAsociados.iterator();

        int contadorActosExcepcion = 0;

        try {

            String acto = "";
            while (it2.hasNext()) {
                SolicitudAsociada solAsoc = (SolicitudAsociada) it2.next();
                SolicitudCertificado solCert = (SolicitudCertificado) solAsoc.getSolicitudHija();
                if (!solCert.isEliminar()) {

                    int tamActosExcepcion = listaExcepciones.size();

                    if (contadorActosExcepcion < tamActosExcepcion) {
                        TipoActo tipoActo = (TipoActo) listaExcepciones.get(contadorActosExcepcion);
                        String idActo = tipoActo.getIdTipoActo();

                        //ACTO CODIGO 80
                        if (idActo.equals(CActo.CESION_BIENES_FISCALES)) {
                            nombreTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_80_NOMBRE;
                            idTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_80;
                        }
                        //ACTO CODIGO 81
                        if (idActo.equals(CActo.CESION_TITULO_GRATUITO)) {
                            nombreTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_CESION_NOMBRE;
                            idTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_CESION;
                        }

                        //ACTO CODIGO 81
                        if (idActo.equals(CActo.VENTA_PLANOS_CATASTRALES)) {
                            nombreTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_81_NOMBRE;
                            idTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_81;

                        }

                        /**
                         * @author Fernando Padilla
                         * @change caso requerimiento externo 138, ACTO CODIGO
                         * 38
                         *
                         */
                        /*if (idActo.equals(CActo.VIVIENDA_INTERES_SOCIAL))
				{
					nombreTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_38_NOMBRE;
					idTipoCertificado     = CTipoCertificado.TIPO_ASOCIADO_38;
					
				}*/
                        contadorActosExcepcion++;
                        acto = idActo;
                    } else {
                        nombreTipoCertificado = CTipoCertificado.ASOCIADO_NOMBRE;
                        idTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_ID;
                    }
                    /*
                         * @author Ellery D. Robles Gomez.
                         * @change Mantis: 8575 - Se comentan lineas".
                     */
//			SolicitudAsociada solAsoc = (SolicitudAsociada) it2.next();
//			SolicitudCertificado solCert = (SolicitudCertificado) solAsoc.getSolicitudHija();
//			if(!solCert.isEliminar()){
                    LiquidacionTurnoCertificado liqCertAux = (LiquidacionTurnoCertificado) solCert.getLiquidaciones().get(0);
                    //LiquidacionTurnoCertificado liqCert = new LiquidacionTurnoCertificado();

                    liqCertAux.setUsuario(liqReg.getUsuario());

                    //Se asocia tipo de tarifa y tipo de certificados a la liquidacion. 
                    TipoCertificado tipoCert = new TipoCertificado();
                    tipoCert.setIdTipoCertificado(idTipoCertificado);
                    tipoCert.setNombre(nombreTipoCertificado);
                    liqCertAux.setTipoCertificado(tipoCert);
                    liqCertAux.setTipoTarifa(liqCertAux.getTipoTarifa());
                    if (acto.equals(CActo.VENTA_PLANOS_CATASTRALES) && liqCertAux.getTipoTarifa().equals("ESPECIAL")) {
                        JDOTarifasDAO tarifasDAO = new JDOTarifasDAO();
                        //SE BUSCA LA TARIFA ESPECIAL PARA CERTIFICADOS CON EL ACTO 81
                        Tarifa tarifa = tarifasDAO.getTarifa(CActo.CERTIFICADOS, CTipoCertificado.ESPECIAL_ASOCIADO_81);
                        if (tarifa == null) {
                            throw new DAOException("No se encontro valor para la llave: " + CActo.CERTIFICADOS + " - " + CTipoCertificado.ESPECIAL_ASOCIADO_81);
                        }
                        /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                         * Redondeo del valor de tarifa.getValor()
                         */
                        liqCertAux.setValor(roundValue(tarifa.getValor()));
                    }

                    Liquidacion liq = (Liquidacion) liqCertAux;
                    liq.setRol(liqReg.getRol());
                    //Se asocia la solicitud de certificados a la liquidacion. 
                    liq.setSolicitud(solCert);
                    //solCert.addLiquidacion(liq);

                    Liquidador liquidador = new LiquidadorCertificados();
                    liqCertAux = (LiquidacionTurnoCertificado) liquidador.liquidar(liq);
                    solAsoc.setSolicitudHija(liqCertAux.getSolicitud());
                    solAsoc.setSolicitudPadre(s);
                    valorFinalCertificadosAsociados += liqCertAux.getValor();
                }

            }
        } catch (LiquidarException lExc) {
            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", lExc);
        }
    }

    /**
     * Obtiene el valor que debe ser liquidado como multa para cada acto que se
     * le haya cancelado impuesto
     */
    void obtenerValorMultaActos(List actos, SolicitudRegistro s, boolean tieneTurnoAnterior, Date fechaInicioTurnoAnterior) throws DAOException {
        Iterator it = actos.iterator();

        while (it.hasNext()) {
            Acto ac = (Acto) it.next();
            if (ac.isCobroImpuestos()) {
                //Se determina la fecha actual.
                Date fechaActual = new Date();

                //Se determina fecha del otorgamiento o expedicion del documento.
                if (s == null) {
                    throw new DAOException("La liquidacion no tiene una solicitud asociada.");
                }
                if (s.getDocumento() == null) {
                    throw new DAOException("La solicitud no tiene asociado un documento.");
                }
                if (s.getDocumento().getFecha() == null) {
                    throw new DAOException("El documento no tiene una fecha de expedicion.");
                }

                Date fechaExpedicion = s.getDocumento().getFecha();

                //Se hace la resta de las fechas para saber si hay objeto a multa.
                //Este caso se presenta si han transcurrido mas de dos meses para los documentos
                //otorgados en el pais, o 3 meses para los documentos expedidos en el extranjero.
                Calendar calendarioActual = Calendar.getInstance();
                calendarioActual.setTime(fechaActual);
                Calendar calendarioExpedicion = Calendar.getInstance();
                calendarioExpedicion.setTime(fechaExpedicion);

                Calendar calendarioTurnoAnterior = Calendar.getInstance();

                if (tieneTurnoAnterior) {
                    calendarioTurnoAnterior.setTime(fechaInicioTurnoAnterior);
                }

                boolean hayMulta = false;
                int meses;

                if (s.getDocumento().getOficinaOrigen() != null) {
                    //La oficina es nacional, se decide si hay multa si han trascurrido
                    //2 meses desde la expedicion:
                    meses = 2;
                } else {
                    meses = 3;
                }

                //Se le suma el n?mero de meses indicado a la fecha de comparaci?n:
                calendarioExpedicion.add(Calendar.MONTH, meses);
                Calendar calendarioVencimiento = calendarioExpedicion;

                //La fecha de expedici?n mas los meses se compara con la fecha actual
                if (calendarioVencimiento.before(calendarioActual)) {
                    //Si se sobrepasa la fecha, es que hay multa
                    hayMulta = true;
                }

                if (hayMulta) {

                    //Se setea la hora hasta la cual contar?a el d?a vencido
                    calendarioVencimiento.set(Calendar.HOUR_OF_DAY, 23);
                    calendarioVencimiento.set(Calendar.MINUTE, 59);
                    calendarioVencimiento.set(Calendar.SECOND, 59);
                    calendarioVencimiento.set(Calendar.MILLISECOND, 0);

                    // Se setea la hora hasta la cual contar?a el d?a vencido
                    calendarioTurnoAnterior.set(Calendar.HOUR_OF_DAY, 23);
                    calendarioTurnoAnterior.set(Calendar.MINUTE, 59);
                    calendarioTurnoAnterior.set(Calendar.SECOND, 59);
                    calendarioTurnoAnterior.set(Calendar.MILLISECOND, 0);

                    //Se setea la hora hasta la cual contar?a el d?a actual
                    calendarioActual.set(Calendar.HOUR_OF_DAY, 23);
                    calendarioActual.set(Calendar.MINUTE, 59);
                    calendarioActual.set(Calendar.SECOND, 59);
                    calendarioActual.set(Calendar.MILLISECOND, 999);

                    if (s.getCirculo() == null) {
                        throw new DAOException("la solicitud NO tiene circulo");
                    }

                    JDOTarifasDAO tarifasDAO = new JDOTarifasDAO();
                    //SE BUSCA LA TASA DE INTERES SIMPLE PARA EL CIRCULO
                    Tarifa tasaDiaria = tarifasDAO.getTarifa(CActo.TASA_DIARIA_MULTA, s.getCirculo().getNombre());
                    if (tasaDiaria == null) {
                        throw new DAOException("No se encontro valor porcentual para la llave: " + CActo.TASA_DIARIA_MULTA + " - " + s.getCirculo().getNombre());
                    }

                    CirculoPk cID = new CirculoPk();
                    cID.idCirculo = s.getCirculo().getIdCirculo();

                    long todosLosDias = 0;

                    //Si el turno anterior es anterior a la fecha del vencimiento unicamente se cobra a partir de la fecha del vencimiento
                    if (tieneTurnoAnterior) {
                        if (calendarioVencimiento.after(calendarioTurnoAnterior)) {
                            todosLosDias = (calendarioActual.getTimeInMillis() - calendarioVencimiento.getTimeInMillis()) / (1000 * 60 * 60 * 24);
                        } else {
                            todosLosDias = (calendarioActual.getTimeInMillis() - calendarioTurnoAnterior.getTimeInMillis()) / (1000 * 60 * 60 * 24);
                        }
                    } else {
                        todosLosDias = (calendarioActual.getTimeInMillis() - calendarioVencimiento.getTimeInMillis()) / (1000 * 60 * 60 * 24);
                    }

                    //Se ignora por bug 2586, La multa est? basada sobre todos los d?as. y no solamente dias habiles
                    //long diasHabiles = todosLosDias - diasNoHabiles;
                    //El valor de la multa es el capital de impuestos pagados
                    //por la tasa de inter?s diaria desde el momento en que se
                    //venci? el plazo
                    double valorTotalMultaActo = roundValue(ac.getValorImpuestos() * todosLosDias * (tasaDiaria.getValor() / 30));
                    //valorTotalMultaActo = roundValue (valorTotalMultaActo);
                    ac.setValorMora(valorTotalMultaActo);
                    valorTotalMulta += valorTotalMultaActo;
                }
            }
        }

    }

    /**
     * M?todo que se encarga de determinar el valor que debe ser reliquidado por
     * concepto de una solicitud de registro. Se utiliza cuando se quiere
     * reliquidar una solicitud de registro.
     *
     * @param liquidacion, liquidacion a la cual se le va a calcular el valor
     * que debe ser pagado.
     * @return liquidacion cuyo valor ha sido calculado de acuerdo con las
     * reglas del negocio.
     * @throws LiquidarException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    public Liquidacion reliquidar(Liquidacion liquidacion) throws LiquidarException {

        LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) liquidacion;
        SolicitudRegistro s = (SolicitudRegistro) liqReg.getSolicitud();
        valorFinalActos = 0;
        valorFinalCertificadosAsociados = 0;

        valorTotalImpuestos = 0;
        valorTotalMulta = 0;
        valorTotalDerechos = 0;

        List listaActos = new ArrayList(5);

        try {

            HermodDAOFactory factory = HermodDAOFactory.getFactory();

            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                        * Redondeo del valor de la variable liqReg.getValorOtroImp().
             */
            liqReg.setValorOtroImp(roundValue(liqReg.getValorOtroImp()));

            //Se obtiene la lista de ACTOS.
            List actos = liqReg.getActos();

            //Se obtiene el valor liquidado por concepto de actos.
            obtenerLiquidacionActos(actos);

            //Se obtiene la lista de tipos de actos.
            listaActos = this.obtenerListaTiposActo(actos);

            //Se crea una lista con los tipos de actos que est?n sujetos a excepciones:
            //IMPORTANTE EL ORDEN EN EL QUE SE VAN ASOCIANDO LOS ELEMENTOS A LA LISTA, SE DEBEN ASOCIAR
            //DE MENOR A MAYOR VALOR DE LOS ACTOS. 
            List listaActosExcepciones = new ArrayList(5);
            listaActosExcepciones = this.obtenerListaActosExcepciones(listaActos);

            //Se obtiene el valor de los certificados asociados.
            List certAsocs = s.getSolicitudesHijas();
            obtenerLiquidacionCertificadosAsociados(certAsocs, listaActosExcepciones, liqReg, s);

            /**
             * ***************************************************************************
             */
            /*              CALCULO DE MULTAS EN LA LIQUIDACION DE REGISTRO               */
            /**
             * ***************************************************************************
             */
            this.calcularValores(s, liqReg, actos);

            //Modificar la solicitud de registro (poner persistente las relaciones faltantes con los solicitudeshijas)
            List solicitudesHijas = s.getSolicitudesHijas();
            Iterator itHijas = solicitudesHijas.iterator();
            while (itHijas.hasNext()) {
                SolicitudAsociada solasoc = (SolicitudAsociada) itHijas.next();
                SolicitudCertificado solCertTemp = (SolicitudCertificado) solasoc.getSolicitudHija();
                //Se mira el proceso y el circulo de la solicitud
                if (solCertTemp.getCirculo() != null && solCertTemp.getProceso() != null) {
                    factory.getSolicitudesDAO().addSolicitudHija(s, solCertTemp);
                }
            }

            SolicitudPk srID = new SolicitudPk();
            srID.idSolicitud = s.getIdSolicitud();
            liqReg.setSolicitud(s);
            liqReg = factory.getLiquidacionesDAO().addLiquidacionToSolicitudRegistro(liqReg, srID);
            LiquidacionPk lId = new LiquidacionPk();
            lId.idLiquidacion = liqReg.getIdLiquidacion();
            lId.idSolicitud = liqReg.getIdSolicitud();

            return liqReg;

        } catch (LiquidarException e) {
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
        } catch (Throwable e) {
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
        }

    }

    /**
     * Este m?todo calcula el valor de la liquidacion. Revisa en los turnos
     * anteriores y encuentra actos registrados del mismo tipo para hacer el
     * descuento. El acto se vuelve a cobrar al ciudadano si no fue radicado en
     * los turnos anteriores
     *
     * @param s SolicitudRegistro del turno a liquidar
     * @param liqReg LiquidacionTurnoRegistro del turno a liquidar
     * @param actos List de actos del turno actual
     * @throws LiquidarException
     */
    /**
     * @author fernando padilla velez
     * @change Mantis 6716: Error al liquidar el valor del acto, Se realiza
     * refactory en el metodo de calcularValores, ya que, exite un bug cuando se
     * realiza una nueva entrada y se agrega un acto que ya existia en el turno
     * anterior. por ejemplo, dos ventas, el sistema dejaba los valores en
     * ceros, no ocasionando costos en la liquidacion para este nuevo acto.
     */
    private void calcularValores(SolicitudRegistro s, LiquidacionTurnoRegistro liqReg, List actos) throws LiquidarException {

        try {
            Turno turnoAnt = s.getTurnoAnterior();
            HermodDAOFactory factory = HermodDAOFactory.getFactory();

            if (turnoAnt != null) {
                TurnoPk tId = new TurnoPk();
                tId.anio = turnoAnt.getAnio();
                tId.idCirculo = turnoAnt.getIdCirculo();
                tId.idProceso = turnoAnt.getIdProceso();
                tId.idTurno = turnoAnt.getIdTurno();
                /**
                 * @author Fernando Padilla Velez
                 * @change Acta - Requerimiento 071- Interes_Mora_Impuesto, Se
                 * valida que el el circulo tenga cobros de impuesto, esto para
                 * no calcular las multas de estos impuestos.
                 */
                if (turnoAnt.getSolicitud().getCirculo().isCobroImpuesto()) {
                    obtenerValorMultaActos(actos, s, true, turnoAnt.getFechaInicio());
                }
                liqReg.setValorDerechos(valorTotalDerechos);
                liqReg.setValorImpuestos(valorTotalImpuestos);
                liqReg.setValorMora(valorTotalMulta);
                liqReg.setValorConservacionDoc(valorConservacion);

                List actosCalcularComp = new ArrayList(actos);

                Turno t = factory.getTurnosDAO().getTurnoByWFId(turnoAnt.getIdWorkflow());

                tId = new TurnoPk();
                tId.anio = t.getAnio();
                tId.idCirculo = t.getIdCirculo();
                tId.idProceso = t.getIdProceso();
                tId.idTurno = t.getIdTurno();

                if (t.getSolicitud() != null
                        && t.getSolicitud().getLiquidaciones() != null
                        && !t.getSolicitud().getLiquidaciones().isEmpty()) {

                    LiquidacionTurnoRegistro liqAnterior = (LiquidacionTurnoRegistro) t.getSolicitud().getLiquidaciones().get(0);

                    if (liqAnterior.getActos() != null) {

                        Log.getInstance().error(LiquidadorRegistro.class, "Jefferson entro a if de actos anteriores");

                        for (Iterator actosAntItera = liqAnterior.getActos().iterator();
                                actosAntItera.hasNext();) {
                            Log.getInstance().error(LiquidadorRegistro.class, "Jefferson entro a for de actos anteriores");
                            Acto actoAnterior = (Acto) actosAntItera.next();
                            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                            * Redondeo del valor de las variables actoAnterior.getValor() y actoAnterior.getValorImpuestos().
                             */
                            //actoAnterior.setValor(valorFinalActos);
                            liqReg.setValorDerechos(liqReg.getValorDerechos() - roundValue(actoAnterior.getValor()));
                            liqReg.setValorImpuestos(liqReg.getValorImpuestos() - roundValue(actoAnterior.getValorImpuestos()));

                            double conservacion = jdoLiquidacionesDAO.TraerConservacionActo(actoAnterior);

                            double conservacionActoAnteriorTmp = roundValue(actoAnterior.getValor() * conservacion);
                            liqReg.setValorConservacionDoc(liqReg.getValorConservacionDoc() - conservacionActoAnteriorTmp);
                        }

                        //se valida que los valores no sean negativos
                        if (liqReg.getValor() < 0) {
                            liqReg.setValor(0);
                        }
                        if (liqReg.getValorDerechos() < 0) {
                            liqReg.setValorDerechos(0);
                        }
                        if (liqReg.getValorImpuestos() < 0) {
                            liqReg.setValorImpuestos(0);
                        }
                        if (liqReg.getValorMora() < 0) {
                            liqReg.setValorMora(0);
                        }
                        if (liqReg.getValorOtroImp() < 0) {
                            liqReg.setValorOtroImp(0);
                        }
                        if (liqReg.getValorConservacionDoc() < 0) {
                            liqReg.setValorConservacionDoc(0);
                        }
                        /**
                         * @autor: Edgar Lora
                         * @mantis: 0012289
                         * @requerimiento: 046_453
                         */
                        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                        if (!validacionesSIR.circuloCobraImpuestos(liqReg.getCirculo())) {
                            if (liqReg.getValorImpuestos() > 0) {
                                liqReg.setValorImpuestos(0);
                            }
                            if (valorTotalMulta > 0) {
                                valorTotalMulta = 0;
                            }
                        }
                    }
                }

                double valorOtroImpAnt = factory.getLiquidacionesDAO().getValorOtroImpuestoByTurno(tId);
                /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                 * Redondeo del valor de la variable valorOtroImpAnt.
                 */
                liqReg.setValorOtroImp(liqReg.getValorOtroImp() - roundValue(valorOtroImpAnt));

                if (liqReg.getValorOtroImp() < 0) {
                    liqReg.setValorOtroImp(0);
                }

                liqReg.setValorMora(valorTotalMulta);

                if (liqReg.getValorMora() < 0) {
                    liqReg.setValorMora(0);
                }

                liqReg.setValor(liqReg.getValorDerechos() + liqReg.getValorImpuestos() + liqReg.getValorMora() + liqReg.getValorOtroImp() + liqReg.getValorConservacionDoc());

                if (liqReg.getValor() < 0) {
                    liqReg.setValor(0);
                }

            } else {
                obtenerValorMultaActos(actos, s, false, null);

                double valorLiquidado = valorFinalActos + valorTotalMulta + liqReg.getValorOtroImp() + valorConservacion;
                valorLiquidado = roundValue(valorLiquidado);

                liqReg.setValor(valorLiquidado);
                liqReg.setValorDerechos(valorTotalDerechos);
                liqReg.setValorImpuestos(valorTotalImpuestos);
                liqReg.setValorMora(valorTotalMulta);
                liqReg.setValorOtroImp(liqReg.getValorOtroImp());
                liqReg.setValorConservacionDoc(valorConservacion);
            }
        } catch (Exception e) {
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
        } catch (Throwable e) {
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
        }
    }

    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio de redondeo a la centena mas cercana
     */
    private static double roundValue(double valor) {
        double diferencia = valor % 100;

        //Redondeo hacia la decena anterior.
        if (diferencia < 50) {
            valor -= diferencia;
        } //Redondeo hacia la centena siguiente.
        else {
            valor -= diferencia;
            valor += 100;
        }

        return valor;
    }

    /**
     * @author Fernando Padilla
     * @change Caso Mantis 0003831: Acta - Requerimiento No 170 - Tarifas
     * Resolucion_81_2010-imm. Se crea esta funcion para el redondeo de valores
     * a la centena mas cercana.
     */
    /*private static double redondeoCentenaMasCercana (double valor)
        {
            double diferencia = valor%100;

            if (diferencia < 50){
                valor -= diferencia;
            }else{
                valor -= diferencia;
                valor +=100;
            }

            return valor;
        }*/
}
