/*
 * Clase para el manejo de los procesos existentes en la aplicaci?n.
 */
package gov.sir.hermod.dao.impl.jdogenie;

import gov.sir.core.web.acciones.administracion.AWReportes;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.ReportesJasperDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.print.server.PrintJobsProperties;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * Clase para el manejo de los procesos existentes en la aplicaci?n. 
 * @author  mrios, mortiz, dlopez
 */
public class JDOReportesJasperDAO implements ReportesJasperDAO {

    private static Logger logEstatico = Logger.getLogger(JDOReportesJasperDAO.class);
    private static final HashMap mapaReportes = new HashMap();


    static {
        ArrayList listaParametros = null;
        listaParametros = new ArrayList();

        //Reporte 05
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_05__PARAM_PPAGOFECHA);
        listaParametros.add(AWReportes.REPORTE_05__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_05__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_05.NEXTVAL FROM dual",
                    "sp_reporte_05(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_05 WHERE sec_proceso = "});

        //Reporte 06
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_06__PARAM_PTURNOFECHA);
        listaParametros.add(AWReportes.REPORTE_06__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_06__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_06.NEXTVAL FROM dual",
                    "sp_reporte_06(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_06 WHERE sec_proceso = "});

        //Reporte 06B
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_6B__PARAM_PTURNOFECHA);
        listaParametros.add(AWReportes.REPORTE_6B__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_6B__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_06b.NEXTVAL FROM dual",
                    "sp_reporte_6b(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_06b WHERE sec_proceso = "});

        //Reporte 07
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_07__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_07__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_07__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_07__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_07.NEXTVAL FROM dual",
                    "sp_reporte_07(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_07 WHERE sec_proceso = "});

        //Reporte 08
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_08__PARAM_PTURNOFECHA);
        listaParametros.add(AWReportes.REPORTE_08__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_08_PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_08.NEXTVAL FROM dual",
                    "sp_reporte_08(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_08 WHERE sec_proceso = "});

        //Reporte 09
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_09__PARAM_PTURNOFECHA);
        listaParametros.add(AWReportes.REPORTE_09__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_09__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_09.NEXTVAL FROM dual",
                    "sp_reporte_09(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_09 WHERE sec_proceso = "});

        //Reporte 11
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_11__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_11__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_11__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_11__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte11.NEXTVAL FROM dual",
                    "sp_reporte_11(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_11 WHERE sec_proceso = "});

        //Reporte 15
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_15__PARAM_PLIQUIDFECHAINI);
        listaParametros.add(AWReportes.REPORTE_15__PARAM_PLIQUIDFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_15__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_15__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_15.NEXTVAL FROM dual",
                    "sp_reporte_15(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_15 WHERE sec_proceso = "});

        //Reporte 16
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_16__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_16__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_16__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_16__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_16.NEXTVAL FROM dual",
                    "sp_reporte_16(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_16 WHERE sec_proceso = "});


        //Reporte 17
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_17__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_17__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_17__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_17__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_17.NEXTVAL FROM dual",
                    "sp_reporte_17(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_17 WHERE sec_proceso = "});

        //AHERRENO 11/12/2012
        //REQ 020_453
        //Reporte 18
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_18__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_18__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_18__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_18__PARAM_CMDKEY, new Object[]{
                    "SELECT seQ_reporte_18.NEXTVAL FROM dual",
                    "sp_reporte_18(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_18 WHERE sec_proceso = "});        
        
        //Reporte 19
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_19__PARAM_PPAGOFECHAINI);
        listaParametros.add(AWReportes.REPORTE_19__PARAM_PPAGOFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_19__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_19__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_19.NEXTVAL FROM dual",
                    "sp_reporte_19(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_19 WHERE sec_proceso = "});

        //Reporte 20
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_20__PARAM_PPAGOFECHAINI);
        listaParametros.add(AWReportes.REPORTE_20__PARAM_PPAGOFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_20__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_20__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_20.NEXTVAL FROM dual",
                    "sp_reporte_20(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_20 WHERE sec_proceso = "});

        //Reporte 22
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_22__PARAM_PPAGOFECHAINI);
        listaParametros.add(AWReportes.REPORTE_22__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_22__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_22.NEXTVAL FROM dual",
                    "sp_reporte_22(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_22 WHERE sec_proceso = "});

        //GCABRERA 15/12/2011
        //Reporte 24
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_24__PARAM_PLIQUIDACIONFECHAINI);
        listaParametros.add(AWReportes.REPORTE_24__PARAM_PLIQUIDACIONFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_24__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_24__PARAM_CMDKEY, new Object[]{
                    "SELECT SIR.SEQ_REPORTE_24.NEXTVAL FROM dual",
                    "sp_reporte_24(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_24 WHERE sec_proceso = "});


        //AHERRENO 11/04/2011
        //Reporte 25
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_25__PARAM_PAGOFECHA);
        listaParametros.add(AWReportes.REPORTE_25__PARAM_CIRCULONOMBRE);
        listaParametros.add(AWReportes.REPORTE_25__PARAM_USUARIONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_25__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_25.NEXTVAL FROM dual",
                    "sp_reporte_25(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_25 WHERE sec_proceso = "});

        //Reporte 30
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_30__PARAM_PDEPARTAMENTO);
        listaParametros.add(AWReportes.REPORTE_30__PARAM_PMUNICIPIO);
        listaParametros.add(AWReportes.REPORTE_30__PARAM_PNUMREPARTO);
        mapaReportes.put(AWReportes.REPORTE_30__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_30.NEXTVAL FROM dual",
                    "sp_reporte_30(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_30 WHERE sec_proceso = "});

        //GCABRERA 19/12/2011
        //Reporte 39
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_39__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_39__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_39__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_39__PARAM_CMDKEY, new Object[]{
                    "SELECT SIR.SEQ_REPORTE_39.NEXTVAL FROM dual",
                    "sp_reporte_39(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir.sir_op_reporte_39 WHERE sec_proceso = "});

        //Reporte 49
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_49__PARAM_PCIRCULONOMBRE);
        listaParametros.add(AWReportes.REPORTE_49__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_49__PARAM_PFECHAFIN);
        mapaReportes.put(AWReportes.REPORTE_49__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_49.NEXTVAL FROM dual",
                    "sp_reporte_49(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_49 WHERE sec_proceso = "});

        //Reporte 51
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_51__PARAM_PFECHA);
        listaParametros.add(AWReportes.REPORTE_51__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_51__PARAM_CMDKEY, new Object[]{
                    "SELECT SEC_REPORTE51.NEXTVAL FROM dual",
                    "sp_reporte_51(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_51 WHERE seq_proceso = "});

        //Reporte 51B
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_51B__PARAM_PFECHA);
        listaParametros.add(AWReportes.REPORTE_51B__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_51B__PARAM_CMDKEY, new Object[]{
                    "SELECT SEC_REPORTE51.NEXTVAL FROM dual",
                    "sp_reporte_51b(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_51 WHERE seq_proceso = "});

        //Reporte 57
        //AHERRENO 11/04/2011
        //Se agrega parametro REPORTE_57__PARAM_PFECHAFIN
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_57__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_57__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_57__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_57__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_57.NEXTVAL FROM dual",
                    "sp_reporte_57(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_57 WHERE sec_proceso = "});

        //Reporte 74
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_74__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_74__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_74__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_74__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_74.NEXTVAL FROM dual",
                    "sp_reporte_74(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_74 WHERE sec_proceso = "});

        //AHERRENO 12/12/2012
        //REQ 020_453
        //Reporte 79
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_79__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_79__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_79__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_79__PARAM_CMDKEY, new Object[]{
                    "SELECT seQ_reporte_79.NEXTVAL FROM dual",
                    "sp_reporte_79(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_79 WHERE sec_proceso = "});        
                
        
        //Reporte 91
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_91__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_91__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_91__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_91__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_91.NEXTVAL FROM dual",
                    "sp_reporte_91(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_91 WHERE sec_proceso = "});

        //Reporte 92
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_92__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_92__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_92__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_92__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_92.NEXTVAL FROM dual",
                    "sp_reporte_92(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_92 WHERE sec_proceso = "});

        //Reporte 94
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_94__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_94__PPARAM_PUSUARIONOMBRE);
        listaParametros.add(AWReportes.REPORTE_94__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_94__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_94.NEXTVAL FROM dual",
                    "sp_reporte_94(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_94 WHERE sec_proceso = "});

        //Reporte 99
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_99__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_99__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_99__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_99__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_99.NEXTVAL FROM dual",
                    "sp_reporte_99(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_99 WHERE sec_proceso = "});

        //Reporte 145
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_145__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_145__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_145__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_145__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_145.NEXTVAL FROM dual",
                    "sp_reporte_145(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_145 WHERE sec_proceso = "});

        //AHERRENO 27/08/2012
        //Reporte 151
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_151__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_151__PARAM_PCIRCULONOMBRE);       
        mapaReportes.put(AWReportes.REPORTE_151__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_151.NEXTVAL FROM dual",
                    "sp_reporte_151(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_151 WHERE sec_proceso = "});        

        //AHERRENO 19/09/2013
        //Reporte 156
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_156__PARAM_PTURNOFECHAINICIO);
        listaParametros.add(AWReportes.REPORTE_156__PARAM_PTURNOFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_156__PARAM_PCIRCULONOMBRE);       
        mapaReportes.put(AWReportes.REPORTE_156__PARAM_CMDKEY, new Object[]{
                    "SELECT SEC_REPORTE156.NEXTVAL FROM dual",
                    "sp_reporte_156(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_156 WHERE sec_proceso = "});    
        
        //AHERRENO 25/10/2010
        //Se agrega par?metro REPORTE_G01__PARAM_IDCIRCULO
        //Reporte G01
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_G01__PARAM_IDCIRCULO);
        listaParametros.add(AWReportes.REPORTE_G01__PARAM_FECHAINI);
        listaParametros.add(AWReportes.REPORTE_G01__PARAM_FECHAFIN);
        mapaReportes.put(AWReportes.REPORTE_G01__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_G01.NEXTVAL FROM dual",
                    "sp_reporte_G01(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_G01 WHERE sec_proceso = "});

        //AHERRENO 17/02/2011
        //Reporte G02
        //Se obtienen los par?metros para enviar al procedimiento SP_REPORTE_G02
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_G02__PARAM_IDCIRCULO);
        listaParametros.add(AWReportes.REPORTE_G02__PARAM_FECHAINI);
        listaParametros.add(AWReportes.REPORTE_G02__PARAM_FECHAFIN);
        /**
        * @Autor: Santiago V?squez
        * @Change: 2028.AJUSTE REPORTES 166, 167 y G02
        * Eliminar la opci?n de usuario
        */
        //listaParametros.add(AWReportes.REPORTE_G02__PARAM_IDUSUARIO);
        mapaReportes.put(AWReportes.REPORTE_G02__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_G02.NEXTVAL FROM dual",
                    "sp_reporte_G02(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_G02 WHERE sec_proceso = "});
        
        /**
         * @Autor: Santiago V?squez
         * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
         * Reporte 178 - Se obtienen los par?metros para enviar al procedimiento SP_REPORTE_1782
         **/
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_178__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_178__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_178__PARAM_IDCIRCULO);
        listaParametros.add(AWReportes.REPORTE_178__PARAM_USUARIOLOG);
        listaParametros.add(AWReportes.REPORTE_178__PARAM_IDUSUARIO);
        mapaReportes.put(AWReportes.REPORTE_178__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_178.NEXTVAL FROM dual",
                    "sp_reporte_178(?, ?, ?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_178 WHERE sec_proceso = "});
        
        /**
         * @Autor: Santiago V?squez
         * @Change: 2015.REPORTE.ESTADISTICO.POR.NATURALEZA.JURIDICA
         **/
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_179__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_179__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_179__PARAM_IDCIRCULO);
        listaParametros.add(AWReportes.REPORTE_179__PARAM_IDNATJUR);
        listaParametros.add(AWReportes.REPORTE_179__PARAM_IDTIPOPREDIO);
        mapaReportes.put(AWReportes.REPORTE_179__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_179.NEXTVAL FROM dual",
                    "sp_reporte_179(?, ?, ?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_179 WHERE sec_proceso = "});
        
        /* Reporte 180 - REL
        * desarrollo5@tsg.net.co - Julian Rojas Ramirez  
        */
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_180__PARAM_PFECHA);
        listaParametros.add(AWReportes.REPORTE_180__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_180__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_180__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_180.NEXTVAL FROM dual",
                    "sp_reporte_180(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_180 WHERE sec_proceso = "});
        
        //AHERRENO 26/10/2010
        //Se agrega par?metro REPORTE_G03__PARAM_IDCIRCULO
        //Reporte G03
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_G03__PARAM_IDCIRCULO);
        listaParametros.add(AWReportes.REPORTE_G03__PARAM_IDUSUARIO);
        mapaReportes.put(AWReportes.REPORTE_G03__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_G03.NEXTVAL FROM dual",
                    "sp_reporte_G03(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_G03 WHERE sec_proceso = "});

        //Reporte G06
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_G06__PARAM_FECHAFIN);
        listaParametros.add(AWReportes.REPORTE_G06__PARAM_IDCIRCULO);
        listaParametros.add(AWReportes.REPORTE_G06__PARAM_IDPROCESO);
        mapaReportes.put(AWReportes.REPORTE_G06__PARAM_CMDKEY, new Object[]{
                    "SELECT sec_reporte_G06.NEXTVAL FROM dual",
                    "sp_reporte_G06(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_G06 WHERE sec_proceso = "});

        //Reporte 06
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_157__PARAM_PTURNOFECHA);
        listaParametros.add(AWReportes.REPORTE_157__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_157__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_157.NEXTVAL FROM dual",
                    "sp_reporte_157(?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_157 WHERE sec_proceso = "});

        //AHERRENO 19/08/2009
        //Reporte FOLIOS MASIVOS
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_FOLIOS_MAS__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_FOLIOS_MAS__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_FOLIOS_MAS__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_FOLIOS_MAS__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_57.NEXTVAL FROM dual",
                    "sp_reporte_FOLIOS_MAS(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_57 WHERE sec_proceso = "});

        //AHERRENO 22/09/2009
        //Reporte E01
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_E01__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_E01__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_E01__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_E01__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_E01.NEXTVAL FROM dual",
                    "sp_reporte_E01(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_E01 WHERE sec_proceso = "});

        //Reporte 161
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_161__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_161__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_161__PARAM_PCIRCULONOMBRE);
        listaParametros.add(AWReportes.REPORTE_161__PARAM_PMUNICIPIO);
        listaParametros.add(AWReportes.REPORTE_161__PARAM_PVEREDA);
        listaParametros.add(AWReportes.REPORTE_161__PARAM_PNATJURIDICA);
        mapaReportes.put(AWReportes.REPORTE_161__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_161.NEXTVAL FROM dual",
                    "sp_reporte_161(?, ?, ?, ?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_161 WHERE sec_proceso = "});

        //AHERRENO 26/04/2010
        //Reporte E02
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_E02__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_E02__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_E02__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_E02__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_E02.NEXTVAL FROM dual",
                    "sp_reporte_E02(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_E02 WHERE sec_proceso = "});

        //AHERRENO 02/08/2010
        //Reporte E03
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_E03__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_E03__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_E03__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_E03__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_E03.NEXTVAL FROM dual",
                    "sp_reporte_E03(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_E03 WHERE sec_proceso = "});

        //AHERRENO 03/05/2010
        //Reporte RUPTA 01
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_RUPTA01__PARAM_PFECHA);
        listaParametros.add(AWReportes.REPORTE_RUPTA01__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_RUPTA01__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_RUPTA01__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_rupta01.NEXTVAL FROM dual",
                    "sp_reporte_rupta01(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_rupta01 WHERE sec_proceso = "});

        //AHERRENO 05/01/2011
        //Reporte 162
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_162__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_162__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_162__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_162__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_162.NEXTVAL FROM dual",
                    "sp_reporte_162(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_162 WHERE sec_proceso = "});

        //AHERRENO 15/02/2011
        //Reporte 163
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_163__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_163__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_163__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_163__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_162.NEXTVAL FROM dual",
                    "sp_reporte_163(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_162 WHERE sec_proceso = "});

        //AHERRENO 23/02/2011
        //Reporte 164
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_164__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_164__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_164__PARAM_PCIRCULONOMBRE);
        listaParametros.add(AWReportes.REPORTE_164__PARAM_PCUANTIA);
        mapaReportes.put(AWReportes.REPORTE_164__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_164.NEXTVAL FROM dual",
                    "sp_reporte_164(?, ?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_164 WHERE sec_proceso = "});

        //AHERRENO 14/03/2011
        //Reporte 165
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_165__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_165__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_165__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_165__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_165.NEXTVAL FROM dual",
                    "sp_reporte_165(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_165 WHERE sec_proceso = "});

        //AHERRENO 14/03/2011
        //Reporte 166
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_166__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_166__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_166__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_166__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_166.NEXTVAL FROM dual",
                    "sp_reporte_166(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_166 WHERE sec_proceso = "});

        //AHERRENO 14/03/2011
        //Reporte 167
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_167__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_167__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_167__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_167__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_167.NEXTVAL FROM dual",
                    "sp_reporte_167(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_167 WHERE sec_proceso = "});

        //AHERRENO 14/03/2011
        //Reporte 168
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_168__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_168__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_168__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_168__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_168.NEXTVAL FROM dual",
                    "sp_reporte_168(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_168 WHERE sec_proceso = "});
        
        //CTORRES 24/04/2013
        //Reporte 175
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_175__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_175__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_175__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_175__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_175.NEXTVAL FROM dual",
                    "sp_reporte_175(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_175 WHERE sec_proceso = "});
        
        //CTORRES 24/04/2013
        //Reporte 176
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_176__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_176__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_176__PARAM_PCIRCULONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_176__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_176.NEXTVAL FROM dual",
                    "sp_reporte_176(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_176 WHERE sec_proceso = "});

        //AHERRENO 27/12/2011
        //Reporte E04
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_E04__PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_E04__PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_E04__PARAM_IDCIRCULO);
        listaParametros.add(AWReportes.REPORTE_E04__PARAM_USUARIONOMBRE);
        mapaReportes.put(AWReportes.REPORTE_E04__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_E04.NEXTVAL FROM dual",
                    "sp_reporte_E04(?, ?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_E04 WHERE sec_proceso = "});
        
        //AHERRENO 01/06/2012
        //Reporte 169
        //Se obtienen los par?metros para enviar al procedimiento
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_169__PARAM_TURNO);
        mapaReportes.put(AWReportes.REPORTE_169__PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_169.NEXTVAL FROM dual",
                    "sp_reporte_169(?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_169 WHERE sec_proceso = "});        
		
        /**
        * Autor: Edgar Lora
        * Mantis: 0011319
        * Requerimiento: 075_151
        */
        listaParametros = new ArrayList();
        listaParametros.add(AWReportes.REPORTE_170_PARAM_PFECHAINI);
        listaParametros.add(AWReportes.REPORTE_170_PARAM_PFECHAFIN);
        listaParametros.add(AWReportes.REPORTE_170_PARAM_IDCIRCULO);
        mapaReportes.put(AWReportes.REPORTE_170_PARAM_CMDKEY, new Object[]{
                    "SELECT seq_reporte_170.NEXTVAL FROM dual",
                    "sp_reporte_170(?, ?, ?, ?)", listaParametros,
                    "DELETE FROM sir_op_reporte_170 WHERE sec_proceso = "});
        //--------------------------------------------------------------------------------
    }

    /**
     * Crea una nueva instancia de <code>JDOProcesosDAO</code>
     */
    public JDOReportesJasperDAO() {
    }

    /**
     * Obtiene y avanza la secuencia de los recibos de certificados masivos, de acuerdo
     * a los parametros recibidos.
     * @param circuloId El identificador del <code>Circulo</code> en el que se va a
     * expedir el recibo de certificados masivos.
     * @param year El a?o en el que se va a expedir el recibo de certificados masivos.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public byte[] generarReporteJasper(String nombreReporte,
            String rutaReportes, HashMap parametrosJasper) throws DAOException {
        byte[] rta = null;
        PersistenceManager pm = null;
        VersantPersistenceManager pm2 = null;

        Connection conn = null;
        Statement st = null;
        PreparedStatement pst = null;
        CallableStatement cst = null;
        ResultSet rs = null;
        
        /**
             * @Autor:  Edgar Lora
             * @Cambio: Este bloque de codigo se incluyo para en caso tal ocurran excepciones en los reportes
             *          aun asi se borre la informacion de este y no afecte los otros.
             * @fecha:  07/02/2012
             */
        boolean seBorraronDatos = false;
        //Esta variable es necesario que sea movida para que pueda ser accesada desde el finally.
        long secReporte = 0;

        if (nombreReporte == null) {

            throw new DAOException("El nombre del reporte es Invalido");
        }
        if (parametrosJasper == null) {
            throw new DAOException("No se indicaron Par?metros");
        }
        if (parametrosJasper == null) {
            throw new DAOException("La ruta de reportes es vacia");
        }


        try {

            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            //que se bloquee la tabla la cual  nos
            //Se hace el cambio de tipo de bloqueo pesimista para
            //provee el secuencial
            pm2 = (VersantPersistenceManager) pm;
            conn = pm2.getJdbcConnection(null);

            if (conn.isClosed()) {
                Class.forName(pm2.getConnectionDriverName(null));
                conn = DriverManager.getConnection(pm2.getConnectionURL(null));
            }

            Object[] datosReporte = (Object[]) mapaReportes.get(nombreReporte);
            if (datosReporte != null) {

                String sqlSec = (String) datosReporte[0];
                pst = conn.prepareStatement(sqlSec);
                rs = pst.executeQuery();
                if (rs.next()) {
                    secReporte = rs.getLong(1);
                }

                String nombrePl = (String) datosReporte[1];
                logEstatico.debug("Invocando procedimiento almacenado: " + nombrePl);
                cst = conn.prepareCall("{call " + nombrePl + "}");

                List listaParams = (List) datosReporte[2];
                int i = 0;
                for (; i < listaParams.size(); i++) {
                    String nombreParam = (String) listaParams.get(i);
                    String valorParam = (String) parametrosJasper.get(nombreParam.toUpperCase());
                    cst.setString(i + 1, valorParam);
                    logEstatico.debug("\tPar?metro " + i + ": " + valorParam);
                }
                cst.setLong(++i, secReporte);
                 cst.execute();

                parametrosJasper.put("P_SEC_PROCESO", Long.toString(secReporte));

            }

            //nombreReporte = nombreReporte.replaceAll(".report", ".jasper");

            logEstatico.debug("generando reporte: " + nombreReporte);

            Set claves = parametrosJasper.keySet();

            for (Iterator itera = claves.iterator();
                    itera.hasNext();) {
                String clave = (String) itera.next();
                logEstatico.debug("\tParametro " + clave + " : " + parametrosJasper.get(clave));
            }

             JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReportes + nombreReporte, parametrosJasper, conn);

            /*AHERRENO 28/09/2009*/
            // Se lee el fichero de propiedades "gov.sir.print.properties" con los nombres de los reportes en excel.
            // El listado de reportes debe estar separado por comas en el fichero.
            // Se verifica si el nombre del reporte a ejecutar se encuentra en el fichero.
            // Si el nombre del reporte se encuentra en el fichero se renderisa el reporte en formato excel
            // de lo contrario en formato PDF.

            PrintJobsProperties prop;
            prop = PrintJobsProperties.getInstancia();
            String[] reportesExcel = (prop.getProperty(PrintJobsProperties.REPORTES_EXCEL)).split(",");
            int tam = reportesExcel.length;
            boolean isExcel = false;

            for(int i = 0; i< tam; i++){
                if(nombreReporte.equals(reportesExcel[i]))
                    isExcel = true;
            }
            if (isExcel) {
                             
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
                exporterXLS.exportReport();
                
                rta = byteArrayOutputStream.toByteArray();

            //*****************************************************************

            } else {
                rta = JasperExportManager.exportReportToPdf(jasperPrint);
            }


                if (datosReporte != null) {
                st = conn.createStatement();
                String sqlDel = (String) datosReporte[3];
                st.executeUpdate(sqlDel + secReporte);
                /**
                 * @Autor:  Edgar Lora
                 * @Cambio: Este bloque de codigo se incluyo para en caso tal ocurran excepciones en los reportes
                 *          aun asi se borre la informacion de este y no afecte los otros.
                 * @fecha:  07/02/2012
                 */
                seBorraronDatos = true;
            }

            pm.currentTransaction().commit();
        } //Excepci?n JDO
        catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            logEstatico.error(e);
            throw new DAOException(
                    "No se pudo generar el reporte debido a problemas en Base de Datos",
                    e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            logEstatico.error(e);
            throw new DAOException(
                    "No se pudo generar el reporte debido a problemas en Base de Datos",
                    e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            logEstatico.error(e);
            throw new DAOException(
                    "No se pudo generar el reporte debido a problemas en Base de Datos",
                    e);
        } //Otra Excepci?n
        catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            logEstatico.error(t);
            throw new DAOException(
                    "No se pudo generar el reporte", t);
        } //Cerrar la transacci?n
        finally {

            /**
             * @Autor:  Edgar Lora
             * @Cambio: Este bloque de codigo se incluyo para en caso tal ocurran excepciones en los reportes
             *          aun asi se borre la informacion de este y no afecte los otros.
             * @fecha:  07/02/2012
             */
            if(!seBorraronDatos){
                try{                    
                    borrarDatosReporte(nombreReporte, secReporte);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return rta;
    }
    
    /**
             * @Autor:  Edgar Lora
             * @Cambio: Este procedimiento se incluy? para en caso tal ocurran excepciones en los reportes
             *          Aun as? se borre la informaci?n de este y no afecte los otros.
             * @fecha:  07/02/2012
             */
    public void borrarDatosReporte(String nombreReporte, long secReporte) throws DAOException, SQLException, ClassNotFoundException{
        PersistenceManager pm = null;
        VersantPersistenceManager pm2 = null;

        Connection conn = null;
        Statement st = null;
        PreparedStatement pst = null;
        CallableStatement cst = null;
        ResultSet rs = null;
        
        pm = AdministradorPM.getPM();
        pm.currentTransaction().begin();
        
        pm2 = (VersantPersistenceManager) pm;
        conn = pm2.getJdbcConnection(null);

        if (conn.isClosed()) {
            Class.forName(pm2.getConnectionDriverName(null));
            conn = DriverManager.getConnection(pm2.getConnectionURL(null));
        }
        
        Object[] datosReporte = (Object[]) mapaReportes.get(nombreReporte);
        if (datosReporte != null) {

                if(st == null){
                    st = conn.createStatement();
                }
                String sqlDel = (String) datosReporte[3];
                st.executeUpdate(sqlDel + secReporte);

        }
        pm.currentTransaction().commit();
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (cst != null) {
                cst.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
        }finally{
            if (pm2 != null) {
                pm2.close();
            }
            if (pm != null) {
                pm.close();
            }
        }
    }
}