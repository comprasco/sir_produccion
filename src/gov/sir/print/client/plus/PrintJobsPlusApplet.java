package gov.sir.print.client.plus;

import gov.sir.core.negocio.modelo.CirculoImpresora;
import gov.sir.core.negocio.modelo.TipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CUsuario;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWImpresion;
import gov.sir.print.client.Log;
import gov.sir.print.client.Receiver;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessControlException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import javax.swing.ImageIcon;

/**
 * Applet de impresion automatica para oficinas.
 *
 * @author jfrias
 *
 */
public class PrintJobsPlusApplet extends Applet implements Observer {

    private java.awt.Button agregarImpresoraButton;

    private java.awt.Button eliminarImpresoraButton;

    private java.awt.Button actualizarImpresorasButton;

    // private java.awt.Button buscarDisponiblesButton;
    private java.awt.Choice tiposImpresionChoice;

    private java.awt.Choice predeterminadaChoice;

    private java.awt.Label tiposImpresionLabel;

    private java.awt.Label impresorasActualesLabel;

    private java.awt.Label otrasImpresorasLabel;

    private java.awt.Label estado;

    private java.awt.Label predeterminadaLabel;

    private java.awt.List impresorasActualesList;

    private java.awt.List impresorasDisponiblesList;

    private java.awt.Canvas canvas;

    private ImpresorasListModel model = new ImpresorasListModel();

    private java.awt.Label errorConfLabel;

    private java.awt.Label peticionLabel;

    private java.awt.List errorConfiguracion;

    private String[] jCheckReglas;

    protected ImageIcon icon_busy;

    protected ImageIcon icon_ready;

    protected ImageIcon icon_wait;

    protected String remoteHost;

    protected int remotePort;

    protected String UID;

    protected String idUsuario = "";

    protected Receiver receiver;

    protected boolean clienteRegistrado;

    private Log logger;

    /*
	 * private JButton jBotonImpresoras; private JButton jBotonSetReglas;
	 * private JCheckBox[] jCheckReglas; private JPanel jPanelOpciones; private
	 * JPanel jPanelInferior; private JSplitPane jSplit; private JTree
	 * jTreeImpresoras; private JScrollPane jScrollImpresoras;
     */
    private PrintManager manager;

    private String IMPRESORA_ID;

    private int localPortLow;

    private int localPortHigh;

    public PrintJobsPlusApplet() {
        IMPRESORA_ID = "";

        try {

            logger = new Log(PrintJobsPlusApplet.class);
            logger.print("PrintJobsPlusApplet Constructor");

            System.setProperty("java.security.manager", "");
            System.setProperty("java.security.policy", gov.sir.print.client.plus.PrintJobsPlusApplet.class
                    .getResource("javaCliente.policy").toString());

            Policy.getPolicy().refresh();
            System.out.println("SE ACTUALIZARON LAS POLITICAS DE SEGURIDAD.");
            logger.print("SE ACTUALIZARON LAS POLITICAS DE SEGURIDAD.");
            logger.print(gov.sir.print.client.plus.PrintJobsPlusApplet.class
                    .getResource("javaCliente.policy").toString());

        } catch (Exception e) {
            System.out.println("NO SE PUDO ACTUALIZAR LAS POLITICAS DE SEGURIDAD.");
            e.printStackTrace();
        }
    }

