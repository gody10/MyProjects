

public class VideoClubItems{
	protected String title,categories,company,date;
	protected int stock;
	public VideoClubItems(String Title,String Categories,String Company,String Date,int Stock){
		title = Title;
		categories = Categories;
		company = Company;
		date = Date;
		stock = Stock;
	}
	public VideoClubItems(){
		
	}
	public String getTitle(){
		return title;
	}
	public String getCategories(){
		return categories;
	}
	public String getCompany(){
		return company;
	}
	public String getDate(){
		return date;
	}
	public void setTitle(String t){
		title = t;
	}
	public void setCategories(String c){
		categories = c;
	}
	public void setCompany(String c){
		company = c;
	}
	public void setDate(String d){
		date = d;
	}
	public void setStock(int d){
		stock = d;
	}
	public int getStock(){
		return stock;
		
	}
	public void AddStock(){
		stock++;
	}
	public void ReduceStock(){
		stock--;
	}
	public String toString(){
		return "Name: " + getTitle() + "\n Categories: " + getCategories() + "\n Company:" + getCompany() + "\n Date" + getDate() + String.format("\n Stock : %s",stock);
	}
}
