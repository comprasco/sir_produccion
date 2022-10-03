<%@ page import="java.io.*,gov.sir.core.web.WebKeys"%>
<%@ page contentType="application/octet-stream"%>
<%
	Boolean respuesta = (Boolean) request.getAttribute(WebKeys.IMPRESION_EXISTE_SESION);
	//System.out.println(" VERIFICAR SESION " +respuesta );

	//ByteArrayInputStream bais = new ByteArrayInputStream(respuesta) ;
	//ObjectInputStream ois = new ObjectInputStream(bais);

	OutputStream output = response.getOutputStream();
	ObjectOutputStream oos = new ObjectOutputStream( output );
	//gov.sir.print.common.Bundle bundle = (gov.sir.print.common.Bundle)ois.readObject();

	oos.writeObject( respuesta );
	oos.flush();
	oos.close();
%>
