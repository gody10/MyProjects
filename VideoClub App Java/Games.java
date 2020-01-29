

public class Games extends VideoClubItems{
	public Games(String Title,String Categories, String Company , String Date,int stock){
		super(Title,Categories,Company,Date,stock);
	}

	public String toString(){
		return "\n Name:" + getTitle() + "\n Categories:" + getCategories() + "\n Company:" + getCompany() + "\n Date:" + getDate() + String.format("\n Stock : %s",stock) ;
	}
	public Games(){
		
	}
}
