package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class UsuarioTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.Usuario transferObject = new gov.sir.core.negocio.modelo.Usuario();
        cache.put(enhancedObject, transferObject);

        // Nombre
        try {
            transferObject.setNombre(enhancedObject.getNombre());
        } catch (Throwable th) {
        }

        // Activo
        try {
            transferObject.setActivo(enhancedObject.isActivo());
        } catch (Throwable th) {
        }

        // Apellido1
        try {
            transferObject.setApellido1(enhancedObject.getApellido1());
        } catch (Throwable th) {
        }

        // Apellido2
        try {
            transferObject.setApellido2(enhancedObject.getApellido2());
        } catch (Throwable th) {
        }

        /**
         * @author Cesar Ramírez
         * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
         * tipoIdentificacion - numIdentificacion
		 *
         */
        // tipoIdentificacion
        try {
            transferObject.setTipoIdentificacion(enhancedObject.getTipoIdentificacion());
        } catch (Throwable th) {
        }

        // numIdentificacion
        try {
            transferObject.setNumIdentificacion(enhancedObject.getNumIdentificacion());
        } catch (Throwable th) {
        }

        // Reparto
        List reparto = null;
        try {
            reparto = enhancedObject.getRepartos();
        } catch (Throwable th) {
        }
        if (reparto != null) {
            for (Iterator itera = reparto.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced repartoEnh = (gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.Reparto objTo = (gov.sir.core.negocio.modelo.Reparto) cache.get(repartoEnh);
                    if (objTo == null) {
                        objTo = (gov.sir.core.negocio.modelo.Reparto) RepartoTransferCopier.deepCopy(repartoEnh, cache);
                    }
                    transferObject.addReparto(objTo);
                }
            }
        }

        // IdUsuario
        try {
            transferObject.setIdUsuario(enhancedObject.getIdUsuario());
        } catch (Throwable th) {
        }

        // Password
        try {
            transferObject.setPassword(enhancedObject.getPassword());
        } catch (Throwable th) {
        }

        // Username
        try {
            transferObject.setUsername(enhancedObject.getUsername());
        } catch (Throwable th) {
        }

        // LoginID
        try {
            transferObject.setLoginID(enhancedObject.getLoginID());
        } catch (Throwable th) {
        }

        // LlavesBloqueo
        List llavesBloqueo = null;
        try {
            llavesBloqueo = enhancedObject.getLlavesBloqueos();
        } catch (Throwable th) {
        }
        if (llavesBloqueo != null) {
            for (Iterator itera = llavesBloqueo.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced llavesBloqueoEnh = (gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.LlaveBloqueo objTo = (gov.sir.core.negocio.modelo.LlaveBloqueo) cache.get(llavesBloqueoEnh);
                    if (objTo == null) {
                        objTo = (gov.sir.core.negocio.modelo.LlaveBloqueo) LlaveBloqueoTransferCopier.deepCopy(llavesBloqueoEnh, cache);
                    }
                    transferObject.addLlavesBloqueo(objTo);
                }
            }
        }

        // UsuarioCirculo
        List usuarioCirculo = null;
        try {
            usuarioCirculo = enhancedObject.getUsuarioCirculos();
        } catch (Throwable th) {
        }
        if (usuarioCirculo != null) {
            for (Iterator itera = usuarioCirculo.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced usuarioCirculoEnh = (gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.UsuarioCirculo objTo = (gov.sir.core.negocio.modelo.UsuarioCirculo) cache.get(usuarioCirculoEnh);
                    if (objTo == null) {
                        objTo = (gov.sir.core.negocio.modelo.UsuarioCirculo) UsuarioCirculoTransferCopier.deepCopy(usuarioCirculoEnh, cache);
                    }
                    transferObject.addUsuarioCirculo(objTo);
                }
            }
        }

        // SubtiposAtencion
        List subtiposAtencion = null;
        try {
            subtiposAtencion = enhancedObject.getSubtiposAtencions();
        } catch (Throwable th) {
        }
        if (subtiposAtencion != null) {
            for (Iterator itera = subtiposAtencion.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced subtiposAtencionEnh = (gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion objTo = (gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion) cache.get(subtiposAtencionEnh);
                    if (objTo == null) {
                        objTo = (gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion) UsuarioSubtipoAtencionTransferCopier.deepCopy(subtiposAtencionEnh, cache);
                    }
                    transferObject.addSubtiposAtencion(objTo);
                }
            }
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.Usuario transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced();
        cache.put(transferObject, enhancedObject);
        // Nombre
        try {
            enhancedObject.setNombre(transferObject.getNombre());
        } catch (Throwable th) {
        }

        // Activo
        try {
            enhancedObject.setActivo(transferObject.isActivo());
        } catch (Throwable th) {
        }

        // Apellido1
        try {
            enhancedObject.setApellido1(transferObject.getApellido1());
        } catch (Throwable th) {
        }

        // Apellido2
        try {
            enhancedObject.setApellido2(transferObject.getApellido2());
        } catch (Throwable th) {
        }

        /**
         * @author Cesar Ramírez
         * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
         * tipoIdentificacion - numIdentificacion
		 *
         */
        // tipoIdentificacion
        try {
            enhancedObject.setTipoIdentificacion(transferObject.getTipoIdentificacion());
        } catch (Throwable th) {
        }

        // numIdentificacion
        try {
            enhancedObject.setNumIdentificacion(transferObject.getNumIdentificacion());
        } catch (Throwable th) {
        }

        // Reparto
        List reparto = null;
        try {
            reparto = transferObject.getRepartos();
        } catch (Throwable th) {
        }
        if (reparto != null) {
            for (Iterator itera = reparto.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.Reparto repartoto = (gov.sir.core.negocio.modelo.Reparto) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced) cache.get(repartoto);
                    if (objEn == null) {
                        objEn = (gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced) RepartoTransferCopier.deepCopy(repartoto, cache);
                    }
                    enhancedObject.addReparto(objEn);
                }
            }
        }

        // IdUsuario
        try {
            enhancedObject.setIdUsuario(transferObject.getIdUsuario());
        } catch (Throwable th) {
        }

        // Password
        try {
            enhancedObject.setPassword(transferObject.getPassword());
        } catch (Throwable th) {
        }

        // Username
        try {
            enhancedObject.setUsername(transferObject.getUsername());
        } catch (Throwable th) {
        }

        // LoginID
        try {
            enhancedObject.setLoginID(transferObject.getLoginID());
        } catch (Throwable th) {
        }

        // LlavesBloqueo
        List llavesBloqueo = null;
        try {
            llavesBloqueo = transferObject.getLlavesBloqueos();
        } catch (Throwable th) {
        }
        if (llavesBloqueo != null) {
            for (Iterator itera = llavesBloqueo.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.LlaveBloqueo llavesBloqueoto = (gov.sir.core.negocio.modelo.LlaveBloqueo) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced) cache.get(llavesBloqueoto);
                    if (objEn == null) {
                        objEn = (gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced) LlaveBloqueoTransferCopier.deepCopy(llavesBloqueoto, cache);
                    }
                    enhancedObject.addLlavesBloqueo(objEn);
                }
            }
        }

        // UsuarioCirculo
        List usuarioCirculo = null;
        try {
            usuarioCirculo = transferObject.getUsuarioCirculos();
        } catch (Throwable th) {
        }
        if (usuarioCirculo != null) {
            for (Iterator itera = usuarioCirculo.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.UsuarioCirculo usuarioCirculoto = (gov.sir.core.negocio.modelo.UsuarioCirculo) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced) cache.get(usuarioCirculoto);
                    if (objEn == null) {
                        objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced) UsuarioCirculoTransferCopier.deepCopy(usuarioCirculoto, cache);
                    }
                    enhancedObject.addUsuarioCirculo(objEn);
                }
            }
        }

        // SubtiposAtencion
        List subtiposAtencion = null;
        try {
            subtiposAtencion = transferObject.getSubtiposAtencions();
        } catch (Throwable th) {
        }
        if (subtiposAtencion != null) {
            for (Iterator itera = subtiposAtencion.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion subtiposAtencionto = (gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced) cache.get(subtiposAtencionto);
                    if (objEn == null) {
                        objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced) UsuarioSubtipoAtencionTransferCopier.deepCopy(subtiposAtencionto, cache);
                    }
                    enhancedObject.addSubtiposAtencion(objEn);
                }
            }
        }

        return enhancedObject;
    }
}
