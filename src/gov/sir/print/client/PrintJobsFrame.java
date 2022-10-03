/*
 * PrintJobsApplet.java
 *
 * Created on 27 de septiembre de 2004, 18:00
 */
package gov.sir.print.client;

import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CUsuario;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWImpresion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessControlException;
import java.security.Policy;
import java.util.Observer;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * Habilita la impresion en un cliente web. Usando un objeto receiver puede
 * obtener una conexion al servidor y esperar a que lleguen trabajos de
 * impresion por dicha conexión.
 *
 * @author dcantor
 */
public class PrintJobsFrame extends JFrame implements Observer {

    protected ImageIcon icon_busy;

    protected ImageIcon icon_ready;

    protected ImageIcon icon_wait;

    protected JLabel estado;

    protected String remoteHost;

    protected int remotePort;

    protected String UID;

    protected String idUsuario = "";

    protected int localPortLow;

    protected int localPortHigh;

    protected Receiver receiver;

    protected boolean clienteRegistrado;

    protected JLabel mensajeTexto;

    protected JLabel labelValidacion;

    protected JTextField textoId;

    protected JPanel panelSuperior;

    protected JPanel panelInferior;

    protected JPanel panelReimresionFallos;

    protected JPanel panelContenedor;

    protected JButton sendId;

    protected JButton impresionesFallidas;

    protected JLabel labelInferior;

    protected BasicService bs = null;

    protected String context = "";

    protected int verificarSesionMinutos = 5;

    private Log logger;

    public PrintJobsFrame() {

        try {
            logger = new Log(PrintJobsFrame.class);
            logger.print("PrintJobsFrame Constructor");

            System.setProperty("java.security.manager", "");
            System.setProperty("java.security.policy", gov.sir.print.client.PrintJobsFrame.class.getResource(
                    "javaCliente.policy").toString());

            Policy.getPolicy().refresh();
            System.out.println("SE ACTUALIZARON LAS POLITICAS DE SEGURIDAD.");
            logger.print("SE ACTUALIZARON LAS POLITICAS DE SEGURIDAD.");
            logger.print(gov.sir.print.client.PrintJobsFrame.class.getResource("javaCliente.policy").toString());

        } catch (Exception e) {
            System.out.println("NO SE PUDO ACTUALIZAR LAS POLITICAS DE SEGURIDAD.");
            e.printStackTrace();
        }

    }

    public void destroy() {
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

        try {
            System.out.println("IMPRESION FRAME INIT servidor de impresión");
            // remoteHost = getParameter("REMOTE_HOST");
            // remotePort = Integer.parseInt(getParameter("REMOTE_PORT"));
            // UID = getParameter("UID");
            // idUsuario = getParameter("ID_USUARIO");
            // localPortLow = Integer.parseInt(getParameter("LOCAL_PORT_LOW"));
            // localPortHigh =
            // Integer.parseInt(getParameter("LOCAL_PORT_HIGH"));

            remoteHost = System.getProperty("REMOTE_HOST");
            this.context = System.getProperty("CONTEXT");
            remotePort = Integer.parseInt(System.getProperty("REMOTE_PORT"));
            UID = System.getProperty("UID");
            idUsuario = System.getProperty("ID_USUARIO");
            localPortLow = Integer.parseInt(System
                    .getProperty("LOCAL_PORT_LOW"));
            localPortHigh = Integer.parseInt(System
                    .getProperty("LOCAL_PORT_HIGH"));

            String tmp = System.getProperty("VERIFICAR_SESION");
            tmp = tmp == null ? "0" : tmp;

            verificarSesionMinutos = Integer.parseInt(tmp);

            logger.print(" remoteHost: " + remoteHost);
            logger.print(" verificarSesionMinutos: "
                    + verificarSesionMinutos);
            bs = (BasicService) ServiceManager
                    .lookup("javax.jnlp.BasicService");
            URL servidor = bs.getCodeBase();

            servidor = new URL(this.context);

            receiver = new Receiver(remoteHost, remotePort, new Processor(),
                    UID, localPortLow, localPortHigh, servidor, idUsuario);
            clienteRegistrado = false;
        } catch (Throwable e) {
            System.out.println("IMPRESION FRAME INIT_ERROR_1 servidor de impresión " + e);
            e.printStackTrace();
        }
        try {
            System.out.println("IMPRESION FRAME B_INIT_GUI servidor de impresión ");
            initGUI();
        } catch (Throwable e) {
            System.out.println("IMPRESION FRAME INIT_ERROR_2 servidor de impresión " + e);
            e.printStackTrace();
        }

    }

    public static void main(String args[]) throws Exception {
        System.out.println("IMPRESION FRAME MAIN servidor de impresión ");
        PrintJobsFrame pjf = new PrintJobsFrame();
        pjf.init();
        pjf.start();
    }

    public void start() throws Exception {
        System.out.println("IMPRESION FRAME START servidor de impresión ");
        receiver.addObserver(this);
        receiver.registrar();
        registrarCliente();
    }

