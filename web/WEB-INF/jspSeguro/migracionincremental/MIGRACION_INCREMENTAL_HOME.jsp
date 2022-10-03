<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<form action="migracionincremental.do" method="post" name="MIGRACION_INCREMENTAL" id="MIGRACION_INCREMENTAL">    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="migracionincremental.do" method="post">
      <input type="hidden" name="ACCION" id="ACCION" value="MIGRAR_FOLIO">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td width="35%">MATRICULA FOLIO</td>
          <td width="65%">
            <input type="text" name="MATRICULA"/>
          </td>
        </tr>
        <tr>
          <td width="35%">&nbsp;</td>
          <td width="65%">
            <input type="submit" name="submitFolio" value="MIGRAR FOLIO"/>
          </td>
        </tr>
      </table>
    </form>
    
<form action="migracionincremental.do" method="post">
      <input type="hidden" name="ACCION" id="ACCION" value="MIGRAR_TURNO">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td width="35%">N&Uacute;MERO DE TURNO</td>
          <td width="65%">
            <input type="text" name="NUM_TURNO"/>
          </td>
        </tr>
        <tr>
          <td width="35%">&nbsp;</td>
          <td width="65%">
            <input type="submit" name="submitTurno" value="MIGRAR TURNO"/>
          </td>
        </tr>
      </table>
    </form>
    
    
    </body>
</html>