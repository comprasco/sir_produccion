package gov.sir.core.negocio.modelo.imprimibles.base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;

public class HojaImprimiblePDF extends HojaImprimible {

	//se sobrecarga este método para evitar problema de
	// relleno negro en imprimible de correcciones
	/**
     * Imprime el logo de la página.
     * @param graphics2D
     * @param page
     */
    private static final long serialVersionUID = 1L;
    protected void imprimirMargen(Graphics2D graphics2D, PageFormat page) {
        double x;
        double y;
        double w;
        double h;
        x = ImprimibleConstantes.MARGEN_IZQ / 2;
        y = ImprimibleConstantes.MARGEN_IZQ / 2;
        w = page.getImageableWidth() - 5;
        h = page.getImageableHeight() - 5;
        System.out.println("******IMPRIMIENDO MARGEN -x:"+x+"-y:"+y+"-w:"+w+"h:"+h);

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect((int) x, (int) y, (int) w, (int) h);
        graphics2D.clearRect((int) x+1, (int) y+1, (int) w-1, (int) h-1);
    }
}
