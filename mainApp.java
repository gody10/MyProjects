import java.util.*;

public class mainApp {
   		public static void main(String args[]) {
			boolean done = false;
			VideoClub myClub = new VideoClub();
			RentalItems myRentals = new RentalItems();
			Random rand = new Random();
			String answer,name,telephone,code,answer2;
			int day,month,year,dayreturn,monthreturn,yearreturn,p,finalcost,cost,d,m,y,rd,rm,ry,rd2,rm2,ry2;
			int i=1;
			int q=0;
			Scanner say = new Scanner(System.in);
			myClub.ReadFile();
			myRentals.ReadFile2();
     		// Fill your code here
   	 		while ( ! done)	{
   	 			System.out.println("\n1. See Movies");
   	 			System.out.println("2. See Games");
   	 			System.out.println("3. See Rental List and Return an Item");
   	 			System.out.println("4. Rent a Movie");
   	 			System.out.println("5. Rent a Game");
   	 			System.out.println("0. exit");
   	 			System.out.print("> ");
   	 			answer = say.nextLine();
   	 			if (answer.equals ("1")){
					// Fill your code here
					myClub.ShowAllMovies();
   	 			}
   	 			else if (answer.equals ("2")){
					// Fill your code here
					myClub.ShowAllGames();
   	 			}

   	 			else if (answer.equals ("3")){
					// Fill your code here
					myRentals.ShowList();
					if (!myRentals.isEmpty()){
						System.out.println("Do you want to return an item Y/N");
						answer = say.nextLine();
						if(answer.equals("Y")){
							System.out.println("Which Item you want to return");
							answer = say.nextLine();
							p = myRentals.lookUpPosition(answer);
							d = myRentals.get(p).getDay();
							m = myRentals.get(p).getMonth();
							y = myRentals.get(p).getYear();
							System.out.println("Please give the following information by using numbers only");
							try{
									Thread.sleep(4000);}
								catch(InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
							System.out.println("What is the Day?");
							rd2 = say.nextInt();
							System.out.println("What is the month?");
							rm2 = say.nextInt();
							System.out.println("What is the year?");
							ry2 = say.nextInt();
							say.nextLine();
							rd = myRentals.get(p).getRDay();
							rm = myRentals.get(p).getRMonth();
							ry = myRentals.get(p).getRYear();
							if(myRentals.get(p).getCost() == 1){
								finalcost = myRentals.get(p).CalculateCost(d,m,y,rd,rm,ry,1);
								if ( rd2 == rd && rm2 == rm && ry2 == ry)
									System.out.println("The final cost is : " + finalcost);
								else{
									finalcost += myRentals.get(p).CalculateCost(rd,rm,ry,rd2,rm2,ry2,2);
									System.out.println("The final cost is : " + finalcost);
								}
							}
							else{
								finalcost = myRentals.get(p).CalculateWeeklyCost(d,m,y,rd,rm,ry,2);
								if ( rd2 == rd && rm2 == rm && ry2 == ry)
									System.out.println("The final cost is : " + finalcost);
								else{
									finalcost += myRentals.get(p).CalculateWeeklyCost(rd,rm,ry,rd2,rm2,ry2,4);
									System.out.println("The final cost is : " + finalcost);
								}
							}
							myRentals.remove(p);
							p = myClub.lookUpPosition(answer);
							myClub.AddStock(p);
						}
					}
   	 			}
   	 			else if (answer.equals ("4")){
					// Fill your code here
					System.out.println("Do you want a DVD or Blue Ray?");
					answer = say.nextLine();
					if (answer.equals("DVD")){
						myClub.ShowAllDVD();
						q=0;
						do{
							if(q!=0){
								System.out.println("The Item you typed is not in this list or it has no stock, please select another Item");
							}
							System.out.println("\nChoose Movie please(Write the Full Name)");
							q++;	
							answer2 = say.nextLine();
							try{
								Thread.sleep(2000);}
							catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							}
						while(myClub.lookUpExistance(answer2)!=true  || myClub.get(myClub.lookUpPosition(answer2)).getStock()<= 0);
						myClub.lookUp(answer2);
						System.out.println("Do you want to Rent it?(Y/N)");
						answer = say.nextLine();
						if (answer.equals("Y")){
							System.out.println("Please give the Day");
							day = say.nextInt();
							System.out.println("Please give the Month");
							month = say.nextInt();
							System.out.println("Please give the Year");
							year = say.nextInt();
							say.nextLine();
							System.out.println("Please give your Telephone");
							telephone = say.nextLine();
							System.out.println("Please give your Name");
							name = say.nextLine();
							System.out.println("Please give the return Day");
							dayreturn = say.nextInt();
							System.out.println("Please give the return Month");
							monthreturn = say.nextInt();
							System.out.println("Please give the return Year");
							yearreturn = say.nextInt();
							i = rand.nextInt(1500);
							code = String.format("%s",i);
							p = myClub.lookUpPosition(answer2);
							System.out.println("For your information the weekly cost is 2 euro and the extracost is 4 euros");
							Rentals ri = new Rentals(answer2,day,month,year,dayreturn,monthreturn,yearreturn,code,2,4,name,telephone);
							i++;
							p = myClub.lookUpPosition(answer2);
							myClub.ReduceStock(p);
							myRentals.addItem(ri);
						}
					}
					
					else{
						myClub.ShowAllBlueRay();
						System.out.println("\nChoose Movie please");
						q=0;
						do{
							if(q!=0){
								System.out.println("The Item you typed is not in this list or it has no stock, please select another Item");
							}
							System.out.println("\nChoose Movie please(Write the Full Name)");
							q++;
							answer2 = say.nextLine();
							try{
								Thread.sleep(2000);}
							catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							}
						while(myClub.lookUpExistance(answer2)!=true  || myClub.get(myClub.lookUpPosition(answer2)).getStock()<= 0);
						myClub.lookUp(answer);
						System.out.println("Do you want to Rent it?(Y/N)");
						answer = say.nextLine();
						if (answer.equals("Y")){
							System.out.println("Please give the Day");
							day = say.nextInt();
							System.out.println("Please give the Month");
							month = say.nextInt();
							System.out.println("Please give the Year");
							year = say.nextInt();
							say.nextLine();
							System.out.println("Please give your Telephone");
							telephone = say.nextLine();
							System.out.println("Please give your Name");
							name = say.nextLine();
							System.out.println("Please give the return Day");
							dayreturn = say.nextInt();
							System.out.println("Please give the return Month");
							monthreturn = say.nextInt();
							System.out.println("Please give the return Year");
							yearreturn = say.nextInt();
							i = rand.nextInt(1500);
							code = String.format("%s",i);
							System.out.println("For your information the daily cost is 1 euro and the extracost is 2 euros");
							Rentals ri = new Rentals(answer2,day,month,year,dayreturn,monthreturn,yearreturn,code,1,2,name,telephone);
							i++;
							p = myClub.lookUpPosition(answer2);
							myClub.ReduceStock(p);
							myRentals.addItem(ri);
						}
					}
   	 			}
				
				
   	 			else if (answer.equals ("5")){
					// Fill your code here;
					System.out.println("Do you want a PlayStation a Xbox or a Nintendo game?");
					answer = say.nextLine();
					if(answer.equals("PlayStation")){
						myClub.ShowAllPlayStation();
						q=0;
						do{
							if(q!=0){
								System.out.println("The Item you typed is not in this list or it has no stock, please select another Item");
							}
							System.out.println("\nChoose Game please(Write the Full Name)");
							q++;	
							answer2 = say.nextLine();
							try{
								Thread.sleep(2000);}
							catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							}
						while(myClub.lookUpExistance(answer2)!=true  || myClub.get(myClub.lookUpPosition(answer2)).getStock()<= 0);
						myClub.lookUp(answer);
						System.out.println("Do you want to Rent it?(Y/N)");
						answer = say.nextLine();
						if (answer.equals("Y") && myClub.get(myClub.lookUpPosition(answer2)).getStock() > 0 ){
							System.out.println("Please give the Day");
							day = say.nextInt();
							System.out.println("Please give the Month");
							month = say.nextInt();
							System.out.println("Please give the Year");
							year = say.nextInt();
							say.nextLine();
							System.out.println("Please give your Telephone");
							telephone = say.nextLine();
							System.out.println("Please give your Name");
							name = say.nextLine();
							System.out.println("Please give the return Day");
							dayreturn = say.nextInt();
							System.out.println("Please give the return Month");
							monthreturn = say.nextInt();
							System.out.println("Please give the return Year");
							yearreturn = say.nextInt();
							i = rand.nextInt(1500);
							System.out.println("For your information the weekly cost is 2 euro and the extracost is 4 euros");
							code = String.format("%s",i);
							Rentals ri = new Rentals(answer2,day,month,year,dayreturn,monthreturn,yearreturn,code,2,4,name,telephone);
							i++;
							p = myClub.lookUpPosition(answer2);
							myClub.ReduceStock(p);
							myRentals.addItem(ri);
						}
					}	
				
					else if(answer.equals("Xbox")){
						myClub.ShowAllXbox();
						q=0;
							do{
							if(q!=0){
								System.out.println("The Item you typed is not in this list or it has no stock, please select another Item");
							}
							System.out.println("\nChoose Game please(Write the Full Name)");
							q++;
							answer2 = say.nextLine();
							try{
								Thread.sleep(2000);}
							catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							}
						while(myClub.lookUpExistance(answer2)!=true  || myClub.get(myClub.lookUpPosition(answer2)).getStock()<= 0);
							myClub.lookUp(answer);
							System.out.println("Do you want to Rent it?(Y/N)");
							answer = say.nextLine();
							if (answer.equals("Y") && myClub.get(myClub.lookUpPosition(answer2)).getStock() > 0){
								System.out.println("Please give the Day");
								day = say.nextInt();
								System.out.println("Please give the Month");
								month = say.nextInt();
								System.out.println("Please give the Year");
								year = say.nextInt();
								say.nextLine();
								System.out.println("Please give your Telephone");
								telephone = say.nextLine();
								System.out.println("Please give your Name");
								name = say.nextLine();
								System.out.println("Please give the return Day");
								dayreturn = say.nextInt();
								System.out.println("Please give the return Month");
								monthreturn = say.nextInt();
								System.out.println("Please give the return Year");
								yearreturn = say.nextInt();
								i = rand.nextInt(1500);
								System.out.println("For your information the weekly cost is 2 euro and the extracost is 4 euros");
								code = String.format("%s",i);
								Rentals ri = new Rentals(answer2,day,month,year,dayreturn,monthreturn,yearreturn,code,2,4,name,telephone);
								i++;
								p = myClub.lookUpPosition(answer2);
								myClub.ReduceStock(p);
								myRentals.addItem(ri);
							}
						}
				
					
					else{
						myClub.ShowAllNintendo();
						q=0;
						do{
							if(q!=0){
								System.out.println("The Item you typed is not in this list or it has no stock, please select another Item");
							}
							System.out.println("\nChoose Game please(Write the Full Name)");
							q++;
							answer2 = say.nextLine();
							try{
								Thread.sleep(2000);}
							catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							}
						while(myClub.lookUpExistance(answer2)!=true  || myClub.get(myClub.lookUpPosition(answer2)).getStock()<= 0);
						myClub.lookUp(answer);
						System.out.println("Do you want to Rent it?(Y/N)");
						answer = say.nextLine();
						if (answer.equals("Y") && myClub.get(myClub.lookUpPosition(answer2)).getStock() > 0 ){
							System.out.println("Please give the Day");
							day = say.nextInt();
							System.out.println("Please give the Month");
							month = say.nextInt();
							System.out.println("Please give the Year");
							year = say.nextInt();
							say.nextLine();
							System.out.println("Please give your Telephone");
							telephone = say.nextLine();
							System.out.println("Please give your Name");
							name = say.nextLine();
							System.out.println("Please give the return Day");
							dayreturn = say.nextInt();
							System.out.println("Please give the return Month");
							monthreturn = say.nextInt();
							System.out.println("Please give the return Year");
							yearreturn = say.nextInt();
							i = rand.nextInt(1500);
							code = String.format("%s",i);
							System.out.println("For your information the weekly cost is 2 euro and the extracost is 4 euros");
							Rentals ri = new Rentals(answer2,day,month,year,dayreturn,monthreturn,yearreturn,code,2,4,name,telephone);
							i++;
							p = myClub.lookUpPosition(answer2);
							myClub.ReduceStock(p);
							myRentals.addItem(ri);
						}
					}
				}
   	 			else if (answer.equals ("0")) done = true;
					
					
			}
	myClub.CreateFile();
	myRentals.CreateFile2();
	}
}