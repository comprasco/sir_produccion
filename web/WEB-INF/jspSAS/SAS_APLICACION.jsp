<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Documento sin t&iacute;tulo</title>
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<link rel="stylesheet" href="../../jsp/auriga/auriga.css" type="text/css">
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
   <tr> 
    <td width="3%">&nbsp;</td>
    <td width="94%"><table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <tr> 
	<tr>
		
	    <td colspan="8"><img src="../../jsp/auriga/images/trans.gif" width="20" height="101"> 
		</td>
		
          <td width="100%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">

              <tr> 
                <td><div align="left"> 
                    <form action="SAS_APLICACIONES.do" method="post">
                      <table width="99%" border="0" align="center" cellpadding="0" cellspacing="5">
                        <tr>
                          <td width="136" align="left" valign="top"><p class="texto">Seleccione 
                              un acci&oacute;n:
                              <select name="ACCION" class="fondolista" id="ACCION">
                                <option value="CREAR">CREAR</option>
                                <option value="CONSULTAR">CONSULTAR</option>
                                <option value="ACTUALIZAR">ACTUALIZAR</option>
                                <option value="ELIMINAR">ELIMINAR</option>
                              </select>
                            </p>
                          </td>
                          <td width="124" class="titulos">Aplicaci&oacute;n ID:</td>
                          <td width="169"><input name="APLICACION_ID" type="text" class="fondo" 
                          	id="APLICACION_ID" size="10" ></td>
                          <td width="269" class="texto">Identificador de la aplicaci&oacute;n 
                            que tiene programas concurrentes asociados</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td class="titulos">Nombre:</td>
                          <td><input name="NOMBRE" type="text" class="fondo" id="NOMBRE" size="60"></td>
                          <td class="texto">Nombre de la aplicaci&oacute;n</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr> 
                          <td colspan="4" align="center" class="Boton">
									<ul>
									<li><img src="../../jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
									<li>
									<input name="Submit" type="submit" class="botones" value="Entrar">
									</li>
									<li><img src="../../jsp/auriga/images/boton_regarga.gif" width="27" height="22"></li>
									</ul>
						  </td>
                        </tr>
                      </table>
                    </form>
                  </div></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td width="3%">&nbsp;</td>
  </tr>
</table>
</body>
</html>