    /**
     * Los parametros LOCAL_PORT_LOW y LOCAL_PORT_HIGH corresponden a un rango
     * de puertos que puede utilizar el applet para establecer su servidor de
     * sockets. Es necesario utilizar un rango para poder establecer una regla
     * en el firewall. (No se pueden dejar todos los puertos abiertos). El
     * servidor de sockets arrancara con el puerto indicado por el parametro
     * LOCAL_PORT_LOW y si este puerto esta ocupado, reintentará con el
     * siguiente hasta alcanzar el puerto indicado por LOCAL_PORT_HIGH. Si se
     * alcanza el puerto LOCAL_PORT_HIGH y no se ha logrado crear el servidor de
     * sockets (el puerto esta ocupado por otra aplicacion) entonces se arroja
     * una excepción de tiempo de ejecucion indicando esta situación.
     *
     * La invarianteo LOCAL_PORT_LOW <= LOCAL_PORT_HIGH se mantiene durante el
     * ciclo de vida del applet.
     *
     */
    public void init() {
        System.out.println("IMPRESION PLUS INIT servidor de impresión");
        setBackground(new Color(205, 216, 237));
        setForeground(new Color(93, 104, 125));
        logger.print("remoteHost = " + getParameter("REMOTE_HOST"));
        logger.print("REMOTE_PORT = " + getParameter("REMOTE_PORT"));
        logger.print("UID = " + getParameter("UID"));
        remoteHost = getParameter("REMOTE_HOST");
        remotePort = Integer.parseInt(getParameter("REMOTE_PORT"));
        UID = getParameter("UID");
        idUsuario = getParameter("ID_USUARIO");
        localPortLow = Integer.parseInt(getParameter("LOCAL_PORT_LOW"));
        localPortHigh = Integer.parseInt(getParameter("LOCAL_PORT_HIGH"));
        manager = new PrintManager();
        manager.setReglas(cargarListaReglas());
        // notificarAlServidor();
        manager.setConfiguracion(cargarConfiguracion());
        manager.setParametros(cargarParametros());
        URL servidor = this.getCodeBase();
        receiver = new Receiver(remoteHost, remotePort, manager, UID,
                localPortLow, localPortHigh, servidor, idUsuario);

        initGUI();
        ImpresorasToListAdaptor adaptor = new ImpresorasToListAdaptor(model,
                impresorasActualesList, impresorasDisponiblesList,
                predeterminadaChoice, errorConfLabel, errorConfiguracion,
                peticionLabel);
        model.addObserver(adaptor);
        model.cargarImpresorasDisponibles();
        model.setConfiguracionImpresoras(manager.getConfiguracion());

        try {
            int tiempoReimpresion = 120000;
            Map parametros = manager.getParametros();
            if (parametros != null && parametros.containsKey(COPLookupCodes.TIEMPO_ESPERA_REIMPRESION_FALLIDOS)) {
                String tiempoEsperaStr = (String) parametros.get(COPLookupCodes.TIEMPO_ESPERA_REIMPRESION_FALLIDOS);
                try {
                    tiempoReimpresion = Integer.parseInt(tiempoEsperaStr);
                } catch (Throwable th) {
                    System.out.println("IMPRESION PLUS INIT_ERROR_1 servidor de impresión " + th);
                    logger.print("Error convertiendo a entero tiempoEsperaFalidos");
                }
            }

            int tiempoReimpresionPrimeraVez = 10000;
            if (parametros != null && parametros.containsKey(COPLookupCodes.TIEMPO_ESPERA_REIMPRESION_FALLIDOS_PRIMERA_VEZ)) {
                String tiempoEsperaStr = (String) parametros.get(COPLookupCodes.TIEMPO_ESPERA_REIMPRESION_FALLIDOS_PRIMERA_VEZ);
                try {
                    tiempoReimpresionPrimeraVez = Integer.parseInt(tiempoEsperaStr);
                } catch (Throwable th) {
                    System.out.println("IMPRESION PLUS INIT_ERROR_2 servidor de impresión " + th);
                    logger.print("Error convertiendo a entero tiempoEsperaFalidos");
                }
            }

            ReimprimirListener reimpresion = new ReimprimirListener(this
                    .getCodeBase());
            reimpresion.tiempoEsperaReimpresion = tiempoReimpresion;
            reimpresion.tiempoEsperaReimpresionPrimeraVez = tiempoReimpresionPrimeraVez;
            new Thread(reimpresion).start();
        } catch (Exception e) {
            System.out.println("IMPRESION PLUS INIT_ERROR_3 servidor de impresión " + e);
        }
    }

