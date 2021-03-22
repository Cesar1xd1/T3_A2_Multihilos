import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

class Ventana extends JFrame{
	ArrayList<String> lista = new ArrayList<String>();
	JTextArea aTxt1,aTxt2;
	JScrollPane scrollbar1,scrollbar2;
	
	public Ventana() {
		getContentPane().setLayout(new FlowLayout());
		setTitle("T3_A2_Hilos_Ocurrencia");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		for(int i=0; i<10000000; i++) {
			if((int)((Math.random()*2)+1)==1) {
				lista.add("Si");
			}else {
				lista.add("No");
			}
		}
		aTxt1=new JTextArea(15,15);
		aTxt1.setBackground(getForeground().green);
		
		aTxt2=new JTextArea(15,15);
		aTxt2.setBackground(getForeground().red);
		
		aTxt1.setEditable(false);
		aTxt2.setEditable(false);
		
		scrollbar1=new JScrollPane(aTxt1);
		scrollbar2=new JScrollPane(aTxt2);
		//Panel SI
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(scrollbar1);
		panel.setBorder(BorderFactory.createTitledBorder("Coincide con SI"));
		add(panel);
		add(new JLabel("     "));
		//Panel NO
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.add(scrollbar2);
		panel2.setBorder(BorderFactory.createTitledBorder("Coincide con NO "));
		add(panel2);
		JProgressBar pb1 = new JProgressBar(0, 2);
		pb1.setValue(0);
		pb1.setStringPainted(true);
		pb1.setString("Contando");
		JProgressBar pb2 = new JProgressBar(0, 2);
		pb2.setValue(0);
		pb2.setStringPainted(true);
		pb2.setString("Contando");
		Llenado llenado=new Llenado(lista, aTxt1, aTxt2, pb1,pb2);
		Thread hLlenado=new Thread(llenado);
		hLlenado.start();
		//Resultados
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.setPreferredSize(new Dimension(200,250));
		panel3.add(new JLabel("Ocurrencia de SI"));
		panel3.add(pb1);
		panel3.add(new JLabel("Ocurrencia de NO"));
		panel3.add(pb2);
		add(panel3);	
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
}
class Llenado implements Runnable{
	ArrayList<String> cole;
	JTextArea areaTexto1,areaTexto2;
	JProgressBar pb1,pb2;
	public Llenado(ArrayList<String> al, JTextArea areaTexto1, JTextArea areaTexto2, JProgressBar pb1,JProgressBar pb2) {
		super();
		this.cole = al;
		this.areaTexto1 = areaTexto1;
		this.areaTexto2 = areaTexto2;
		this.pb1 = pb1;
		this.pb2=pb2;
	}
	public void run() {
		long cSi=0, cNo=0;
		for(String pocicion: cole) {
			if(pocicion.equals("Si")) {
				areaTexto1.append((cSi++)+" "+pocicion+"\n");
			}else {
				areaTexto2.append((cNo++)+" "+pocicion+"\n");
			}		
		}
		System.out.println(cNo);
		System.out.println(cSi);
		ProgreBar pbb1=new ProgreBar(pb1,cSi);
		ProgreBar pbb2=new ProgreBar(this.pb2,cNo);
		Thread th=new Thread(pbb1);
		Thread thh=new Thread(pbb2);	
		th.start();
		thh.start();
	}
}
class ProgreBar implements Runnable{
	DecimalFormat decimal=new DecimalFormat("##.###");
	JProgressBar pb0;
	long t;
	public ProgreBar(JProgressBar pb0, long t) {
		super();
		this.pb0 = pb0;
		this.t = t;
	}
	public void run() {
		pb0.setMaximum(10000000);
		for(int i=1; i<=t;i+=1000) {
			pb0.setString("Calculando...");
		pb0.setValue(i);
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		   }
	    }
		String op ="("+String.valueOf(t)+"/"+"1000000)"+"*"+"100";
		System.out.println(op);
		double porcentaje=0.0;
		double a=t;
		double b=10000000;
		porcentaje=a/b;
		porcentaje=porcentaje*100;
		System.out.println("Porcentaje: "+porcentaje);
       	pb0.setString(porcentaje+"%");
	}
	
}
public class PruebaT3_A2 {

	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new Ventana();
					
				}
			});

	}

}
