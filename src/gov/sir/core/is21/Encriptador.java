// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 19/12/2007 10:17:01
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Encriptador.java

package gov.sir.core.is21;

import gov.sir.core.negocio.modelo.util.Log;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encriptador
{
	
    public static String encriptar(String texto, byte llaveAplicar[], String algoritmo)
    {
        String resultado = "";
        try
        {
            Cipher c1 = Cipher.getInstance(algoritmo);
            java.security.spec.KeySpec desKeySpec = new DESKeySpec(llaveAplicar);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algoritmo);
            java.security.Key llave = keyFactory.generateSecret(desKeySpec);
            c1.init(1, llave);
            byte encriptado[] = c1.doFinal(texto.getBytes());
            BASE64Encoder base = new BASE64Encoder();
            resultado = base.encode(encriptado);
        }
        catch(Exception e)
        {
        	Log.getInstance().error(Encriptador.class,"Ocurri\363 un error al encriptar el texto", e);
        	Log.getInstance().error(Encriptador.class,e);
            e.printStackTrace();
            throw new RuntimeException(" Ocurri\363 un error al encriptar el texto. " + e.getMessage());
        }
        return resultado;
    }

    public static String desEncriptar(String texto, byte llaveAplicar[], String algoritmo)
    {
        String resultado = "";
        try
        {
            Cipher c1 = Cipher.getInstance(algoritmo);
            java.security.spec.KeySpec desKeySpec = new DESKeySpec(llaveAplicar);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algoritmo);
            java.security.Key llave = keyFactory.generateSecret(desKeySpec);
            c1.init(2, llave);
            BASE64Decoder base = new BASE64Decoder();
            byte encriptado[] = base.decodeBuffer(texto);
            resultado = new String(c1.doFinal(encriptado));
        }
        catch(Exception e)
        {
        	Log.getInstance().error(Encriptador.class,"Ocurri\363 un error al encriptar el texto", e);
        	Log.getInstance().error(Encriptador.class,e);
            e.printStackTrace();
            throw new RuntimeException(" Ocurri\363 un error al desencriptar el texto. " + e.getMessage());
        }
        return resultado;
    }

    public Encriptador()
    {
    }
}