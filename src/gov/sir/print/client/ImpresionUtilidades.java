package gov.sir.print.client;

import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ImpresionUtilidades {

	private URL urlServidor;

	private String contextoWeb;
	
	private int listeninigPort = 0;

	/**
	 * @param urlServidor
	 * @param contextoWeb
	 */
	public ImpresionUtilidades(URL urlServidor, String contextoWeb,
			int listeninigPort) {
		super();
		this.urlServidor = urlServidor;
		this.contextoWeb = contextoWeb;
		this.listeninigPort = listeninigPort;
	}

	/**
	 * Construye la url con los parametros indicados
	 * 
	 * @param parametros
	 * @return
	 */
	public String armarURL(Map parametros) {
		StringBuffer respuesta = new StringBuffer();

		respuesta.append("http://");
		respuesta.append(urlServidor.getHost());
		respuesta.append(":");
		respuesta.append(urlServidor.getPort());
		respuesta.append(contextoWeb);
		respuesta.append("/infoImpresion.do?");

		Set llaves = parametros.keySet();

		for (Iterator iter = llaves.iterator(); iter.hasNext();) {
			Object key = (Object) iter.next();
			respuesta.append(key);
			respuesta.append("=");
			respuesta.append(parametros.get(key));
			respuesta.append("&");
		}

		return respuesta.toString();
	}

	/**
	 * Descarga un objeto desde la url indicada
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Object conectarServidor(String url) throws Exception {
		Object respuesta = null;
		ObjectInputStream ois = null;
		try {

			URL urlDescargaImprimible = new URL(url);
			URLConnection connectionDescarga = urlDescargaImprimible
					.openConnection();

			ois = new ObjectInputStream(connectionDescarga.getInputStream());
			respuesta = ois.readObject();

			return respuesta;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new Exception(" Error al conectarse a la url especificada. "
					+ e.getMessage());
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	/**
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public Object realizarPeticion(Map parametros) throws Exception {
		String url = "";
		Object respuesta = null;
		url = armarURL(parametros);

		respuesta = conectarServidor(url);
		return respuesta;
	}
}