    /**
     *
     */
    private Map cargarConfiguracion() {
        System.out.println("IMPRESION PLUS CARGAR CONFIG servidor de impresión ");
        URL servidor = this.getCodeBase();
        String contextoWeb = servidor.getPath().substring(0,
                servidor.getPath().indexOf("/", 1));
        StringBuffer command = new StringBuffer("http://" + servidor.getHost()
                + ":" + servidor.getPort() + contextoWeb
                + "/infoImpresion.do?UID=" + UID + "&" + WebKeys.ACCION + "="
                + AWImpresion.CARGAR_CONFIGURACION_ACTUAL);
        Map configuracion = null;
        try {
            URL url = new URL(command.toString());
            URLConnection sirConnection = url.openConnection();
            sirConnection.setUseCaches(false);
            ObjectInputStream ois = new ObjectInputStream(sirConnection
                    .getInputStream());
            configuracion = (Map) ois.readObject();
            ois.close();
        } catch (MalformedURLException e) {
            System.out.println("IMPRESION PLUS ERROR_CARGAR_CONFIG1 servidor de impresión " + e);
            logger.print("ERROR:" + e);
        } catch (IOException e1) {
            System.out.println("IMPRESION PLUS ERROR_CARGAR_CONFIG2 servidor de impresión " + e1);
            logger.print("ERROR:" + e1);
        } catch (ClassNotFoundException e) {
            System.out.println("IMPRESION PLUS ERROR_CARGAR_CONFIG3 servidor de impresión " + e);
            logger.print("ERROR:" + e);
        }
        Iterator it = configuracion.keySet().iterator();
        logger.print("****CONFIGURACION DESDE NEGOCIO");
        while (it.hasNext()) {
            TipoImprimible ti = (TipoImprimible) it.next();
            logger.print("TIPO_IMP:" + ti.getIdTipoImprimible());
            Iterator itImpr = ((List) configuracion.get(ti)).iterator();
            while (itImpr.hasNext()) {
                CirculoImpresora ci = (CirculoImpresora) itImpr.next();
                logger.print("IMPRESORA:" + ci.getIdImpresora());
            }
        }
        return configuracion;

    }

    private Map cargarParametros() {
        System.out.println("IMPRESION PLUS CARGAR_PARAMETROS servidor de impresión ");
        URL servidor = this.getCodeBase();
        String contextoWeb = servidor.getPath().substring(0,
                servidor.getPath().indexOf("/", 1));
        StringBuffer command = new StringBuffer("http://" + servidor.getHost()
                + ":" + servidor.getPort() + contextoWeb
                + "/infoImpresion.do?" + WebKeys.ACCION + "="
                + AWImpresion.CARGAR_PARAMETROS_CONFIGURACION);
        Map configuracion = null;
        try {
            URL url = new URL(command.toString());
            URLConnection sirConnection = url.openConnection();
            sirConnection.setUseCaches(false);
            ObjectInputStream ois = new ObjectInputStream(sirConnection
                    .getInputStream());
            configuracion = (Map) ois.readObject();
            ois.close();
        } catch (MalformedURLException e) {
            System.out.println("IMPRESION PLUS ERROR_CARGAR_PARAMETROS1 servidor de impresión " + e);
            logger.print("ERROR:" + e);
        } catch (IOException e1) {
            System.out.println("IMPRESION PLUS ERROR_CARGAR_PARAMETROS2 servidor de impresión " + e1);
            logger.print("ERROR:" + e1);
        } catch (ClassNotFoundException e) {
            System.out.println("IMPRESION PLUS ERROR_CARGAR_PARAMETROS3 servidor de impresión " + e);
            logger.print("ERROR:" + e);
        }
        return configuracion;

    }

    public void start() {
        System.out.println("IMPRESION PLUS START servidor de impresión ");
        receiver.addObserver(this);
        receiver.registrar();
        registrarCliente();
    }

