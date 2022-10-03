/**
 * Funci�n que recupera el n�mero de anotaci�nes del folio correspondiente al id de la matricula(idMatricula) recibido como par�metro
 *  y despliega un mensaje para que el usuario digite el n�mero de anotaci�n desde el cual quiere visualizar.
 *  Si el usuario digit� un n�mero mayor que cero y menor que el numero de anotaciones total, es almacenado en el campo recibido c�mo par�metro(campoNumAnotaciones).
 *  
 * 	- Par�metros:
 * 		- idMatricula: servlet destino (ruta acceso a servlet path)
 * 		- campoNumAnotaciones: array con los valores de los atributos de la petici�n.
 *
 * 	En la p�gina donde se utilice esta funci�n debe llamarse al js conXMLHTTO debido a que se utilizar� la funci�n "connectHTTP", de este archivo,
 * 	para conectarse al servlet y realizar la consulta
 */
function getNumAnotacionesFolio(idMatricula, campoNumAnotaciones, campoAnotacionesTotal){
    var numAnotaciones;
    var anotacionesDesde;
    /**
     * Creamos el array con los nombres de los par�metros y el de los valores.
     */
    var atributos= new Array();
	var valores = new Array();
    atributos[0] = "ACCION";
    valores[0] = "getNumAnotacionesFolio";
    atributos[1] = "ID_MATRICULA";
    valores[1] = idMatricula;
    try{
        /*
         * Realizamos la peticion por xmlHTTP
         */
        var xmlDoc = conectarHTTP("AnotacionesFolioXMLHTTPServlet", atributos, valores);
        //alert(xmlDoc.xml);
        if(hayError(xmlDoc)){
            alert(getTagText(xmlDoc.getElementsByTagName("descripcion")));
            campoNumAnotaciones.value = "0";
            campoAnotacionesTotal.value = "0";
        }else{
            numAnotaciones = parseInt(xmlDoc.getElementsByTagName("totalAnotaciones") .item(0).text);
            
            if(numAnotaciones>100){
                campoAnotacionesTotal.value = numAnotaciones;
                var strAnotacionesDesde = window.prompt("***Este folio contiene " + numAnotaciones + " anotaciones.***\n***Digite el n�mero de la anotaci�n a partir de la cual desea visualizar***", '');

                try{
                    anotacionesDesde = parseInt(strAnotacionesDesde);
                    if(anotacionesDesde){
                        if(anotacionesDesde>0){
                            campoNumAnotaciones.value = anotacionesDesde;
                        }
                        
                    }
                }catch(error){
                    campoNumAnotaciones.value = "0";
                    campoAnotacionesTotal.value = "0";
                }
            }else{
                campoNumAnotaciones.value = "0";
                campoAnotacionesTotal.value = "0";
            }
            

        }
    }finally{
        calificar(idMatricula);
    }
    
}


