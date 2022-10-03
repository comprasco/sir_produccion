package gov.sir.core.web.acciones.consulta;

import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.web.acciones.excepciones.CadenaNoPermitidaException;
import gov.sir.core.web.acciones.excepciones.MaximoNumeroConsultasException;
import gov.sir.core.web.acciones.excepciones.MaximoNumeroIntentosException;

import java.util.List;

/**
 * Clase que se encarga de la lógica para el seguimienteo de las condiciones válidas
 * para las consultas simples
 */
public class SolicitudConsultaValidator implements java.io.Serializable {

	//private Busqueda busquedaActual;

	/** Constante que identifica el máximo número de cosultas  */
	private final static int MAXIMO_NUMERO_CONSULTAS = 3;

	/** Constante que identifica el máximo número de intentos por consulta   */
	private final static int MAXIMO_NUMERO_INTENTOS = 5;

	/** Constante que identifica el número de caracteres inmutables para el parámetro de una consulta */
	private final static int NUMERO_CARACTERES_INMUTABLES = 3;

	private final static String INTENTOS = "INTENTOS";

	private SolicitudConsulta solicitudConsulta;
	private int indiceBusquedaActual;

	/**
	* Configura el validador de consultas
        * @param circulo Círculo en el cual se realiza la consulta
        * @param proceso Proceso dentro del cual se realiza la consulta
        * @param tipo Tipo de consulta a realizar
        * @param usuario Usuario que realiza la consulta
	*/
	public SolicitudConsultaValidator(String tipo, Circulo circulo, Proceso proceso, Usuario usuario) {
		solicitudConsulta = new SolicitudConsulta();

		TipoConsulta tipoConsulta = new TipoConsulta();
		tipoConsulta.setIdTipoConsulta(tipo);

		if (tipo.equals(TipoConsulta.TIPO_SIMPLE)) {
			tipoConsulta.setNombre(TipoConsulta.SIMPLE);
		} else if (tipo.equals(TipoConsulta.TIPO_COMPLEJO)) {
			tipoConsulta.setNombre(TipoConsulta.COMPLEJA);
		} else if (tipo.equals(TipoConsulta.TIPO_FOLIOS)) {
			tipoConsulta.setNombre(TipoConsulta.FOLIO);
		}
		solicitudConsulta.setTipoConsulta(tipoConsulta);
		solicitudConsulta.setCirculo(circulo);
		solicitudConsulta.setProceso(proceso);
		solicitudConsulta.setUsuario(usuario);
	}

	public void setCiudadano(Ciudadano ciudadanoSolicitante) {
		solicitudConsulta.setCiudadano(ciudadanoSolicitante);
	}

	/**
	 * @return Ciudadano solicitante asociado a la consulta
	 */
	public Ciudadano getCiudadano() {
		return solicitudConsulta.getCiudadano();
	}

	/**
	* Genera una nueva consulta para el usuario actual
        * @throws MaximoNumeroConsultasException Cuando se ha superado el número de consultas permitido
        * @return Nuevo objeto de busquda asociada a la siguiente consulta
	*/
	public Busqueda siguienteBusqueda(String idCirculo) throws MaximoNumeroConsultasException {
		TipoConsulta tipoConsulta = solicitudConsulta.getTipoConsulta();
		if (tipoConsulta.getIdTipoConsulta().equals(TipoConsulta.TIPO_SIMPLE)) {
			if (solicitudConsulta.getBusquedas().size() >= MAXIMO_NUMERO_CONSULTAS) {
				throw new MaximoNumeroConsultasException();
			}
		} else if (tipoConsulta.getIdTipoConsulta().equals(TipoConsulta.TIPO_COMPLEJO)) {
/*			if (!esValidaOtraBusquedaCompleja()) {
				throw new MaximoNumeroConsultasException();
			}*/
		}

		Busqueda busqueda = new Busqueda();
		busqueda.setNumeroIntento(0);
        busqueda.setIdCirculoBusqueda(idCirculo);
        //busquedaActual = busqueda;
		//solicitudConsulta.addBusqueda(busqueda);
		//indiceBusquedaActual = solicitudConsulta.getBusquedas().size() - 1;
		return busqueda;
	}

	/**
	 * Valida la realización de una nueva búsqueda compleja de acuerdo al número de consultas canceladas por el usuario
	 * @return Si es válido realizar una nueva consulta compleja
	 */
	public boolean esValidaOtraBusquedaCompleja(){
                int maxNum = solicitudConsulta.getNumeroMaximoBusquedas();
		int cantBusquedasRealizadas = solicitudConsulta.getBusquedas().size();
		int indiceSiguiente = cantBusquedasRealizadas + 1;

		if (indiceSiguiente > maxNum) {
			return false;
		}else{
			return true;
		}
	}

