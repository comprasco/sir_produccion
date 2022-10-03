package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadano;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.util.DateFormatUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author ppabon
 * Clase que representa el Imprimible de una constancia de inscripción de testamentos.
 */
public class ImprimibleInscripcionTestamento extends ImprimibleBase {
        private static final long serialVersionUID = 1L;
	protected int tituloMicro = ImprimibleConstantes.TITULO_MICRO;
	private Testamento testamento;
	private String turno;
        private boolean changetextforregistrador = false;
	private String nombreCirculo;
	private String fechaImpresion;
	private Date fecha;
        private boolean impFirma = false;
        private byte[] pixelesImagenFirmaRegistrador = null;
	private String nombreRegistrador = null;
	private String cargoRegistrador = null;
	private Usuario usuarioSIR = null;
	private Documento documento=null;

	/**
	 * Constructor de la clase.
	 * @param nota
	 */
	public ImprimibleInscripcionTestamento(Testamento testamento, Documento documento , String turno, String nombreCirculo, String fechaImpresion, Usuario usuarioSIR , Date fecha, String tipoImprimible) {
		super(tipoImprimible);
		this.testamento = testamento;
		this.turno = turno;
		this.documento = documento;
		this.usuarioSIR = usuarioSIR;
		this.nombreCirculo = nombreCirculo;
		this.fechaImpresion = fechaImpresion;
		this.fecha = fecha;
	}

        
	public boolean isChangetextforregistrador() {

        return changetextforregistrador;

	}

