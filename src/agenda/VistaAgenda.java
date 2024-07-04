package agenda;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class VistaAgenda extends javax.swing.JFrame {

    //Guarda la conexion
    Connection conn = null;

    //Es para obtener y enviar datos a la bd
    PreparedStatement pst;

    //Guarda consultas a la bd
    ResultSet rs;

    public VistaAgenda() {
        //Es para ocultar la barra de titulo que viene por defecto asignado al JFrame 
        setUndecorated(true);

        initComponents();
        
        //Defino un icono para el ejecutable
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconoAgenda.png")));

        //Llamamos al metodo de conexion para verificar si la conexion es exitosa
        getConnection();

        //Llamamos al metodo
        config();

        //Es para central el JFrame
        setLocationRelativeTo(null);

        //Llamos al metodo listarUsuarios para obtener la lista de usuarios
        listarUsuarios();

    }

    //Envio de atributos a la vista.
    private void config() {
        //El setToolTipText se usa para mostar un mensaje emergente cuando se pasa por el elemento
        label_cerrar.setToolTipText("Cerrar");
        label_minimizar.setToolTipText("Minimizar");
        txt_buscar.setToolTipText("Dato a buscar");
        btn_buscar.setToolTipText("Buscar");
        cmbx_filtros.setToolTipText("Filtros de busqueda");
        jTable1.setToolTipText("Datos del sistema");
        txt_id.setToolTipText("No es editable");
        btn_limpiar.setToolTipText("Limpiar busqueda");

        //El setEditable(false) es para que no se puede escribir cobre el campo
        txt_id.setEditable(false);

        //El setBackgroud(Color.white) es para enviar el color blanco al campo
        txt_id.setBackground(Color.white);

        //Este metodo es para seleccionar una sola fila de la tabla
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Inhabilitamos los botones de modificar y eleminar y solo habilitamos el de guardar
        btn_guardar.setEnabled(true);
        btn_modificar.setEnabled(false);
        btn_limpiar.setVisible(false);
    }

    //Metodo para realizar la conexion a la bd
    public Connection getConnection() {
        try {
            Class.forName("java.sql.DriverManager");
            //Se quitaron las credenciales a la db de la vps, modificar segun se requiera
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tablaDB?useSSL=false", "usuario", "password");
            if (conn != null) {
                System.out.println("Conexión Existosa!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar conectar a la base de datos.");
            System.err.println("Error: " + e.getMessage());
        }
        return conn;
    }

    //Metdo para listar usuarios registrados en la bd
    void listarUsuarios() {
        //Declaramos un DefaultTableModel para enviarle el nuevo modelo a la tabla
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        //Le decimos que comience desde 0
        modelo.setRowCount(0);

        //Declaramos un vector de tipo String para almacenar los datos que se obtentran
        String[] datos = new String[4];

        try {
            //Este es el metodo para escribir la intruccion sql para obtener datos de MySql
            pst = conn.prepareStatement("select * from usuarios");

            //Almacenamos lo que se obtiene de la consulta
            rs = pst.executeQuery();

            //Como son varios datos a obtener usamos un while y le decimos que pare hasta que no encuentre mas datos
            while (rs.next()) {
                //A cada posicion del vector le vamos enviados las filas obtenidas de cada columna
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

                //Le enviamos el vectos a nuestro modelo
                modelo.addRow(datos);
            }

            //Le enviamos el modelo a la tabla
            jTable1.setModel(modelo);

        } catch (SQLException e) {
            //En caso de error nos mostrar una ventana emergente y un mensaje por consola con el error
            JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar obtener los datos\n"
                    + "de la base de datos.");
            System.err.println("Error: " + e.getMessage());
        } finally {
            //Al finalizar decimos que que se cierre la consulta y los datos obtenidos
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    String filtro() {
        if (cmbx_filtros.getSelectedIndex() == 0) {
            return "id";
        } else if (cmbx_filtros.getSelectedIndex() == 1) {
            return "nombre_apellidos";
        } else if (cmbx_filtros.getSelectedIndex() == 2) {
            return "telefono";
        } else {
            return "correo";
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_fondo = new javax.swing.JPanel();
        label_titulo = new javax.swing.JLabel();
        label_minimizar = new javax.swing.JLabel();
        label_cerrar = new javax.swing.JLabel();
        panel_formulario = new javax.swing.JPanel();
        txt_id = new javax.swing.JTextField();
        txt_nombre = new javax.swing.JTextField();
        txt_telefono = new javax.swing.JTextField();
        txt_correo = new javax.swing.JTextField();
        btn_guardar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        panel_crud = new javax.swing.JPanel();
        cmbx_filtros = new javax.swing.JComboBox<>();
        btn_limpiar = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_exportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_fondo.setBackground(new java.awt.Color(255, 255, 255));
        panel_fondo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_titulo.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        label_titulo.setForeground(new java.awt.Color(153, 153, 153));
        label_titulo.setText("MiAgenda");
        panel_fondo.add(label_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        label_minimizar.setFont(new java.awt.Font("Century", 0, 24)); // NOI18N
        label_minimizar.setForeground(new java.awt.Color(102, 153, 255));
        label_minimizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_minimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agenda/horizontal line.png"))); // NOI18N
        label_minimizar.setToolTipText("Minimizar");
        label_minimizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        label_minimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_minimizarMouseClicked(evt);
            }
        });
        panel_fondo.add(label_minimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 20, 30, 30));

        label_cerrar.setFont(new java.awt.Font("Century", 0, 24)); // NOI18N
        label_cerrar.setForeground(new java.awt.Color(30, 30, 30));
        label_cerrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agenda/cross.png"))); // NOI18N
        label_cerrar.setToolTipText("Cerrar");
        label_cerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        label_cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_cerrarMouseClicked(evt);
            }
        });
        panel_fondo.add(label_cerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, 30, 30));

        panel_formulario.setBackground(new java.awt.Color(255, 255, 255));
        panel_formulario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "CONTACTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        panel_formulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_id.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_id.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Id", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        panel_formulario.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 180, 60));

        txt_nombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nombre y Apellido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        txt_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreActionPerformed(evt);
            }
        });
        panel_formulario.add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 440, 60));

        txt_telefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_telefono.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Teléfono", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        panel_formulario.add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 180, 60));

        txt_correo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_correo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Correo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        panel_formulario.add(txt_correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 440, 60));

        btn_guardar.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btn_guardar.setText("GUARDAR");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        panel_formulario.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 120, -1));

        btn_modificar.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btn_modificar.setText("MODIFICAR");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });
        panel_formulario.add(btn_modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, 120, -1));

        btn_eliminar.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btn_eliminar.setText("ELIMINAR");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        panel_formulario.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 130, 120, -1));

        panel_fondo.add(panel_formulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 880, 210));
        panel_formulario.getAccessibleContext().setAccessibleName("MiAgenda");

        panel_crud.setBackground(new java.awt.Color(255, 255, 255));
        panel_crud.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_crud.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbx_filtros.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbx_filtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "NOMBRE", "TELEFONO", "CORREO" }));
        panel_crud.add(cmbx_filtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 30));

        btn_limpiar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_limpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_limpiar.setText("X");
        btn_limpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_limpiarMouseClicked(evt);
            }
        });
        panel_crud.add(btn_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 20, 30));

        txt_buscar.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        panel_crud.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 300, 30));

        btn_buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agenda/search_30px.png"))); // NOI18N
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        panel_crud.add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 40, 30));

        panel_fondo.add(panel_crud, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 470, 50));

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE Y APELLIDOS", "TELEFONO", "CORREO ELECTRONICO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panel_fondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 880, 280));

        btn_exportar.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btn_exportar.setText("EXPORTAR");
        btn_exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportarActionPerformed(evt);
            }
        });
        panel_fondo.add(btn_exportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, 120, -1));

        getContentPane().add(panel_fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void label_cerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_cerrarMouseClicked
        //Este if verifica si el usuario dio click izquierdo.
        if (MouseEvent.BUTTON1 == evt.getButton()) {
            /*
            Mostramos una ventana emergente con emergente para confirmar la salida del sistema.
            Y la respuesta la capturamos en la variable respuesta, donde si le da a Si sera 0.
             */
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea salir de la aplicacion?", "Salir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //Validamos si el usuario le da click a Si para salir
            if (respuesta == 0) {
                System.exit(0);
            }

        }
    }//GEN-LAST:event_label_cerrarMouseClicked

    private void label_minimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_minimizarMouseClicked
        //Este if verifica si el usuario dio click izquierdo
        if (MouseEvent.BUTTON1 == evt.getButton()) {
            //Codigo para minimizar. (Enviar a segundo plano)
            this.setState(JFrame.ICONIFIED);
        }
    }//GEN-LAST:event_label_minimizarMouseClicked

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        //Verificamos si los campos requeridos para el registro estan vacios
        if (txt_nombre.getText().isEmpty() || txt_telefono.getText().isEmpty()
                || txt_correo.getText().isEmpty()) {
            //Si estan vacios se muestra la venta emergente
            JOptionPane.showMessageDialog(null, "Debe de los campos de nombre apellidos, telefono y "
                    + "correo electronico.");
        } else {
            //Declaramos las variables que se usaran para enviar los datos obtenidos de los campos de Texto
            int id = 0;
            String nombre_apellidos = txt_nombre.getText().trim();
            String telefono = txt_telefono.getText().trim();
            String correo = txt_correo.getText().trim();

            //Metdo de envio de datos a MySql
            try {
                //Este es el metodo para escribir la intruccion sql para el envio de datos a MySql
                pst = conn.prepareStatement("insert into usuarios values (?,?,?,?)");

                //Se envian los datos en el orden de las columnas
                pst.setInt(1, id);
                pst.setString(2, nombre_apellidos);
                pst.setString(3, telefono);
                pst.setString(4, correo);

                //Se envian los datos obtenidos
                pst.executeUpdate();

                //Se muestra un mensaje de exito
                JOptionPane.showMessageDialog(null, "Registro fue exitoso.");

                //Limpiamos los campos
                limpiarCampos();

                //Llamos al metodo listarUsuarios para actualizar la lista
                listarUsuarios();

            } catch (HeadlessException | SQLException e) {
                //En caso de error nos mostrar una ventana emergente y un mensaje por consola con el error
                JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar registrar al usuario.");
                System.err.println("Error: " + e.getMessage());
            } finally {
                //Finalizamos el envio de datos
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        //Este if verifica si el usuario dio click izquierdo
        if (MouseEvent.BUTTON1 == evt.getButton()) {
            //Guardamos la fila seleccionada en la variable fila
            int fila = jTable1.getSelectedRow();

            //Enviamos los datos obtenidos de la fila seleccionada a los campos. 
            //(Tanto filas como columnas de un JTable comienzan desde n-1)
            txt_id.setText(jTable1.getValueAt(fila, 0).toString());
            txt_nombre.setText(jTable1.getValueAt(fila, 1).toString());
            txt_telefono.setText(jTable1.getValueAt(fila, 2).toString());
            txt_correo.setText(jTable1.getValueAt(fila, 3).toString());

            //Habilitamos los botones de modificar y eliminar y solo Inhabilitamos el de guardar
            btn_guardar.setEnabled(false);
            btn_modificar.setEnabled(true);
           
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        //Verificamos si los campos requeridos para el registro estan vacios
        if (txt_nombre.getText().isEmpty() || txt_telefono.getText().isEmpty()
                || txt_correo.getText().isEmpty()) {
            //Si estan vacios se muestra la venta emergente
            JOptionPane.showMessageDialog(null, "Debe de los campos de nombre apellidos, telefono y "
                    + "correo electronico.");
        } else {
            //Declaramos las variables que se usaran para enviar los datos obtenidos de los campos de Texto
            int id = Integer.parseInt(txt_id.getText());
            String nombre_apellidos = txt_nombre.getText().trim();
            String telefono = txt_telefono.getText().trim();
            String correo = txt_correo.getText().trim();

            //Metdo de envio de datos a MySql
            try {
                //Este es el metodo para escribir la intruccion sql para el envio de datos a MySql
                pst = conn.prepareStatement("update usuarios set nombre_apellidos=?, telefono=?, correo=? "
                        + "where id='" + id + "'");

                //Se envian los datos en el orden de las columnas
                pst.setString(1, nombre_apellidos);
                pst.setString(2, telefono);
                pst.setString(3, correo);

                //Se envian los datos obtenidos
                pst.executeUpdate();

                //Se muestra un mensaje de exito
                JOptionPane.showMessageDialog(null, "Contacto modificado con éxito.");

                //Limpiamos los campos
                limpiarCampos();

                //Llamos al metodo listarUsuarios para actualizar la lista
                listarUsuarios();

                //Inhabilitamos los botones de modificar y eliminar y solo habilitamos el de guardar
                btn_guardar.setEnabled(true);
                btn_modificar.setEnabled(false);
                

            } catch (HeadlessException | SQLException e) {
                //En caso de error nos mostrar una ventana emergente y un mensaje por consola con el error
                JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar los datos del usuario.");
                System.err.println("Error: " + e.getMessage());
            } finally {
                //Finalizamos el envio de datos
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        //Verificarmos si el campo de la busqueda esta vacio
        if (txt_buscar.getText().isEmpty()) {
            //Si esta el vacio se muestra la venta emergente
            JOptionPane.showMessageDialog(null, "Debe de ingresar un dato a buscar.");
        } else {
            //Declaramos un DefaultTableModel para enviarle el nuevo modelo a la tabla
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

            //Le decimos que comience desde 0
            modelo.setRowCount(0);

            //Declaramos un vector de tipo String para almacenar los datos que se obtentran
            String[] datos = new String[4];

            //Obtenemos el dato a buscar.
            String dato = txt_buscar.getText().trim();

            //Declaramos un contador para obtener el numero de resultados obtenidos
            int cont = 0;

            try {
                //Este es el metodo para escribir la intruccion sql para obtener datos de MySql
                pst = conn.prepareStatement("select * from usuarios where " + filtro() + " like '%" + dato + "%'");

                //Almacenamos lo que se obtiene de la consulta
                rs = pst.executeQuery();

                //Como son varios datos a obtener usamos un while y le decimos que pare hasta que no encuentre mas datos
                while (rs.next()) {
                    //A cada posicion del vector le vamos enviados las filas obtenidas de cada columna
                    datos[0] = rs.getString(1);
                    datos[1] = rs.getString(2);
                    datos[2] = rs.getString(3);
                    datos[3] = rs.getString(4);

                    //Le enviamos el vectos a nuestro modelo
                    modelo.addRow(datos);

                    //Incrementamos de uno en uno cada vez que encuentre un resultado
                    cont++;
                }

                //Validamos si encontro resultados
                if (cont > 0) {
                    //Le enviamos el modelo a la tabla
                    jTable1.setModel(modelo);
                } else {
                    //En caso de no encontrar resultados
                    JOptionPane.showMessageDialog(null, "No se encontro ningun resultado de su busqueda.");
                    //Volvemos a listar los usuarios
                    listarUsuarios();
                }
                //Mostarmos el boton para hacer una nueva busqueda
                btn_limpiar.setVisible(true);

            } catch (SQLException e) {
                //En caso de error nos mostrar una ventana emergente y un mensaje por consola con el error
                JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar obtener los datos\n"
                        + "de la base de datos.");
                System.err.println("Error: " + e.getMessage());
            } finally {
                //Al finalizar decimos que que se cierre la consulta y los datos obtenidos
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_limpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_limpiarMouseClicked
        //Este if verifica si el usuario dio click izquierdo
        if (MouseEvent.BUTTON1 == evt.getButton()) {
            //Limpiamos el campo de busqueda
            txt_buscar.setText("");
            
            //Listamos a los usuarios
            listarUsuarios();
            
            //Ocultamos el boton
            btn_limpiar.setVisible(false);
        }
    }//GEN-LAST:event_btn_limpiarMouseClicked

    private void txt_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
        String url = "jdbc:mysql://149.50.134.107:3306/contacts?useSSL=false";
        String user = "test";
        String password = "test";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Ensure the file has a .txt extension
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
                 BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                // Get column names
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    writer.write(rs.getMetaData().getColumnName(i));
                    if (i < columnCount) writer.write("\t");
                }
                writer.newLine();

                // Write data rows
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        writer.write(rs.getString(i));
                        if (i < columnCount) writer.write("\t");
                    }
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(this, "Archivo exportado!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Falla al exportar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Exportación cancelada ", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        //Obtenemos el id del usuario del campo.
        int id = Integer.parseInt(txt_id.getText());

        //Metdo de eliminacion de datos a MySql
        try {
            //Este es el metodo para escribir la intruccion sql para la eliminacion de datos a MySql
            pst = conn.prepareStatement("delete from usuarios where id='" + id + "'");

            //Se envian la instrucion
            pst.executeUpdate();

            //Se muestra un mensaje de exito
            JOptionPane.showMessageDialog(null, "Datos eliminados con exito.");

            //Limpiamos los campos
            limpiarCampos();

            //Llamos al metodo listarUsuarios para actualizar la lista
            listarUsuarios();

            //Inhabilitamos los botones de modificar y eliminar y solo habilitamos el de guardar
            btn_guardar.setEnabled(true);
            btn_modificar.setEnabled(false);
            btn_eliminar.setEnabled(false);

        } catch (HeadlessException | SQLException e) {
            //En caso de error nos mostrar una ventana emergente y un mensaje por consola con el error
            JOptionPane.showMessageDialog(null, "Ocurrio un error al eliminar los datos del usuario.");
            System.err.println("Error: " + e.getMessage());
        } finally {
            //Finalizamos el envio de datos
            try {
                pst.close();
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JLabel btn_limpiar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox<String> cmbx_filtros;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel label_cerrar;
    private javax.swing.JLabel label_minimizar;
    private javax.swing.JLabel label_titulo;
    private javax.swing.JPanel panel_crud;
    private javax.swing.JPanel panel_fondo;
    private javax.swing.JPanel panel_formulario;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables

    void limpiarCampos() {
        txt_id.setText("");
        txt_nombre.setText("");
        txt_telefono.setText("");
        txt_correo.setText("");
    }

}
