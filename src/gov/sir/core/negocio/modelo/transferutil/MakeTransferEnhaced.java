package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class MakeTransferEnhaced {

    public static Object makeTransfer(Object source) {
        Object rta = null;
        java.util.HashMap cache = new java.util.HashMap();
        if (source == null) {
            return null;
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced) source;
            rta = CuentasBancariasTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced) source;
            rta = CanalesRecaudoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhanced) source;
            rta = ReproduccionSellosTransferCopier.deepCopy(obj, cache);
        }else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhanced) source;
            rta = CamposCapturaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhanced) source;
            rta = FormaPagoCamposTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced) source;
            rta = ArchivosJustificaTransferCopier.deepCopy(obj, cache);
        }else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced) source;
            rta = TramiteSuspensionTransferCopier.deepCopy(obj, cache);
        }else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.JustificaAdmEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.JustificaAdmEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.JustificaAdmEnhanced) source;
            rta = JustificaAdmTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced) source;
            rta = JustificaTiposTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced) source;
            rta = ZonaRegistralTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced) source;
            rta = ZonaNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WfProcesosEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WfProcesosEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WfProcesosEnhanced) source;
            rta = WfProcesosTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced) source;
            rta = WebSegregacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced) source;
            rta = WebFolioNuevoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced) source;
            rta = WebFolioHeredadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced) source;
            rta = WebFolioEnglobeTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebFolioDerivadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebFolioDerivadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebFolioDerivadoEnhanced) source;
            rta = WebFolioDerivadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced) source;
            rta = WebEnglobeTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced) source;
            rta = WebDocumentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced) source;
            rta = WebDireccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced) source;
            rta = WebCiudadanoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced) source;
            rta = WebAnotaHeredaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced) source;
            rta = WebAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced) source;
            rta = VeredaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaDetalleEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaDetalleEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaDetalleEnhanced) source;
            rta = ValidacionNotaDetalleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced) source;
            rta = ValidacionNotaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced) source;
            rta = ValidacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced) source;
            rta = UsuarioSubtipoAtencionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced) source;
            rta = UsuarioCirculoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced) source;
            rta = UsuarioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced) source;
            rta = TurnoSalvedadFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced) source;
            rta = TurnoSalvedadAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced) source;
            rta = TurnoHistoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoFolioTramiteMigEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoFolioTramiteMigEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoFolioTramiteMigEnhanced) source;
            rta = TurnoFolioTramiteMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoFolioMigEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoFolioMigEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoFolioMigEnhanced) source;
            rta = TurnoFolioMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced) source;
            rta = TurnoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced) source;
            rta = TurnoEjecucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoDerivadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoDerivadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoDerivadoEnhanced) source;
            rta = TurnoDerivadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced) source;
            rta = TurnoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced) source;
            rta = TurnoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced) source;
            rta = TrasladoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced) source;
            rta = TipoTarifaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced) source;
            rta = TipoRelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced) source;
            rta = TipoRecursoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced) source;
            rta = TipoRecepcionPeticionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced) source;
            rta = TipoPredioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced) source;
            rta = TipoPantallaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced) source;
            rta = TipoPagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced) source;
            rta = TipoOficioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced) source;
            rta = TipoOficinaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced) source;
            rta = TipoNotaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced) source;
            rta = TipoImpuestoTransferCopier.deepCopy(obj, cache);
        } /**
         * @author Fernando Padilla Velez
         * @change Modificado para el caso MANTIS 135_141_Impuesto Meta. en caso
         * de que el objeto sea de la instancia
         * gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced
         * realizara ca copia del objeto.
         */
        else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced) source;
            rta = TipoImpuestoCirculoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoImprimibleEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoImprimibleEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoImprimibleEnhanced) source;
            rta = TipoImprimibleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoHorarioNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoHorarioNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoHorarioNotarialEnhanced) source;
            rta = TipoHorarioNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced) source;
            rta = TipoFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced) source;
            rta = TipoDocumentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced) source;
            rta = TipoDerechoRegTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced) source;
            rta = TipoConsultaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced) source;
            rta = TipoCertificadoValidacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced) source;
            rta = TipoCertificadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced) source;
            rta = TipoCalculoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced) source;
            rta = TipoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced) source;
            rta = TipoActoDerechoRegistralTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced) source;
            rta = TipoActoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhanced) source;
            rta = TextoImprimibleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TextoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TextoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TextoEnhanced) source;
            rta = TextoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced) source;
            rta = TestamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TarifaDepartamentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TarifaDepartamentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TarifaDepartamentoEnhanced) source;
            rta = TarifaDepartamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced) source;
            rta = TarifaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced) source;
            rta = SucursalBancoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced) source;
            rta = SubtipoSolicitudTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced) source;
            rta = SubtipoAtencionTipoActoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced) source;
            rta = SubtipoAtencionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced) source;
            rta = SubAtencionSubSolicitudTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced) source;
            rta = SolicitudVinculadaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced) source;
            rta = SolicitudRestitucionRepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced) source;
            rta = SolicitudRepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced) source;
            rta = SolicitudRegistroTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudPermisoCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudPermisoCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudPermisoCorreccionEnhanced) source;
            rta = SolicitudPermisoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced) source;
            rta = SolicitudFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhanced) source;
            rta = SolicitudFolioMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced) source;
            rta = SolicitudFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced) source;
            rta = SolicitudDevolucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced) source;
            rta = SolicitudCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced) source;
            rta = SolicitudConsultaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced) source;
            rta = SolicitudCheckedItemTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced) source;
            rta = SolicitudCertificadoMasivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced) source;
            rta = SolicitudCertificadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced) source;
            rta = SolicitudAsociadaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced) source;
            rta = SolicitudAntiguoSistemaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced) source;
            rta = SolicitudTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced) source;
            rta = SolCheckedItemDevTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced) source;
            rta = SecuenciasTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced) source;
            rta = SalvedadFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced) source;
            rta = SalvedadAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced) source;
            rta = RolSIRTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced) source;
            rta = RolPantallaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced) source;
            rta = ResultadoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced) source;
            rta = ResultadoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced) source;
            rta = RepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced) source;
            rta = RepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.RelacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.RelacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.RelacionEnhanced) source;
            rta = RelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced) source;
            rta = RecursoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced) source;
            rta = RangoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced) source;
            rta = ProhibicionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhanced) source;
            rta = ProcesoRepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced) source;
            rta = ProcesoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.PlantillaPertenenciaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.PlantillaPertenenciaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.PlantillaPertenenciaEnhanced) source;
            rta = PlantillaPertenenciaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.PermisoCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.PermisoCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.PermisoCorreccionEnhanced) source;
            rta = PermisoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced) source;
            rta = PantallaAdministrativaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced) source;
            rta = DocumentoPagoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced) source;
            rta = PagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced) source;
            rta = OtorganteNaturalTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced) source;
            rta = OPLookupTypesTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced) source;
            rta = OPLookupCodesTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced) source;
            rta = OficioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced) source;
            rta = OficinaOrigenTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced) source;
            rta = OficinaCategoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.NumeroMatriculaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.NumeroMatriculaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.NumeroMatriculaEnhanced) source;
            rta = NumeroMatriculaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.NotaActuacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.NotaActuacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.NotaActuacionEnhanced) source;
            rta = NotaActuacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced) source;
            rta = NotaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced) source;
            rta = NaturalezaJuridicaEntidadTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced) source;
            rta = NaturalezaJuridicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced) source;
            rta = MunicipioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced) source;
            rta = MinutaEntidadPublicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced) source;
            rta = MinutaAccionNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced) source;
            rta = MinutaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced) source;
            rta = LlaveBloqueoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced) source;
            rta = LiquidacionTurnoRestitucionRepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced) source;
            rta = LiquidacionTurnoRepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced) source;
            rta = LiquidacionTurnoRegistroTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced) source;
            rta = LiquidacionTurnoFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced) source;
            rta = LiquidacionTurnoDevolucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced) source;
            rta = LiquidacionTurnoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced) source;
            rta = LiquidacionTurnoConsultaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced) source;
            rta = LiquidacionTurnoCertificadoMasivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced) source;
            rta = LiquidacionTurnoCertificadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.InicioProcesosEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.InicioProcesosEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.InicioProcesosEnhanced) source;
            rta = InicioProcesosTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ImprimiblePdfEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ImprimiblePdfEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ImprimiblePdfEnhanced) source;
            rta = ImprimiblePdfTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced) source;
            rta = ImprimibleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhanced) source;
            rta = ImpresionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.HorarioNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.HorarioNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.HorarioNotarialEnhanced) source;
            rta = HorarioNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced) source;
            rta = GrupoNaturalezaJuridicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FolioValidoMigEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FolioValidoMigEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FolioValidoMigEnhanced) source;
            rta = FolioValidoMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced) source;
            rta = FolioDerivadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced) source;
            rta = FolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced) source;
            rta = FirmaRegistradorTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FaseAlertaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FaseAlertaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FaseAlertaEnhanced) source;
            rta = FaseAlertaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced) source;
            rta = FaseTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced) source;
            rta = EstadoHistoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced) source;
            rta = EstadoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced) source;
            rta = EstadoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.EstacionReciboAuditoriaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.EstacionReciboAuditoriaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.EstacionReciboAuditoriaEnhanced) source;
            rta = EstacionReciboAuditoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced) source;
            rta = EstacionReciboTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced) source;
            rta = EntidadPublicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced) source;
            rta = EjeTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced) source;
            rta = DominioNaturalezaJuridicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced) source;
            rta = DocumentoFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced) source;
            rta = DocumentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced) source;
            rta = DocPagoTimbreConstanciaLiquidacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoPseEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoPseEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoPseEnhanced) source;
            rta = DocPagoPseTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced) source;
            rta = DocPagoHeredadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced) source;
            rta = DocPagoEfectivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced) source;
            rta = DocPagoConvenioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced) source;
            rta = DocPagoGeneralTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced) source;
            rta = DocPagoConsignacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced) source;
            rta = DocPagoChequePosfechadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced) source;
            rta = DocPagoChequeGerenciaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced) source;
            rta = DocPagoChequeTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced) source;
            rta = DocPagoTimbreConstanciaLiquidacionCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoPseCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoPseCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoPseCorreccionEnhanced) source;
            rta = DocPagoPseCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoCorreccionEnhanced) source;
            rta = DocPagoHeredadoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoCorreccionEnhanced) source;
            rta = DocPagoEfectivoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioCorreccionEnhanced) source;
            rta = DocPagoConvenioCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralCorreccionEnhanced) source;
            rta = DocPagoGeneralCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionCorreccionEnhanced) source;
            rta = DocPagoConsignacionCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoCorreccionEnhanced) source;
            rta = DocPagoChequePosfechadoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaCorreccionEnhanced) source;
            rta = DocPagoChequeGerenciaCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeCorreccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeCorreccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeCorreccionEnhanced) source;
            rta = DocPagoChequeCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced) source;
            rta = DireccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced) source;
            rta = DepartamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced) source;
            rta = DatosAntiguoSistemaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced) source;
            rta = ComplementacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced) source;
            rta = CiudadanoProhibicionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced) source;
            rta = CiudadanoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoTrasladoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoTrasladoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoTrasladoEnhanced) source;
            rta = CirculoTrasladoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced) source;
            rta = CirculoTipoPagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhanced) source;
            rta = CirculoRelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced) source;
            rta = CirculoProcesoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced) source;
            rta = CirculoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoImpresoraEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoImpresoraEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoImpresoraEnhanced) source;
            rta = CirculoImpresoraTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced) source;
            rta = CirculoFestivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced) source;
            rta = CirculoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced) source;
            rta = CheckItemDevTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced) source;
            rta = CheckItemTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced) source;
            rta = CausalRestitucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced) source;
            rta = CategoriaNotariaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced) source;
            rta = CategoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced) source;
            rta = CancelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.CambioMatriculaCerEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.CambioMatriculaCerEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.CambioMatriculaCerEnhanced) source;
            rta = CambioMatriculaCerTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced) source;
            rta = BusquedaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced) source;
            rta = BloqueoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced) source;
            rta = BancoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AumentoReciboEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AumentoReciboEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AumentoReciboEnhanced) source;
            rta = AumentoReciboTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhanced) source;
            rta = AuditoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced) source;
            rta = AplicacionPagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced) source;
            rta = AnotacionCiudadanoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced) source;
            rta = AnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AlertaEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AlertaEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AlertaEnhanced) source;
            rta = AlertaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced) source;
            rta = AlcanceGeograficoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhanced) source;
            rta = ActoresRepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced) source;
            rta = ActoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced) source;
            rta = AccionNotarialTransferCopier.deepCopy(obj, cache);
        } /*
                                            * @author      : Carlos Mario Torres Urina
                                            * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
         */ else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced) source;
            rta = FundamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced) source;
            rta = TipoFundamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced) source;
            rta = TrasladoFundamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced) source;
            rta = TrasladoDatosTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhanced) {
            gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhanced obj = (gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhanced) source;
            rta = BancosXCirculoTransferCopier.deepCopy(obj, cache);
        }
        return rta;
    }

    /**
     * Obtiene un objeto enhanced a partir de un objeto transfer
     */
    public static Object makeEnhanced(Object source) {
        Object rta = null;
        java.util.HashMap cache = new java.util.HashMap();
        if (source == null) {
            return null;
        } else if (source instanceof gov.sir.core.negocio.modelo.CuentasBancarias) {
            gov.sir.core.negocio.modelo.CuentasBancarias obj = (gov.sir.core.negocio.modelo.CuentasBancarias) source;
            rta = CuentasBancariasTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CanalesRecaudo) {
            gov.sir.core.negocio.modelo.CanalesRecaudo obj = (gov.sir.core.negocio.modelo.CanalesRecaudo) source;
            rta = CanalesRecaudoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CamposCaptura) {
            gov.sir.core.negocio.modelo.CamposCaptura obj = (gov.sir.core.negocio.modelo.CamposCaptura) source;
            rta = CamposCapturaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.FormaPagoCampos) {
            gov.sir.core.negocio.modelo.FormaPagoCampos obj = (gov.sir.core.negocio.modelo.FormaPagoCampos) source;
            rta = FormaPagoCamposTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.JustificaTipos) {
            gov.sir.core.negocio.modelo.JustificaTipos obj = (gov.sir.core.negocio.modelo.JustificaTipos) source;
            rta = JustificaTiposTransferCopier.deepCopy(obj, cache);
        }else if (source instanceof gov.sir.core.negocio.modelo.TramiteSuspension) {
            gov.sir.core.negocio.modelo.TramiteSuspension obj = (gov.sir.core.negocio.modelo.TramiteSuspension) source;
            rta = TramiteSuspensionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.JustificaAdm) {
            gov.sir.core.negocio.modelo.JustificaAdm obj = (gov.sir.core.negocio.modelo.JustificaAdm) source;
            rta = JustificaAdmTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ReproduccionSellos) {
            gov.sir.core.negocio.modelo.ReproduccionSellos obj = (gov.sir.core.negocio.modelo.ReproduccionSellos) source;
            rta = ReproduccionSellosTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ArchivosJustifica) {
            gov.sir.core.negocio.modelo.ArchivosJustifica obj = (gov.sir.core.negocio.modelo.ArchivosJustifica) source;
            rta = ArchivosJustificaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ZonaRegistral) {
            gov.sir.core.negocio.modelo.ZonaRegistral obj = (gov.sir.core.negocio.modelo.ZonaRegistral) source;
            rta = ZonaRegistralTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ZonaNotarial) {
            gov.sir.core.negocio.modelo.ZonaNotarial obj = (gov.sir.core.negocio.modelo.ZonaNotarial) source;
            rta = ZonaNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WfProcesos) {
            gov.sir.core.negocio.modelo.WfProcesos obj = (gov.sir.core.negocio.modelo.WfProcesos) source;
            rta = WfProcesosTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebSegregacion) {
            gov.sir.core.negocio.modelo.WebSegregacion obj = (gov.sir.core.negocio.modelo.WebSegregacion) source;
            rta = WebSegregacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebFolioNuevo) {
            gov.sir.core.negocio.modelo.WebFolioNuevo obj = (gov.sir.core.negocio.modelo.WebFolioNuevo) source;
            rta = WebFolioNuevoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebFolioHeredado) {
            gov.sir.core.negocio.modelo.WebFolioHeredado obj = (gov.sir.core.negocio.modelo.WebFolioHeredado) source;
            rta = WebFolioHeredadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebFolioEnglobe) {
            gov.sir.core.negocio.modelo.WebFolioEnglobe obj = (gov.sir.core.negocio.modelo.WebFolioEnglobe) source;
            rta = WebFolioEnglobeTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebFolioDerivado) {
            gov.sir.core.negocio.modelo.WebFolioDerivado obj = (gov.sir.core.negocio.modelo.WebFolioDerivado) source;
            rta = WebFolioDerivadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebEnglobe) {
            gov.sir.core.negocio.modelo.WebEnglobe obj = (gov.sir.core.negocio.modelo.WebEnglobe) source;
            rta = WebEnglobeTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebDocumento) {
            gov.sir.core.negocio.modelo.WebDocumento obj = (gov.sir.core.negocio.modelo.WebDocumento) source;
            rta = WebDocumentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebDireccion) {
            gov.sir.core.negocio.modelo.WebDireccion obj = (gov.sir.core.negocio.modelo.WebDireccion) source;
            rta = WebDireccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebCiudadano) {
            gov.sir.core.negocio.modelo.WebCiudadano obj = (gov.sir.core.negocio.modelo.WebCiudadano) source;
            rta = WebCiudadanoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebAnotaHereda) {
            gov.sir.core.negocio.modelo.WebAnotaHereda obj = (gov.sir.core.negocio.modelo.WebAnotaHereda) source;
            rta = WebAnotaHeredaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.WebAnotacion) {
            gov.sir.core.negocio.modelo.WebAnotacion obj = (gov.sir.core.negocio.modelo.WebAnotacion) source;
            rta = WebAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Vereda) {
            gov.sir.core.negocio.modelo.Vereda obj = (gov.sir.core.negocio.modelo.Vereda) source;
            rta = VeredaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ValidacionNotaDetalle) {
            gov.sir.core.negocio.modelo.ValidacionNotaDetalle obj = (gov.sir.core.negocio.modelo.ValidacionNotaDetalle) source;
            rta = ValidacionNotaDetalleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ValidacionNota) {
            gov.sir.core.negocio.modelo.ValidacionNota obj = (gov.sir.core.negocio.modelo.ValidacionNota) source;
            rta = ValidacionNotaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Validacion) {
            gov.sir.core.negocio.modelo.Validacion obj = (gov.sir.core.negocio.modelo.Validacion) source;
            rta = ValidacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion) {
            gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion obj = (gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion) source;
            rta = UsuarioSubtipoAtencionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.UsuarioCirculo) {
            gov.sir.core.negocio.modelo.UsuarioCirculo obj = (gov.sir.core.negocio.modelo.UsuarioCirculo) source;
            rta = UsuarioCirculoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Usuario) {
            gov.sir.core.negocio.modelo.Usuario obj = (gov.sir.core.negocio.modelo.Usuario) source;
            rta = UsuarioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoSalvedadFolio) {
            gov.sir.core.negocio.modelo.TurnoSalvedadFolio obj = (gov.sir.core.negocio.modelo.TurnoSalvedadFolio) source;
            rta = TurnoSalvedadFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion) {
            gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion obj = (gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion) source;
            rta = TurnoSalvedadAnotacionTransferCopier.deepCopy(obj, cache);
        }  else if (source instanceof gov.sir.core.negocio.modelo.DocumentoPagoCorreccion) {
            gov.sir.core.negocio.modelo.DocumentoPagoCorreccion obj = (gov.sir.core.negocio.modelo.DocumentoPagoCorreccion) source;
            rta = DocumentoPagoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoHistoria) {
            gov.sir.core.negocio.modelo.TurnoHistoria obj = (gov.sir.core.negocio.modelo.TurnoHistoria) source;
            rta = TurnoHistoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoFolioTramiteMig) {
            gov.sir.core.negocio.modelo.TurnoFolioTramiteMig obj = (gov.sir.core.negocio.modelo.TurnoFolioTramiteMig) source;
            rta = TurnoFolioTramiteMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoFolioMig) {
            gov.sir.core.negocio.modelo.TurnoFolioMig obj = (gov.sir.core.negocio.modelo.TurnoFolioMig) source;
            rta = TurnoFolioMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoFolio) {
            gov.sir.core.negocio.modelo.TurnoFolio obj = (gov.sir.core.negocio.modelo.TurnoFolio) source;
            rta = TurnoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoEjecucion) {
            gov.sir.core.negocio.modelo.TurnoEjecucion obj = (gov.sir.core.negocio.modelo.TurnoEjecucion) source;
            rta = TurnoEjecucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoDerivado) {
            gov.sir.core.negocio.modelo.TurnoDerivado obj = (gov.sir.core.negocio.modelo.TurnoDerivado) source;
            rta = TurnoDerivadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TurnoAnotacion) {
            gov.sir.core.negocio.modelo.TurnoAnotacion obj = (gov.sir.core.negocio.modelo.TurnoAnotacion) source;
            rta = TurnoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Turno) {
            gov.sir.core.negocio.modelo.Turno obj = (gov.sir.core.negocio.modelo.Turno) source;
            rta = TurnoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Traslado) {
            gov.sir.core.negocio.modelo.Traslado obj = (gov.sir.core.negocio.modelo.Traslado) source;
            rta = TrasladoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoTarifa) {
            gov.sir.core.negocio.modelo.TipoTarifa obj = (gov.sir.core.negocio.modelo.TipoTarifa) source;
            rta = TipoTarifaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoRelacion) {
            gov.sir.core.negocio.modelo.TipoRelacion obj = (gov.sir.core.negocio.modelo.TipoRelacion) source;
            rta = TipoRelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoRecurso) {
            gov.sir.core.negocio.modelo.TipoRecurso obj = (gov.sir.core.negocio.modelo.TipoRecurso) source;
            rta = TipoRecursoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoRecepcionPeticion) {
            gov.sir.core.negocio.modelo.TipoRecepcionPeticion obj = (gov.sir.core.negocio.modelo.TipoRecepcionPeticion) source;
            rta = TipoRecepcionPeticionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoPredio) {
            gov.sir.core.negocio.modelo.TipoPredio obj = (gov.sir.core.negocio.modelo.TipoPredio) source;
            rta = TipoPredioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoPantalla) {
            gov.sir.core.negocio.modelo.TipoPantalla obj = (gov.sir.core.negocio.modelo.TipoPantalla) source;
            rta = TipoPantallaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoPago) {
            gov.sir.core.negocio.modelo.TipoPago obj = (gov.sir.core.negocio.modelo.TipoPago) source;
            rta = TipoPagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoOficio) {
            gov.sir.core.negocio.modelo.TipoOficio obj = (gov.sir.core.negocio.modelo.TipoOficio) source;
            rta = TipoOficioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoOficina) {
            gov.sir.core.negocio.modelo.TipoOficina obj = (gov.sir.core.negocio.modelo.TipoOficina) source;
            rta = TipoOficinaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoNota) {
            gov.sir.core.negocio.modelo.TipoNota obj = (gov.sir.core.negocio.modelo.TipoNota) source;
            rta = TipoNotaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoImpuesto) {
            gov.sir.core.negocio.modelo.TipoImpuesto obj = (gov.sir.core.negocio.modelo.TipoImpuesto) source;
            rta = TipoImpuestoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoImprimible) {
            gov.sir.core.negocio.modelo.TipoImprimible obj = (gov.sir.core.negocio.modelo.TipoImprimible) source;
            rta = TipoImprimibleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoHorarioNotarial) {
            gov.sir.core.negocio.modelo.TipoHorarioNotarial obj = (gov.sir.core.negocio.modelo.TipoHorarioNotarial) source;
            rta = TipoHorarioNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoFotocopia) {
            gov.sir.core.negocio.modelo.TipoFotocopia obj = (gov.sir.core.negocio.modelo.TipoFotocopia) source;
            rta = TipoFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoDocumento) {
            gov.sir.core.negocio.modelo.TipoDocumento obj = (gov.sir.core.negocio.modelo.TipoDocumento) source;
            rta = TipoDocumentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoDerechoReg) {
            gov.sir.core.negocio.modelo.TipoDerechoReg obj = (gov.sir.core.negocio.modelo.TipoDerechoReg) source;
            rta = TipoDerechoRegTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoConsulta) {
            gov.sir.core.negocio.modelo.TipoConsulta obj = (gov.sir.core.negocio.modelo.TipoConsulta) source;
            rta = TipoConsultaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoCertificadoValidacion) {
            gov.sir.core.negocio.modelo.TipoCertificadoValidacion obj = (gov.sir.core.negocio.modelo.TipoCertificadoValidacion) source;
            rta = TipoCertificadoValidacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoCertificado) {
            gov.sir.core.negocio.modelo.TipoCertificado obj = (gov.sir.core.negocio.modelo.TipoCertificado) source;
            rta = TipoCertificadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoCalculo) {
            gov.sir.core.negocio.modelo.TipoCalculo obj = (gov.sir.core.negocio.modelo.TipoCalculo) source;
            rta = TipoCalculoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoAnotacion) {
            gov.sir.core.negocio.modelo.TipoAnotacion obj = (gov.sir.core.negocio.modelo.TipoAnotacion) source;
            rta = TipoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoActoDerechoRegistral) {
            gov.sir.core.negocio.modelo.TipoActoDerechoRegistral obj = (gov.sir.core.negocio.modelo.TipoActoDerechoRegistral) source;
            rta = TipoActoDerechoRegistralTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoActo) {
            gov.sir.core.negocio.modelo.TipoActo obj = (gov.sir.core.negocio.modelo.TipoActo) source;
            rta = TipoActoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TextoImprimible) {
            gov.sir.core.negocio.modelo.TextoImprimible obj = (gov.sir.core.negocio.modelo.TextoImprimible) source;
            rta = TextoImprimibleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Texto) {
            gov.sir.core.negocio.modelo.Texto obj = (gov.sir.core.negocio.modelo.Texto) source;
            rta = TextoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Testamento) {
            gov.sir.core.negocio.modelo.Testamento obj = (gov.sir.core.negocio.modelo.Testamento) source;
            rta = TestamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TarifaDepartamento) {
            gov.sir.core.negocio.modelo.TarifaDepartamento obj = (gov.sir.core.negocio.modelo.TarifaDepartamento) source;
            rta = TarifaDepartamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Tarifa) {
            gov.sir.core.negocio.modelo.Tarifa obj = (gov.sir.core.negocio.modelo.Tarifa) source;
            rta = TarifaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SucursalBanco) {
            gov.sir.core.negocio.modelo.SucursalBanco obj = (gov.sir.core.negocio.modelo.SucursalBanco) source;
            rta = SucursalBancoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SubtipoSolicitud) {
            gov.sir.core.negocio.modelo.SubtipoSolicitud obj = (gov.sir.core.negocio.modelo.SubtipoSolicitud) source;
            rta = SubtipoSolicitudTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo) {
            gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo obj = (gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo) source;
            rta = SubtipoAtencionTipoActoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SubtipoAtencion) {
            gov.sir.core.negocio.modelo.SubtipoAtencion obj = (gov.sir.core.negocio.modelo.SubtipoAtencion) source;
            rta = SubtipoAtencionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SubAtencionSubSolicitud) {
            gov.sir.core.negocio.modelo.SubAtencionSubSolicitud obj = (gov.sir.core.negocio.modelo.SubAtencionSubSolicitud) source;
            rta = SubAtencionSubSolicitudTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudVinculada) {
            gov.sir.core.negocio.modelo.SolicitudVinculada obj = (gov.sir.core.negocio.modelo.SolicitudVinculada) source;
            rta = SolicitudVinculadaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudRestitucionReparto) {
            gov.sir.core.negocio.modelo.SolicitudRestitucionReparto obj = (gov.sir.core.negocio.modelo.SolicitudRestitucionReparto) source;
            rta = SolicitudRestitucionRepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudRepartoNotarial) {
            gov.sir.core.negocio.modelo.SolicitudRepartoNotarial obj = (gov.sir.core.negocio.modelo.SolicitudRepartoNotarial) source;
            rta = SolicitudRepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudRegistro) {
            gov.sir.core.negocio.modelo.SolicitudRegistro obj = (gov.sir.core.negocio.modelo.SolicitudRegistro) source;
            rta = SolicitudRegistroTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion) {
            gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion obj = (gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion) source;
            rta = SolicitudPermisoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudFotocopia) {
            gov.sir.core.negocio.modelo.SolicitudFotocopia obj = (gov.sir.core.negocio.modelo.SolicitudFotocopia) source;
            rta = SolicitudFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudFolioMig) {
            gov.sir.core.negocio.modelo.SolicitudFolioMig obj = (gov.sir.core.negocio.modelo.SolicitudFolioMig) source;
            rta = SolicitudFolioMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudFolio) {
            gov.sir.core.negocio.modelo.SolicitudFolio obj = (gov.sir.core.negocio.modelo.SolicitudFolio) source;
            rta = SolicitudFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudDevolucion) {
            gov.sir.core.negocio.modelo.SolicitudDevolucion obj = (gov.sir.core.negocio.modelo.SolicitudDevolucion) source;
            rta = SolicitudDevolucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudCorreccion) {
            gov.sir.core.negocio.modelo.SolicitudCorreccion obj = (gov.sir.core.negocio.modelo.SolicitudCorreccion) source;
            rta = SolicitudCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudConsulta) {
            gov.sir.core.negocio.modelo.SolicitudConsulta obj = (gov.sir.core.negocio.modelo.SolicitudConsulta) source;
            rta = SolicitudConsultaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudCheckedItem) {
            gov.sir.core.negocio.modelo.SolicitudCheckedItem obj = (gov.sir.core.negocio.modelo.SolicitudCheckedItem) source;
            rta = SolicitudCheckedItemTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo) {
            gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo obj = (gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo) source;
            rta = SolicitudCertificadoMasivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudCertificado) {
            gov.sir.core.negocio.modelo.SolicitudCertificado obj = (gov.sir.core.negocio.modelo.SolicitudCertificado) source;
            rta = SolicitudCertificadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudAsociada) {
            gov.sir.core.negocio.modelo.SolicitudAsociada obj = (gov.sir.core.negocio.modelo.SolicitudAsociada) source;
            rta = SolicitudAsociadaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolicitudAntiguoSistema) {
            gov.sir.core.negocio.modelo.SolicitudAntiguoSistema obj = (gov.sir.core.negocio.modelo.SolicitudAntiguoSistema) source;
            rta = SolicitudAntiguoSistemaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Solicitud) {
            gov.sir.core.negocio.modelo.Solicitud obj = (gov.sir.core.negocio.modelo.Solicitud) source;
            rta = SolicitudTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SolCheckedItemDev) {
            gov.sir.core.negocio.modelo.SolCheckedItemDev obj = (gov.sir.core.negocio.modelo.SolCheckedItemDev) source;
            rta = SolCheckedItemDevTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Secuencias) {
            gov.sir.core.negocio.modelo.Secuencias obj = (gov.sir.core.negocio.modelo.Secuencias) source;
            rta = SecuenciasTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SalvedadFolio) {
            gov.sir.core.negocio.modelo.SalvedadFolio obj = (gov.sir.core.negocio.modelo.SalvedadFolio) source;
            rta = SalvedadFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.SalvedadAnotacion) {
            gov.sir.core.negocio.modelo.SalvedadAnotacion obj = (gov.sir.core.negocio.modelo.SalvedadAnotacion) source;
            rta = SalvedadAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.RolSIR) {
            gov.sir.core.negocio.modelo.RolSIR obj = (gov.sir.core.negocio.modelo.RolSIR) source;
            rta = RolSIRTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.RolPantalla) {
            gov.sir.core.negocio.modelo.RolPantalla obj = (gov.sir.core.negocio.modelo.RolPantalla) source;
            rta = RolPantallaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ResultadoFolio) {
            gov.sir.core.negocio.modelo.ResultadoFolio obj = (gov.sir.core.negocio.modelo.ResultadoFolio) source;
            rta = ResultadoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ResultadoAnotacion) {
            gov.sir.core.negocio.modelo.ResultadoAnotacion obj = (gov.sir.core.negocio.modelo.ResultadoAnotacion) source;
            rta = ResultadoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.RepartoNotarial) {
            gov.sir.core.negocio.modelo.RepartoNotarial obj = (gov.sir.core.negocio.modelo.RepartoNotarial) source;
            rta = RepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Reparto) {
            gov.sir.core.negocio.modelo.Reparto obj = (gov.sir.core.negocio.modelo.Reparto) source;
            rta = RepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Relacion) {
            gov.sir.core.negocio.modelo.Relacion obj = (gov.sir.core.negocio.modelo.Relacion) source;
            rta = RelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Recurso) {
            gov.sir.core.negocio.modelo.Recurso obj = (gov.sir.core.negocio.modelo.Recurso) source;
            rta = RecursoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.RangoFolio) {
            gov.sir.core.negocio.modelo.RangoFolio obj = (gov.sir.core.negocio.modelo.RangoFolio) source;
            rta = RangoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Prohibicion) {
            gov.sir.core.negocio.modelo.Prohibicion obj = (gov.sir.core.negocio.modelo.Prohibicion) source;
            rta = ProhibicionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ProcesoReparto) {
            gov.sir.core.negocio.modelo.ProcesoReparto obj = (gov.sir.core.negocio.modelo.ProcesoReparto) source;
            rta = ProcesoRepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Proceso) {
            gov.sir.core.negocio.modelo.Proceso obj = (gov.sir.core.negocio.modelo.Proceso) source;
            rta = ProcesoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.PlantillaPertenencia) {
            gov.sir.core.negocio.modelo.PlantillaPertenencia obj = (gov.sir.core.negocio.modelo.PlantillaPertenencia) source;
            rta = PlantillaPertenenciaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.PermisoCorreccion) {
            gov.sir.core.negocio.modelo.PermisoCorreccion obj = (gov.sir.core.negocio.modelo.PermisoCorreccion) source;
            rta = PermisoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.PantallaAdministrativa) {
            gov.sir.core.negocio.modelo.PantallaAdministrativa obj = (gov.sir.core.negocio.modelo.PantallaAdministrativa) source;
            rta = PantallaAdministrativaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Pago) {
            gov.sir.core.negocio.modelo.Pago obj = (gov.sir.core.negocio.modelo.Pago) source;
            rta = PagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.OtorganteNatural) {
            gov.sir.core.negocio.modelo.OtorganteNatural obj = (gov.sir.core.negocio.modelo.OtorganteNatural) source;
            rta = OtorganteNaturalTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.OPLookupTypes) {
            gov.sir.core.negocio.modelo.OPLookupTypes obj = (gov.sir.core.negocio.modelo.OPLookupTypes) source;
            rta = OPLookupTypesTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.OPLookupCodes) {
            gov.sir.core.negocio.modelo.OPLookupCodes obj = (gov.sir.core.negocio.modelo.OPLookupCodes) source;
            rta = OPLookupCodesTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Oficio) {
            gov.sir.core.negocio.modelo.Oficio obj = (gov.sir.core.negocio.modelo.Oficio) source;
            rta = OficioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.OficinaOrigen) {
            gov.sir.core.negocio.modelo.OficinaOrigen obj = (gov.sir.core.negocio.modelo.OficinaOrigen) source;
            rta = OficinaOrigenTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.OficinaCategoria) {
            gov.sir.core.negocio.modelo.OficinaCategoria obj = (gov.sir.core.negocio.modelo.OficinaCategoria) source;
            rta = OficinaCategoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.NumeroMatricula) {
            gov.sir.core.negocio.modelo.NumeroMatricula obj = (gov.sir.core.negocio.modelo.NumeroMatricula) source;
            rta = NumeroMatriculaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.NotaActuacion) {
            gov.sir.core.negocio.modelo.NotaActuacion obj = (gov.sir.core.negocio.modelo.NotaActuacion) source;
            rta = NotaActuacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Nota) {
            gov.sir.core.negocio.modelo.Nota obj = (gov.sir.core.negocio.modelo.Nota) source;
            rta = NotaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad) {
            gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad obj = (gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad) source;
            rta = NaturalezaJuridicaEntidadTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.NaturalezaJuridica) {
            gov.sir.core.negocio.modelo.NaturalezaJuridica obj = (gov.sir.core.negocio.modelo.NaturalezaJuridica) source;
            rta = NaturalezaJuridicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Municipio) {
            gov.sir.core.negocio.modelo.Municipio obj = (gov.sir.core.negocio.modelo.Municipio) source;
            rta = MunicipioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.MinutaEntidadPublica) {
            gov.sir.core.negocio.modelo.MinutaEntidadPublica obj = (gov.sir.core.negocio.modelo.MinutaEntidadPublica) source;
            rta = MinutaEntidadPublicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.MinutaAccionNotarial) {
            gov.sir.core.negocio.modelo.MinutaAccionNotarial obj = (gov.sir.core.negocio.modelo.MinutaAccionNotarial) source;
            rta = MinutaAccionNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Minuta) {
            gov.sir.core.negocio.modelo.Minuta obj = (gov.sir.core.negocio.modelo.Minuta) source;
            rta = MinutaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LlaveBloqueo) {
            gov.sir.core.negocio.modelo.LlaveBloqueo obj = (gov.sir.core.negocio.modelo.LlaveBloqueo) source;
            rta = LlaveBloqueoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto) source;
            rta = LiquidacionTurnoRestitucionRepartoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial) source;
            rta = LiquidacionTurnoRepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro) source;
            rta = LiquidacionTurnoRegistroTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia) source;
            rta = LiquidacionTurnoFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion) source;
            rta = LiquidacionTurnoDevolucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion) source;
            rta = LiquidacionTurnoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta) source;
            rta = LiquidacionTurnoConsultaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo) source;
            rta = LiquidacionTurnoCertificadoMasivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado) {
            gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado obj = (gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado) source;
            rta = LiquidacionTurnoCertificadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.InicioProcesos) {
            gov.sir.core.negocio.modelo.InicioProcesos obj = (gov.sir.core.negocio.modelo.InicioProcesos) source;
            rta = InicioProcesosTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ImprimiblePdf) {
            gov.sir.core.negocio.modelo.ImprimiblePdf obj = (gov.sir.core.negocio.modelo.ImprimiblePdf) source;
            rta = ImprimiblePdfTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Imprimible) {
            gov.sir.core.negocio.modelo.Imprimible obj = (gov.sir.core.negocio.modelo.Imprimible) source;
            rta = ImprimibleTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Impresion) {
            gov.sir.core.negocio.modelo.Impresion obj = (gov.sir.core.negocio.modelo.Impresion) source;
            rta = ImpresionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.HorarioNotarial) {
            gov.sir.core.negocio.modelo.HorarioNotarial obj = (gov.sir.core.negocio.modelo.HorarioNotarial) source;
            rta = HorarioNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica) {
            gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica obj = (gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica) source;
            rta = GrupoNaturalezaJuridicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.FolioValidoMig) {
            gov.sir.core.negocio.modelo.FolioValidoMig obj = (gov.sir.core.negocio.modelo.FolioValidoMig) source;
            rta = FolioValidoMigTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.FolioDerivado) {
            gov.sir.core.negocio.modelo.FolioDerivado obj = (gov.sir.core.negocio.modelo.FolioDerivado) source;
            rta = FolioDerivadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Folio) {
            gov.sir.core.negocio.modelo.Folio obj = (gov.sir.core.negocio.modelo.Folio) source;
            rta = FolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.FirmaRegistrador) {
            gov.sir.core.negocio.modelo.FirmaRegistrador obj = (gov.sir.core.negocio.modelo.FirmaRegistrador) source;
            rta = FirmaRegistradorTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.FaseAlerta) {
            gov.sir.core.negocio.modelo.FaseAlerta obj = (gov.sir.core.negocio.modelo.FaseAlerta) source;
            rta = FaseAlertaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Fase) {
            gov.sir.core.negocio.modelo.Fase obj = (gov.sir.core.negocio.modelo.Fase) source;
            rta = FaseTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.EstadoHistoria) {
            gov.sir.core.negocio.modelo.EstadoHistoria obj = (gov.sir.core.negocio.modelo.EstadoHistoria) source;
            rta = EstadoHistoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.EstadoFolio) {
            gov.sir.core.negocio.modelo.EstadoFolio obj = (gov.sir.core.negocio.modelo.EstadoFolio) source;
            rta = EstadoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.EstadoAnotacion) {
            gov.sir.core.negocio.modelo.EstadoAnotacion obj = (gov.sir.core.negocio.modelo.EstadoAnotacion) source;
            rta = EstadoAnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.EstacionReciboAuditoria) {
            gov.sir.core.negocio.modelo.EstacionReciboAuditoria obj = (gov.sir.core.negocio.modelo.EstacionReciboAuditoria) source;
            rta = EstacionReciboAuditoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.EstacionRecibo) {
            gov.sir.core.negocio.modelo.EstacionRecibo obj = (gov.sir.core.negocio.modelo.EstacionRecibo) source;
            rta = EstacionReciboTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.EntidadPublica) {
            gov.sir.core.negocio.modelo.EntidadPublica obj = (gov.sir.core.negocio.modelo.EntidadPublica) source;
            rta = EntidadPublicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Eje) {
            gov.sir.core.negocio.modelo.Eje obj = (gov.sir.core.negocio.modelo.Eje) source;
            rta = EjeTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DominioNaturalezaJuridica) {
            gov.sir.core.negocio.modelo.DominioNaturalezaJuridica obj = (gov.sir.core.negocio.modelo.DominioNaturalezaJuridica) source;
            rta = DominioNaturalezaJuridicaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocumentoFotocopia) {
            gov.sir.core.negocio.modelo.DocumentoFotocopia obj = (gov.sir.core.negocio.modelo.DocumentoFotocopia) source;
            rta = DocumentoFotocopiaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Documento) {
            gov.sir.core.negocio.modelo.Documento obj = (gov.sir.core.negocio.modelo.Documento) source;
            rta = DocumentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion) {
            gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion obj = (gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion) source;
            rta = DocPagoTimbreConstanciaLiquidacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoPse) {
            gov.sir.core.negocio.modelo.DocPagoPse obj = (gov.sir.core.negocio.modelo.DocPagoPse) source;
            rta = DocPagoPseTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoHeredado) {
            gov.sir.core.negocio.modelo.DocPagoHeredado obj = (gov.sir.core.negocio.modelo.DocPagoHeredado) source;
            rta = DocPagoHeredadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoEfectivo) {
            gov.sir.core.negocio.modelo.DocPagoEfectivo obj = (gov.sir.core.negocio.modelo.DocPagoEfectivo) source;
            rta = DocPagoEfectivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoConvenio) {
            gov.sir.core.negocio.modelo.DocPagoConvenio obj = (gov.sir.core.negocio.modelo.DocPagoConvenio) source;
            rta = DocPagoConvenioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoGeneral) {
            gov.sir.core.negocio.modelo.DocPagoGeneral obj = (gov.sir.core.negocio.modelo.DocPagoGeneral) source;
            rta = DocPagoGeneralTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoConsignacion) {
            gov.sir.core.negocio.modelo.DocPagoConsignacion obj = (gov.sir.core.negocio.modelo.DocPagoConsignacion) source;
            rta = DocPagoConsignacionTransferCopier.deepCopy(obj, cache);
            /**
             * @autor HGOMEZ
             * @mantis 12422
             * @Requerimiento 049_453
             * @descripcion Se valida que la variable source sea instacia de un
             * domento tipo Tarjeta Credito, Tarjeta Debito o Pago Electrónico
             * PSE.
             */
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoTarjetaCredito) {
            gov.sir.core.negocio.modelo.DocPagoTarjetaCredito obj = (gov.sir.core.negocio.modelo.DocPagoTarjetaCredito) source;
            rta = DocPagoTarjetaCreditoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoTarjetaDebito) {
            gov.sir.core.negocio.modelo.DocPagoTarjetaDebito obj = (gov.sir.core.negocio.modelo.DocPagoTarjetaDebito) source;
            rta = DocPagoTarjetaDebitoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoElectronicoPSE) {
            gov.sir.core.negocio.modelo.DocPagoElectronicoPSE obj = (gov.sir.core.negocio.modelo.DocPagoElectronicoPSE) source;
            rta = DocPagoElectronicoPSETransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoChequePosfechado) {
            gov.sir.core.negocio.modelo.DocPagoChequePosfechado obj = (gov.sir.core.negocio.modelo.DocPagoChequePosfechado) source;
            rta = DocPagoChequePosfechadoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoChequeGerencia) {
            gov.sir.core.negocio.modelo.DocPagoChequeGerencia obj = (gov.sir.core.negocio.modelo.DocPagoChequeGerencia) source;
            rta = DocPagoChequeGerenciaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoCheque) {
            gov.sir.core.negocio.modelo.DocPagoCheque obj = (gov.sir.core.negocio.modelo.DocPagoCheque) source;
            rta = DocPagoChequeTransferCopier.deepCopy(obj, cache);
        }else if (source instanceof gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacionCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacionCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacionCorreccion) source;
            rta = DocPagoTimbreConstanciaLiquidacionCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoPseCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoPseCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoPseCorreccion) source;
            rta = DocPagoPseCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoHeredadoCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoHeredadoCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoHeredadoCorreccion) source;
            rta = DocPagoHeredadoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoEfectivoCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoEfectivoCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoEfectivoCorreccion) source;
            rta = DocPagoEfectivoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoConvenioCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoConvenioCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoConvenioCorreccion) source;
            rta = DocPagoConvenioCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoGeneralCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoGeneralCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoGeneralCorreccion) source;
            rta = DocPagoGeneralCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoConsignacionCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoConsignacionCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoConsignacionCorreccion) source;
            rta = DocPagoConsignacionCorreccionTransferCopier.deepCopy(obj, cache);
            /**
             * @autor HGOMEZ
             * @mantis 12422
             * @Requerimiento 049_453
             * @descripcion Se valida que la variable source sea instacia de un
             * domento tipo Tarjeta Credito, Tarjeta Debito o Pago Electrónico
             * PSE.
             */
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoTarjetaCreditoCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoTarjetaCreditoCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoTarjetaCreditoCorreccion) source;
            rta = DocPagoTarjetaCreditoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoTarjetaDebitoCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoTarjetaDebitoCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoTarjetaDebitoCorreccion) source;
            rta = DocPagoTarjetaDebitoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoElectronicoPSECorreccion) {
            gov.sir.core.negocio.modelo.DocPagoElectronicoPSECorreccion obj = (gov.sir.core.negocio.modelo.DocPagoElectronicoPSECorreccion) source;
            rta = DocPagoElectronicoPSECorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoChequePosfechadoCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoChequePosfechadoCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoChequePosfechadoCorreccion) source;
            rta = DocPagoChequePosfechadoCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoChequeGerenciaCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoChequeGerenciaCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoChequeGerenciaCorreccion) source;
            rta = DocPagoChequeGerenciaCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DocPagoChequeCorreccion) {
            gov.sir.core.negocio.modelo.DocPagoChequeCorreccion obj = (gov.sir.core.negocio.modelo.DocPagoChequeCorreccion) source;
            rta = DocPagoChequeCorreccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Direccion) {
            gov.sir.core.negocio.modelo.Direccion obj = (gov.sir.core.negocio.modelo.Direccion) source;
            rta = DireccionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Departamento) {
            gov.sir.core.negocio.modelo.Departamento obj = (gov.sir.core.negocio.modelo.Departamento) source;
            rta = DepartamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.DatosAntiguoSistema) {
            gov.sir.core.negocio.modelo.DatosAntiguoSistema obj = (gov.sir.core.negocio.modelo.DatosAntiguoSistema) source;
            rta = DatosAntiguoSistemaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Complementacion) {
            gov.sir.core.negocio.modelo.Complementacion obj = (gov.sir.core.negocio.modelo.Complementacion) source;
            rta = ComplementacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CiudadanoProhibicion) {
            gov.sir.core.negocio.modelo.CiudadanoProhibicion obj = (gov.sir.core.negocio.modelo.CiudadanoProhibicion) source;
            rta = CiudadanoProhibicionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Ciudadano) {
            gov.sir.core.negocio.modelo.Ciudadano obj = (gov.sir.core.negocio.modelo.Ciudadano) source;
            rta = CiudadanoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CirculoTraslado) {
            gov.sir.core.negocio.modelo.CirculoTraslado obj = (gov.sir.core.negocio.modelo.CirculoTraslado) source;
            rta = CirculoTrasladoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CirculoTipoPago) {
            gov.sir.core.negocio.modelo.CirculoTipoPago obj = (gov.sir.core.negocio.modelo.CirculoTipoPago) source;
            rta = CirculoTipoPagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CirculoRelacion) {
            gov.sir.core.negocio.modelo.CirculoRelacion obj = (gov.sir.core.negocio.modelo.CirculoRelacion) source;
            rta = CirculoRelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CirculoProceso) {
            gov.sir.core.negocio.modelo.CirculoProceso obj = (gov.sir.core.negocio.modelo.CirculoProceso) source;
            rta = CirculoProcesoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CirculoNotarial) {
            gov.sir.core.negocio.modelo.CirculoNotarial obj = (gov.sir.core.negocio.modelo.CirculoNotarial) source;
            rta = CirculoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CirculoImpresora) {
            gov.sir.core.negocio.modelo.CirculoImpresora obj = (gov.sir.core.negocio.modelo.CirculoImpresora) source;
            rta = CirculoImpresoraTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CirculoFestivo) {
            gov.sir.core.negocio.modelo.CirculoFestivo obj = (gov.sir.core.negocio.modelo.CirculoFestivo) source;
            rta = CirculoFestivoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Circulo) {
            gov.sir.core.negocio.modelo.Circulo obj = (gov.sir.core.negocio.modelo.Circulo) source;
            rta = CirculoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CheckItemDev) {
            gov.sir.core.negocio.modelo.CheckItemDev obj = (gov.sir.core.negocio.modelo.CheckItemDev) source;
            rta = CheckItemDevTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CheckItem) {
            gov.sir.core.negocio.modelo.CheckItem obj = (gov.sir.core.negocio.modelo.CheckItem) source;
            rta = CheckItemTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CausalRestitucion) {
            gov.sir.core.negocio.modelo.CausalRestitucion obj = (gov.sir.core.negocio.modelo.CausalRestitucion) source;
            rta = CausalRestitucionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CategoriaNotaria) {
            gov.sir.core.negocio.modelo.CategoriaNotaria obj = (gov.sir.core.negocio.modelo.CategoriaNotaria) source;
            rta = CategoriaNotariaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Categoria) {
            gov.sir.core.negocio.modelo.Categoria obj = (gov.sir.core.negocio.modelo.Categoria) source;
            rta = CategoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Cancelacion) {
            gov.sir.core.negocio.modelo.Cancelacion obj = (gov.sir.core.negocio.modelo.Cancelacion) source;
            rta = CancelacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.CambioMatriculaCer) {
            gov.sir.core.negocio.modelo.CambioMatriculaCer obj = (gov.sir.core.negocio.modelo.CambioMatriculaCer) source;
            rta = CambioMatriculaCerTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Busqueda) {
            gov.sir.core.negocio.modelo.Busqueda obj = (gov.sir.core.negocio.modelo.Busqueda) source;
            rta = BusquedaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.BloqueoFolio) {
            gov.sir.core.negocio.modelo.BloqueoFolio obj = (gov.sir.core.negocio.modelo.BloqueoFolio) source;
            rta = BloqueoFolioTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Banco) {
            gov.sir.core.negocio.modelo.Banco obj = (gov.sir.core.negocio.modelo.Banco) source;
            rta = BancoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.AumentoRecibo) {
            gov.sir.core.negocio.modelo.AumentoRecibo obj = (gov.sir.core.negocio.modelo.AumentoRecibo) source;
            rta = AumentoReciboTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Auditoria) {
            gov.sir.core.negocio.modelo.Auditoria obj = (gov.sir.core.negocio.modelo.Auditoria) source;
            rta = AuditoriaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.AplicacionPago) {
            gov.sir.core.negocio.modelo.AplicacionPago obj = (gov.sir.core.negocio.modelo.AplicacionPago) source;
            rta = AplicacionPagoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.AnotacionCiudadano) {
            gov.sir.core.negocio.modelo.AnotacionCiudadano obj = (gov.sir.core.negocio.modelo.AnotacionCiudadano) source;
            rta = AnotacionCiudadanoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Anotacion) {
            gov.sir.core.negocio.modelo.Anotacion obj = (gov.sir.core.negocio.modelo.Anotacion) source;
            rta = AnotacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Alerta) {
            gov.sir.core.negocio.modelo.Alerta obj = (gov.sir.core.negocio.modelo.Alerta) source;
            rta = AlertaTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.AlcanceGeografico) {
            gov.sir.core.negocio.modelo.AlcanceGeografico obj = (gov.sir.core.negocio.modelo.AlcanceGeografico) source;
            rta = AlcanceGeograficoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.ActoresRepartoNotarial) {
            gov.sir.core.negocio.modelo.ActoresRepartoNotarial obj = (gov.sir.core.negocio.modelo.ActoresRepartoNotarial) source;
            rta = ActoresRepartoNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Acto) {
            gov.sir.core.negocio.modelo.Acto obj = (gov.sir.core.negocio.modelo.Acto) source;
            rta = ActoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.AccionNotarial) {
            gov.sir.core.negocio.modelo.AccionNotarial obj = (gov.sir.core.negocio.modelo.AccionNotarial) source;
            rta = AccionNotarialTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.Consignacion) {
            gov.sir.core.negocio.modelo.Consignacion obj = (gov.sir.core.negocio.modelo.Consignacion) source;
            rta = ConsignacionTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.BancosXCirculo) {
            gov.sir.core.negocio.modelo.BancosXCirculo obj = (gov.sir.core.negocio.modelo.BancosXCirculo) source;
            rta = BancosXCirculoTransferCopier.deepCopy(obj, cache);
            /*
            * @author      : Carlos Mario Torres Urina
            * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
             */
        } else if (source instanceof gov.sir.core.negocio.modelo.Fundamento) {
            gov.sir.core.negocio.modelo.Fundamento obj = (gov.sir.core.negocio.modelo.Fundamento) source;
            rta = FundamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TipoFundamento) {
            gov.sir.core.negocio.modelo.TipoFundamento obj = (gov.sir.core.negocio.modelo.TipoFundamento) source;
            rta = TipoFundamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TrasladoFundamento) {
            gov.sir.core.negocio.modelo.TrasladoFundamento obj = (gov.sir.core.negocio.modelo.TrasladoFundamento) source;
            rta = TrasladoFundamentoTransferCopier.deepCopy(obj, cache);
        } else if (source instanceof gov.sir.core.negocio.modelo.TrasladoDatos) {
            gov.sir.core.negocio.modelo.TrasladoDatos obj = (gov.sir.core.negocio.modelo.TrasladoDatos) source;
            rta = TrasladoDatosTransferCopier.deepCopy(obj, cache);
        }
        return rta;
    }

}
