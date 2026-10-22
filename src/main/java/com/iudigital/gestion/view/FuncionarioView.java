package com.iudigital.gestion.view;

import com.iudigital.gestion.dao.FormacionAcademicaDAOImpl;
import com.iudigital.gestion.dao.FuncionarioDAOImpl;
import com.iudigital.gestion.dao.GrupoFamiliarDAOImpl;
import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.FormacionAcademica;
import com.iudigital.gestion.model.Funcionario;
import com.iudigital.gestion.model.GrupoFamiliar;
import com.iudigital.gestion.service.FormacionAcademicaService;
import com.iudigital.gestion.service.FuncionarioService;
import com.iudigital.gestion.service.GrupoFamiliarService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class FuncionarioView extends JFrame {

    // Servicios
    private final FuncionarioService servicioFuncionarios = new FuncionarioService(new FuncionarioDAOImpl());
    private final GrupoFamiliarService servicioFamiliares = new GrupoFamiliarService(new GrupoFamiliarDAOImpl());
    private final FormacionAcademicaService servicioFormacion = new FormacionAcademicaService(new FormacionAcademicaDAOImpl());

    // Estado del funcionario actualmente seleccionado
    private int idFuncionarioSeleccionado = 0;
    private String nombreFuncionarioSeleccionado = "";
    private String docFuncionarioSeleccionado = "";

    // Componentes - Pestaña 1: Funcionarios
    private final JTextField txtFuncId = new JTextField(6);
    private final JComboBox<String> cbFuncTipo = new JComboBox<>(new String[]{"CC", "CE", "TI", "Pasaporte", "Otro"});
    private final JTextField txtFuncNumero = new JTextField(15);
    private final JTextField txtFuncNombres = new JTextField(16);
    private final JTextField txtFuncApellidos = new JTextField(16);
    private final JComboBox<String> cbFuncEstadoCivil = new JComboBox<>(new String[]{"Soltero/a", "Casado/a", "Unión libre", "Divorciado/a", "Viudo/a"});
    private final JComboBox<String> cbFuncSexo = new JComboBox<>(new String[]{"Femenino", "Masculino", "Otro", "Prefiere no indicar"});
    private final JTextField txtFuncDireccion = new JTextField(20);
    private final JTextField txtFuncTelefono = new JTextField(14);
    private final JTextField txtFuncFecha = new JTextField(12);
    private final DefaultTableModel modeloFuncionarios = new DefaultTableModel(
        new String[]{"ID", "Tipo ID", "Número ID", "Nombres", "Apellidos", "Estado civil", "Sexo", "Dirección", "Teléfono", "Nacimiento"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tablaFuncionarios = new JTable(modeloFuncionarios);

    // Componentes - Pestaña 2: Grupo Familiar
    private final JLabel lblInfoFamiliar = new JLabel();
    private final JTextField txtFamId = new JTextField(6);
    private final JComboBox<String> cbFamTipo = new JComboBox<>(new String[]{"TI", "CC", "RC", "CE", "Pasaporte", "Otro"});
    private final JTextField txtFamNumero = new JTextField(15);
    private final JTextField txtFamNombres = new JTextField(16);
    private final JTextField txtFamApellidos = new JTextField(16);
    private final JTextField txtFamFecha = new JTextField(12);
    private final JComboBox<String> cbFamRol = new JComboBox<>(new String[]{"Hijo/a", "Cónyuge", "Padre", "Madre", "Hermano/a", "Otro"});
    private final DefaultTableModel modeloFamiliares = new DefaultTableModel(
        new String[]{"ID Familiar", "ID Funcionario", "Tipo ID", "Número ID", "Nombres", "Apellidos", "Nacimiento", "Rol Familiar"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tablaFamiliares = new JTable(modeloFamiliares);

    // Componentes - Pestaña 3: Formación Académica
    private final JLabel lblInfoFormacion = new JLabel();
    private final JTextField txtFormId = new JTextField(6);
    private final JTextField txtFormUniversidad = new JTextField(24);
    private final JComboBox<String> cbFormNivel = new JComboBox<>(new String[]{
        "Técnico", "Tecnología", "Profesional", "Especialización", "Maestría", "Doctorado", "Bachiller", "Otro"
    });
    private final JTextField txtFormTitulo = new JTextField(24);
    private final DefaultTableModel modeloFormacion = new DefaultTableModel(
        new String[]{"ID Formación", "ID Funcionario", "Universidad / Institución", "Nivel de Estudio", "Título Obtenido"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tablaFormacion = new JTable(modeloFormacion);

    public FuncionarioView() {
        setTitle("Sistema de Gestión de Funcionarios — IU Digital de Antioquia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        construirInterfaz();
        cargarFuncionarios();
        actualizarBannersSeleccion();
    }

    private void construirInterfaz() {
        JPanel principal = new JPanel(new BorderLayout(8, 8));
        principal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("GESTIÓN INTEGRAL DE FUNCIONARIOS", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        principal.add(titulo, BorderLayout.NORTH);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(pestañas.getFont().deriveFont(Font.BOLD, 13f));
        pestañas.addTab("🏢 Funcionarios", crearPanelFuncionarios());
        pestañas.addTab("👨‍👩‍👧‍👦 Grupo Familiar", crearPanelGrupoFamiliar());
        pestañas.addTab("🎓 Formación Académica", crearPanelFormacionAcademica());

        pestañas.addChangeListener(e -> {
            int idx = pestañas.getSelectedIndex();
            if (idx == 1) {
                cargarFamiliares();
            } else if (idx == 2) {
                cargarFormacion();
            }
        });

        principal.add(pestañas, BorderLayout.CENTER);
        setContentPane(principal);
    }

    // ==========================================
    // PESTAÑA 1: FUNCIONARIOS
    // ==========================================
    private JPanel crearPanelFuncionarios() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del Funcionario (Fecha en formato AAAA-MM-DD)"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 5, 4, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        txtFuncId.setEditable(false);
        int fila = 0;
        agregarFilaFormulario(form, c, fila++, "ID (Auto)", txtFuncId, "Tipo Identificación", cbFuncTipo);
        agregarFilaFormulario(form, c, fila++, "Número Identificación", txtFuncNumero, "Nombres", txtFuncNombres);
        agregarFilaFormulario(form, c, fila++, "Apellidos", txtFuncApellidos, "Estado Civil", cbFuncEstadoCivil);
        agregarFilaFormulario(form, c, fila++, "Sexo", cbFuncSexo, "Dirección", txtFuncDireccion);
        agregarFilaFormulario(form, c, fila++, "Teléfono", txtFuncTelefono, "Fecha Nacimiento", txtFuncFecha);

        panel.add(form, BorderLayout.NORTH);

        // Tabla
        tablaFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaFuncionarios.getSelectedRow() >= 0) {
                cargarSeleccionFuncionario();
            }
        });
        panel.add(new JScrollPane(tablaFuncionarios), BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JButton btnCrear = new JButton("Crear Funcionario");
        JButton btnActualizar = new JButton("Actualizar Funcionario");
        JButton btnEliminar = new JButton("Eliminar Funcionario");
        JButton btnLimpiar = new JButton("Limpiar Formulario");
        JButton btnRecargar = new JButton("Recargar Lista");

        btnCrear.addActionListener(e -> crearFuncionario());
        btnActualizar.addActionListener(e -> actualizarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
        btnLimpiar.addActionListener(e -> limpiarFormularioFuncionario());
        btnRecargar.addActionListener(e -> cargarFuncionarios());

        botones.add(btnCrear);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        botones.add(btnRecargar);

        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    // ==========================================
    // PESTAÑA 2: GRUPO FAMILIAR
    // ==========================================
    private JPanel crearPanelGrupoFamiliar() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        // Banner superior informativo
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 230), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        lblInfoFamiliar.setFont(lblInfoFamiliar.getFont().deriveFont(Font.BOLD, 13f));
        bannerPanel.add(lblInfoFamiliar, BorderLayout.CENTER);

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del Familiar (asociado al funcionario seleccionado)"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 5, 4, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        txtFamId.setEditable(false);
        int fila = 0;
        agregarFilaFormulario(form, c, fila++, "ID Familiar (Auto)", txtFamId, "Rol / Parentesco", cbFamRol);
        agregarFilaFormulario(form, c, fila++, "Tipo Identificación", cbFamTipo, "Número Identificación", txtFamNumero);
        agregarFilaFormulario(form, c, fila++, "Nombres", txtFamNombres, "Apellidos", txtFamApellidos);
        agregarFilaFormulario(form, c, fila++, "Fecha Nacimiento (Opcional)", txtFamFecha, "", new JLabel());

        JPanel norte = new JPanel(new BorderLayout(6, 6));
        norte.add(bannerPanel, BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        // Tabla
        tablaFamiliares.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaFamiliares.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaFamiliares.getSelectedRow() >= 0) {
                cargarSeleccionFamiliar();
            }
        });
        panel.add(new JScrollPane(tablaFamiliares), BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JButton btnCrear = new JButton("Guardar Familiar");
        JButton btnActualizar = new JButton("Actualizar Familiar");
        JButton btnEliminar = new JButton("Eliminar Familiar");
        JButton btnLimpiar = new JButton("Limpiar Formulario");
        JButton btnRecargar = new JButton("Recargar Familiares");

        btnCrear.addActionListener(e -> crearFamiliar());
        btnActualizar.addActionListener(e -> actualizarFamiliar());
        btnEliminar.addActionListener(e -> eliminarFamiliar());
        btnLimpiar.addActionListener(e -> limpiarFormularioFamiliar());
        btnRecargar.addActionListener(e -> cargarFamiliares());

        botones.add(btnCrear);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        botones.add(btnRecargar);

        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    // ==========================================
    // PESTAÑA 3: FORMACIÓN ACADÉMICA
    // ==========================================
    private JPanel crearPanelFormacionAcademica() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        // Banner superior informativo
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 230), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        lblInfoFormacion.setFont(lblInfoFormacion.getFont().deriveFont(Font.BOLD, 13f));
        bannerPanel.add(lblInfoFormacion, BorderLayout.CENTER);

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos de Formación Académica (asociada al funcionario seleccionado)"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 5, 4, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        txtFormId.setEditable(false);
        int fila = 0;
        agregarFilaFormulario(form, c, fila++, "ID Formación (Auto)", txtFormId, "Nivel de Estudio", cbFormNivel);
        agregarFilaFormulario(form, c, fila++, "Universidad / Institución", txtFormUniversidad, "Título Obtenido", txtFormTitulo);

        JPanel norte = new JPanel(new BorderLayout(6, 6));
        norte.add(bannerPanel, BorderLayout.NORTH);
        norte.add(form, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        // Tabla
        tablaFormacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaFormacion.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaFormacion.getSelectedRow() >= 0) {
                cargarSeleccionFormacion();
            }
        });
        panel.add(new JScrollPane(tablaFormacion), BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JButton btnCrear = new JButton("Guardar Estudio");
        JButton btnActualizar = new JButton("Actualizar Estudio");
        JButton btnEliminar = new JButton("Eliminar Estudio");
        JButton btnLimpiar = new JButton("Limpiar Formulario");
        JButton btnRecargar = new JButton("Recargar Estudios");

        btnCrear.addActionListener(e -> crearFormacion());
        btnActualizar.addActionListener(e -> actualizarFormacion());
        btnEliminar.addActionListener(e -> eliminarFormacion());
        btnLimpiar.addActionListener(e -> limpiarFormularioFormacion());
        btnRecargar.addActionListener(e -> cargarFormacion());

        botones.add(btnCrear);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        botones.add(btnRecargar);

        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE INTERFAZ
    // ==========================================
    private void agregarFilaFormulario(JPanel p, GridBagConstraints c, int fila,
                                       String l1, java.awt.Component a,
                                       String l2, java.awt.Component b) {
        c.gridy = fila;
        c.gridx = 0; c.weightx = 0;
        p.add(new JLabel(l1.isEmpty() ? "" : l1 + ":"), c);
        c.gridx = 1; c.weightx = 1;
        p.add(a, c);
        c.gridx = 2; c.weightx = 0;
        p.add(new JLabel(l2.isEmpty() ? "" : l2 + ":"), c);
        c.gridx = 3; c.weightx = 1;
        p.add(b, c);
    }

    private void actualizarBannersSeleccion() {
        if (idFuncionarioSeleccionado > 0) {
            String texto = "👤 Funcionario seleccionado: [ID " + idFuncionarioSeleccionado + "] "
                + nombreFuncionarioSeleccionado + " (Identificación: " + docFuncionarioSeleccionado + ")";
            lblInfoFamiliar.setText(texto);
            lblInfoFamiliar.setForeground(new Color(20, 90, 40));
            lblInfoFormacion.setText(texto);
            lblInfoFormacion.setForeground(new Color(20, 90, 40));
        } else {
            String aviso = "⚠️ Ningún funcionario seleccionado. Selecciona uno en la pestaña 'Funcionarios' para gestionar sus datos.";
            lblInfoFamiliar.setText(aviso);
            lblInfoFamiliar.setForeground(new Color(170, 70, 0));
            lblInfoFormacion.setText(aviso);
            lblInfoFormacion.setForeground(new Color(170, 70, 0));
        }
    }

    // ==========================================
    // LÓGICA DE NEGOCIO: FUNCIONARIOS
    // ==========================================
    private Funcionario leerFormularioFuncionario() {
        String fechaTexto = txtFuncFecha.getText().trim();
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaTexto);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Escribe la fecha de nacimiento en formato AAAA-MM-DD (ejemplo: 1995-04-12).");
        }
        int id = txtFuncId.getText().isBlank() ? 0 : Integer.parseInt(txtFuncId.getText());
        return new Funcionario(
            id,
            (String) cbFuncTipo.getSelectedItem(),
            txtFuncNumero.getText().trim(),
            txtFuncNombres.getText().trim(),
            txtFuncApellidos.getText().trim(),
            (String) cbFuncEstadoCivil.getSelectedItem(),
            (String) cbFuncSexo.getSelectedItem(),
            txtFuncDireccion.getText().trim(),
            txtFuncTelefono.getText().trim(),
            fecha
        );
    }

    private void crearFuncionario() {
        try {
            servicioFuncionarios.crear(leerFormularioFuncionario());
            mensajeExito("Funcionario creado correctamente.");
            limpiarFormularioFuncionario();
            cargarFuncionarios();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al crear funcionario", ex);
        }
    }

    private void actualizarFuncionario() {
        try {
            if (txtFuncId.getText().isBlank()) {
                throw new IllegalArgumentException("Selecciona primero un funcionario de la tabla para actualizar.");
            }
            servicioFuncionarios.actualizar(leerFormularioFuncionario());
            mensajeExito("Funcionario actualizado correctamente.");
            cargarFuncionarios();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al actualizar funcionario", ex);
        }
    }

    private void eliminarFuncionario() {
        try {
            if (txtFuncId.getText().isBlank()) {
                throw new IllegalArgumentException("Selecciona primero un funcionario de la tabla para eliminar.");
            }
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este funcionario?\n"
                + "Se eliminarán también en cascada todos sus familiares y estudios asociados.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion != JOptionPane.YES_OPTION) return;

            int id = Integer.parseInt(txtFuncId.getText());
            servicioFuncionarios.eliminar(id);
            mensajeExito("Funcionario eliminado correctamente.");
            limpiarFormularioFuncionario();
            idFuncionarioSeleccionado = 0;
            nombreFuncionarioSeleccionado = "";
            docFuncionarioSeleccionado = "";
            actualizarBannersSeleccion();
            modeloFamiliares.setRowCount(0);
            modeloFormacion.setRowCount(0);
            cargarFuncionarios();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al eliminar funcionario", ex);
        }
    }

    private void cargarFuncionarios() {
        try {
            modeloFuncionarios.setRowCount(0);
            List<Funcionario> lista = servicioFuncionarios.listar();
            for (Funcionario f : lista) {
                modeloFuncionarios.addRow(new Object[]{
                    f.getIdFuncionario(),
                    f.getTipoIdentificacion(),
                    f.getNumeroIdentificacion(),
                    f.getNombres(),
                    f.getApellidos(),
                    f.getEstadoCivil(),
                    f.getSexo(),
                    f.getDireccion(),
                    f.getTelefono(),
                    f.getFechaNacimiento()
                });
            }
        } catch (AccesoDatosException ex) {
            mensajeError("Error al consultar funcionarios", ex);
        }
    }

    private void cargarSeleccionFuncionario() {
        int fila = tablaFuncionarios.getSelectedRow();
        if (fila < 0) return;

        idFuncionarioSeleccionado = (int) modeloFuncionarios.getValueAt(fila, 0);
        String tipoId = String.valueOf(modeloFuncionarios.getValueAt(fila, 1));
        docFuncionarioSeleccionado = String.valueOf(modeloFuncionarios.getValueAt(fila, 2));
        String nombres = String.valueOf(modeloFuncionarios.getValueAt(fila, 3));
        String apellidos = String.valueOf(modeloFuncionarios.getValueAt(fila, 4));
        nombreFuncionarioSeleccionado = nombres + " " + apellidos;

        txtFuncId.setText(String.valueOf(idFuncionarioSeleccionado));
        cbFuncTipo.setSelectedItem(tipoId);
        txtFuncNumero.setText(docFuncionarioSeleccionado);
        txtFuncNombres.setText(nombres);
        txtFuncApellidos.setText(apellidos);
        cbFuncEstadoCivil.setSelectedItem(modeloFuncionarios.getValueAt(fila, 5));
        cbFuncSexo.setSelectedItem(modeloFuncionarios.getValueAt(fila, 6));
        txtFuncDireccion.setText(String.valueOf(modeloFuncionarios.getValueAt(fila, 7)));
        txtFuncTelefono.setText(String.valueOf(modeloFuncionarios.getValueAt(fila, 8)));
        txtFuncFecha.setText(String.valueOf(modeloFuncionarios.getValueAt(fila, 9)));

        actualizarBannersSeleccion();
        cargarFamiliares();
        cargarFormacion();
    }

    private void limpiarFormularioFuncionario() {
        tablaFuncionarios.clearSelection();
        txtFuncId.setText("");
        txtFuncNumero.setText("");
        txtFuncNombres.setText("");
        txtFuncApellidos.setText("");
        txtFuncDireccion.setText("");
        txtFuncTelefono.setText("");
        txtFuncFecha.setText("");
        cbFuncTipo.setSelectedIndex(0);
        cbFuncEstadoCivil.setSelectedIndex(0);
        cbFuncSexo.setSelectedIndex(0);
    }

    // ==========================================
    // LÓGICA DE NEGOCIO: GRUPO FAMILIAR
    // ==========================================
    private GrupoFamiliar leerFormularioFamiliar() {
        if (idFuncionarioSeleccionado <= 0) {
            throw new IllegalArgumentException("Primero debes seleccionar un funcionario en la pestaña 'Funcionarios'.");
        }
        String fechaTexto = txtFamFecha.getText().trim();
        LocalDate fecha = null;
        if (!fechaTexto.isBlank()) {
            try {
                fecha = LocalDate.parse(fechaTexto);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Escribe la fecha de nacimiento del familiar en formato AAAA-MM-DD o déjala en blanco.");
            }
        }
        int idFamiliar = txtFamId.getText().isBlank() ? 0 : Integer.parseInt(txtFamId.getText());
        return new GrupoFamiliar(
            idFamiliar,
            idFuncionarioSeleccionado,
            (String) cbFamTipo.getSelectedItem(),
            txtFamNumero.getText().trim(),
            txtFamNombres.getText().trim(),
            txtFamApellidos.getText().trim(),
            fecha,
            (String) cbFamRol.getSelectedItem()
        );
    }

    private void crearFamiliar() {
        try {
            servicioFamiliares.crear(leerFormularioFamiliar());
            mensajeExito("Familiar registrado correctamente.");
            limpiarFormularioFamiliar();
            cargarFamiliares();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al registrar familiar", ex);
        }
    }

    private void actualizarFamiliar() {
        try {
            if (txtFamId.getText().isBlank()) {
                throw new IllegalArgumentException("Selecciona primero un familiar de la tabla para actualizar.");
            }
            servicioFamiliares.actualizar(leerFormularioFamiliar());
            mensajeExito("Familiar actualizado correctamente.");
            limpiarFormularioFamiliar();
            cargarFamiliares();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al actualizar familiar", ex);
        }
    }

    private void eliminarFamiliar() {
        try {
            if (txtFamId.getText().isBlank()) {
                throw new IllegalArgumentException("Selecciona primero un familiar de la tabla para eliminar.");
            }
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar a este familiar?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion != JOptionPane.YES_OPTION) return;

            int id = Integer.parseInt(txtFamId.getText());
            servicioFamiliares.eliminar(id);
            mensajeExito("Familiar eliminado correctamente.");
            limpiarFormularioFamiliar();
            cargarFamiliares();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al eliminar familiar", ex);
        }
    }

    private void cargarFamiliares() {
        modeloFamiliares.setRowCount(0);
        if (idFuncionarioSeleccionado <= 0) return;

        try {
            List<GrupoFamiliar> lista = servicioFamiliares.listarPorFuncionario(idFuncionarioSeleccionado);
            for (GrupoFamiliar f : lista) {
                modeloFamiliares.addRow(new Object[]{
                    f.getIdFamiliar(),
                    f.getIdFuncionario(),
                    f.getTipoIdentificacion(),
                    f.getNumeroIdentificacion(),
                    f.getNombres(),
                    f.getApellidos(),
                    f.getFechaNacimiento() != null ? f.getFechaNacimiento().toString() : "",
                    f.getRolFamiliar()
                });
            }
        } catch (AccesoDatosException ex) {
            mensajeError("Error al consultar grupo familiar", ex);
        }
    }

    private void cargarSeleccionFamiliar() {
        int fila = tablaFamiliares.getSelectedRow();
        if (fila < 0) return;

        txtFamId.setText(String.valueOf(modeloFamiliares.getValueAt(fila, 0)));
        cbFamTipo.setSelectedItem(modeloFamiliares.getValueAt(fila, 2));
        txtFamNumero.setText(String.valueOf(modeloFamiliares.getValueAt(fila, 3)));
        txtFamNombres.setText(String.valueOf(modeloFamiliares.getValueAt(fila, 4)));
        txtFamApellidos.setText(String.valueOf(modeloFamiliares.getValueAt(fila, 5)));
        Object fecha = modeloFamiliares.getValueAt(fila, 6);
        txtFamFecha.setText(fecha != null ? fecha.toString() : "");
        cbFamRol.setSelectedItem(modeloFamiliares.getValueAt(fila, 7));
    }

    private void limpiarFormularioFamiliar() {
        tablaFamiliares.clearSelection();
        txtFamId.setText("");
        txtFamNumero.setText("");
        txtFamNombres.setText("");
        txtFamApellidos.setText("");
        txtFamFecha.setText("");
        cbFamTipo.setSelectedIndex(0);
        cbFamRol.setSelectedIndex(0);
    }

    // ==========================================
    // LÓGICA DE NEGOCIO: FORMACIÓN ACADÉMICA
    // ==========================================
    private FormacionAcademica leerFormularioFormacion() {
        if (idFuncionarioSeleccionado <= 0) {
            throw new IllegalArgumentException("Primero debes seleccionar un funcionario en la pestaña 'Funcionarios'.");
        }
        int idFormacion = txtFormId.getText().isBlank() ? 0 : Integer.parseInt(txtFormId.getText());
        return new FormacionAcademica(
            idFormacion,
            idFuncionarioSeleccionado,
            txtFormUniversidad.getText().trim(),
            (String) cbFormNivel.getSelectedItem(),
            txtFormTitulo.getText().trim()
        );
    }

    private void crearFormacion() {
        try {
            servicioFormacion.crear(leerFormularioFormacion());
            mensajeExito("Formación académica registrada correctamente.");
            limpiarFormularioFormacion();
            cargarFormacion();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al registrar formación académica", ex);
        }
    }

    private void actualizarFormacion() {
        try {
            if (txtFormId.getText().isBlank()) {
                throw new IllegalArgumentException("Selecciona primero un estudio de la tabla para actualizar.");
            }
            servicioFormacion.actualizar(leerFormularioFormacion());
            mensajeExito("Formación académica actualizada correctamente.");
            limpiarFormularioFormacion();
            cargarFormacion();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al actualizar formación académica", ex);
        }
    }

    private void eliminarFormacion() {
        try {
            if (txtFormId.getText().isBlank()) {
                throw new IllegalArgumentException("Selecciona primero un estudio de la tabla para eliminar.");
            }
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este registro de estudio?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion != JOptionPane.YES_OPTION) return;

            int id = Integer.parseInt(txtFormId.getText());
            servicioFormacion.eliminar(id);
            mensajeExito("Formación académica eliminada correctamente.");
            limpiarFormularioFormacion();
            cargarFormacion();
        } catch (IllegalArgumentException | AccesoDatosException ex) {
            mensajeError("Error al eliminar formación académica", ex);
        }
    }

    private void cargarFormacion() {
        modeloFormacion.setRowCount(0);
        if (idFuncionarioSeleccionado <= 0) return;

        try {
            List<FormacionAcademica> lista = servicioFormacion.listarPorFuncionario(idFuncionarioSeleccionado);
            for (FormacionAcademica f : lista) {
                modeloFormacion.addRow(new Object[]{
                    f.getIdFormacion(),
                    f.getIdFuncionario(),
                    f.getUniversidad(),
                    f.getNivelEstudio(),
                    f.getTitulo()
                });
            }
        } catch (AccesoDatosException ex) {
            mensajeError("Error al consultar formación académica", ex);
        }
    }

    private void cargarSeleccionFormacion() {
        int fila = tablaFormacion.getSelectedRow();
        if (fila < 0) return;

        txtFormId.setText(String.valueOf(modeloFormacion.getValueAt(fila, 0)));
        txtFormUniversidad.setText(String.valueOf(modeloFormacion.getValueAt(fila, 2)));
        cbFormNivel.setSelectedItem(modeloFormacion.getValueAt(fila, 3));
        txtFormTitulo.setText(String.valueOf(modeloFormacion.getValueAt(fila, 4)));
    }

    private void limpiarFormularioFormacion() {
        tablaFormacion.clearSelection();
        txtFormId.setText("");
        txtFormUniversidad.setText("");
        txtFormTitulo.setText("");
        cbFormNivel.setSelectedIndex(0);
    }

    // ==========================================
    // MENSAJES Y MANEJO SEGURO DE EXCEPCIONES
    // ==========================================
    private void mensajeExito(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mensajeError(String titulo, Exception ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.getMessage() != null ? ex.getMessage() : "Ocurrió un error inesperado.");
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            sb.append("\n\nDetalle técnico: ").append(ex.getCause().getMessage());
        }
        JOptionPane.showMessageDialog(this, sb.toString(), titulo, JOptionPane.ERROR_MESSAGE);
    }
}