        public void setChangetextforregistrador(boolean changetextforregistrador) {
            this.changetextforregistrador = changetextforregistrador;
        }
        /**
         * Genera el imprimible.
         */
    public void generate(PageFormat pageFormat) {
        super.generate(pageFormat);
        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
        
        String sDoc = this.crearLineaDocumento(this.documento);
        this.imprimirLinea(ImprimibleConstantes.TITULO2, sDoc );
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        
        List testadores = testamento.getTestadores();
        for(int i=0; i<testadores.size(); i++){
            Ciudadano ciudadano = ((TestamentoCiudadano)testadores.get(i)).getCiudadano();
            this.imprimirTestador(ciudadano);
        }
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "El documento fue registrado en el libro de testamentos" , false);
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "TOMO :" , false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, testamento.getTomo());
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "NÚMERO ANOTACION :" , false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, testamento.getNumeroAnotaciones());
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "FECHA :" , false);
        String fechaActual  = this.formatear(fecha , "dd/MM/yyyy");
        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4,fechaActual);
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "REVOCA ESCRITURA :" , false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, testamento.getRevocaEscritura());
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "OBSERVACIONES");
        Vector lineas = this.getLineas(this.testamento.getObservacion());
        for (int i = 0; i < lineas.size(); i++) {
            String linea = (String) lineas.get(i);
            this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
        }
        
        if(this.isImpFirma()){
            this.imprimirFirma();
        }
        this.imprimirFirmaNotaDevolutiva();
    }
        /**
	 * Método que imprime la firma al final del documento
	 * @param imagenFirmaRegistrador
	 */
	private void imprimirFirmaDigital(BufferedImage imagenFirmaRegistrador) {
		int altura = imagenFirmaRegistrador.getHeight();

		int h = imagenFirmaRegistrador.getHeight();
		h = h + (ImprimibleConstantes.SEPARACION_LINE * 5);

		int hActual = this.getI();

		if((hActual + h) > ImprimibleConstantes.MAXIMO_VERTICAL){
			this.goPageEnd();
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		}

		this.imprimirLinea(this.tituloMicro, ImprimibleConstantes.SEPARADOR3_PEQ);
		this.imprimirGrafico(imagenFirmaRegistrador);

		int i = this.getI();
		i += altura;
		this.setI(i);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                String linea = "";
                if(this.isChangetextforregistrador()){
                         linea = "REGISTRADOR DE INSTRUMENTOS PUBLICOS";

                }else{
                             linea = "El registrador ";

                        if (this.cargoRegistrador != null) {
                                linea += (this.cargoRegistrador + " ");
                        }

                        if (this.nombreRegistrador != null) {
                                linea += this.nombreRegistrador;
                        }
                }
		this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
		this.imprimirLinea(this.tituloMicro, ImprimibleConstantes.SEPARADOR3_PEQ);
	}
            protected void imprimirFirma() {
                            //BufferedImage imagenFirmaRegistrador
                            BufferedImage imagen = null;

                            try {
                                    if (this.pixelesImagenFirmaRegistrador != null) {
                                            imagen = UIUtils.loadImage(this.pixelesImagenFirmaRegistrador);
                                    }
                            } catch (IOException e) {
                                    e.printStackTrace();
                            }

                            if (imagen == null) {
                                    super.imprimirFirma();
                            } else {
                                    this.imprimirFirmaDigital(imagen);
                            }
                    }
	/**
	 * Imprime el nombre del testador.
	 * @param sol La solicitud.
	 */
	protected void imprimirTestador(Ciudadano ciudadano) {
		String completo = this.getNombreCompletoConId(ciudadano);

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "TESTADOR :", false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, completo);

	}

	/**
	 * Imprime el encabezado de la nota devolutiva.
	 */
        
        public String getTurno() {
            return turno;
        }

        public void setTurno(String turno) {
            this.turno = turno;
        }
	protected void makeNewPage() {
		super.makeNewPage();

		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS DE " + nombreCirculo);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");

		String titulo = "CONSTANCIA DE INSCRIPCIÓN DE TESTAMENTOS";

		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, titulo);

		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 10, "");

		String fechaImp = this.fechaImpresion;

		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, fechaImp);

		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "Página: " + this.getNumberOfPages());

		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "TURNO: " + this.turno);

	}

	/**
	 * Funcion que recibe una cadena de texto que contiene caracteres especiales de fin de linea '\r' y '\n'
	 * indicando que se debe generar una nueva linea en un texto.
	 * @param line Linea con todo el texto.
	 * @return un vector de Strings con el mismo texto original, pero cada elemento del vector de respuesta
	 * representa un renglón en el texto.
	 */
	private Vector getLineas(String line) {

		String cad = "";
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != '\r')
				cad += line.charAt(i);
		}

		String cadenas[] = cad.split("\n");

		int tam = cadenas.length;
		String cad2 = "";

		Vector vectorCad = new Vector();
		for (int i = 0; i < tam; i++) {
			vectorCad.add(cadenas[i]);

		}

		return vectorCad;
	}

	/**
	 * Método que imprime la firma al final del documento
	 * @param imagenFirmaRegistrador
	 */
	private String crearLineaDocumento(Documento doc) {
		String tDoc = "";

		if(doc!=null){

			TipoDocumento tipoDoc = doc.getTipoDocumento();
			OficinaOrigen oficinaOrigen = doc.getOficinaOrigen();
			String oficinaInternacional = doc.getOficinaInternacional();

			tDoc =  (tipoDoc!=null&&tipoDoc.getNombre()!=null?tipoDoc.getNombre():" ") + " " +doc.getNumero() + " del "+ (doc.getFecha()!=null? this.formatear(doc.getFecha(),"dd/MM/yyyy"):"") ;

			if(oficinaInternacional!=null && !oficinaInternacional.equals("")){
				 tDoc = tDoc + " de "+  oficinaInternacional ;
			}else{

				if(oficinaOrigen!=null){

					Vereda vereda= oficinaOrigen.getVereda();
					String ubicacion = "";

					if(vereda!=null){
						Municipio mun = vereda.getMunicipio();

						if(mun!=null){
							Departamento depto = mun.getDepartamento();
							if(depto !=null){
								ubicacion = " de " + mun.getNombre() + " - " + depto.getNombre();
							}else{
								ubicacion = " de " + mun.getNombre();
							}

						}else{
							ubicacion = " de " + vereda.getNombre();
						}

					}

					tDoc =  tDoc + " de "+ (oficinaOrigen!=null&&oficinaOrigen.getNombre()!=null?oficinaOrigen.getNombre():" ") + ubicacion;
				}


			}


		}


		return tDoc!=null?tDoc.toUpperCase():tDoc;

	}


	public  String formatear(Date fecha, String formato){
		if (formato != null){
			return DateFormatUtil.format(formato, fecha);
		}
		else{
			return fecha.toString();
		}
	}

            public boolean isImpFirma() {
                return impFirma;
            }

            public void setImpFirma(boolean impFirma) {
                this.impFirma = impFirma;
            }

            public byte[] getPixelesImagenFirmaRegistrador() {
                return pixelesImagenFirmaRegistrador;
            }

            public void setPixelesImagenFirmaRegistrador(byte[] pixelesImagenFirmaRegistrador) {
                this.pixelesImagenFirmaRegistrador = pixelesImagenFirmaRegistrador;
            }
	protected void imprimirFirmaNotaDevolutiva() {

		String calificador = "FUNCIONARIO QUE EFECTUO EL REGISTRO";
		String registrador = "REGISTRADOR DE INSTRUMENTOS PUBLICOS";
		String linea ="==============================================================================================================";
		String finDocumento ="                      FIN DE ESTE DOCUMENTO";


		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");

		this.imprimirLinea(ImprimibleConstantes.TITULO2, calificador, false);
                if(!this.isChangetextforregistrador()){
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, 270 , registrador, true);
                }else{
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, 270 , "", true);
                }
		String nombreCalificador = "  " + (usuarioSIR!=null&&""+usuarioSIR.getIdUsuario()!=null?""+usuarioSIR.getIdUsuario():"");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, nombreCalificador);

		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,linea);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,finDocumento);

	}

	/**
	 * @return
	 */
	public String getCargoRegistrador() {
		return cargoRegistrador;
	}

	/**
	 * @return
	 */
	public String getNombreRegistrador() {
		return nombreRegistrador;
	}


	/**
	 * @param string
	 */
	public void setCargoRegistrador(String string) {
		cargoRegistrador = string;
	}

	/**
	 * @param string
	 */
	public void setNombreRegistrador(String string) {
		nombreRegistrador = string;
	}



}
