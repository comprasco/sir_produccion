package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.web.WebKeys;


/**
 * @author jfrias
 * @author dsalas
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListaElementoHelper extends Helper {
	
	public static final int TAMANO_LISTA_MULTIPLE_DEFAULT = 8;
	public static final String ORDENAMIENTO_POR_ID = "OrdenId";
	public static final String ORDENAMIENTO_POR_VALOR = "OrdenValor";
	
    protected String idSelected=null;
	protected List tipos;
    private String nombre;
    private String cssClase;
    private String id;
	protected String funcion = "";
	private String width="";
    private String campoOrdenamiento = ORDENAMIENTO_POR_VALOR;
    private boolean showInstruccion = true;
    private String disabled = " ";
    
    private boolean ordenar = true;
    
	protected boolean tipoMultiple;
	protected int tamanoLista;
	
	private List listaIdSelected=null;

    /**
     * Crea un objeto ListaElementoHelper
     */
    public ListaElementoHelper() {
        super();
		tipoMultiple = false;
		tamanoLista  = TAMANO_LISTA_MULTIPLE_DEFAULT;

    }

    /**
     * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        
    }

    /**
     * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     */
     public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {
        if (tipos == null || tipos.isEmpty()) { 
            out.write("No hay datos");
        } else {
        	String cert = (String) request.getSession().getAttribute(getId());
			if (cert != null || idSelected != null || listaIdSelected!=null) {
                out.write("<select width=\""+width+"\" name=\"" + getNombre() + "\"  "+ ((tipoMultiple==true)?" multiple size=\""+tamanoLista+"\" ":"") +"   class=\"" + getCssClase() + "\" id=\"" + getId() + "\" onFocus=\"campoactual('"+getId()+"');\" " + funcion + disabled + ">");

                if(showInstruccion)
                	out.write("<option value=\"" + WebKeys.SIN_SELECCIONAR + "\" selected>-Seleccione una opcion-</option>");
                
                Iterator it = tipos.iterator();
                System.out.println("CREA EL ITERATOR DE TIPOS");
                while (it.hasNext()) { 
                    ElementoLista el = (ElementoLista) it.next();
                    System.out.println("ELEMENTO LISTA");
                    if (listaIdSelected!=null && listaIdSelected.contains(el.getId())){
                    	out.write("<option value=\"" + el.getId() +
    							"\" selected>" + el.getValor() + "</option>");
                    }
                    else if (idSelected != null && idSelected.equals(el.getId())) {
						out.write("<option value=\"" + el.getId() +
							"\" selected>" + el.getValor() + "</option>");
					}
                    else if (el.getId().equals(cert) && idSelected == null) {
                        out.write("<option value=\"" + el.getId() +
                            "\" selected>" + el.getValor() + "</option>");
                            
                    } else {
                        out.write("<option value=\"" + el.getId() + "\">" +
                            el.getValor() + "</option>");
                    }
                }

                out.write("</select>");
				out.write("<img id=\""+getId()+"_img\" src=\""+request.getContextPath()+"/jsp/images/spacer.gif\" class=\"imagen_campo\">");
            } else {
                out.write("<select width=\""+width+"\" name=\"" + getNombre()  + "\"  "+ ((tipoMultiple==true)?" multiple size=\""+tamanoLista+"\" ":"") +"      class=\"" + getCssClase() + "\" id=\"" + getId() + "\"  onFocus=\"campoactual('"+getId()+"');\" " + funcion + ">");

				if(showInstruccion)
	                out.write("<option value=\"" + WebKeys.SIN_SELECCIONAR + "\" selected>-Seleccione una opcion-</option>");

                Iterator it = tipos.iterator();

                while (it.hasNext()) {
                    ElementoLista el = (ElementoLista) it.next();
                    out.write("<option value=\"" + el.getId() + "\">" +
                        el.getValor() + "</option>");
                }

                out.write("</select>");
				out.write("<img id=\""+getId()+"_img\" src=\""+request.getContextPath()+"/jsp/images/spacer.gif\" class=\"imagen_campo\">");
            }
        }
    }

	/**
     * Se usa para convertir la lista de objetos del modelo en una lista de
     * <code>ElementoLista</code> que se va a imprimir en una lista desplegable
     * @param lista La lista de objetos del negocio
     */
    //public abstract void setListaTipos(List lista);

    /**
     * @return
     */
    public String getCssClase() {
        return cssClase;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param string
     */
    public void setCssClase(String string) {
        cssClase = string;
    }

    /**
     * @param string
     */
    public void setId(String string) {
        id = string;
    }

    /**
     * @param string
     */
    public void setNombre(String string) {
        nombre = string;
    }
    
    public void setSelected(String idSelected){
    	this.idSelected=idSelected;
    }

    /**
     * @return
     */
    public List getTipos() {
        return tipos;
    }

    /**
     * @param list
     */
    public void setTipos(List list) {
        if(this.id!=null && this.id.equals(SeleccionarNaturalezaJuridicaHelper.NATURALEZA_JURIDICA_ID) && list.size()>0){
        	List ordenada = separarOrdenar(list);
        	tipos = ordenada;
                System.out.println("PRIMER IF SET TIPOS");
        }else if(ordenar){
        	quickSort(list, 0, list.size() - 1);
        	tipos = list;
                System.out.println("SEGUNDO IF SET TIPOS");
        }else{
        	tipos = list;
                System.out.println("ELSE IF SET TIPOS");
        }
    }
    
    private List separarOrdenar(List list) {
		List lista = new ArrayList();
		List listaSecundaria = new ArrayList();
		Iterator it = list.iterator();
		while(it.hasNext()){
			ElementoLista el = (ElementoLista)it.next();
			if(el.getId().length()==3){
				lista.add(el);
			}else{
				listaSecundaria.add(el);
			}
		}
		quickSort(lista, 0, lista.size() - 1);
		quickSort(listaSecundaria, 0, listaSecundaria.size() - 1);
		lista.addAll(listaSecundaria);
		return lista;
	}

	/**
     * @return
     */
    public String getFuncion() {
            return funcion;
    }

    /**
     * @param string
     */
    public void setFuncion(String string) {
            funcion = string;
    }

    public void quickSort(List vec, int loBound0, int hiBound0)
    {
        int loBound = loBound0;
        int hiBound = hiBound0;
        if(loBound >= hiBound)
            return;
        if(hiBound - loBound == 1)
        {
        	if (campoOrdenamiento.equals(ORDENAMIENTO_POR_VALOR)){
	            if(((ElementoLista)vec.get(loBound)).getValor().compareTo(((ElementoLista)vec.get(hiBound)).getValor()) > 0)
	            {
	                String temp1 = ((ElementoLista)vec.get(loBound)).getValor();
	                String temp2 = ((ElementoLista)vec.get(loBound)).getId();
	                ((ElementoLista)vec.get(loBound)).setValor(((ElementoLista)vec.get(hiBound)).getValor());
	                ((ElementoLista)vec.get(loBound)).setId(((ElementoLista)vec.get(hiBound)).getId());
	                ((ElementoLista)vec.get(hiBound)).setValor(temp1);
	                ((ElementoLista)vec.get(hiBound)).setId(temp2);
	            }
            } else if (campoOrdenamiento.equals(ORDENAMIENTO_POR_ID)){
			if(((ElementoLista)vec.get(loBound)).getId().compareTo(((ElementoLista)vec.get(hiBound)).getId()) > 0)
			{
				String temp1 = ((ElementoLista)vec.get(loBound)).getValor();
				String temp2 = ((ElementoLista)vec.get(loBound)).getId();
				((ElementoLista)vec.get(loBound)).setValor(((ElementoLista)vec.get(hiBound)).getValor());
				((ElementoLista)vec.get(loBound)).setId(((ElementoLista)vec.get(hiBound)).getId());
				((ElementoLista)vec.get(hiBound)).setValor(temp1);
				((ElementoLista)vec.get(hiBound)).setId(temp2);
			}
		}
            return;
        }
        long pivote = Math.round((loBound + hiBound) / 2);
        String pivot1 = ((ElementoLista)vec.get((int)pivote)).getValor();
        String pivot2 = ((ElementoLista)vec.get((int)pivote)).getId();
        ((ElementoLista)vec.get((int)pivote)).setValor(((ElementoLista)vec.get(hiBound)).getValor());
        ((ElementoLista)vec.get((int)pivote)).setId(((ElementoLista)vec.get(hiBound)).getId());
        ((ElementoLista)vec.get(hiBound)).setValor(pivot1);
        ((ElementoLista)vec.get(hiBound)).setId(pivot2);
        while (loBound < hiBound) {
        	if (campoOrdenamiento.equals(ORDENAMIENTO_POR_VALOR)){
	            for(; ((ElementoLista)vec.get(loBound)).getValor().compareTo(pivot1) <= 0 && loBound < hiBound; loBound++);
	            for(; pivot1.compareTo(((ElementoLista)vec.get(hiBound)).getValor()) <= 0 && loBound < hiBound; hiBound--);
			} else if (campoOrdenamiento.equals(ORDENAMIENTO_POR_ID)){
				for(; ((ElementoLista)vec.get(loBound)).getId().compareTo(pivot2) <= 0 && loBound < hiBound; loBound++);
				for(; pivot2.compareTo(((ElementoLista)vec.get(hiBound)).getId()) <= 0 && loBound < hiBound; hiBound--);
			}
            if(loBound < hiBound)
            {
                String temp1 = ((ElementoLista)vec.get(loBound)).getValor();
                String temp2 = ((ElementoLista)vec.get(loBound)).getId();
                ((ElementoLista)vec.get(loBound)).setValor(((ElementoLista)vec.get(hiBound)).getValor());
                ((ElementoLista)vec.get(loBound)).setId(((ElementoLista)vec.get(hiBound)).getId());
                ((ElementoLista)vec.get(hiBound)).setValor(temp1);
                ((ElementoLista)vec.get(hiBound)).setId(temp2);
            }
        }
        ((ElementoLista)vec.get(hiBound0)).setValor(((ElementoLista)vec.get(hiBound)).getValor());
        ((ElementoLista)vec.get(hiBound0)).setId(((ElementoLista)vec.get(hiBound)).getId());
        ((ElementoLista)vec.get(hiBound)).setValor(pivot1);
        ((ElementoLista)vec.get(hiBound)).setId(pivot2);
        quickSort(vec, loBound0, loBound - 1);
        quickSort(vec, hiBound + 1, hiBound0);
    }

	/**
	 * @return
	 */
	public boolean isTipoMultiple() {
		return tipoMultiple;
	}

	/**
	 * @param b
	 */
	public void setTipoMultiple(boolean b) {
		tipoMultiple = b;
	}

	/**
	 * @return
	 */
	public int getTamanoLista() {
		return tamanoLista;
	}

	/**
	 * @param i
	 */
	public void setTamanoLista(int i) {
		tamanoLista = i;
	}

	/**
	 * @return
	 */
	public String getCampoOrdenamiento() {
		return campoOrdenamiento;
	}

	/**
	 * @param string
	 */
	public void setCampoOrdenamiento(String string) {
		campoOrdenamiento = string;
	}

	/**
	 * @return
	 */
	public boolean isShowInstruccion() {
		return showInstruccion;
	}

	/**
	 * @param b
	 */
	public void setShowInstruccion(boolean b) {
		showInstruccion = b;
	}

	/**
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

	public List getListaIdSelected() {
		return listaIdSelected;
	}

	public void setListaIdSelected(List listaIdSelected) {
		this.listaIdSelected = listaIdSelected;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		if (disabled)
			this.disabled = "DISABLED";
		else
			this.disabled = " ";
	}

	public boolean isOrdenar() {
		return ordenar;
	}

	public void setOrdenar(boolean ordenar) {
		this.ordenar = ordenar;
	}

}
