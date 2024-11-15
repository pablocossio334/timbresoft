package capaPresentacion;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import capaLogica.eventos;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Font;
import java.util.ArrayList;
public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;
    private DefaultTableModel modeloTabla;
	private JLabel lblHoraActual;
	private JComboBox cmbTipo;
	private JSpinner sphora;
    private JSpinner spminutos;
    private JButton btnEliminar;
    private eventos proximoEvento;
    private JLabel lblPxox;
    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 *agregar fila a tabla
	 */
	public void cargarProximoEvento() {
		proximoEvento=new eventos().obtenerProximoEvento();
		lblPxox.setText(""+proximoEvento.getHoraEvento());
		
	}
	public void agregarFilaTabla(String tiempo,String duracion) {
		String[] datos= {tiempo,duracion};
		modeloTabla.addRow(datos);
		
	}
	 private void iniciarReloj() {
	        // Formato de la hora
	      
	        // Crear un Timer que actualice el JLabel cada segundo
	        Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            @Override
	            public void run() {
	               
	                LocalTime horaActual=LocalTime.now();
	               
	                boolean horasIguales = horaActual.getHour() == proximoEvento.getHoraEvento().getHour();
	                boolean minutosIguales = horaActual.getMinute() == proximoEvento.getHoraEvento().getMinute();
	                boolean segundosCero=horaActual.getSecond()==0;
	                System.out.println(horasIguales + "--" + minutosIguales+"--"+segundosCero);
	                
	                lblHoraActual.setText(horaActual.getHour()+":"+horaActual.getMinute()+":"+horaActual.getSecond());
	            }
	        }, 0, 1000); // 0 delay inicial, actualizar cada 1000 ms (1 segundo)
	    }
	 public void ActualizarTabla() {
		 eventos eventoz = new eventos();
			ArrayList<eventos> eventoS=eventoz.obtenerEventosBaseDato();
		 modeloTabla.setRowCount(0);
		for(eventos e:eventoS) {
			
			agregarFilaTabla(e.getHoraEvento().toString(),e.getDuracion()+"") ;
		}
		 
	 }
	public VentanaPrincipal() {
		setResizable(false);
		setTitle("TIMBRESOFT");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 516, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 77, 201, 176);
		contentPane.add(scrollPane);
		
		table = new JTable();
		String[] cabeceras= {"hora","duracion"};
		modeloTabla=new DefaultTableModel(null,cabeceras);
				
				
				
		table.setModel(modeloTabla);
		scrollPane.setViewportView(table);
		
		cmbTipo = new JComboBox();
		cmbTipo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"cambio", "recreo"}));
		cmbTipo.setBounds(278, 155, 174, 37);
		contentPane.add(cmbTipo);
		
		sphora = new JSpinner();
		sphora.setFont(new Font("Tahoma", Font.PLAIN, 17));
		sphora.setBounds(285, 110, 43, 34);
		contentPane.add(sphora);
		
		spminutos = new JSpinner();
		spminutos.setFont(new Font("Tahoma", Font.PLAIN, 17));
		spminutos.setBounds(350, 110, 43, 34);
		contentPane.add(spminutos);
		
		JButton btnAgregar = new JButton("AGREGAR");
		btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAgregar.addActionListener(new ActionListener() {
			int dur;
			public void actionPerformed(ActionEvent e) {
				if(cmbTipo.getSelectedIndex()==0) {
					dur=3;
				}
					else
						dur=10;
			eventos E=new eventos();
			LocalTime horaSeleccionada=LocalTime.of((Integer)sphora.getValue(),(Integer) spminutos.getValue());
			E.setHoraEvento(horaSeleccionada);
			E.setDuracion(dur);
			System.out.println(E.getHoraEvento()+"-"+"-"+E.getDuracion());
			boolean seAgrego =E.agregarEventoAbase();
			if(seAgrego==true) {
				JOptionPane.showMessageDialog(null, "Se Agrego el evento correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "No se puede agregar el evento, ya existe un evento a esa hora", "Error", JOptionPane.ERROR_MESSAGE);
			}
			ActualizarTabla();		
			}
		});
		btnAgregar.setBounds(303, 203, 130, 35);
		contentPane.add(btnAgregar);
		
		JLabel lblNewLabel = new JLabel("AGREGAR NUEVO HORARIO");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(252, 70, 238, 37);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(":");
		lblNewLabel_1.setBounds(337, 73, 37, 37);
		contentPane.add(lblNewLabel_1);
		
		lblHoraActual = new JLabel("");
		lblHoraActual.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblHoraActual.setBounds(42, 27, 103, 39);
		contentPane.add(lblHoraActual);
		
		JLabel lblNewLabel_2 = new JLabel("<HORA ACTUAL>");
		lblNewLabel_2.setBounds(42, 11, 104, 14);
		contentPane.add(lblNewLabel_2);
		
		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected=table.getSelectedRow();
				if(selected==-1) {
					System.out.println("Error");
					
				}
				else
				{
					Object valorCelda = table.getValueAt(selected, 0);
					eventos eventoEliminar=new eventos();
					LocalTime horaEvento=LocalTime.parse(valorCelda.toString());
					eventoEliminar.setHoraEvento(horaEvento);
					int respuesta = JOptionPane.showConfirmDialog(
				            null,
				            "¿Quiere eliminar el registro?",
				            "Confirmación",
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE
				        );

				        if (respuesta == JOptionPane.YES_OPTION) {
				            System.out.println("El usuario eligió eliminar el registro.");
				            eventoEliminar.eliminarEventoBase();
				            // Lógica para eliminar el registro
				        } else if (respuesta == JOptionPane.NO_OPTION) {
				            System.out.println("El usuario eligió no eliminar el registro.");
				            // Lógica para cancelar la operación
				        }
					
					System.out.println("Valor de la celda seleccionada: " + valorCelda);
					ActualizarTabla();
					
				}
			}
		});
		btnEliminar.setBounds(123, 262, 89, 23);
		contentPane.add(btnEliminar);
		
		JLabel lblNewLabel_2_1 = new JLabel("<PROXIMO EVENTO>");
		lblNewLabel_2_1.setBounds(289, 11, 122, 14);
		contentPane.add(lblNewLabel_2_1);
		
		lblPxox = new JLabel("");
		lblPxox.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPxox.setBounds(290, 27, 103, 39);
		contentPane.add(lblPxox);
		
		ActualizarTabla();
		cargarProximoEvento();
		iniciarReloj();
		
	}
}
