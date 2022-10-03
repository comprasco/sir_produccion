package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CiudadanoProhibicionPk;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Application identity objectid-class.
 */
public class CiudadanoProhibicionEnhancedPk implements java.io.Serializable {
	
	
	public final static int FECHA_ANO_DIA_MES = 4;
	public final static int FECHA_ANO_MES_DIA = 3;
	public final static int FECHA_DIA_MES_ANO = 1;
	public final static int FECHA_MES_DIA_ANO = 2;

	public Date fechaInicial;
    public String idCiudadano;
    public String idProhibicion;

    public CiudadanoProhibicionEnhancedPk() {
    }
    

	public CiudadanoProhibicionEnhancedPk(CiudadanoProhibicionPk id){
		idCiudadano = id.idCiudadano;
		idProhibicion = id.idProhibicion;
		fechaInicial = id.fechaInicial;
	}
            
	public CiudadanoProhibicionPk getCiudadanoProhibicionID(){
		CiudadanoProhibicionPk id = new CiudadanoProhibicionPk();
		id.idCiudadano = idCiudadano;
		id.idProhibicion = idProhibicion;
		id.fechaInicial = fechaInicial;
		return id;
	}


    public CiudadanoProhibicionEnhancedPk(String s) {
        int i, p = 0;
		i= s.indexOf('-', p);
		fechaInicial = stringToCalendar(s.substring(p, i), FECHA_ANO_MES_DIA).getTime();
        i= s.indexOf('-', p);
        idCiudadano = s.substring(p, i);
        p = i + 1;
        idProhibicion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CiudadanoProhibicionEnhancedPk)) return false;

        final CiudadanoProhibicionEnhancedPk id = (CiudadanoProhibicionEnhancedPk) o;
		if (this.fechaInicial != null ? !fechaInicial.equals(id.fechaInicial) : id.fechaInicial != null) return false;
        if (this.idCiudadano != null ? !idCiudadano.equals(id.idCiudadano) : id.idCiudadano != null) return false;
        if (this.idProhibicion != null ? !idProhibicion.equals(id.idProhibicion) : id.idProhibicion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
		result = 29 * result + (fechaInicial != null ? fechaInicial.hashCode() : 0);
        result = 29 * result + (idCiudadano != null ? idCiudadano.hashCode() : 0);
        result = 29 * result + (idProhibicion != null ? idProhibicion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
		buffer.append(fechaInicial);
		buffer.append('-');
        buffer.append(idCiudadano);
        buffer.append('-');
        buffer.append(idProhibicion);
        return buffer.toString();
    }
    

	public static Calendar stringToCalendar(String date, int inputDateFormat) {
		  Calendar c = Calendar.getInstance();
		  StringTokenizer fechaToken = null;
		  String fechaTemp = "";
		  String hora = "";
		  String ano = null;
		  String dia = null;
		  String mes = null;
		  int a = 0;
		  int m = 0;
		  int d = 0;
		  int hh = 0;
		  int mm = 0;

		  if (date == null) {
			  return c;
		  }

		  fechaTemp = date;

		  if (date.length() > 10) {
			  fechaTemp = date.substring(0, 10);
			  hora = date.substring(10);
		  }

		  if (fechaTemp.indexOf("/") > 0) {
			  fechaToken = new StringTokenizer(fechaTemp, "/");
		  } else if (fechaTemp.indexOf("-") > 0) {
			  fechaToken = new StringTokenizer(fechaTemp, "-");
		  } else {
			  return c;
		  }

		  if (inputDateFormat == FECHA_DIA_MES_ANO) {
			  dia = fechaToken.nextToken();
			  mes = fechaToken.nextToken();
			  ano = fechaToken.nextToken();
		  } else if (inputDateFormat == FECHA_MES_DIA_ANO) {
			  mes = fechaToken.nextToken();
			  dia = fechaToken.nextToken();
			  ano = fechaToken.nextToken();
		  } else if (inputDateFormat == FECHA_ANO_DIA_MES) {
			  ano = fechaToken.nextToken();
			  dia = fechaToken.nextToken();
			  mes = fechaToken.nextToken();
		  } else if (inputDateFormat == FECHA_ANO_MES_DIA) {
			  ano = fechaToken.nextToken();
			  mes = fechaToken.nextToken();
			  dia = fechaToken.nextToken();
		  } else {
			  return c;
		  }

		  try {
			  a = Integer.parseInt(ano.trim());
			  m = Integer.parseInt(mes.trim()) - 1;
			  d = Integer.parseInt(dia.trim());

			  if (hora.length() >= 5) {
				  fechaToken = new StringTokenizer(hora, ":");

				  if (fechaToken.countTokens() >= 2) {
					  hh = Integer.parseInt(fechaToken.nextToken().trim());
					  mm = Integer.parseInt(fechaToken.nextToken().trim());
				  }
			  }

			  c.set(a, m, d, hh, mm);
		  } catch (NumberFormatException e) {
			  e.printStackTrace();
			  throw new RuntimeException("The string: '" + date +
				  "'is not a valid date format", e);
		  }

		  return c;
	  }
}