package gov.sir.core.negocio.modelo;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.auriga.core.modelo.TransferObject;




public class LLavesMostrarFolioHelper implements TransferObject {

	private List llaves;//lista con objetos tipo llaveHelper
        private static final long serialVersionUID = 1L;
	/** Metodo constructor  */
	
	public LLavesMostrarFolioHelper() {
		llaves=new Vector();
	}

	public void addLLave(LLaveMostrarFolioHelper lh){
		llaves.add(lh);
	}

	public void removeLLaves(String nResultado, String nPaginador, HttpServletRequest reques){
		Iterator ill= llaves.iterator();
		for(;ill.hasNext();){
			LLaveMostrarFolioHelper lla= (LLaveMostrarFolioHelper) ill.next();
			if((lla.getNombrePaginador().equals(nPaginador)) && (lla.getNombreResultado().equals(nResultado)) ){
				ill.remove();
			}
		}
	}
	
	public void removeLLaves( HttpServletRequest request){
		Iterator ill= llaves.iterator();
		for(;ill.hasNext();){
			LLaveMostrarFolioHelper lla= (LLaveMostrarFolioHelper) ill.next();
			request.getSession().removeAttribute(lla.getNombrePaginador());
			request.getSession().removeAttribute(lla.getNombreResultado());
			request.getSession().removeAttribute(lla.getNombreNumPagina());
			ill.remove();
		}
	}
	
	public LLaveMostrarFolioHelper getLLave(String nResultado, String nPaginador){
		Iterator ill= llaves.iterator();
		for(;ill.hasNext();){
			LLaveMostrarFolioHelper lla= (LLaveMostrarFolioHelper) ill.next();
			if((lla.getNombrePaginador().equals(nPaginador)) && (lla.getNombreResultado().equals(nResultado)) ){
				return lla;
			}
		}
		return null;
	}
}