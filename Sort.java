package duplicate;
import java.util.Comparator;
public class Sort implements Comparator<Sort> {
	private String title;
    private String author;
public void setAuthor(String author){
	this.author = author;
}
public String getAuthor(){
	return author;
}
public void setTitle(String title){
	this.title = title;
}
public String getTitle(){
	return title;
}
public int compare(Sort s1,Sort s2) {
	return s1.getTitle().compareTo(s2.getTitle());
}
}