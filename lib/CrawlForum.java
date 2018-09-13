package duplicate;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
public class CrawlForum{
public static void parse(String data){
	try{
		Document doc = Jsoup.parse(data);
		Elements table = doc.select("title");
		for(Element row : table){
			System.out.println(row.text());
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
/*
String arr[] = line.split("\n");
			for(int j=0;j<arr.length;j++){
				String value = arr[j].trim();
				if(value.length() > 0){
					if(value.indexOf("<author>") != -1){
						int start = value.indexOf(">");
						int end = value.lastIndexOf("<");
						if(start >= 0 && end > 0)
							author.append(value.substring(start+1,end)+" ");
					}
					if(value.indexOf("<title>") != -1){
						int start = value.indexOf(">");
						int end = value.lastIndexOf("<");
						if(start >= 0 && end > 0){
							title.append(value.substring(start+1,end));
							Sort sr = new Sort();
							sr.setAuthor(author.toString().trim());
							sr.setTitle(title.toString());
							dataset.add(sr);
							author.delete(0,author.length());
							title.delete(0,title.length());
						}
					}
				}
			}
			java.util.Collections.sort(dataset,new Sort());
			for(int j=0;j<5;j++){
				Sort sr = dataset.get(j);
				//System.out.println(sr.getTitle());
			}
			tot = tot + dataset.size();
			dataset.clear();
		}
		random.close();
		//System.out.println(tot);
	}catch(Exception e){
		e.printStackTrace();
	}
	*/
} 