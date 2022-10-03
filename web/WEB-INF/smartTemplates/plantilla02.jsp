<%@ taglib uri="/smartags" prefix="smartview"  %>
<html>
<head>
<title><smartview:mostrar parametro="titulo"/></title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/jsp/estilos.css" type="text/css" />
</head>

<frameset frameborder="NO" 
          border="0" 
          framespacing="0" 
          cols="*" 
          rows="<smartview:mostrar parametro="topRows" />, 
                <smartview:mostrar parametro="mainRows" />, 
                <smartview:mostrar parametro="bottomRows" />"
>
  
  <frame name="topFrame"  scrolling="NO"  src="<%=request.getContextPath() %><smartview:mostrar parametro="topFrame" />" >
  <frame name="mainFrame" scrolling="YES" src="<%=request.getContextPath() %><smartview:mostrar parametro="mainFrame" />">
  <frame name="bottomFrame" scrolling="NO"  src="<%=request.getContextPath() %><smartview:mostrar parametro="bottomFrame" />" >
</frameset>

<noframes> 
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br/><br/><center><p>Esta pagina necesita marcos (frames): Por favor actualize su navegador o cambie las opciones del mismo para activar el soporte de marcos</p></center>
</body>
</noframes> 
</html>