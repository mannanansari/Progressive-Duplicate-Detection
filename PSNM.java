package duplicate;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.io.File;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.apache.commons.lang3.StringUtils;
import javax.swing.SwingUtilities;
import java.util.HashSet;
public class PSNM{
	static ArrayList<Sort> dataset = new ArrayList<Sort>();
	static RandomAccessFile random;
	static HashSet<String> duplicates = new HashSet<String>();
	static long order = 0;
	static int k = 0;
public static void setWindow(int val){
	k = val;
	System.out.println(k);
}
public static void psnm(File file,String skey,int window){
	try{
		dataset.clear();
		duplicates.clear();
		long tot = 0;
		final long partition = file.length()/window;
		print(partition+" size\n");
		random = new RandomAccessFile(file,"r");
		for(k=0;k<window;k++){
			byte b[]=new byte[(int)partition];
			random.read(b);
			random.seek(random.getFilePointer());
			String line = new String(b);
			Document doc = Jsoup.parse(line);
			Elements key = doc.select(skey);
			for(Element value : key){
				String text = value.text().trim();
				text = text.toLowerCase().replaceAll("[^a-zA-Z\\s+]", "");
				Sort sr = new Sort();
				sr.setTitle(text.trim());
				dataset.add(sr);
				order = order + 1;
			}
			java.util.Collections.sort(dataset,new Sort());
			print("Processing partition size "+dataset.size()+"\n");
			final ArrayList<Sort> data = new ArrayList<Sort>(dataset);
			dataset.clear();
			new Thread(new Runnable(){
				public void run(){
					checkDup(data);
				}
			}).start();
		}
		random.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public static void checkDup(ArrayList<Sort> data){
	for(int i=0;i<data.size();i++){
		Sort sr = data.get(i);
		String s1 = sr.getTitle();
		if(!duplicates.contains(s1)){
			for(int j=i+1;j<data.size();j++){
				Sort temp = data.get(j);
				String s2 = temp.getTitle();
				int distance = StringUtils.getLevenshteinDistance(s1,s2);
				if(distance <= 1)
					duplicates.add(s2);
				if(distance > 1)
					 j = data.size();
			}
		}
	}
	print("Duplicate detection done on partition. Current duplicate size "+duplicates.size()+"\n");
}
public static void print(final String text){
	System.out.print(text);
}
}
