package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class NotaTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.Nota transferObject = new gov.sir.core.negocio.modelo.Nota();
        cache.put(enhancedObject, transferObject);

        // Time
        try {
            if (enhancedObject.getTime() != null) {
                transferObject.setTime(new Date(enhancedObject.getTime().getTime()));
            }
        } catch (Throwable th) {
        }

        // IdCirculo
        try {
            transferObject.setIdCirculo(enhancedObject.getIdCirculo());
        } catch (Throwable th) {
        }

        // IdFase
        try {
            transferObject.setIdFase(enhancedObject.getIdFase());
        } catch (Throwable th) {
        }

        // IdProceso
        try {
            transferObject.setIdProceso(enhancedObject.getIdProceso());
        } catch (Throwable th) {
        }

        // Descripcion
        try {
            transferObject.setDescripcion(enhancedObject.getDescripcion());
        } catch (Throwable th) {
        }

        // Usuario
        gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioEnh = null;
        try {
            usuarioEnh = enhancedObject.getUsuario();
        } catch (Throwable th) {
        }
        if (usuarioEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.Usuario objTo = (gov.sir.core.negocio.modelo.Usuario) cache.get(usuarioEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.Usuario) UsuarioTransferCopier.deepCopy(usuarioEnh, cache);
                }
                transferObject.setUsuario(objTo);
            }

        }

        // Anio
        try {
            transferObject.setAnio(enhancedObject.getAnio());
        } catch (Throwable th) {
        }

        // Turno
        gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced turnoEnh = null;
        try {
            turnoEnh = enhancedObject.getTurno();
        } catch (Throwable th) {
        }
        if (turnoEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.Turno objTo = (gov.sir.core.negocio.modelo.Turno) cache.get(turnoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.Turno) TurnoTransferCopier.deepCopy(turnoEnh, cache);
                }
                transferObject.setTurno(objTo);
            }

        }

        // IdNota
        try {
            transferObject.setIdNota(enhancedObject.getIdNota());
        } catch (Throwable th) {
        }

        // IdTurno
        try {
            transferObject.setIdTurno(enhancedObject.getIdTurno());
        } catch (Throwable th) {
        }

        // Tiponota
        gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced tiponotaEnh = null;
        try {
            tiponotaEnh = enhancedObject.getTiponota();
        } catch (Throwable th) {
        }
        if (tiponotaEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.TipoNota objTo = (gov.sir.core.negocio.modelo.TipoNota) cache.get(tiponotaEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.TipoNota) TipoNotaTransferCopier.deepCopy(tiponotaEnh, cache);
                }
                transferObject.setTiponota(objTo);
            }

        }

        // IdTurnoHistoria
        try {
            transferObject.setIdTurnoHistoria(enhancedObject.getIdTurnoHistoria());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.Nota transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced();
        cache.put(transferObject, enhancedObject);
        // Time
        try {
            if (transferObject.getTime() != null) {
                enhancedObject.setTime(new Date(transferObject.getTime().getTime()));
            }
        } catch (Throwable th) {
        }

        // IdCirculo
        try {
            enhancedObject.setIdCirculo(transferObject.getIdCirculo());
        } catch (Throwable th) {
        }

        // IdFase
        try {
            enhancedObject.setIdFase(transferObject.getIdFase());
        } catch (Throwable th) {
        }

        // IdProceso
        try {
            enhancedObject.setIdProceso(transferObject.getIdProceso());
        } catch (Throwable th) {
        }

        // Descripcion
        try {
            enhancedObject.setDescripcion(transferObject.getDescripcion());
        } catch (Throwable th) {
        }

        // Usuario
        gov.sir.core.negocio.modelo.Usuario usuario = null;
        try {
            usuario = (gov.sir.core.negocio.modelo.Usuario) transferObject.getUsuario();
        } catch (Throwable th) {
        }
        if (usuario != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced) cache.get(usuario);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced) UsuarioTransferCopier.deepCopy(usuario, cache);
                }
                enhancedObject.setUsuario(objEn);
            }

        }

        // Anio
        try {
            enhancedObject.setAnio(transferObject.getAnio());
        } catch (Throwable th) {
        }

        // Turno
        gov.sir.core.negocio.modelo.Turno turno = null;
        try {
            turno = (gov.sir.core.negocio.modelo.Turno) transferObject.getTurno();
        } catch (Throwable th) {
        }
        if (turno != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced) cache.get(turno);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced) TurnoTransferCopier.deepCopy(turno, cache);
                }
                enhancedObject.setTurno(objEn);
            }

        }

        // IdNota
        try {
            enhancedObject.setIdNota(transferObject.getIdNota());
        } catch (Throwable th) {
        }

        // IdTurno
        try {
            enhancedObject.setIdTurno(transferObject.getIdTurno());
        } catch (Throwable th) {
        }

        // Tiponota
        gov.sir.core.negocio.modelo.TipoNota tiponota = null;
        try {
            tiponota = (gov.sir.core.negocio.modelo.TipoNota) transferObject.getTiponota();
        } catch (Throwable th) {
        }
        if (tiponota != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced) cache.get(tiponota);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced) TipoNotaTransferCopier.deepCopy(tiponota, cache);
                }
                enhancedObject.setTiponota(objEn);
            }

        }

        // IdTurnoHistoria
        try {
            enhancedObject.setIdTurnoHistoria(transferObject.getIdTurnoHistoria());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}
