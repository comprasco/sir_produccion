package gov.sir.hermod.dao;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.RelacionPk;
import gov.sir.core.negocio.modelo.TipoRelacion;
import gov.sir.core.negocio.modelo.TipoRelacionPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

import java.util.List;

public interface RelacionesDAO {

        public List buscarFasesRelacionadasPorRelacionId( String relacionId ) throws DAOException;

	public List getTiposRelacionesFase(String idFase) throws DAOException;

	public List getTiposRelaciones() throws DAOException;

	public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion) throws DAOException;

	public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo) throws DAOException;

	public Relacion addTurnoToRelacion(Relacion relacion, Turno turno) throws DAOException;

	public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos) throws DAOException;
	
	public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String respuesta) throws DAOException;

	public TipoRelacion getTipoRelacion(TipoRelacionPk idTipoRelacion) throws DAOException;

	public void setNotaToRelacion(RelacionPk idRelacion, String nota) throws DAOException;

	public Relacion getRelacion(gov.sir.core.negocio.modelo.RelacionPk idRelacion) throws DAOException;
	
	public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion) throws DAOException;
	
	public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion, String respuesta) throws DAOException;

	public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion, String idRelacion) throws DAOException;
	
	public List getTurnosByRelacion(Proceso proceso, Fase fase, Circulo circulo, String idRelacion) throws DAOException;	
}
