package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.PantallaAdministrativa;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class PantallaAdministrativaEnhanced extends Enhanced{

    private long idPantallaAdministrativa; // pk
    private String nombre;
    private String vista;
    private String pagina;
    private TipoPantallaEnhanced tipoPantalla;
    private List roles = new ArrayList(); // contains RolPantallaEnhanced  inverse RolPantallaEnhanced.pantalla

    public PantallaAdministrativaEnhanced() {
    }

    public long getIdPantallaAdministrativa() {
        return idPantallaAdministrativa;
    }

    public void setIdPantallaAdministrativa(long idPantallaAdministrativa) {
        this.idPantallaAdministrativa = idPantallaAdministrativa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    public TipoPantallaEnhanced getTipoPantalla() {
        return tipoPantalla;
    }

    public void setTipoPantalla(TipoPantallaEnhanced tipoPantalla) {
        this.tipoPantalla = tipoPantalla;
    }

    /** Retorna la pantalla asociada al mostrarse como un menu-item */
    public String getPagina() {
    	return pagina;
    }

    /** Cambia la pantalla asociada al mostrarse como un menu-item */
    public void setPagina(String pagina) {
    	this.pagina = pagina;
    }

    public List getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public boolean addRole(RolPantallaEnhanced newRole) {
        return roles.add(newRole);
    }

    public boolean removeRole(RolPantallaEnhanced oldRole) {
        return roles.remove(oldRole);
    }


	public static PantallaAdministrativaEnhanced enhance (
	PantallaAdministrativa pantallaAdministrativa)
	{
		return (PantallaAdministrativaEnhanced)Enhanced.enhance(pantallaAdministrativa);
	}
}
