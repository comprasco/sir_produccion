<%@ page 
contentType="text/html; charset=iso-8859-1" 
language="java" 
%>
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
    <td width="94%"><table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <tr> 
          <td width="100%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td><div align="left"> 
                    <form action="SAS_PROGRAMAS_CONCURRENTES.do" method="post">
                      <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr> 
                          <td width="147"><span class="texto">Seleccione una acci&oacute;n</span> 
                            <select name="ACCION" class="fondolista" id="ACCION">
                              <option value="CREAR">CREAR</option>
                              <option value="CONSULTAR">CONSULTAR</option>
                              <option value="ACTUALIZAR">ACTUALIZAR</option>
                              <option value="ELIMINAR">ELIMINAR</option>
                            </select></td>
                          <td width="180" class="titulo">PROGRAMA ID:</td>
                          <td width="188"><input name="PROGRAMA_ID" type="text" class="fondo" id="PROGRAMA_ID" size="10"></td>
                          <td width="234" class="texto">Identificador del programa</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td  class="titulos">&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td class="titulo">APLICACI&Oacute;N ID:</td>
                          <td><input name="APLICACION_ID" type="text" class="fondo" id="APLICACION_ID" size="10"></td>
                          <td class="texto">Identificador de la aplicaci&oacute;n</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td  class="titulos">&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td class="titulo">NOMBRE:</td>
                          <td><input name="NOMBRE" type="text" class="fondo" id="NOMBRE" size="30"></td>
                          <td class="texto">Nombre del programa</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td class="titulos">&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td class="titulo">DESCRIPCI&Oacute;N:</td>
                          <td><input name="DESCRIPCION" type="text" class="fondo" id="DESCRIPCION" size="60"></td>
                          <td class="texto">Descripci&oacute;n del programa</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td class="titulos">&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td class="titulo">CLASE:</td>
                          <td><input name="CLASE" type="text" class="fondo" id="CLASE" size="60"></td>
                          <td class="texto">Clase del programa</td>
                        </tr>
                        <tr> 
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr align="center"> 
						  <td colspan="4" class="Boton">                             
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
  </tr>
</table>
</body>
</html>
