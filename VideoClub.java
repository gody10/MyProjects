import java.util.*;
import java.io.*;
public class VideoClub{
	private ArrayList <VideoClubItems> stock = new ArrayList <VideoClubItems>();
	Scanner in = new Scanner(System.in);
	
	public void ShowAllMovies(){
		if (!stock.isEmpty()){
			for (VideoClubItems v : stock){
				if(v instanceof Movies || v instanceof BlueRay || v instanceof DVD){
					System.out.println(v);
				}
			}
		}
		else{
			System.out.println("Sry there is no Stock");
		}
	}
	
	public void ShowAllGames(){
		if (!stock.isEmpty()){
			for (VideoClubItems v : stock){
				if(v instanceof Xbox || v instanceof PlayStation || v instanceof Nintendo || v instanceof Games){
					System.out.println(v);
				}
			}
		}
		else{
			System.out.println("Sry there is no Stock");
		}
	} 
	public void ShowAllDVD(){
			int i=0;
			for (VideoClubItems v : stock){
				if(v instanceof DVD){
					System.out.print(v);
					i=1;
				}
			}
			if (i==0){
				System.out.println("Sry no DVD's in stock");
			}
	}
	
	public void ShowAllBlueRay(){
			int i=0;
			for (VideoClubItems v : stock){
				if(v instanceof BlueRay){
					System.out.print(v);
					i=1;
				}
			}
			if (i==0){
				System.out.println("Sry no BlueRay's in stock");
			}
	}
	
	
	public void ShowAllPlayStation(){
		int i=0;
		if (!stock.isEmpty()){ 
			for (VideoClubItems v : stock){
				if (v instanceof PlayStation){
					System.out.println(v);
					i=1;
				}
			}
		}
		if (i==0){
				System.out.println("Sry no PlayStation games in stock");
			}
	}
	public void ShowAllXbox(){
		int i=0;
		if (!stock.isEmpty()){ 
			for (VideoClubItems v : stock){
				if (v instanceof Xbox){
					System.out.println(v);
					i=1;
				}
			}
		}
		if (i==0){
				System.out.println("Sry no Xbox games in stock");
			}
	}
	
	public void ShowAllNintendo(){
		int i=0;
		if (!stock.isEmpty()){ 
			for (VideoClubItems v : stock){
				if (v instanceof Nintendo){
					System.out.println(v);
					i=1;
				}
			}
		}
		if (i==0){
				System.out.println("Sry no Nintendo games in stock");
			}
	}
	
	
	
	public void ShowAllItems(){
		if (!stock.isEmpty()){ 
			for (VideoClubItems v : stock){
					System.out.println(v);
			}
		}
		else{
			System.out.println("Sry there is no Stock");
		}
	}
		
	public void AddVideoClubItem(VideoClubItems item){
		boolean found = false;
		for(VideoClubItems v:stock){
			if (item == v){
				found = true;
			}
		}
		if (found == false){
			stock.add(item);
		}
		else{
			System.out.println("Item already in List");
		}
	}
	
	public void lookUp (String theName) {
		for (VideoClubItems v:stock){
			if (v.getTitle().contains(theName)){
				System.out.println(v);
			}
		}
	}
	public boolean lookUpExistance(String theName){
		boolean p = false;
		for (VideoClubItems v:stock){
			if (v.getTitle().contains(theName)){
				p = true;
			}
		}
		return p;
	}

	public VideoClubItems get(int p){
		return stock.get(p);
	}
	public void remove (String theName) {
		for (int i=stock.size()-1; i>=0; i--){
			if (stock.get(i).getTitle().contains(theName)){
				System.out.print(stock.get(i));
				stock.remove(i);
				
			}
		}
	}
	public int lookUpPosition (String theName){
		int found = 99;
		for (int i=stock.size()-1; i>=0; i--){
			if (stock.get(i).getTitle().contains(theName)){
				found = i;
			}
		}
		return found;
	}
	
