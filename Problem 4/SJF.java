import java.util.*;
import java.io.*;
public class SJF {
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		int n = 0;										//No. of processes
		int pid[] = new int[20];						//array holds id of each process
		int at[] = new int[20];							//array holds arrival time of each process
		int bt[] = new int[20];							//array holds burst time of each process
		int ct[] = new int[20];							//array holds completion time of each process
		int ta[] = new int[20];							//array holds the turnaround time of each process
		int wt[] = new int[20];							//array holds the  waiting time of each process
		int f[] = new int[20];							//array indicates which process is completed and which isn't
		int seq[] = new int[20];						//array to store the sequence of each process
		int st=0, tot=0;								//System time and No. of processes completed
		int t = 0;										//Temporarily stores system time
		float avgwt=0, avgta=0;							//Average waiting time and Average turnaround time
		try {
            File myObj = new File("input.txt");			//Input should be read from a file: "input.txt"
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String temp[]=data.split(" ");
                pid[n]=Integer.parseInt(temp[0]);
                at[n]=Integer.parseInt(temp[1]);
                bt[n]=Integer.parseInt(temp[2]);
                n++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {				//Throws exception when the file isn't  found
            System.out.println("An error occurred.");
            e.printStackTrace();						//Print details of the exception
        }
		boolean a = true;
		while(true)														//Executes all the process one by one depending on arrival and burst time
		{
			int c=n, min=999;	
			if (tot == n)												//Stop execution when all processes are executed
				break;
			
			for (int i=0; i<n; i++)			//Pick the process with minimum burst time which is already arrived in the system and executes it
			{
				if ((at[i] <= st) && (f[i] == 0) && (bt[i]<min))
				{
					min=bt[i];
					c=i;
				}
			}
			if (c==n) 						//When no process presents in the system then system time is increased
				st++;
			else							
			{
				seq[tot] = c;				//Store the sequence of execution of each process
				ct[c]=st+bt[c];				//Calcuulate completion time
				st+=bt[c];					//Calculate system time after execution of the process = system time + burst time
				ta[c]=ct[c]-at[c];			//Calculate turnaround time = completion time - arriavl time
				wt[c]=ta[c]-bt[c];			//Calculate waiting time = turnaround time - burst time
				f[c]=1;						//Marks the process completed its execution
				tot++;						//Increase no of process completed its execution
			}
		}
		System.out.println("\nProcess\tArrival Time\tBrust Time\tCompletetion Time\tTurnaround Time\tWaiting Time");	//Summary  of each process
		for(int i=0;i<n;i++)				//Calculate average turnaround time and average waiting time
		{
			avgwt+= wt[i];
			avgta+= ta[i];
			System.out.println("  "+pid[i]+"\t\t"+at[i]+"\t\t"+bt[i]+"\t\t"+ct[i]+"\t\t\t"+ta[i]+"\t\t"+wt[i]);
		}
		System.out.println("\n\nSystem Summary...\n");                          //Summary of the system while processes are executing
		for(int i=0;i<n;i++) {									
			if(t<at[seq[i]]) {															//Display when no process presents in  the system
				while(t<at[seq[i]]) {
					System.out.println("<System time\t"+t+"> No process is running.");
                    t++;
				}
			}
			while(t<ct[seq[i]]) {												//Display the process name which is executing
				System.out.println("<System time\t"+t+"> process "+pid[seq[i]]+" is running.");
                t++;
			}
			System.out.println("<System time\t"+t+"> process "+pid[seq[i]]+" is finished...");
		}
		int total_bt = 0, ctime = ta[0];
		for(int i=0;i<n;i++)												//Calculate total burst time
		    total_bt += bt[i];
		for(int i=1;i<n;i++) {												//Calculate total time needed by every process 
		    if(ctime<ta[i])
		        ctime = ta[i];
		}
		System.out.println ("\nAverage Turnaround Time: "+ (float)avgta/(float)n);		//Display all informations
		System.out.println ("Average Waiting Time: "+ (float)avgwt/(float)n);
		System.out.println ("Average Response Time: "+ (float)avgwt/(float)n);	//Response time and Waiting time is same in non-preemptive algorithm
		System.out.println("Average CPU usage: "+(float)total_bt/(float)ctime*100);
		sc.close();
	}
}