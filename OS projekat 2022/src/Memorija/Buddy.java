package Memorija;
import java.util.Scanner;
import java.util.ArrayList;
public class Buddy {
public static Scanner in = new Scanner(System.in);
public static ArrayList<Process> proList = new ArrayList<Process>();
public static int mSize;
//_ _ _ _  _ _ _ _ _ _ _  process class   _ _ _ _ _ _ _ _ _ _  _ _ _ _
public static class Process{
	int id ;
	int allocatedSize;
	int usedMemory;
	public int getUsedMemory() {
		return usedMemory;
	}
	public void setUsedMemory(int usedMemory) {
		this.usedMemory = usedMemory;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAllocatedSize() {
		return allocatedSize;
	}
	public void setAllocatedSize(int allocatedSize) {
		this.allocatedSize = allocatedSize;
	}
	
}
//_ _ _ _  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _  _ _ _ _
	
	public static boolean checkPow2(int num) {
		while(num%2==0)
		  {
		   num /= 2;
		  }
		  if(num==1)
		  {
		   return true;
		  }
		  else
		  {
		   return false;
		  }
	}
	public static int memoryAllocation(int num) {
		int i = 1;
		while(i < num) {
			i *=2;
		}
		return i ;
	}
	
	
	public static void main(String[] args) {
		boolean flag = false ;
		//_ _ _ _  _ _ _ _ _ _ _   picking size   _ _ _ _ _ _ _ _ _ _  _ _ _ _
		while(!flag) { 
		System.out.println("please enter memory size (in pow of 2): ");
		mSize = in.nextInt();
		flag = (checkPow2(mSize));
		}
		while(flag) {
			System.out.println(	"1. Enter process"+"\n"+
								"2. Exit process"+"\n"+
								"3. Print status"+"\n"+
								"4. Exit"+"\n"+
								"Enter your choice:");
			int choice = in.nextInt();
			//_ _ _ _  _ _ _ _ _ _ _   option 1   _ _ _ _ _ _ _ _ _ _  _ _ _ _
			if(choice ==1) {
				boolean flag1 = true;
				Process pro = new Process();
				while(flag1) {
					System.out.println("enter process id : ");
					int proId = in.nextInt();
					System.out.println("enter process memory size : ");
					int proSize = in.nextInt();
					if(proSize > mSize) {
						System.out.println("not enough memory");
						System.out.println();
					}
					else {
						pro.setId(proId);
						pro.setUsedMemory(proSize);
						int allocatedMem = memoryAllocation(proSize);
						pro.setAllocatedSize(allocatedMem);
						mSize -= allocatedMem;
						proList.add(pro);
						flag1 = false;
					}
				}
			}
			//_ _ _ _  _ _ _ _ _ _ _   option 2   _ _ _ _ _ _ _ _ _ _  _ _ _ _
			else if(choice == 2) {
				boolean flag2 = true;
				if(proList.isEmpty()) {
					System.out.println("the are no running process ");
					flag2 = false;
				}
				while(flag2) {
					System.out.println("please enter process id you like to terminated : ");
					int terId = in.nextInt();
					for(Process p : proList) {
						if(p.getId() == terId) {
							int minusSize = p.getAllocatedSize();
							System.out.println("terminated process "+p.getId() + " and clearing "+p.getAllocatedSize()+" from the memory");
							proList.remove(p);
							mSize += minusSize;
							flag2 = false;
							break;
						}
					}
					if(flag2) {
						System.out.println("invalid process id");
						System.out.println();
					}
				}
			}
			//_ _ _ _  _ _ _ _ _ _ _   option 3   _ _ _ _ _ _ _ _ _ _  _ _ _ _
			else if(choice == 3) {
				int i = 0 ;
				int usedMemory = 0;
				for(Process p : proList) {
					System.out.println("process "+p.getId()+" useing "+p.getAllocatedSize() +" size block from "+i + " to "+(i+p.allocatedSize));
					usedMemory += p.getUsedMemory();
					i += p.allocatedSize;
				}
				System.out.println();
				System.out.println("Internal Fragmentation -->  "+usedMemory +" used from " +i +" allocated");
			}
			//_ _ _ _  _ _ _ _ _ _ _   option 4   _ _ _ _ _ _ _ _ _ _  _ _ _ _
			else if(choice == 4) {
				flag = false;
				System.out.println("Thank you and byby");
			}
			else {
				System.out.println("invalid option choiced");
			}
		}	
		

	}
	
}