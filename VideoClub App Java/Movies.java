import java.util.*;


public class Movies extends VideoClubItems{
	protected String director,writer,actors,length;
	public Movies(String Title , String Categories , String Director, String Writer, String Actors, String Company, String Date, String Length,int stock){
		super(Title,Categories,Company,Date,stock);
		director = Director;
		writer = Writer;
		actors = Actors;
		length = Length;
	}
	public Movies(){
		director = null;
		writer = null;
		actors = null;
		length = null;
	}
	public String getDirector(){
		return director;
	}
	public String getWriter(){
		return writer;
	}
	public String getActors(){
		return actors;
	}
	public String getLength(){
		return length;
	}
	public void setDirector(String d){
		director = d;
	}
	public void setWriter(String w){
		writer = w;
	}
	public void setActors(String a){
		actors = a;
	}
	public void setLength(String l){
		length = l;
	}
	public String toString(){
		return "\n Name: " + getTitle() + "\n Categories: " + getCategories() + "\n Director:" + getDirector() +"\n Writer:" + getWriter() + "\n Actors:" + getActors() + "\n Company:" + getCompany() + "\n Date:" + getDate() + "\n Length:"+ getLength() + String.format("\n Stock : %s",stock);
	}
	
}
