package duplicate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import java.io.File;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.io.PrintStream;
import javax.swing.SwingUtilities;
import org.jfree.ui.RefineryUtilities;
public class MainScreen extends JFrame implements Runnable
{
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1;
	JButton b1,b2,b3,b4,b5,b6;
	Font f1,f2;
	JPanel p1,p2;
	Thread thread;
	JComboBox c1,c2;
	JScrollPane jsp;
	static JTextArea area;
	long start,end;
	JFileChooser chooser;
	File file;
	boolean flag;
	public MainScreen()
	{
		setTitle("Duplicate Detection");
		getContentPane().setLayout(new BorderLayout());
		f1 = new Font("Times New Roman",Font.BOLD,22);
		p1 = new JPanel();
		l1 = new JLabel("<HTML><BODY><CENTER>PROGRESSIVE DUPLICATE DETECTION</CENTER></BODY></HTML>");
		l1.setFont(this.f1);
		l1.setForeground(new Color(125,54,2));
		p1.setBackground(new Color(140,150,180));
		p1.add(l1);
	
		chooser = new JFileChooser(new File("."));
		f2 = new Font("Courier New",Font.PLAIN,16);
    
		p2 = new JPanel();
		p2.setLayout(null);

		l3 = new JLabel("Upload Dataset");
		l3.setFont(f2);
		p2.add(l3);
		l3.setBounds(50,10,180,30);

		tf1 = new JTextField(10);
		tf1.setFont(f2);
		tf1.setBounds(230,10,150,30);
		p2.add(tf1);
	
		b1 = new JButton("Upload");
		b1.setFont(f2);
		b1.setBounds(400,10,100,30);
		p2.add(b1);
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				int option = chooser.showOpenDialog(MainScreen.this);
				if(option == chooser.APPROVE_OPTION)
				{
					file = chooser.getSelectedFile();
					tf1.setText(file.getName());
				}
			}
		});

		b1 = new JButton("Terminate");
		b1.setFont(f2);
		b1.setBounds(520,10,150,30);
		p2.add(b1);
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(flag)
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							PSNM.setWindow(1000);
							end = System.currentTimeMillis();
							Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
							setCursor(normalCursor);
							ArrayList<String> dup = new ArrayList<String>(PSNM.duplicates);
							ViewDuplicate vd = new ViewDuplicate("PSNM");
							vd.setVisible(true);
							vd.setSize(600,400);
							vd.viewDup(dup);
						}
					}).start();
				}
				if(!flag)
				{
					new Thread(new Runnable()
					{
						public void run()
						{
							PB.setPartition(1000);
							end = System.currentTimeMillis();
							Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
							setCursor(normalCursor);
							ArrayList<String> dup = new ArrayList<String>(PB.duplicates);
							ViewDuplicate vd = new ViewDuplicate("PB Blocking");
							vd.setVisible(true);
							vd.setSize(600,400);
							vd.viewDup(dup);
						}
					}).start();
				}
			}
		});

		l4 = new JLabel("Sorting Key");
		l4.setFont(f2);
		p2.add(l4);
		l4.setBounds(50,60,180,30);

		c1 = new JComboBox();
		c1.setFont(f2);
		c1.addItem("title");
		c1.addItem("author");
		c1.setBounds(230,60,150,30);
		p2.add(c1);

		l5 = new JLabel("Window/Block Size");
		l5.setFont(f2);
		p2.add(l5);
		l5.setBounds(50,110,180,30);

		c2 = new JComboBox();
		c2.setFont(f2);
		c2.addItem("30");
		c2.addItem("40");
		c2.addItem("50");
		c2.setBounds(230,110,150,30);
		p2.add(c2);

	
		b2 = new JButton("PSNM Algorithm");
		b2.setFont(f2);
		b2.setBounds(10,160,200,30);
		p2.add(b2);
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				flag = true;
				new Thread(new Runnable()
				{
					public void run()
					{
						Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
						setCursor(hourglassCursor);
						start = System.currentTimeMillis();
						PSNM.psnm(file,c1.getSelectedItem().toString().trim(),Integer.parseInt(c2.getSelectedItem().toString().trim()));
						end = System.currentTimeMillis();
						Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
						setCursor(normalCursor);
						ArrayList<String> dup = new ArrayList<String>(PSNM.duplicates);
						ViewDuplicate vd = new ViewDuplicate("PSNM");
						vd.setVisible(true);
						vd.setSize(600,400);
						vd.viewDup(dup);
					}
				}).start();
			}
		});

		b3 = new JButton("PB Algorithm");
		b3.setFont(f2);
		b3.setBounds(220,160,200,30);
		p2.add(b3);
		b3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				flag = false;
				new Thread(new Runnable()
				{
					public void run()
					{
						Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
						setCursor(hourglassCursor);
						start = System.currentTimeMillis();
						PB.pb(file,c1.getSelectedItem().toString().trim(),Integer.parseInt(c2.getSelectedItem().toString().trim()));
						end = System.currentTimeMillis();
						Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
						setCursor(normalCursor);
						ArrayList<String> dup = new ArrayList<String>(PSNM.duplicates);
						ViewDuplicate vd = new ViewDuplicate("PB Blocking");
						vd.setVisible(true);
						vd.setSize(600,400);
						vd.viewDup(dup);
					}
				}).start();
			}
		});

		b4 = new JButton("Runtime Chart");
		b4.setFont(f2);
		b4.setBounds(430,160,200,30);
		p2.add(b4);
		b4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(flag)
				{
					Chart chart1 = new Chart("PSNM Runtime & Duplicate Detection Chart",(end-start),PSNM.duplicates.size());
					chart1.pack();
					RefineryUtilities.centerFrameOnScreen(chart1);
					chart1.setVisible(true);
				}
				if(!flag)
				{
					Chart chart1 = new Chart("PB Blocking Runtime & Duplicate Detection Chart",(end-start),PB.duplicates.size());
					chart1.pack();
					RefineryUtilities.centerFrameOnScreen(chart1);
					chart1.setVisible(true);
				}
			}
		});

		b5 = new JButton("Exit");
		b5.setFont(f2);
		b5.setBounds(640,160,200,30);
		p2.add(b5);
		b5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				System.exit(0);
			}
		});

		area = new JTextArea();
		area.setFont(f2);
		area.setEditable(false);
		jsp = new JScrollPane(area);
		jsp.setBounds(10,210,840,450);
		p2.add(jsp);
		PrintStream printStream = new PrintStream(new CustomOutputStream(area));
		System.setOut(printStream);
		System.setErr(printStream);

		getContentPane().add(p1, BorderLayout.NORTH);
		getContentPane().add(p2, BorderLayout.CENTER);
		thread = new Thread(this);
		thread.start();
	}
	public void run()
	{
		try{
		while(true){
			l1.setForeground(Color.white);
			thread.sleep(500);
			l1.setForeground(new Color(125,54,2));
			thread.sleep(500);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String a[])throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MainScreen screen = new MainScreen();
		screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screen.setVisible(true);
	 
	}
}