    /**
     * Inicia la GUI.
     */
    public void initGUI() {
        System.out.println("IMPRESION PLUS INIT_GUI servidor de impresión ");
        tiposImpresionLabel = new java.awt.Label();
        impresorasActualesLabel = new java.awt.Label();
        otrasImpresorasLabel = new java.awt.Label();
        tiposImpresionChoice = new java.awt.Choice();
        impresorasActualesList = new java.awt.List();
        impresorasDisponiblesList = new java.awt.List();
        agregarImpresoraButton = new java.awt.Button();
        eliminarImpresoraButton = new java.awt.Button();
        actualizarImpresorasButton = new java.awt.Button();
        // buscarDisponiblesButton = new java.awt.Button();
        predeterminadaChoice = new java.awt.Choice();
        predeterminadaLabel = new java.awt.Label();
        errorConfiguracion = new java.awt.List();
        errorConfLabel = new java.awt.Label();
        peticionLabel = new java.awt.Label();
        setLayout(null);

        tiposImpresionLabel.setText("Tipo de Impresi\u00f3n");
        tiposImpresionLabel.setFont(new Font("Arial", Font.BOLD, 11));
        add(tiposImpresionLabel);
        tiposImpresionLabel.setBounds(30, 20, 110, 20);

        add(tiposImpresionChoice);
        tiposImpresionChoice.setBounds(140, 20, 220, 20);

        impresorasActualesLabel
                .setText("Impresoras para el Tipo de Impresi\u00f3n");
        impresorasActualesLabel.setFont(new Font("Arial", Font.BOLD, 11));
        add(impresorasActualesLabel);
        impresorasActualesLabel.setBounds(30, 60, 220, 20);

        add(impresorasActualesList);
        impresorasActualesList.setBounds(30, 80, 220, 220);

        otrasImpresorasLabel.setText("Otras Impresoras Disponibles");
        otrasImpresorasLabel.setFont(new Font("Arial", Font.BOLD, 11));
        add(otrasImpresorasLabel);
        otrasImpresorasLabel.setBounds(330, 60, 220, 20);

        add(impresorasDisponiblesList);
        impresorasDisponiblesList.setBounds(330, 80, 220, 220);

        agregarImpresoraButton.setLabel("<<");
        add(agregarImpresoraButton);
        agregarImpresoraButton.setBounds(275, 170, 30, 24);

        eliminarImpresoraButton.setLabel(">>");
        add(eliminarImpresoraButton);
        eliminarImpresoraButton.setBounds(275, 210, 30, 24);

        actualizarImpresorasButton.setLabel("Actualizar Informaci\u00f3n");
        add(actualizarImpresorasButton);
        actualizarImpresorasButton.setBounds(220, 480, 140, 30);

        errorConfLabel.setText("Hay Tipos de Impresión sin Configurar:");
        errorConfLabel.setFont(new Font("Arial", Font.BOLD, 11));
        add(errorConfLabel);
        errorConfLabel.setBounds(330, 320, 250, 20);

        add(errorConfiguracion);
        errorConfiguracion.setBounds(330, 340, 220, 100);

        peticionLabel.setText("POR FAVOR CONFIGURARLOS!!");
        peticionLabel.setForeground(new Color(225, 4, 60));
        add(peticionLabel);
        peticionLabel.setBounds(350, 440, 250, 20);

        /*
		 * buscarDisponiblesButton.setLabel("Buscar Impresoras");
		 * add(buscarDisponiblesButton); buscarDisponiblesButton.setBounds(380,
		 * 420, 122, 24);
         */
        add(predeterminadaChoice);
        predeterminadaChoice.setBounds(30, 420, 220, 20);

        predeterminadaLabel.setText("Escoger Impresora Predeterminada");
        predeterminadaLabel.setFont(new Font("Arial", Font.BOLD, 11));
        add(predeterminadaLabel);
        predeterminadaLabel.setBounds(30, 390, 200, 20);

        tiposImpresionChoice.add("--Seleccione una Opción--");
        Iterator itReglas = manager.getReglas().iterator();
        while (itReglas.hasNext()) {
            String nomRegla = ((TipoImprimible) itReglas.next()).getNombre();
            tiposImpresionChoice.add(nomRegla);
        }

        tiposImpresionChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cargarImpresorasActuales();
            }
        });

        agregarImpresoraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarImpresora();
            }
        });

        eliminarImpresoraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarImpresora();
            }
        });

        actualizarImpresorasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarImpresoras();
            }
        });

        predeterminadaChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cambiarPredeterminada();
            }
        });

        /*
		 * buscarDisponiblesButton.addActionListener(new ActionListener(){
		 * public void actionPerformed(ActionEvent e) {
		 * buscarImpresorasDisponibles(); } });
         */
    }

    private void cambiarPredeterminada() {
        System.out.println("IMPRESION PLUS CARGAR_PREDE servidor de impresión ");
        String tipoImp = tiposImpresionChoice.getSelectedItem();
        if (!tipoImp.equals("--Seleccione una Opción--")) {
            String predet = predeterminadaChoice.getSelectedItem();
            manager.setImpresoraPredeterminada(tipoImp, predet);
        }
    }

    private void buscarImpresorasDisponibles() {
        System.out.println("IMPRESION PLUS BUSCAR_IMPRE servidor de impresión ");
        model.cargarImpresorasDisponibles();
    }

    private void actualizarImpresoras() {
        System.out.println("IMPRESION PLUS ACTUALIZAR_IMPRE servidor de impresión ");
        Map config = manager.getConfiguracion();

        try {
            URL servidor = this.getCodeBase();
            String contextoWeb = servidor.getPath().substring(0,
                    servidor.getPath().indexOf("/", 1));

            String actualizarReglas = "http://" + servidor.getHost() + ":"
                    + servidor.getPort() + contextoWeb + "/infoImpresion.do?"
                    + WebKeys.ACCION + "=" + AWImpresion.ACTUALIZAR_REGLAS
                    + "&" + CImpresion.UID + "=" + UID;
            String parametros = "";
            Iterator itReglas = config.keySet().iterator();
            int i = 0;
            while (itReglas.hasNext()) {
                TipoImprimible regla = (TipoImprimible) itReglas.next();
                parametros = parametros.concat("&REGLA_" + i + "="
                        + regla.getIdTipoImprimible());
                List impresoras = (List) config.get(regla);
                int j = 0;
                Iterator itImpresoras = impresoras.iterator();
                while (itImpresoras.hasNext()) {
                    CirculoImpresora circImp = (CirculoImpresora) itImpresoras
                            .next();
                    String impresoraSinEspacios = circImp.getIdImpresora()
                            .replaceAll(" ", "_");
                    if (circImp.isPredeterminada()) {
                        parametros = parametros.concat("&IMPR_" + j + "_REGLA_"
                                + i + "_PRED=" + impresoraSinEspacios);
                    } else {
                        parametros = parametros.concat("&IMPR_" + j + "_REGLA_"
                                + i + "_=" + impresoraSinEspacios);
                    }
                    j++;
                }
                i++;
            }
            actualizarReglas = actualizarReglas.concat(parametros);
            URL urlReglas = new URL(actualizarReglas);
            URLConnection connectionDescarga = urlReglas.openConnection();
            connectionDescarga.setUseCaches(false);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connectionDescarga.getInputStream()));
            in.close();
        } catch (MalformedURLException e) {
            System.out.println("IMPRESION PLUS ERROR_ACTUALIZAR_IMPRE1 servidor de impresión " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IMPRESION PLUS ERROR_ACTUALIZAR_IMPRE2 servidor de impresión " + e);
            e.printStackTrace();
        }
    }

    private void eliminarImpresora() {
        System.out.println("IMPRESION PLUS ELIMINAR_IMPRE servidor de impresión ");
        String impresora = impresorasActualesList.getSelectedItem();
        String predeter = predeterminadaChoice.getSelectedItem();
        if (impresora != null
                && !impresora.trim().equals("")
                && !tiposImpresionChoice.getSelectedItem().equals(
                        "--Seleccione una Opción--")) {
            model.eliminarImpresora(impresora, predeter);
            manager.eliminarImpresoraRegla(tiposImpresionChoice
                    .getSelectedItem(), impresora);
            model.setConfiguracionImpresoras(manager.getConfiguracion());
        }
    }

    private void agregarImpresora() {
        System.out.println("IMPRESION PLUS AGREGAR_IMPRE servidor de impresión ");
        String impresora = impresorasDisponiblesList.getSelectedItem();
        if (impresora != null
                && !impresora.trim().equals("")
                && !tiposImpresionChoice.getSelectedItem().equals(
                        "--Seleccione una Opción--")) {
            model.agregarImpresora(impresora);
            manager.agregarImpresoraRegla(tiposImpresionChoice
                    .getSelectedItem(), impresora, UID);
            model.setConfiguracionImpresoras(manager.getConfiguracion());
        }
    }

    private void cargarImpresorasActuales() {
        System.out.println("IMPRESION PLUS CARGAR_IMPRE_ACTUAL servidor de impresión ");
        String tipo = tiposImpresionChoice.getSelectedItem();
        if (tipo.equals("--Seleccione una Opción--")) {
            model.setImpresorasActuales(new ArrayList());
        } else {
            Iterator itReglas = manager.getReglas().iterator();
            while (itReglas.hasNext()) {
                TipoImprimible tipoImp = (TipoImprimible) itReglas.next();
                if (tipo.equals(tipoImp.getNombre())) {
                    List impresoras = (List) manager.getConfiguracion().get(
                            tipoImp);
                    model.setImpresorasActuales(impresoras);
                    break;
                }
            }
        }

    }

    /**
     * Metodo main de pruebas
     *
     */
    public static void main(String[] args) {
        System.out.println("IMPRESION PLUS MAIN servidor de impresión ");
        showGUI();
    }

    /**
     * Crea una nueva instancia de esta clase y la muestra dentro de un JFrame.
     */
    public static void showGUI() {
        System.out.println("IMPRESION PLUS SHOW_GUI servidor de impresión ");
        try {
            javax.swing.JFrame frame = new javax.swing.JFrame();
            PrintJobsPlusApplet inst = new PrintJobsPlusApplet();
            inst.init();
            frame.setContentPane(inst);
            frame.getContentPane().setSize(inst.getSize());
            frame
                    .setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("IMPRESION PLUS ERROR_SHOW_GUI servidor de impresión ");
            e.printStackTrace();
        }
    }

    private List cargarListaReglas() {
        System.out.println("IMPRESION PLUS CARGAR_LISTAS servidor de impresión ");
        List reglas = null;
        try {
            URL servidor = this.getCodeBase();
            String contextoWeb = servidor.getPath().substring(0,
                    servidor.getPath().indexOf("/", 1));

            String consultarReglas = "http://" + servidor.getHost() + ":"
                    + servidor.getPort() + contextoWeb + "/infoImpresion.do?"
                    + WebKeys.ACCION + "=" + AWImpresion.CONSULTAR_LISTA_REGLAS
                    + "&" + CImpresion.UID + "=" + UID
                    + "&" + "USUARIO_ID=" + idUsuario;

            logger.print(" consultarReglas url --> " + consultarReglas);
            URL urlReglas = new URL(consultarReglas);
            URLConnection connectionDescarga = urlReglas.openConnection();
            connectionDescarga.setUseCaches(false);
            ObjectInputStream ois = new ObjectInputStream(connectionDescarga
                    .getInputStream());
            reglas = (List) ois.readObject();
            logger.print(" consultarReglas respuesta --> " + reglas);
            ois.close();
        } catch (MalformedURLException e) {
            System.out.println("IMPRESION PLUS CARGAR_LISTAS1 servidor de impresión " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IMPRESION PLUS CARGAR_LISTAS2 servidor de impresión " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("IMPRESION PLUS CARGAR_LISTAS3 servidor de impresión " + e);
            e.printStackTrace();
        }
        return reglas;
    }

    /**
     * Registra en el servidor la dirección IP del cliente para que el servidor
     * la conozca y sepa a que dirección IP enviar una impresión determinada
     */
    private void registrarCliente() {
        System.out.println("IMPRESION PLUS REGISTRAR_CLIENTE servidor de impresión ");
        URL servidor = this.getCodeBase();
        String contextoWeb = servidor.getPath().substring(0,
                servidor.getPath().indexOf("/", 1));

        StringBuffer command = null;
        String ipLocal = "127.0.0.1";
        String ip192 = "192";
        String parametro = "";
        String TIPO_ENVIO = "TIPO_ENVIO";
        String IPS_CLIENTE = "IPS_CLIENTE";

        if (receiver.getLocalHost().equals(ipLocal)
                || receiver.getLocalHost().startsWith(ip192)) {
            String ipCliente = getIPAddress();
            if (ipCliente == null || ipCliente.equals("")) {
                ipCliente = ipLocal;
            }
            parametro = "POR_APPLET";
            command = new StringBuffer("http://" + servidor.getHost() + ":"
                    + servidor.getPort() + contextoWeb + "/infoImpresion.do?"
                    + WebKeys.ACCION + "=" + AWImpresion.REGISTRAR_CLIENTE
                    + "&" + CImpresion.UID + "=" + receiver.getUID() + "&"
                    + CImpresion.LOCALHOST + "=" + ipCliente + "&"
                    + CUsuario.ID_USUARIO + "=" + idUsuario + "&"
                    + CImpresion.LISTENINGPORT + "="
                    + receiver.getListeningPort() + "&" + TIPO_ENVIO + "="
                    + parametro + "&" + IPS_CLIENTE + "="
                    + receiver.getIpsCliente());
        } else {
            parametro = "POR_RECEIVER";
            command = new StringBuffer("http://" + servidor.getHost() + ":"
                    + servidor.getPort() + contextoWeb + "/infoImpresion.do?"
                    + WebKeys.ACCION + "=" + AWImpresion.REGISTRAR_CLIENTE
                    + "&" + CImpresion.UID + "=" + receiver.getUID() + "&"
                    + CUsuario.ID_USUARIO + "=" + idUsuario + "&"
                    + CImpresion.LOCALHOST + "=" + receiver.getLocalHost()
                    + "&" + CImpresion.LISTENINGPORT + "="
                    + receiver.getListeningPort() + "&" + TIPO_ENVIO + "="
                    + parametro + "&" + IPS_CLIENTE + "="
                    + receiver.getIpsCliente());
        }

        logger.print(command.toString());

        try {
            // SE DETERMINA SI EL CLIENTE DESCARGO EL APPLET, SI SE GENERA UNA
            // EXCEPCIÓN SIGNIFICA QUE EL USUARIO AUN NO LO HA FIRMADO, SI POR
            // EL CONTRARIO NO OCURRE EXCEPCIÓN
            // SE PROCEDE A GUARGAR DICHA DIRECCIÓN.
            String verificarDescargaApplet = "http://" + servidor.getHost()
                    + ":" + servidor.getPort() + contextoWeb
                    + "/infoImpresion.do?" + WebKeys.ACCION + "="
                    + AWImpresion.VERIFICAR_DESCARGA_APPLET;
            URL urlVerificacion = new URL(verificarDescargaApplet);
            URLConnection connectionVerificacion = urlVerificacion
                    .openConnection();
            connectionVerificacion.setUseCaches(false);
            BufferedReader bufferVerificacion = new BufferedReader(
                    new InputStreamReader(connectionVerificacion
                            .getInputStream()));
            logger.print(verificarDescargaApplet);

            URL url = new URL(command.toString());
            URLConnection sirConnection = url.openConnection();
            sirConnection.setUseCaches(false);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    sirConnection.getInputStream()));
            in.close();

        } catch (AccessControlException e) {
            System.out.println("IMPRESION PLUS ERROR_REGISTRAR_CLIENTE1 servidor de impresión " + e);
            logger.print("ERROR (AccessControlException):" + e);
        } catch (MalformedURLException e) {
            System.out.println("IMPRESION PLUS ERROR_REGISTRAR_CLIENTE2 servidor de impresión " + e);
            logger.print("ERROR:" + e);
        } catch (IOException e1) {
            System.out.println("IMPRESION PLUS ERROR_REGISTRAR_CLIENTE3 servidor de impresión " + e1);
            logger.print("ERROR:" + e1);
        }

    }

    public class ReimprimirListener implements Runnable {

        URL servidor = null;

        boolean primeraVez = true;

        int tiempoEsperaReimpresion;

        int tiempoEsperaReimpresionPrimeraVez;

        public ReimprimirListener(URL servidor) {
            System.out.println("IMPRESION PLUS ReimprimirListener servidor de impresión ");
            this.servidor = servidor;
        }

        public void run() {

            while (true) {
                try {
                    reimprimirFallidos();
                } catch (Exception e) {
                    System.out.println("IMPRESION PLUS ERROR_reimprimirFallidos servidor de impresión " + e);
                    e.printStackTrace();
                }
            }
        }

        private void reimprimirFallidos() throws Exception {
            if (primeraVez) {
                primeraVez = false;
                Thread.sleep(tiempoEsperaReimpresionPrimeraVez);
            } else {
                Thread.sleep(tiempoEsperaReimpresion);
            }

            reimprimir();
        }

        private void reimprimir() {
            System.out.println("IMPRESION PLUS REIMPRIMIR servidor de impresión ");

            String tipoCliente = "";
            if (UID.length() <= 3) {
                tipoCliente = CImpresion.CLIENTE_ADMINISTRADOR;
            } else {
                tipoCliente = CImpresion.CLIENTE_LOCAL;
            }

            try {
                List impresionesFallidas = null;
                String contextoWeb = servidor.getPath().substring(0,
                        servidor.getPath().indexOf("/", 1));

                // SE SOLICITA AL SERVIDOR LA LISTA DE ID-IMPRIMIBLES QUE NO SE
                // HAN IMPRESO, POR EL PROTOCOLO HTTP.
                String consultarImpresionesFallidas = "http://"
                        + servidor.getHost() + ":" + servidor.getPort()
                        + contextoWeb + "/infoImpresion.do?" + WebKeys.ACCION
                        + "=" + AWImpresion.CONSULTAR_IMPRESIONES_FALLIDAS
                        + "&" + CImpresion.UID + "=" + UID + "&"
                        + CImpresion.LOCALHOST + "=" + receiver.getLocalHost()
                        + "&" + CImpresion.TIPO_CLIENTE + "=" + tipoCliente;
                logger.print("CADENA CONSULTAR IMPRESIONES FALLIDAS : "
                        + consultarImpresionesFallidas);

                URL urlImpresionesFallidas = new URL(
                        consultarImpresionesFallidas);
                URLConnection connectionDescarga = urlImpresionesFallidas
                        .openConnection();
                connectionDescarga.setUseCaches(false);
                ObjectInputStream ois = new ObjectInputStream(
                        connectionDescarga.getInputStream());
                List idImpresionesFallidas = (List) ois.readObject();
                ois.close();

                if (idImpresionesFallidas != null) {
                    logger.print((new Date()).toString());
                    logger.print("IMPRESIONES FALLIDAS" + idImpresionesFallidas.size());

                    Iterator it = idImpresionesFallidas.iterator();
                    while (it.hasNext()) {
                        String id = (String) it.next();
                        try {
                            receiver.processPorIdentificador(id);
                        } catch (Exception e) {
                        }
                    }
                }

            } catch (StreamCorruptedException sce) {
                System.out.println("IMPRESION PLUS ERROR_REIMPRIMIR1 servidor de impresión " + sce);
                logger.print((new Date()).toString());
                logger.print("ERROR (StreamCorruptedException):" + sce);
                sce.printStackTrace();
            } catch (MalformedURLException e1) {
                System.out.println("IMPRESION PLUS ERROR_REIMPRIMIR2 servidor de impresión " + e1);
                logger.print((new Date()).toString());
                logger.print("ERROR: (MalformedURLException)" + e1);
                e1.printStackTrace();
            } catch (IOException e2) {
                System.out.println("IMPRESION PLUS ERROR_REIMPRIMIR3 servidor de impresión " + e2);
                logger.print((new Date()).toString());
                logger.print("ERROR (IOException):" + e2);
                e2.printStackTrace();
            } catch (Exception ex) {
                System.out.println("IMPRESION PLUS ERROR_REIMPRIMIR4 servidor de impresión " + ex);
                logger.print((new Date()).toString());
                logger.print("ERROR (Exception):" + ex);
                ex.printStackTrace();
            }

        }

    }

    /**
     * Returns the IP address that the JVM is running in
     */
    public String getIPAddress() {
        System.out.println("IMPRESION PLUS IPADDRESS servidor de impresión ");
        String strIPAddress = null;
        URL origin = null;
        String hostName = null;
        String strProtocol = null;
        Socket local = null;
        InetAddress objLocalHost = null;
        try {
            origin = this.getCodeBase(); // this is the Applet-class
            hostName = origin.getHost();
            strProtocol = origin.getProtocol();
            System.out.println("IMPRESION PLUS HOSTNAME servidor de impresión " + hostName);
            System.out.println("IMPRESION PLUS ORIGIN servidor de impresión " + origin);

            if (origin.getPort() != -1) {
                local = new Socket(hostName, origin.getPort());
            } else {
                if (strProtocol.equalsIgnoreCase("http")) {
                    try {
                        local = new Socket(hostName, 80);
                    } catch (Exception e) {
                        System.out.println("IMPRESION PLUS ERROR_SOCKET_80 servidor de impresión " + e);
                        try {
                            local = new Socket(hostName, 7778);
                        } catch (Exception e1) {
                            System.out.println("IMPRESION PLUS ERROR_SOCKET_7778 servidor de impresión " + e1);
                            try {
                                local = new Socket(hostName, 7777);
                            } catch (Exception e2) {
                                System.out.println("IMPRESION PLUS ERROR_SOCKET_7777 servidor de impresión " + e2);
                            }
                        }
                    }

                } else if (strProtocol.equalsIgnoreCase("https")) {
                    local = new Socket(hostName, 443);
                }
            }

            objLocalHost = local.getLocalAddress();
            strIPAddress = objLocalHost.getHostAddress();
        } catch (Exception ex) {
            System.out.println("IMPRESION PLUS ERROR_SOCKET_GENERAL servidor de impresión " + ex);
            ex.printStackTrace();
            strIPAddress = "";
        } finally {
            try {
                System.out.println("IMPRESION PLUS CERRAR_SOCKET servidor de impresión ");
                local.close();
            } catch (IOException ioe) {
                System.out.println("IMPRESION PLUS ERROR_CERRAR_SOCKET servidor de impresión " + ioe);
                ioe.printStackTrace();
            }
        }

        return strIPAddress;
    }

    public void update(java.util.Observable o, Object arg) {
        String mensaje = (String) arg;
        logger.print(">>>" + mensaje);
        if (mensaje.equals(Receiver.BUSY)) {
            logger.print("*****OCUPADO*****");
        } else if (mensaje.equals(Receiver.READY)) {
            // estado.setIcon(icon_ready);
            logger.print("*****LISTO*****");
            // registrarCliente();
        } else {
            logger.print("*****ESPERANDO*****");
            // estado.setIcon(icon_wait);
        }
        // repaint();
    }
}