	public void AddStock(int index){
		stock.get(index).AddStock();
	}
	public void ReduceStock(int index){
		stock.get(index).ReduceStock();
	}
	public void ReadFile(){
		
		BufferedReader reader = null;
		VideoClubItems product = null;
        String line;
		System.out.println("\n >>>>>>> Adding Objects (Items) from File to List ...");
        try {
			reader = new BufferedReader(new FileReader(new File("products.txt")));
            line = reader.readLine();
            while (line != null) {
                if (line.trim().equals("Product")) {
                    line = reader.readLine();
                    if (line.trim().equals("{")) {
                        line = reader.readLine();
                        if (line.trim().startsWith("Item ")) {
                            if (line.trim().substring(5).trim().equals("BlueRay")) {
                                product = new BlueRay();
                                line = reader.readLine();
                                if (line.trim().startsWith("Title "))
                                    product.setTitle(line.trim().substring(6).trim());
                                line = reader.readLine();
                                if (line.trim().startsWith("Categories"))
                                    product.setCategories(line.trim().substring(11).trim());
                                line = reader.readLine();
                                if (line.trim().startsWith("Director "))
                                    ((BlueRay) product).setDirector(line.trim().substring(9).trim());
                                line = reader.readLine();
                                if (line.trim().startsWith("Writer "))
                                    ((BlueRay) product).setWriter(line.trim().substring(7).trim());
                                line = reader.readLine();
                                if (line.trim().startsWith("Actors "))
                                    ((BlueRay) product).setActors(line.trim().substring(7).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Company "))
                                    product.setCompany(line.trim().substring(8).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Date "))
                                    product.setDate(line.trim().substring(5).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Length "))
                                    ((BlueRay)product).setLength(line.trim().substring(7).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Stock "))
                                    product.setStock((Integer.parseInt(line.trim().substring(5).trim())));
                                line = reader.readLine();
                                if (line.trim().equals("}"))
                                    stock.add(product);
							} // BlueRay
							else if (line.trim().substring(5).trim().equals("DVD")) {
								product = new DVD();
                                line = reader.readLine();
                                if (line.trim().startsWith("Title "))
                                    product.setTitle(line.trim().substring(6).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Categories "))
                                    product.setCategories(line.trim().substring(11).trim());
                                line = reader.readLine();
                                if (line.trim().startsWith("Director "))
                                    ((DVD) product).setDirector(line.trim().substring(9).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Writer "))
                                    ((DVD) product).setWriter(line.trim().substring(7).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Actors "))
                                    ((DVD) product).setActors(line.trim().substring(7).trim());
                                line = reader.readLine();
                                if (line.trim().startsWith("Company "))
                                    product.setCompany(line.trim().substring(8).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Date "))
                                    product.setDate(line.trim().substring(5).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Length "))
                                    ((DVD) product).setLength(line.trim().substring(7).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("NewMovie "))
                                    ((DVD) product).setNewMovie(line.trim().substring(9).trim());
                                line = reader.readLine();
								if (line.trim().startsWith("Stock "))
                                    product.setStock(Integer.parseInt(line.trim().substring(5).trim()));
                                line = reader.readLine();
                                if (line.trim().equals("}"))
                                    stock.add(product);
                            }//DVD
							else if (line.trim().substring(5).trim().equals("PlayStation")){
								product = new PlayStation();
								line=reader.readLine();
								if (line.trim().startsWith("Title "))
									product.setTitle(line.trim().substring(6).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Categories "))
									product.setCategories(line.trim().substring(11).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Company "))
									product.setCompany(line.trim().substring(8).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Date "))
									product.setDate(line.trim().substring(5).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Stock "))
									product.setStock(Integer.parseInt(line.trim().substring(5).trim()));
								line=reader.readLine();
								if (line.trim().equals("}"))
								stock.add(product);
					} //PlayStation
							else if (line.trim().substring(5).trim().equals("Xbox")){
								product = new Xbox();
								line=reader.readLine();
								if (line.trim().startsWith("Title "))
									product.setTitle(line.trim().substring(6).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Categories "))
									product.setCategories(line.trim().substring(11).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Company "))
									product.setCompany(line.trim().substring(8).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Date "))
									product.setDate(line.trim().substring(5).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Stock "))
									product.setStock(Integer.parseInt(line.trim().substring(5).trim()));
								line=reader.readLine();
								if (line.trim().equals("}"))
								stock.add(product);	
				}	//Xbox
							else if (line.trim().substring(5).trim().equals("Nintendo")){
								product = new Nintendo();
								line = reader.readLine();
								if (line.trim().startsWith("Title "))
									product.setTitle(line.trim().substring(6).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Categories "))
									product.setCategories(line.trim().substring(11).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Company "))
									product.setCompany(line.trim().substring(8).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Date "))
									product.setDate(line.trim().substring(5).trim());
								line=reader.readLine();
								if (line.trim().startsWith("Stock "))
									product.setStock(Integer.parseInt(line.trim().substring(5).trim()));
								line=reader.readLine();
								if (line.trim().equals("}"))
								stock.add(product);
							}
							//Nintendo
									
								
								
							
                        } //Item
                    }
                } //Product
				line = reader.readLine();
				
			}//while
			reader.close();
			
		} //try

		catch (IOException e) {
            System.out.println	("Error reading line ...");
		}
	}
	
	public void CreateFile(){
		System.out.println(" >>>>>>> Write data from ARRAYLIST to FILE...");
		FileWriter writer = null;
		try	{
			writer = new FileWriter(new File("products.txt"));
			for(VideoClubItems product: stock){
				if (product instanceof BlueRay){
					writer.write("Product"+"\n"+"{" + "\n" + "Item BlueRay"+ "\n" 
					+ "Title "+ product.getTitle() + "\n" + "Categories "+ product.getCategories()
					+ "\n"+ "Director "+ ((BlueRay) product).getDirector()+ "\n" + "Writer " + ((BlueRay) product).getWriter()
					+ "\n" + "Actors " + ((BlueRay) product).getActors() + "\n"
					+ "Company " + ((BlueRay) product).getCompany() + "\n" + "Date "+ ((BlueRay) product).getDate()
					+ "\n" + "Length " + ((BlueRay) product).getLength() + "\n" + "Stock " + product.getStock() + "\n" + "}	\n");

					
				}
				else if(product instanceof DVD){
					writer.write("Product"+"\n"+"{" + "\n" + "Item DVD"+ "\n" 
					+ "Title "+ product.getTitle() + "\n" + "Categories "+ product.getCategories()
					+ "\n"+ "Director "+ ((DVD) product).getDirector()+ "\n" + "Writer " + ((DVD) product).getWriter()
					+ "\n" + "Actors " + ((DVD) product).getActors() + "\n"
					+ "Company " + ((DVD) product).getCompany() + "\n" + "Date "+ ((DVD) product).getDate()
					+ "\n" + "Length " + ((DVD) product).getLength() + "\n" +"NewMovie " + ((DVD) product).getNewMovie() + "\n" + "Stock " + product.getStock() + "\n" + "}\n");
					
				}
				
				else if (product instanceof PlayStation){
					writer.write("Product"+"\n"+"{" + "\n" + "Item PlayStation"+ "\n"
					+ "Title "+ product.getTitle() + "\n" + "Categories "+ product.getCategories() + "\n"
					+ "Company " + product.getCompany() + "\n" + "Date "+ product.getDate() + "\n" 
					+ "Stock " + product.getStock() + "\n" + "}	\n");
				}
				else if (product instanceof Xbox){
					writer.write("Product"+"\n"+"{" + "\n" + "Item Xbox"+ "\n"
					+ "Title "+ product.getTitle() + "\n" + "Categories "+ product.getCategories() + "\n"
					+ "Company " + product.getCompany() + "\n" + "Date "+ product.getDate() + "\n" 
					+ "Stock " + product.getStock() + "\n" + "}	\n");
				}
				else if (product instanceof Nintendo){
					writer.write("Product"+"\n"+"{" + "\n" + "Item Nintendo"+ "\n"
					+ "Title "+ product.getTitle() + "\n" + "Categories "+ product.getCategories() + "\n"
					+ "Company " + product.getCompany() + "\n" + "Date "+ product.getDate() + "\n" 
					+ "Stock " + product.getStock() + "\n" + "}	\n");
				}
			}
			writer.close();
			
	}
		catch (IOException e) {
			System.err.println("Error writing file.");
			}
}
	
}