<%@ page import="java.io.*,gov.sir.core.negocio.modelo.constantes.CImpresion,gov.sir.core.negocio.modelo.Imprimible,gov.sir.core.web.WebKeys"%><%@ page contentType="application/octet-stream"%><%
Imprimible imp = null;
String idImprimible = request.getParameter(CImpresion.ID_IMPRIMIBLE);
Object obj = (Object)session.getAttribute(WebKeys.IMPRIMIBLE + idImprimible);
boolean puedeImprimirse = false;
if(obj instanceof Imprimible ){
	imp = (Imprimible)session.getAttribute(WebKeys.IMPRIMIBLE + idImprimible);
	if(imp.getDatosImprimible()!=null){
		puedeImprimirse = true;
	}
}
if( puedeImprimirse	){
	if(imp !=null){
		//System.out.println("PAGINA DESCARGA: NO ES NULO EL IMPRIMIBLE");
	}else{
		//System.out.println("PAGINA DESCARGA: ES NULO EL IMPRIMIBLE");
	}
	ByteArrayInputStream bais = new ByteArrayInputStream(imp.getDatosImprimible()) ;
	ObjectInputStream ois = new ObjectInputStream(bais);
	OutputStream output = response.getOutputStream();
	ObjectOutputStream oos = new ObjectOutputStream( output );
	gov.sir.print.common.Bundle bundle = (gov.sir.print.common.Bundle)ois.readObject();
	session.removeAttribute(WebKeys.IMPRIMIBLE + idImprimible);
	oos.writeObject( bundle);
	oos.flush();
	oos.close();
}else{
	OutputStream output = response.getOutputStream();
	ObjectOutputStream oos = new ObjectOutputStream( output );
	session.removeAttribute(WebKeys.IMPRIMIBLE + idImprimible);
	oos.writeObject(new Boolean(false));
	oos.flush();
	oos.close();
}
%>