        /**
         * Valida la realización de una nueva búsqueda compleja de acuerdo al número de consultas canceladas por el usuario
         * @return si es válido realizar una nueva consulta simple
         */
        public boolean esValidaOtraBusquedaSimple(){
                int maxNum = solicitudConsulta.getNumeroMaximoBusquedas();
                int cantBusquedasRealizadas = solicitudConsulta.getBusquedas().size();
                int indiceSiguiente = cantBusquedasRealizadas + 1;

                if (indiceSiguiente > maxNum) {
                        return false;
                }else{
                        return true;
                }
        }

	/**
	* Registra un nuevo intento dentro de la consulta actual del usuario
        * @param nuevaBusqueda Búsqueda a validar
        * @throws CadenaNoPermitidaException Si el círculo desde donde se efectúa la consulta es inválido
        * @throws MaximoNumeroConsultasException Si se ha superado el número de consultas permitido
        * @throws MaximoNumeroIntentosException Si se ha superado el número de intentos permitido (6)
        * @return Búsqueda actualizada
	*/
	public Busqueda validarBusqueda(Busqueda nuevaBusqueda, Busqueda busquedaAnterior)
		throws MaximoNumeroIntentosException, MaximoNumeroConsultasException, CadenaNoPermitidaException {

		String idTipoConsulta = solicitudConsulta.getTipoConsulta().getIdTipoConsulta();
		//Busqueda busquedaAnterior = getBusquedaActual();
		long numero_intentos = busquedaAnterior.getNumeroIntento();
		if (idTipoConsulta.equals(TipoConsulta.TIPO_SIMPLE)
			|| idTipoConsulta.equals(TipoConsulta.TIPO_SIMPLE_CALIFICACION)
                        || idTipoConsulta.equals(TipoConsulta.TIPO_COMPLEJO)
                        	|| idTipoConsulta.equals(TipoConsulta.TIPO_EXENTO)) {

			if (numero_intentos >= MAXIMO_NUMERO_INTENTOS) {
				throw new MaximoNumeroIntentosException();
			}


                        busquedaAnterior.setMatricula(
                                validarParametrosBusqueda(
                                        busquedaAnterior.getMatricula(),
                                        nuevaBusqueda.getMatricula()));

			busquedaAnterior.setNumeroDocCiudadano(
				validarParametrosBusqueda(
					busquedaAnterior.getNumeroDocCiudadano(),
					nuevaBusqueda.getNumeroDocCiudadano()));

			busquedaAnterior.setTipoDocCiudadano(
				validarParametrosBusqueda(
					busquedaAnterior.getTipoDocCiudadano(),
					nuevaBusqueda.getTipoDocCiudadano()));

			busquedaAnterior.setNombreCiudadano(
				validarParametrosBusqueda(
					busquedaAnterior.getNombreCiudadano(),
					nuevaBusqueda.getNombreCiudadano()));

			busquedaAnterior.setApellido1Ciudadano(
				validarParametrosBusqueda(
					busquedaAnterior.getApellido1Ciudadano(),
					nuevaBusqueda.getApellido1Ciudadano()));

			busquedaAnterior.setApellido2Ciudadano(
				validarParametrosBusqueda(
					busquedaAnterior.getApellido2Ciudadano(),
					nuevaBusqueda.getApellido2Ciudadano()));

			busquedaAnterior.setNombreNaturalezaJuridica(
				validarParametrosBusqueda(
					busquedaAnterior.getNombreNaturalezaJuridica(),
					nuevaBusqueda.getNombreNaturalezaJuridica()));

			busquedaAnterior.setDireccion(
				validarParametrosBusqueda(
					busquedaAnterior.getDireccion(),
					nuevaBusqueda.getDireccion()));

			busquedaAnterior.setNumeroCatastral(
				validarParametrosBusqueda(
					busquedaAnterior.getNumeroCatastral(),
					nuevaBusqueda.getNumeroCatastral()));

			busquedaAnterior.setIdTipoPredio(
				validarParametrosBusqueda(
					busquedaAnterior.getIdTipoPredio(),
					nuevaBusqueda.getIdTipoPredio()));

			busquedaAnterior.setIdEje(
				validarParametrosBusqueda(busquedaAnterior.getIdEje(), nuevaBusqueda.getIdEje()));

			busquedaAnterior.setValorEje(
				validarParametrosBusqueda(busquedaAnterior.getValorEje(), nuevaBusqueda.getValorEje()));
			if(nuevaBusqueda.getIdCirculoBusqueda()!=null){
				busquedaAnterior.setIdCirculoBusqueda(nuevaBusqueda.getIdCirculoBusqueda());
			}
		}

		//	busquedaAnterior.setNumeroIntento(numero_intentos + 1);
		return busquedaAnterior;
	}

