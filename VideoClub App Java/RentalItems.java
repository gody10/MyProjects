import java.util.*;
import java.io.*;
public class RentalItems{
	private ArrayList <Rentals> list = new ArrayList <Rentals>();
	Scanner in = new Scanner(System.in);
	
	public void ShowList(){
		if (!list.isEmpty()){
			for (Rentals v : list){
				System.out.println(v);
			}
		}
		else{
			System.out.println("There is nothing in the Rental List");
		}
	}
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public Rentals get(int p){
		return list.get(p);
	}
	
	public void addItem(Rentals item){
		list.add(item);
	}
	public void remove(int p){
		list.remove(p);
		
	}
	public void remove2 (String theName) {
		for (int i=list.size()-1; i>=0; i--){
			if (list.get(i).getTitle().contains(theName)){
				list.remove(i);
			}
		}
	}
	
	public void lookUp (String theName) {
		int i=0;
		for (Rentals v:list){
			if (v.getTitle().contains(theName)){
				System.out.println (v);
				i=1;
			}
		}
		if (i==0){
			System.out.println("Didn't find what you are looking for.");
		}
	}
	
	public int lookUpPosition (String theName){
		int found = 99;
		for (int i=list.size()-1; i>=0; i--){
			if (list.get(i).getTitle().contains(theName)){
				found = i;
			}
		}
		return found;
	}
	public void ReadFile2(){
		
		BufferedReader reader = null;
		Rentals product = null;
        String line;
		try {
			reader = new BufferedReader(new FileReader(new File("rentals.txt")));
            line = reader.readLine();
            while (line != null) {
                if (line.trim().equals("Product")) {
                    line = reader.readLine();
                    if (line.trim().equals("{")) {
                        line = reader.readLine();
						if (line.trim().startsWith("Item ")) {
							if (line.trim().substring(5).trim().equals("Rentals")){
								product = new Rentals();
								line = reader.readLine();
								if (line.trim().startsWith("Title "))
										product.setTitle(line.trim().substring(6).trim());
										line = reader.readLine();
								if (line.trim().startsWith("Code "))
									product.setCode(line.trim().substring(5).trim());
									line = reader.readLine();
								if (line.trim().startsWith("Day "))
											product.setDay(Integer.parseInt(line.trim().substring(4).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("Month "))
											product.setMonth(Integer.parseInt(line.trim().substring(6).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("Year "))
											product.setYear(Integer.parseInt(line.trim().substring(5).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("Cost "))
											product.setCost(Integer.parseInt(line.trim().substring(5).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("ExtraCost "))
											product.setExtraCost(Integer.parseInt(line.trim().substring(10).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("Name "))
											product.setName(line.trim().substring(5).trim());
											line = reader.readLine();
								if (line.trim().startsWith("Phone "))
											product.setTelephone(line.trim().substring(6).trim());
											line = reader.readLine();
								if (line.trim().startsWith("Return Day "))
											product.setRDay(Integer.parseInt(line.trim().substring(10).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("Return Month "))
											product.setRMonth(Integer.parseInt(line.trim().substring(12).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("Return Year "))
											product.setRYear(Integer.parseInt(line.trim().substring(11).trim()));
											line = reader.readLine();
								if (line.trim().startsWith("}"))
											list.add(product);
						  //Item
							}
						}
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
	
	public void CreateFile2(){
		FileWriter writer = null;
		try	{
			writer = new FileWriter(new File("rentals.txt"));
			for (Rentals product : list){
				writer.write("Product"+"\n"+"{" + "\n" + "Item Rentals"+ "\n"  
				+ "Title "+ product.getTitle() + "\n" + "Code " + product.getCode() + "\n"
				+ "Day " + product.getDay() + "\n" + "Month " + product.getMonth() + "\n"
				+ "Year " + product.getYear() + "\n" + "Cost " + product.getCost() + "\n"
				+ "ExtraCost " + product.getExtraCost() + "\n" + "Name " + product.getName() + "\n"
				+ "Phone " + product.getTelephone() + "\n" + "Return Day " + product.getRDay() + "\n"
				+ "Return Month " + product.getRMonth() + "\n" + "Return Year " + product.getRYear() + "\n"
				+ "}" +  "\n");
			}
			writer.close();
		}
		catch (IOException e) {
			System.err.println("Error writing file.");
			}
	}
	
}
