package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Liquidacion;
import java.util.Date;

/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public abstract class LiquidacionEnhanced  extends Enhanced{

    private String idLiquidacion; // pk 
    private String idSolicitud; //pk
    private double valor;
    private SolicitudEnhanced solicitud;
    private PagoEnhanced pago;
    private UsuarioEnhanced usuario;
	private Date fecha;
	private String circulo;
        private String numMatAgr;

    public String getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(String idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public SolicitudEnhanced getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEnhanced solicitud) {
        this.solicitud = solicitud;
        this.setIdSolicitud(solicitud.getIdSolicitud());
    }
    
	public static LiquidacionEnhanced enhance(Liquidacion liquidacion){
		return (LiquidacionEnhanced) Enhanced.enhance(liquidacion);
	}

	/**
	 * @return
	 */
	public PagoEnhanced getPago() {
		return pago;
	}

	/**
	 * @param pago
	 */
	public void setPago(PagoEnhanced pago) {
		this.pago = pago;
	}


	/**
	 * @return
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param string
	 */
	public void setIdSolicitud(String string) {
		idSolicitud = string;
	}
	/**
	 * @return
	 */
	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	/**
	 * @param enhanced
	 */
	public void setUsuario(UsuarioEnhanced enhanced) {
		usuario = enhanced;
	}

	/**
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param date
	 */
	public void setFecha(Date date) {
		fecha = date;
	}

	public String getCirculo() {
		return circulo;
	}

	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

        public String getNumMatAgr() {
            return numMatAgr;
        }

        public void setNumMatAgr(String numMatAgr) {
            this.numMatAgr = numMatAgr;
        }
}