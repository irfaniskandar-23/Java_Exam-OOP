import java.io.*;
import java.util.*;

interface DiscConsiderable
{
	double DISC=10;
	public double calcDisc();
}
abstract class Menu
{
	protected String name;
	protected double price;

	Menu(String name, double price)
	{
		this.name=name;
		this.price=price;
	}
	public String getName()
	{
		return name;
	}
	public  abstract double calcTotal();
	public abstract void displayDetails();
}
class Staff
{
	private String staffID;
	private String name;
	private int numPromo;
	private int numPizza;
	private ArrayList<Menu> orderList;
	private ArrayList<Integer> qtyList;

	Staff(String staffID,  String name)
	{
		this.staffID=staffID;
		this.name=name;
		numPromo=0;
		numPizza=0;
		orderList=new ArrayList<Menu>();
		qtyList=new ArrayList<Integer>();
	}
	public String getStaffid()
	{
		return staffID;
	}
	public void addOrder(Menu m , Integer b)
	{
		if(m instanceof Pizza)
		{
			numPizza++;
		}
		else if(m instanceof Promo)
		{
			numPromo++;
		}
		orderList.add(m);
		qtyList.add(b);
	}

	public void dispalyInfoStaff()
	{
		System.out.println("Staff ID   : "+staffID);
		System.out.println("Staff Name : "+name);
		System.out.println();
	}
	public void displayInfoOrder()
	{
		System.out.println("Number of All Orders     : "+orderList.size());
		System.out.println("Number of Promo Orders   : "+numPromo);
		System.out.println("Numbers of Pizza Orders  : "+numPizza);
		System.out.println();
		double total=0.0;
	 	System.out.printf(" %-5s%-32s%-22s%-22s%-22s%-22s%n","No","Description","Unit Price (RM)","Discount (RM)","Quantity","Total (RM)");
	 		for(int i=0;i<orderList.size();i++)
	 		{	
				System.out.printf("%-5s",(i+1)+".");
				((Menu)orderList.get(i)).displayDetails();
				System.out.printf("%15s\t\t\t  %.2f",(qtyList).get(i),(((qtyList).get(i))*(((Menu)orderList.get(i)).calcTotal())));
				total+=(((qtyList).get(i))*(((Menu)orderList.get(i)).calcTotal()));
				System.out.println();
	 		}
	 	System.out.println();
	 	System.out.println();
	 	System.out.printf("Total Sales : RM %.2f",total);
	}
}

class Pizza extends Menu
{
	private String size;

	Pizza(String name, double price, String size)
	{
		super(name,price);
		this.size=size;
	}
	public double calcTotal()
	{
		return price;
	}
	public void displayDetails()
	{
		System.out.printf("%-32s\t%.2f\t\t\t    ",name,calcTotal()," ");
	}
}

class Promo extends Menu implements DiscConsiderable
{
	Promo(String name, double price)
	{
		super(name,price);
	}
	public double calcDisc()
	{
		Double priceDisc=price*(DISC/100);
		return priceDisc;
	}
	public double calcTotal()
	{
		return 	(price-calcDisc());
	}
	public void displayDetails()
	{
		System.out.printf("%-32s\t%.2f\t\t\t%.2f",name,price,calcDisc());
	}
}

class OrderTest
{
	public static void main(String[] args)
	{
		ArrayList<Staff>staffList=new ArrayList<Staff>();
		ArrayList<Menu>menuList=new ArrayList<Menu>();
		Scanner input=new Scanner(System.in);
		int tasks;

		do{
			System.out.println("\n========== Menu ==========");
			System.out.println("[1] Add Staff");
			System.out.println("[2] Add Menu");
			System.out.println("[3] Add Order");
			System.out.println("[4] Display Order" );
			System.out.println("[5] Exit");
			System.out.println("=========================");

			System.out.println();
			System.out.print("Select Task: ");	
			tasks=input.nextInt();


			switch(tasks)
			{
				case 1:
				       System.out.println();
					   System.out.println("<<< Add Staff >>>");
					   System.out.print("Enter Staff Id   : ");
					   String id=input.next();
					   input.nextLine();
					   System.out.print("Enter Staff Name : ");
					   String nama=input.nextLine();
					   Staff pekerja=new Staff(id,nama);
					   staffList.add(pekerja);
					   break;

				case 2:
				       System.out.println();
					   System.out.println("\n<<< Add Menu >>>");
					   System.out.print("Type (Promo/Pizza) : ");
					   String type=input.next();
					   input.nextLine();

					   if(type.equals("Promo"))
					   {
						   System.out.print("Name : ");
						   String jenis=input.nextLine();
						   System.out.print("Price : RM ");
						   double harga=input.nextDouble();
						   Menu menu=new Promo(jenis,harga);
						   menuList.add(menu);
					   }

					   else if(type.equals("Pizza"))
					   {
						   System.out.print("Name : ");
						   String jenis=input.nextLine();
						   System.out.print("Price  : RM ");
						   double harga=input.nextDouble();
						   System.out.print("Size (S/M/L): ");
						   String size=input.next();
						   String append=jenis+"  ("+size+")";
						   Menu menu=new Pizza(append,harga,size);
					       menuList.add(menu);
					   }
					   break;

				case 3:
			           System.out.println();
			           
				       if((staffList.isEmpty())||(menuList.isEmpty()))
				       {
				       	System.out.println("Sorry!! No staff or menu, please add staff or menu first...");
				       }
				       else
				       {
						   System.out.println("<<< Add Order >>>");
						   System.out.println("Staff List:");
						   	for(int i=0;i<staffList.size();i++)
						   	{
						   		System.out.println((i+1)+") "+(((Staff)staffList.get(i)).getStaffid()));
						   	}

						   System.out.print("\nSelect Staff: ");
						   int nostaff=input.nextInt();
						   System.out.println();
						   char choice;

						   	do{
						     	System.out.println("--- Enter Order ---");
						   		for(int i=0;i<menuList.size();i++)
						   		{	
						   			 System.out.println((i+1)+") "+(((Menu)menuList.get(i)).getName()));
						   		}
						   		System.out.println("\n");
						   		System.out.print("Select Menu: ");
						        int menu=input.nextInt();
						        System.out.print("Quantity: ");
						        int quantity=input.nextInt();
						        ((Staff)staffList.get(nostaff-1)).addOrder((Menu)menuList.get(menu-1),quantity);
						        System.out.println();
						        System.out.print("Press 'Y' to continue >> ");
						        choice=input.next().charAt(0);

					         }while((choice=='Y'));
					   }
					 break;

				case 4:
				        System.out.println();
						if(staffList.isEmpty())
						{
							System.out.println("Sorry!! No staff, please add staff first...");
						}
						else
						{
							System.out.println("<<< Staff(s) and Order(s) Info >>>");
						    System.out.println("\nNumber of Staffs: "+staffList.size());
								for(int i=0;i<staffList.size();i++)
								{
									System.out.println("\nStaff #"+(i+1));
									((Staff)staffList.get(i)).dispalyInfoStaff();
							    	((Staff)staffList.get(i)).displayInfoOrder();
							    	System.out.println();
								}
						}
						break;

				case 5:
				        System.out.println("Thank You! :)");
				        System.exit(0);
						break;

				default:break;
			}
		}while(tasks!=6);
	}
}