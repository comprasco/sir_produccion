/*****************************************************************************************************************
 Construye la llamada al servlet a través de un objeto XMLHTTP.
 	- Parámetros:
		- strServlet: servlet destino (ruta acceso a servlet path)
		- arrAtributos: array con los nombres de los atributos de la petición.
		- arrValores: array con los valores de los atributos de la petición.
 	- Retorno:
 		- objeto XMLDOM
******************************************************************************************************************/
function conectarHTTP(strServlet,arrAtributos,arrValores)  {
	var xmlhttp;// = new ActiveXObject("Microsoft.XMLHTTP");
	var xmlDoc;// = new ActiveXObject("Microsoft.XMLDOM");

    // Se verifica si el navegador es Firefox o Internet Explorer para ver la forma en que se creará el objeto XMLHTTP y XMLDOM
    try {
        xmlhttp = new ActiveXObject('Msxml2.XMLHTTP');
        xmlDoc = document.implementation.createDocument("","",null);
    }catch (e) {
        try {
            xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
            xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        }catch (e) {
            console.log('XMLHttpRequest no soportado');
        }
    }

	if (arrAtributos.length > 0) {
		//construir petición
		var peticionXML = strServlet + "?" + arrAtributos[0] + "=" + arrValores[0];
		for (var i=1;i<arrAtributos.length;i++) {
			peticionXML = peticionXML + "&" + arrAtributos[i] + "=" + arrValores[i];
		}

		//llamada a traves de objeto XMLHTTP
		try {
			xmlhttp.Open("POST",peticionXML,false);
			xmlhttp.Send();
			var strXML = xmlhttp.responseText;

		} catch (errorConexion){
			//alert(errorConexion.description);
			alert(errorConexion.description);
			return false;
		}

		xmlDoc.loadXML(strXML);
	}
    return xmlDoc;
}

/*****************************************************************************************************************
Función que dice si hay un error mirando el xml.
	- Parámetros:
		- xmlDoc: xml del que se quiere mirar si hay error.
	- Retorno:
		- hayError: booleana que devuelve true si hay error o false en caso contrario
*****************************************************************************************************************/
function hayError(xmlDoc){
	var hayError = true;
	var lista = xmlDoc.getElementsByTagName("XMLError");

	if(lista.length > 0){
		hayError = true;
	}
	else hayError = false;

	return hayError;
}


