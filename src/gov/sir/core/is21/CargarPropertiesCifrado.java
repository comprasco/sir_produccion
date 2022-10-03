// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 19/12/2007 10:06:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CargarPropertiesCifrado.java

package gov.sir.core.is21;

import gov.sir.core.negocio.modelo.util.Log;

import java.io.IOException;

import java.util.Iterator;
import java.util.Properties;

// Referenced classes of package com.is21.util.cifrar:
//            Encriptador

public class CargarPropertiesCifrado
{

	Properties encriptedProperties;
    byte key[];

	 
    private void $init$()
    {
        encriptedProperties = null;
        key = null;
    }

    public CargarPropertiesCifrado(Properties prop, String archivoPropiedadesLlave, String propiedadLlave, ClassLoader loader)
        throws IOException
    {
        $init$();
        init(prop, archivoPropiedadesLlave, propiedadLlave, loader);
    }

    public CargarPropertiesCifrado(String archivoPropiedades, ClassLoader loader)
        throws IOException
    {
        $init$();
        Properties encriptado = new Properties();
        java.io.InputStream is = loader.getResourceAsStream(archivoPropiedades);
        encriptado.load(is);
        String llaveArchivo = encriptado.getProperty("archivo.llave.desencriptar");
        init(encriptado, llaveArchivo, "llave.desencriptar", loader);
    }

    private void init(Properties prop, String archivoPropiedadesLlave, String propiedadLlave, ClassLoader loader)
        throws IOException
    {
        Properties llaves = new Properties();
        encriptedProperties = prop;
        if(archivoPropiedadesLlave == null || archivoPropiedadesLlave.length() == 0)
            return;
        java.io.InputStream in = loader.getResourceAsStream(archivoPropiedadesLlave);
        llaves.load(in);
        key = llaves.getProperty(propiedadLlave).getBytes();
        if(key == null)
            throw new RuntimeException("La propiedad: " + propiedadLlave + " no existe en el archivo de propiedades: " + archivoPropiedadesLlave);
        else
            return;
    }

    public Properties desencriptar()
    {
        Properties prop = new Properties();
        String llavenueva;
        String valornuevo;
        for(Iterator it = encriptedProperties.keySet().iterator(); it.hasNext(); prop.put(llavenueva, valornuevo))
        {
            String propiedad = (String)it.next();
            llavenueva = propiedad;
            valornuevo = encriptedProperties.getProperty(propiedad);
            Log.getInstance().debug(CargarPropertiesCifrado.class, " se carga propiedad... " + propiedad);
            if(propiedad.indexOf(".encripted") > 0)
            {
                llavenueva = propiedad.replaceAll(".encripted", "");
                Log.getInstance().debug(CargarPropertiesCifrado.class," se desencripta propiedad a... " + llavenueva);
                valornuevo = Encriptador.desEncriptar(valornuevo, key, "DES");
            }
        }

        return prop;
    }

 }