    public void update(java.util.Observable o, Object arg) {
        System.out.println("IMPRESION FRAME UPDATE servidor de impresión ");
        String mensaje = (String) arg;
        logger.print("PrintJobsApplet>>>" + mensaje);
        if (mensaje.equals(Receiver.BUSY)) {
            estado.setIcon(icon_busy);
        } else if (mensaje.equals(Receiver.READY)) {
            estado.setIcon(icon_ready);
            // registrarCliente();
        } else {
            estado.setIcon(icon_wait);
        }
        repaint();
    }

    protected static ImageIcon createImageIcon(String path) {

        try {
            URL imgURL = PrintJobsFrame.class.getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            }
            System.err.println("no pude encontrar el archivo: " + path);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void preInitGUI() {
        System.out.println("IMPRESION FRAME PREINIT servidor de impresión ");

        this.setTitle("Aplicación de Impresión Individual");

        // Páneles
        panelSuperior = new JPanel();
        panelInferior = new JPanel();
        panelReimresionFallos = new JPanel();
        panelContenedor = new JPanel();

        // Layouts
        panelContenedor.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelReimresionFallos.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Tamaños Preferidos
        panelInferior.setPreferredSize(new Dimension(270, 110));
        panelSuperior.setPreferredSize(new Dimension(270, 65));
        panelReimresionFallos.setPreferredSize(new Dimension(270, 90));
        // panelContenedor.setPreferredSize(new Dimension(270, 460));
        // panelContenedor.setPreferredSize(new Dimension(270, 230));

        // Tamaños Mínimos
        panelInferior.setMinimumSize(new Dimension(270, 110));
        panelReimresionFallos.setMinimumSize(new Dimension(270, 90));
        panelSuperior.setMinimumSize(new Dimension(270, 110));
        // panelContenedor.setMinimumSize(new Dimension(270, 230));
        // panelContenedor.setMaximumSize(new Dimension(270, 460));

        // Bordes
        Border border = new TitledBorder("");
        Font font = new Font("", Font.BOLD, 12);
        panelInferior.setBorder(BorderFactory.createTitledBorder(border,
                "Reimpresión.", TitledBorder.LEFT, TitledBorder.CENTER, font,
                Color.BLUE));
        panelInferior.setForeground(Color.BLUE);
        panelInferior.setBackground(new Color(205, 216, 237));

        panelReimresionFallos.setBorder(BorderFactory.createTitledBorder(
                border, "Impresiones pendientes.", TitledBorder.LEFT,
                TitledBorder.CENTER, font, Color.BLUE));
        panelReimresionFallos.setForeground(Color.BLUE);
        panelReimresionFallos.setBackground(new Color(205, 216, 237));

        getContentPane().setBackground(new Color(205, 216, 237));

        // Botones
        this.sendId = new JButton("Imprimir");
        sendId.addActionListener(new ButtonListener());

        // Botones
        this.impresionesFallidas = new JButton("Imprimir pendientes");
        impresionesFallidas
                .addActionListener(new ReimprimirFallosButtonListener());

        // Labels
        labelInferior = new JLabel("Ingrese el identificador de la reimpresión");
        labelInferior.setForeground(Color.BLUE);
        labelValidacion = new JLabel("");
        this.textoId = new JTextField();
        textoId.setPreferredSize(new Dimension(70, 20));

        // icon_busy = createImageIcon(getParameter("BUSY"));
        // icon_ready = createImageIcon(getParameter("READY"));
        // icon_wait = createImageIcon(getParameter("WAIT"));
        estado = new JLabel(icon_wait, JLabel.CENTER);

        ListenerCerrarVentana listener = new ListenerCerrarVentana(
                this.receiver, this, this.verificarSesionMinutos);

        this.addWindowListener(listener);

        Thread t = new Thread(listener);
        t.start();
    }

    public void initGUI() {
        System.out.println("IMPRESION FRAME INIT_GUI servidor de impresión ");

        preInitGUI();

        panelSuperior.add(this.estado);
        panelInferior.add(labelValidacion);
        panelInferior.add(labelInferior);
        panelInferior.add(textoId);
        panelInferior.add(sendId);
        panelReimresionFallos.add(impresionesFallidas);
        // panelContenedor.add(panelSuperior);
        panelContenedor.add(panelInferior);
        panelContenedor.add(panelReimresionFallos);
        getContentPane().add(panelContenedor);

        postInitGUI();
    }

    public void postInitGUI() {
        System.out.println("IMPRESION FRAME POST_INIT_GUI servidor de impresión ");
        this.show();
        this.pack();
        this.setState(JFrame.ICONIFIED);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Registra en el servidor la dirección IP del cliente para que el servidor
     * la conozca y sepa a que dirección IP enviar una impresión determinada
     */
    private void registrarCliente() throws Exception {
        System.out.println("IMPRESION FRAME REGISTRARCLIENTE servidor de impresión");
        System.out.println("IMPRESION FRAME CLIENTEREGISTRADO " + clienteRegistrado);
        if (clienteRegistrado) {
            return;
        }
        URL servidor = this.bs.getCodeBase();
        servidor = new URL(this.context);
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
            System.out.println("IMPRESION FRAME POR_APPLET servidor de impresión");
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
            System.out.println("IMPRESION FRAME POR_RECEIVER servidor de impresión");
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

        // StringBuffer command = new
        // StringBuffer("http://"+servidor.getHost()+":"+servidor.getPort()+contextoWeb+"/infoImpresion.do?"+WebKeys.ACCION+"="+AWImpresion.REGISTRAR_CLIENTE+"&"+CImpresion.UID+"="+receiver.getUID()+"&"+CImpresion.LOCALHOST+"="+receiver.getLocalHost()+"&"+CImpresion.LISTENINGPORT
        // + "="+receiver.getListeningPort()+ "&" + CUsuario.ID_USUARIO + "=" + receiver.getIdUsuario() );
        logger.print(command.toString());

        try {
            System.out.println("IMPRESION FRAME VERIFICAR_DESCARGA_APPLET servidor de impresión ");
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
            BufferedReader bufferVerificacion = new BufferedReader(
                    new InputStreamReader(connectionVerificacion
                            .getInputStream()));
            logger.print(verificarDescargaApplet);

            URL url = new URL(command.toString());
            URLConnection sirConnection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    sirConnection.getInputStream()));
            in.close();
            clienteRegistrado = true;
        } catch (AccessControlException e) {
            System.out.println("IMPRESION FRAME ERROR_ACCESS servidor de impresión " + e);
            logger.print("ERROR (AccessControlException):" + e);
        } catch (MalformedURLException e1) {
            System.out.println("IMPRESION FRAME ERROR_MALFORMED servidor de impresión " + e1);
            logger.print("ERROR:" + e1);
        } catch (IOException e2) {
            System.out.println("IMPRESION FRAME ERROR_IO servidor de impresión " + e2);
            logger.print("ERROR:" + e2);
        }

    }

