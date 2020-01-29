import java.util.*;

public class DVD extends Movies{
	protected String newmovie;
	public DVD(String Title , String Categories, String Director, String Writer, String Actors, String Company, String Date, String Length, String NewMovie,int stock){
		super(Title,Categories,Director,Writer,Actors,Company,Date,Length,stock);
		newmovie = NewMovie;
	}
	public DVD(){
	}
	
	public String getNewMovie(){
		return newmovie;
	}
	
	public void setNewMovie(String nm){
		newmovie = nm;
	}
	public String toString(){
		return "\n Name: " + getTitle() + "\n Categories: " + getCategories() + "\n Director:" + getDirector() +"\n Writer:" + getWriter() + "\n Actors:" + getActors() + "\n Company:" + getCompany() + "\n Date:" + getDate() + "\n Length:"+ getLength() + "\n NewMovie:" + getNewMovie() + String.format("\n Stock : %s",stock);
	}


	
}
