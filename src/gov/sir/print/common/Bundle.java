/*
 * Bundle.java
 *
 * Created on 16 de septiembre de 2004, 16:17
 */
package gov.sir.print.common;

import gov.sir.core.negocio.modelo.ImprimiblePdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Esta clase encapsula la transmision de imprimibles por la red.
 *
 * @author dcantor
 */
public class Bundle implements Serializable {

    private Imprimible imprimible;
    private ImprimiblePdf imprimiblePdf;
    private byte[] datosComprimidos;
    private String nombreImpresora;
    private static final long serialVersionUID = 1L;
    private int numeroCopias = 1;

    public Bundle(Imprimible imprimible) {
        this.imprimible = imprimible;
        this.numeroCopias = 1;
    }

    public Bundle(Imprimible imprimible, int n) {
        this.imprimible = imprimible;
        this.numeroCopias = n;
    }

    public Bundle(Imprimible imprimible, String nombreImpresora) {
        this.imprimible = imprimible;
        this.nombreImpresora = nombreImpresora;
        this.numeroCopias = 1;
    }

    public Bundle(Imprimible imprimible, String nombreImpresora, int n) {
        this.imprimible = imprimible;
        this.nombreImpresora = nombreImpresora;
        this.numeroCopias = n;
    }

    public Imprimible getImprimible() {
        return imprimible;
    }

    public String getNombreImpresora() {
        return nombreImpresora;
    }

    public int getNumeroCopias() {
        return numeroCopias;
    }

    public static ByteArrayOutputStream encode(Bundle b) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(b);
        oos.close();
        return baos;
    }

    public static Bundle decode(ByteArrayInputStream bais) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(bais);
        Bundle b = (Bundle) ois.readObject();
        return b;
    }

    /**
     * @param string
     */
    public void setNombreImpresora(String string) {
        nombreImpresora = string;
    }

    /**
     * @param i
     */
    public void setNumeroCopias(int i) {
        numeroCopias = i;
    }

    public ImprimiblePdf getImprimiblePdf() {
        return imprimiblePdf;
    }

    public void setImprimiblePdf(ImprimiblePdf imprimiblePdf) {
        this.imprimiblePdf = imprimiblePdf;
    }

    public byte[] getDatosComprimidos() {
        return datosComprimidos;
    }

    public void setDatosComprimidos(byte[] datosComprimidos) {
        this.datosComprimidos = datosComprimidos;
    }

    public void setImprimible(Imprimible imprimible) {
        this.imprimible = imprimible;
    }

}
