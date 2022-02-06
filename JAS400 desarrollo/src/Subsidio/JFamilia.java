package Subsidio;


import Subsidio.JFEmpresa;
import Subsidio.JEmpleados;
import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;   
import java.time.format.DateTimeFormatter;  

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JFamilia extends javax.swing.JFrame {
    
    private JEmpleados Afiliado;
    private JConection JBase_Datos;
    private Connection Cn;    
    private String NumeroFormato = "###,###,###,###.##";
    private String NumeroFormatoFecha = "####/##/##";
    private DecimalFormat JFormato ;
    private DecimalFormat JFormatoFecha ;
    private JCheques Ch;
    private JPlanillaUnicaAportes Planilla;
    
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    
    private Object [] Cabecera2 ;
    private Object [] [] Detalle2  ;
    
    private Object [] Cabecera3 ;
    private Object [] [] Detalle3  ;
    
    private Object [] Cabecera4 ;
    private Object [] [] Detalle4  ;    
    private String Usuario;
    
    SimpleDateFormat formatoFecha;
    /**
     * Creates new form JFamilia
     */
    public JFamilia(int Documento, JConection Jbase, Connection cn2, String Usuario  ) {
        formatoFecha = new SimpleDateFormat("yyyyMMdd");
        this.Usuario = Usuario;
        Cabecera =  new Object [7];
        Detalle  = new Object [20][7];
        Cabecera2 =  new Object [7];
        Detalle2  = new Object [2][7];
        
        Cabecera3 =  new Object [9];
        Detalle3  = new Object [10000][9];
        
        Cabecera4 =  new Object [7];
        Detalle4  = new Object [8000][7];
        
        this.JBase_Datos = Jbase;
        this.Cn = cn2;
        this.Afiliado = new JEmpleados(Documento,this.JBase_Datos, this.Cn );
        JFormato= new DecimalFormat(NumeroFormato);
        JFormatoFecha= new DecimalFormat(NumeroFormatoFecha);
        Ch = new JCheques(Documento,this.JBase_Datos, this.Cn);
        
        Planilla = new JPlanillaUnicaAportes(this.Afiliado.getDocumento(),this.JBase_Datos, this.Cn);
        Muestra_Salario();
        initComponents();
        this.getInformacionEmpleado();
        this.getInformacionConyuge();
        this.getHijos();
        this.getPadres();
        this.getHistoricoCheques();
        this.getPlanillaUnica();
        get_Cantidad_Dias(""+this.Afiliado.getDocumento() ,"201601");
        getCrditos_pendientes(""+this.Afiliado.getDocumento());       
    }
    
    public void getPlanillaUnica(){
        this.Cabecera4[0]="Numero de Planilla";
        this.Cabecera4[1]="Tipo de Planilla";
        this.Cabecera4[2]="Codigo Empresa";
        this.Cabecera4[3]="Nombre de Empresa";
        this.Cabecera4[4]="Periodo";
        this.Cabecera4[5]="Dias";
        this.Cabecera4[6]="Valor Aporte";
        int fila = -1;
        Planilla.setFirts();
        
        while(Planilla.getNext()){
            fila++;
            this.Detalle4[fila][0] = Planilla.getNumeroPlanilla();
            this.Detalle4[fila][1] = Planilla.getTipoPlanilla();
            this.Detalle4[fila][2] = Planilla.getCodigoEmpresa();
            this.Detalle4[fila][3] = Planilla.getNombreEmpresa();
            this.Detalle4[fila][4] = Planilla.getPeriodo();
            this.Detalle4[fila][5] = Planilla.getDias();
            this.Detalle4[fila][6] = JFormato.format(Double.parseDouble(Planilla.getValorAporte()));
        }
        jTable4.setModel(new javax.swing.table.DefaultTableModel(Detalle4, Cabecera4));
    }
    
    
    public void getHistoricoCheques(){
        this.Cabecera3[0] ="Periodo";
        this.Cabecera3[1] ="Fecha";
        this.Cabecera3[2] ="Secuencia";
        this.Cabecera3[3] ="Numero cheque";
        this.Cabecera3[4] ="Documento Beneficiado";
        this.Cabecera3[5] ="Nombre Beneficiado";
        this.Cabecera3[6] ="Valor";
        this.Cabecera3[7] ="Situacion";
        this.Cabecera3[8] ="Estado";
        
        int fila = -1;
        Ch.setFirts();
        while(Ch.getNext()){
            fila++;
            this.Detalle3[fila][0] = Ch.getPeriodo();
            this.Detalle3[fila][1] = Ch.getFecha();
            this.Detalle3[fila][2] = Ch.getSecuencia();
            this.Detalle3[fila][3] = Ch.getNumero();
            this.Detalle3[fila][4] = Ch.getEmpleado();
            this.Detalle3[fila][5] = this.Afiliado.getNombre();
            this.Detalle3[fila][6] = JFormato.format(Double.parseDouble(Ch.getValor()));
            this.Detalle3[fila][7] = Ch.getSituacion();
            this.Detalle3[fila][8] = Ch.getEstado();
       }
        //System.out.println("PRIMERO ");
        jTable3.setModel(new javax.swing.table.DefaultTableModel(Detalle3, Cabecera3));
        //System.out.println("SEGUNDO ");
    }
    public void getPadres(){
        this.Cabecera2[0] ="Documento";
        this.Cabecera2[1] ="Nombre";
        this.Cabecera2[2] ="Recibe Subsidio";
        this.Cabecera2[3] ="Fecha de Nacimiento";
        this.Cabecera2[4] ="Fecha de Afiliacion";
        this.Cabecera2[5] ="Sexo";
        this.Cabecera2[6] ="Discapacitado";
        
        try{
            if(this.Afiliado.getPadres().getExistePadre()){
                    this.Detalle2[0][0] = JFormato.format(Double.parseDouble(""+this.Afiliado.getPadres().getDocumento()));
                    this.Detalle2[0][1] = this.Afiliado.getPadres().getNombreCompleto();
                    this.Detalle2[0][2] = this.Afiliado.getPadres().getRecibeSubsidio();
                    this.Detalle2[0][3] = this.Afiliado.getPadres().getFechaNacimiento();
                    this.Detalle2[0][4] = this.Afiliado.getPadres().getFechaAfiliacion();
                    this.Detalle2[0][5] = this.Afiliado.getPadres().getSexo();
                    this.Detalle2[0][6] = this.Afiliado.getPadres().getDiscapacitado();
            }
        }catch(Exception e ){
                    javax.swing.JOptionPane.showMessageDialog(this, " ClassJFamiliar:getPadres() "+e.getMessage());
        }    
        
        if(this.Afiliado.getMadre().getExistePadre()){
            this.Detalle2[1][0] = JFormato.format(Double.parseDouble(""+this.Afiliado.getMadre().getDocumento()));
            this.Detalle2[1][1] = this.Afiliado.getMadre().getNombreCompleto();
            this.Detalle2[1][2] = this.Afiliado.getMadre().getRecibeSubsidio();
            this.Detalle2[1][3] = this.Afiliado.getMadre().getFechaNacimiento();
            this.Detalle2[1][4] = this.Afiliado.getMadre().getFechaAfiliacion();
            this.Detalle2[1][5] = this.Afiliado.getMadre().getSexo();
            this.Detalle2[1][6] = this.Afiliado.getMadre().getDiscapacitado();
        }
        jTable2.setModel(new javax.swing.table.DefaultTableModel(Detalle2, Cabecera2));
    }
    
    
    public void getHijos(){
        
        this.Cabecera[0] ="Documento";
        this.Cabecera[1] ="Nombre";
        this.Cabecera[2] ="Recibe Subsidio";
        this.Cabecera[3] ="Fecha de Nacimiento";
        this.Cabecera[4] ="Fecha de Afiliacion";
        this.Cabecera[5] ="Sexo";
        this.Cabecera[6] ="Discapacitado";
        
        this.Afiliado.getHijos().setFirst();
        int fila = -1;
        while (this.Afiliado.getHijos().getNext()) {
            fila++;
            this.Detalle[fila][0] = JFormato.format(Double.parseDouble(this.Afiliado.getHijos().getDocumento()));
            this.Detalle[fila][1] = this.Afiliado.getHijos().getPrimerNombre().trim()+" "+this.Afiliado.getHijos().getSegundoNombre().trim()+" "+this.Afiliado.getHijos().getPrimerApellido().trim()+" "+this.Afiliado.getHijos().getSegundoApellido().trim();
            this.Detalle[fila][2] = this.Afiliado.getHijos().getRecibeSubsidio();
            this.Detalle[fila][3] = this.Afiliado.getHijos().getFechaNacimiento().trim();
            this.Detalle[fila][4] = this.Afiliado.getHijos().getFechaAfiliacion().trim();
            this.Detalle[fila][5] = this.Afiliado.getHijos().getSexo().trim();
            this.Detalle[fila][6] = this.Afiliado.getHijos().getDiscapacitado().trim();        
        }
        if(fila != -1){
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
        }

    }
    
    public void Muestra_Salario(){
       try {
            LocalDateTime now = LocalDateTime.now();  
            System.out.println(" "+now);
            String Sql = "INSERT INTO SELINLIB.JAUDITORIA (CODIGO,TIPO,OPCION,FECHOR) "
                       + " VALUES('"+this.Usuario+"','show','Grupo familiar Subsidio "+this.Afiliado.getDocumento()+" -Salario ','"+now+"')";
            System.out.println(" "+Sql);
            boolean res = JBase_Datos.Exc_Sql(this.Cn,Sql);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " "+e.getMessage());
        }
    }
    
    public void getInformacionEmpleado(){
       try{
            jTextField1.setText(""+JFormato.format(this.Afiliado.getDocumento()));
            jTextField2.setText(""+this.Afiliado.getPrimerNombre().trim()+" "+this.Afiliado.getSegundoNombre().trim());
            jTextField6.setText(""+this.Afiliado.getPrimerApellido().trim() +" "+this.Afiliado.getSegundoApellido().trim());
            jTextField3.setText(""+this.Afiliado.getFechaAfiliacion());
            jTextField5.setText(""+this.Afiliado.getFechaNacimiento());
            jTextField4.setText(""+JFormato.format(this.Afiliado.getSalario()));
            jTextField13.setText(""+JFormato.format(this.Afiliado.getEmpresa().getCodigo()));
            jTextField14.setText(this.Afiliado.getEmpresa().getNombre().trim());

            if (this.Afiliado.getSexo().equals("M")) {
               jComboBox1.setSelectedIndex(0);
            }else{
                jComboBox1.setSelectedIndex(1);
            }

            if(this.Afiliado.getEstado()){
               jComboBox2.setSelectedIndex(0); 
            }else{
                jComboBox2.setSelectedIndex(1);
            }

            if (this.Afiliado.getRecibe()) {
               jComboBox5.setSelectedIndex(0);
            }else{
                jComboBox5.setSelectedIndex(1);
            }
       }catch(Exception e ){
           JOptionPane.showMessageDialog(null,"Class:JFamilia:getInformacionEmpleado() "+e.getMessage());
       }
    }
    public void getInformacionConyuge(){
        if (this.Afiliado.getConyuge().getExisteConyuge()) {
            jTextField8.setText(""+JFormato.format(this.Afiliado.getConyuge().getDocumento()));
            jTextField7.setText(""+this.Afiliado.getConyuge().getPrimerNombre().trim()+" "+this.Afiliado.getConyuge().getSegundoApellido().trim());
            jTextField9.setText(""+this.Afiliado.getConyuge().getPrimerApellido().trim()+" "+this.Afiliado.getConyuge().getSegundoApellido().trim());
            jTextField10.setText(""+this.Afiliado.getConyuge().getFechaAfiliacion());
            jTextField11.setText(""+this.Afiliado.getConyuge().getFechaNacimiento());
            jTextField12.setText(""+JFormato.format(this.Afiliado.getConyuge().getSalario()));
            if (this.Afiliado.getConyuge().getRecibeSubsidio()) {
                jComboBox4.setSelectedIndex(0);
            }else{
                jComboBox4.setSelectedIndex(1);
            }
            if (this.Afiliado.getConyuge().getSexo().equals("M")) {
                jComboBox3.setSelectedIndex(0);
            }else{
                jComboBox3.setSelectedIndex(1);
            }
            jComboBox6.setSelectedIndex(0);
        }else{
            jComboBox6.setSelectedIndex(1);
        }
    }
    
    public void getCrditos_pendientes(String Documento){
        try {
            Vector SG_cab = new Vector();
            SG_cab.add("Tipo de Credito");
            SG_cab.add("Valor");
            SG_cab.add("Deuda Actual");
            SG_cab.add("Fecha");
            SG_cab.add("Estado");
            
            Vector SG_Fila = new Vector();
            Vector SG_Det = new Vector();
            String Str_Sql="select tcacve , tmotot,( TMOCAP + TMOINP + TMOIVP + TMOCAD )as deuda ,tmoest , tmofec, tmofcr from sgdatos.tmocc where TCRIDN="+Documento +" and tmoest='G'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while (rs.next()){
               SG_Det = new Vector();
               SG_Det.add(rs.getString("tcacve"));
               SG_Det.add(this.JFormato.format(rs.getDouble("tmotot")));
               SG_Det.add(this.JFormato.format(rs.getDouble("deuda")));
               SG_Det.add(rs.getString("tmofec"));
               SG_Det.add(rs.getString("tmoest"));
               SG_Fila.add(SG_Det);
            }
            rs.close();
            jTable6.setModel(new javax.swing.table.DefaultTableModel(SG_Fila, SG_cab));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"getCrditos_pendientes() "+e.getMessage());
        }
    }
    
    public String getDireccion_Empresa(String Codigo){
        String Direccion ="";
        try {
            String Str_Sql ="select EMPDIR from subsilib.mempval where empcod="+Codigo;
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             while (rs.next()){
                Direccion =  rs.getString("EMPDIR");
             }
             rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"getDireccion_Empresa() "+e.getMessage());
        }
        
        return Direccion;
    }
    
    public void get_Cantidad_Dias(String Documento ,String Periodo){
        Vector DFila = new Vector();
        Vector DCabecera = new Vector();
        DCabecera.add("Codigo");
        DCabecera.add("Nombre Empresa");
        DCabecera.add("Direccion Empresa");
        DCabecera.add("Cedula");
        DCabecera.add("Dias");
        try{
             String StrSql = "select  CODIGO, NOMAPO,CEDPLA, sum(dias) as DIAS from \n" +
                        "     ( SELECT  CODIGO, NOMAPO,CEDPLA, PERAPO, DIAS\n" +
                        "      FROM SUBSILIB.CERPLA AS t1, SUBSILIB.CERPLAD AS t2 WHERE t1.NROPLA=t2.PLANIL \n" +
                        "      and t2.cedpla="+Documento+" and perapo>= " +Periodo+
                        "      group by CODIGO, NOMAPO,CEDPLA, PERAPO,DIAS \n" +
                        "      order by perapo desc) as TX1\n" +
                        " group by  CODIGO, NOMAPO,CEDPLA ";
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while (rs.next()){
                  Vector DDetalle = new Vector();
                  DDetalle.add(rs.getString("CODIGO"));
                  DDetalle.add(rs.getString("NOMAPO"));
                  DDetalle.add(getDireccion_Empresa(rs.getString("CODIGO")));
                  DDetalle.add(rs.getString("CEDPLA"));
                  DDetalle.add(rs.getString("DIAS"));
                  DFila.add(DDetalle);
             }
             //************************************************************************************************************
             StrSql = "SELECT CODIGO, NOMAPO,CEDPLA, MAX(PERAPO) as PERAPO\n" +
                      "FROM SUBSILIB.CERPLA AS t1, SUBSILIB.CERPLAD AS t2 WHERE t1.NROPLA=t2.PLANIL \n" +
                      "and t2.cedpla="+Documento+" and perapo>= "+Periodo+" group by CODIGO, NOMAPO,CEDPLA \n" +
                      "order by perapo desc";
             rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while (rs.next()){
                  Vector DDetalle = new Vector();
                  DDetalle.add(rs.getString("CODIGO"));
                  DDetalle.add(rs.getString("NOMAPO"));
                  DDetalle.add(getDireccion_Empresa(rs.getString("CODIGO")));
                  DDetalle.add(" --- ULTIMOS APORTE ----- ");
                  DDetalle.add(rs.getString("PERAPO"));
                  DFila.add(DDetalle);
             }
             
             rs.close();
             jTable5.setModel(new javax.swing.table.DefaultTableModel(DFila, DCabecera));
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null,"JPlanillaUnicaAportes:get_Cantidad_Dias() "+e.getMessage());
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informacion del Afiliado");
        setResizable(false);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Informacion del Empleado"));

        jLabel1.setText("Nombres");

        jLabel2.setText("Apellidos");

        jTextField1.setEditable(false);

        jTextField2.setEditable(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Femenino" }));

        jLabel3.setText("Sexo");

        jLabel4.setText("Fecha de Nacimiento");

        jLabel5.setText("Fecha Afiliacion");

        jLabel6.setText("Salario Basico");

        jLabel7.setText("Estado");

        jTextField3.setEditable(false);

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Eliminado" }));

        jTextField6.setEditable(false);

        jLabel8.setText("Documento");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        jLabel17.setText("Recibe Subsidio");

        jTextField13.setEditable(false);
        jTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField13MouseClicked(evt);
            }
        });

        jTextField14.setEditable(false);

        jLabel19.setText("Codigo Empresa");

        jLabel20.setText("Nombre Empresa");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                    .addComponent(jTextField1)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jLabel1)
                                .addGap(142, 142, 142)
                                .addComponent(jLabel2))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addGap(44, 44, 44)
                                            .addComponent(jLabel3))))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(jLabel7)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField13)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField14))
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(jLabel20)
                                                .addGap(0, 0, Short.MAX_VALUE))))))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5)
                        .addGap(139, 139, 139)
                        .addComponent(jLabel6)))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion de la Conyuge", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), java.awt.Color.black)); // NOI18N

        jLabel9.setText("Nombre");

        jLabel10.setText("Apellidos");

        jLabel11.setText("Documento");

        jTextField7.setEditable(false);

        jTextField8.setEditable(false);

        jTextField9.setEditable(false);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Femenino" }));

        jLabel12.setText("Sexo");

        jTextField10.setEditable(false);

        jLabel13.setText("Fecha Afiliacion");

        jTextField11.setEditable(false);

        jLabel14.setText("Fecha de Nacimiento");

        jTextField12.setEditable(false);

        jLabel15.setText("Salario Basico");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));

        jLabel16.setText("Recibe Subsidio");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "No tiene" }));

        jLabel18.setText("Estado");

        jButton2.setText("Cancelar");

        jButton1.setText("Validar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(156, 156, 156)
                                .addComponent(jLabel10)
                                .addGap(105, 105, 105))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                                            .addComponent(jTextField10)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addGap(27, 27, 27)
                                                        .addComponent(jLabel18))
                                                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                                            .addComponent(jTextField7)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addComponent(jLabel14)))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jLabel15)))
                                .addGap(22, 22, 22)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel12))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(jLabel13))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Afiliado-Conyuge", jPanel1);

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Hijos"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Documento", "Nombres", "Recibe Subsidio", "Fecha de Nacimiento", "Fecha de Afiliacion", "Sexo"
            }
        ));
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Padres"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Documento", "Nombre", "Recibe Subsidio", "Fecha de Nacimiento", "Fecha de Afiliacion", "Sexo", "Discapacitado"
            }
        ));
        jTable2.setEnabled(false);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Familiares", jPanel2);

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Subsdio Familiar"));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Periodo", "Fecha", "Secuencia Cheque", "Numero del Cheque", "Documento Bsneficiado", "Nombre del Beneficiado", "Valor", "Situacion", "Estado"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                .addGap(171, 171, 171))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(287, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Historico de Cheques", jPanel3);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Aporte Planilla Unica"));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Numero de Planilla", "Tipo de Planilla", "Codigo de Empresa", "Nombre Empresa", "Periodo", "Dias", "Valor Aporte"
            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre Empresa", "Direccion", "Cedula", "Dias"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de Credito", "Valor ", "Fecha", "Estado"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Planilla Unica", jPanel9);

        jMenu3.setText("Ejecutar");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Tarjetas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 785, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

   }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        if (jTable3.getValueAt(jTable3.getSelectedRow(), 1) != null ){
            int Fecha = Integer.parseInt(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
            int Periodo  = Integer.parseInt(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            double NumeroCheque  = Double.parseDouble(jTable3.getValueAt(jTable3.getSelectedRow(), 3).toString());
            JFLiquidacionSubsidio FSubsidio = new JFLiquidacionSubsidio(this.Afiliado,Periodo ,Fecha, NumeroCheque, JBase_Datos, this.Cn);
            FSubsidio.setVisible(true);
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTextField13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField13MouseClicked
        // TODO add your handling code here:
        if (!jTextField13.getText().trim().equals("")) {
           JFEmpresa Empresa =   new JFEmpresa(this.Afiliado.getEmpresa().getCodigo(), this.JBase_Datos, this.Cn);
           Empresa.setVisible(true);
        }
        
    }//GEN-LAST:event_jTextField13MouseClicked

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable4MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JTarjetas BuscarTarjeta  = new JTarjetas(this.JBase_Datos , this.Cn); 
        BuscarTarjeta.setCedula(""+this.Afiliado.getDocumento());
        BuscarTarjeta.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFamilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFamilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFamilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFamilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JFamilia(0, null, null, null ).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