    /**
     * Returns the IP address that the JVM is running in
     */
    public String getIPAddress() {
        System.out.println("IMPRESION FRAME IPADDRESS servidor de impresión ");
        String strIPAddress = null;
        URL origin = null;
        String hostName = null;
        String strProtocol = null;
        Socket local = null;
        InetAddress objLocalHost = null;
        try {
            origin = this.bs.getCodeBase(); // this is the Applet-class
            hostName = origin.getHost();
            strProtocol = origin.getProtocol();
            System.out.println("IMPRESION FRAME HOSTNAME servidor de impresión " + hostName);
            System.out.println("IMPRESION FRAME ORIGIN servidor de impresión " + origin);

            if (origin.getPort() != -1) {
                local = new Socket(hostName, origin.getPort());
            } else {
                if (strProtocol.equalsIgnoreCase("http")) {
                    try {
                        local = new Socket(hostName, 80);
                    } catch (Exception e) {
                        System.out.println("IMPRESION FRAME ERROR_SOCKET_80 servidor de impresión " + e);
                        try {
                            local = new Socket(hostName, 7778);
                        } catch (Exception e1) {
                            System.out.println("IMPRESION ERROR_SOCKET_7778 servidor de impresión " + e1);
                            try {
                                local = new Socket(hostName, 7777);
                            } catch (Exception e2) {
                                System.out.println("IMPRESION ERROR_SOCKET_7777 servidor de impresión " + e2);
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
            System.out.println("IMPRESION FRAME ERROR_SOCKET_GENERAL servidor de impresión " + ex);
            ex.printStackTrace();
            strIPAddress = "";
        } finally {
            try {
                System.out.println("IMPRESION FRAME CERRAR_SOCKET servidor de impresión ");
                local.close();
            } catch (IOException ioe) {
                System.out.println("IMPRESION FRAME ERROR_CERRAR_SOCKET servidor de impresión " + ioe);
                ioe.printStackTrace();
            }
        }

        return strIPAddress;
    }

    private class ButtonListener implements ActionListener // The listener
    // class
    {

        public void actionPerformed(ActionEvent push) // If button is
        // clicked...will run
        // this
        {

            String texto = textoId.getText();
            labelValidacion.setText("");

            try {
                int identificador = new Integer(texto).intValue();
                receiver.processPorIdentificador("" + identificador);
            } catch (Exception e) {
                labelValidacion.setText("Número de impresión inválido !!");
                labelValidacion.setForeground(Color.RED);
            }

        }
    }

    private class ReimprimirFallosButtonListener implements ActionListener // The
    // listener
    // class
    {

        public void actionPerformed(ActionEvent push) // If button is
        // clicked...will run
        // this
        {

            try {
                receiver.imprimirPendientes();
            } catch (Exception e) {
                System.out.println("FRAME ERROR AL IMPRIMIR LAS IMPRESIONES PENDIENTES." + e.getMessage());
                logger.print("ERROR AL IMPRIMIR LAS IMPRESIONES PENDIENTES." + e.getMessage());
            }

        }
    }

}
