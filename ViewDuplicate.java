package duplicate;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
public class ViewDuplicate extends JFrame{
	JScrollPane jsp;
	DefaultTableModel dtm;
	JTable table;
	JPanel p1;
public ViewDuplicate(String type){
	super("View "+type+" Duplicate Text");
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	jsp = new JScrollPane(table);
	dtm.addColumn("Duplicate Text");
	getContentPane().add(jsp,BorderLayout.CENTER);
	Font f1 = new Font("Courier New",Font.BOLD,13);
	table.setFont(f1);
}
public void clear(){
	for(int i=dtm.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
public void viewDup(ArrayList<String> dup){
	clear();
	for(int i=0;i<dup.size();i++){
		Object arr[] = {dup.get(i)};
		dtm.addRow(arr);
	}
}
}