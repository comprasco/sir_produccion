<%@page import="java.io.FileInputStream,java.io.File,java.io.OutputStream" %><%

File file = new File( request.getParameter("ARCHIVO" ) );


if( !file.exists() ) {
  System.out.println("el archivo no existe");
  file = new File(request.getContextPath()+"/jsp/images/sin_firma.gif");
}

System.out.println("el archivo existe ...");
FileInputStream fis = new FileInputStream(  file  ) ;

OutputStream output = response.getOutputStream();

byte[] buffer = new byte[1024];
int bytes_read = 0;

while( ( bytes_read = fis.read( buffer,0, buffer.length ) ) > 0 ) {
	output.write( buffer,0,bytes_read );
}
output.close();
%>