	/**
	 * Metodo para validar los parámetros de un búsqueda teniendo en cuenta el número de caracteres inmutables
         * @param cadena_anterior Cadena original en la búsqueda
         * @param cadena_nueva Cadena nueva dentro de la búsquea (intento actual)
         * @throws CadenaNoPermitidaException Si la nueva cadena es inválida
         * @return Nueva cadena, si es válida
	 */
	private String validarParametrosBusqueda(String cadena_anterior, String cadena_nueva)
		throws CadenaNoPermitidaException {
		if (cadena_anterior != null) {
			if (cadena_nueva != null) {

				String inicio_anterior =
					(cadena_anterior.length() < NUMERO_CARACTERES_INMUTABLES)
						? cadena_anterior
						: cadena_anterior.substring(0, NUMERO_CARACTERES_INMUTABLES);

				String inicio_nuevo =
					(cadena_nueva.length() < NUMERO_CARACTERES_INMUTABLES)
						? cadena_nueva
						: cadena_nueva.substring(0, NUMERO_CARACTERES_INMUTABLES);

				if (inicio_anterior.length() < inicio_nuevo.length()) {
					inicio_anterior
						+= inicio_nuevo.substring(inicio_anterior.length(), inicio_nuevo.length());
				}

                                // BUGS 6223, 6224
				if (inicio_anterior.equals(inicio_nuevo) || cadena_nueva.equals("")) {
					return cadena_nueva;
				} else {
					throw new CadenaNoPermitidaException(
						"No se permite la consulta con el parámetro "
							+ cadena_nueva
							+ " (El parámetro debe iniciar con los caracteres "
							+ inicio_anterior
							+ ") ");
				}
			} else {
				throw new CadenaNoPermitidaException(
					"No puede modificar el parámetro de consulta con el valor:" + cadena_anterior);
			}
		} else {
			return cadena_nueva;
		}
	}

	/**
	 * Retorna el número de intentos ejecutados de la consulta actual
         * @throws MaximoNumeroConsultasException Si se ha superado el número máximo de consultas permitidas
         * @return El número del intento actual
	 */
/*	public long getNumeroIntentosConsultaActual() throws MaximoNumeroConsultasException {
		return getBusquedaActual().getNumeroIntento();
	}*/

	/**
	 * @return int Número de búsquedas que el usuario ha realizado
	 */
	public int getNumeroBusquedas() {
		return solicitudConsulta.getBusquedas().size();
	}

	/**
	 * @return Busqueda con la consulta actual
         * @throws MaximoNumeroConsultasException Si se ha superado el numero máximo de consultas permitidas
	 */
/*	public Busqueda getBusquedaActual() throws MaximoNumeroConsultasException {
		if (solicitudConsulta.getBusquedas().size() == 0) {
			siguienteBusqueda();
		}

		return obtenerUltimaBusqueda(solicitudConsulta.getBusquedas());
		//return busquedaActual;
	}*/

	/**
	 * @param busquedas
	 * @return
	 */
	private Busqueda obtenerUltimaBusqueda(List busquedas) {
		Busqueda rtn = null;
		long max = -1;

		for (int i = busquedas.size() - 1; i > -1 ; i--) {
			Busqueda element = (Busqueda) busquedas.get(i);
			if(element.getFechaCreacion().getTime() > max){
				max = element.getFechaCreacion().getTime();
				rtn = element;
			}
		}
		return rtn;
	}



	/**
	 * @return Objeto <code>SolicitudConsulta</code> asociado a este validador
	 */
	public SolicitudConsulta getSolicitudConsulta() {
		return solicitudConsulta;
	}

	/**
	 * @param consulta Objeto <code>SolicitudConsulta</code> a asociar a este validador
	 */
	public void setSolicitudConsulta(SolicitudConsulta consulta) {
		solicitudConsulta = consulta;
	}

	/**
	 * Establece el número máximo de consultas del validador
	 * @param cantidad Número máximo de consultas a asociar al validador
	 */
	public void setNumMaxBusquedas(int cantidad) {
		solicitudConsulta.setNumeroMaximoBusquedas(cantidad);
	}

}
