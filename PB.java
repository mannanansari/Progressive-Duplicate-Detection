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
public class PB{
	static ArrayList<Sort> dataset = new ArrayList<Sort>();
	static RandomAccessFile random;
	static HashSet<String> duplicates = new HashSet<String>();
	static long order = 0;
	static int k = 0;
public static void setPartition(int val){
	k = val;
	System.out.println(k);
}
public static void pb(File file,String skey,int psize){
	try{
		dataset.clear();
		duplicates.clear();
		long tot = 0;
		long partition = file.length()/psize;
		print(partition+" size\n");
		random = new RandomAccessFile(file,"r");
		long block_size = 100; //block size
		long block_per_partition =  partition/ block_size;
		for(k=0;k<psize;k++){
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
			long from = 0;
			long to = block_size;
			final ArrayList blocks[] = new ArrayList[(int)block_per_partition];
			for(int j=0;j<block_per_partition;j++){
				if(to < dataset.size()){
					ArrayList<Sort> block = new ArrayList<Sort>(dataset.subList((int)from,(int)to));
					java.util.Collections.sort(block,new Sort());
					blocks[j] = block;
					from = to;
					to = to + block_size;
				}
			}
			dataset.clear();
			print("Processing block size "+blocks.length+"\n");
			new Thread(new Runnable(){
				public void run(){
					checkDup(blocks);
				}
			}).start();
		}
		random.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public static void checkDup(ArrayList data[]){
	for(int m=0;m<data.length;m++){
		if(data[m] != null){
			ArrayList<Sort> block = data[m];
			for(int i=0;i<block.size();i++){
				Sort sr = block.get(i);
				String s1 = sr.getTitle();
				if(!duplicates.contains(s1)){
					for(int j=i+1;j<block.size();j++){
						Sort temp = block.get(j);
						String s2 = temp.getTitle();
						int distance = StringUtils.getLevenshteinDistance(s1,s2);
						if(distance <= 1)
							duplicates.add(s2);
						if(distance > 1)
							j = block.size();
					}
				}
			}
		}
	}
	print("Duplicate detection done on blocks. Current duplicate size "+duplicates.size()+"\n");
}
public static void print(final String text){
	System.out.print(text);
}
}
