import java.util.*;
import java.io.*;
import java.text.ParseException;
public class FCFS {
    static int w = 0;                                  //wasted time
    static void findWaitingTime(int processes[], int n, int bt[], int wt[], int at[]) {         //calculate waiting time
        int service_time[] = new int[n];                //total service time  of each process
        service_time[0] = at[0];                            
        wt[0] = 0;          
        for (int i = 1; i < n ; i++) {                              //calculate waiting time and wasted time by each process
            int wasted=0;
            service_time[i] = service_time[i-1] + bt[i-1];          //calculate service time
            wt[i] = service_time[i] - at[i];                        //waiting time = service time - arrival time
            if (wt[i] < 0) {
                wasted = Math.abs(wt[i]);           
                wt[i] = 0;
            }
            service_time[i] = service_time[i] + wasted;     
            w += wasted;                                           
        }
    }
    static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) {         //calculate turnaround time
        for (int i = 0; i < n ; i++)
            tat[i] = bt[i] + wt[i];                                                           //turnaround time = burst time + waiting time
    }
    static void findavgTime(int processes[], int n, int bt[], int at[]) {                   //calculate summary of each process
        int wt[] = new int[n], tat[] = new int[n];
        int compl_time[] = new int[n];
        int total = 0;
        int w=0, t=0;
        findWaitingTime(processes, n, bt, wt, at);
        findTurnAroundTime(processes, n, bt, wt, tat);
        System.out.print("Processes " + " Burst Time " + " Arrival Time "+ " Waiting Time " + " Turn-Around Time " + " Completion Time \n");
        int total_wt = 0, total_tat = 0;
        for (int i = 0 ; i < n ; i++) {                                     //Average waiting time, turnaround time and completetion time
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            compl_time[i] = tat[i] + at[i];
            System.out.println(i+1 + "\t\t" + bt[i] + "\t\t"+ at[i] + "\t\t" + wt[i] + "\t\t "+ tat[i] + "\t\t " + compl_time[i]);
            total = compl_time[i];
        }
        System.out.println("\n\nSystem Summary......");                 //Print system summary
        for(int i=0;i<n;i++) {
            if(t<at[i]) {
                while(t<at[i]) {
                    System.out.println("<System time\t"+t+"> No process is running.");
                    t++;
                }
            }
            while(t<compl_time[i]) {
                System.out.println("<System time\t"+t+"> process "+processes[i]+" is running.");
                t++;
            }
            System.out.println("<System time\t"+t+"> process "+processes[i]+" is finished...");
        }
        float usage = (total-w)/total*100;
        System.out.println("Average cpu usgae: "+usage+"%");
        System.out.println("Average waiting time = " + (float)total_wt / (float)n);
        System.out.println("Average response time = " + (float)total_wt / (float)n);          //Average response time and average waiting time is always equal for FCFS
        System.out.println("Average turn around time = " + (float)total_tat / (float)n);
    }
    public static void main(String args[]) throws ParseException {
    int processes[] = new int[20];                          //Array to store process id
    int n = 0;                                              //no. of processes
    int burst_time[] = new int[20];                         //array to store burst time
    int arrival_time[] = new int[20];                       //array to store arrival time
    try {                                                   //Read input from a file: The file name should be "input.txt"
        File myObj = new File("input.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String temp[]=data.split(" ");
            processes[n]=Integer.parseInt(temp[0]);
            arrival_time[n]=Integer.parseInt(temp[1]);
            burst_time[n]=Integer.parseInt(temp[2]);
            n++;
        }
        myReader.close();
    } catch (FileNotFoundException e) {                 //catch Exception if file isn't found
        System.out.println("An error occurred.");
        e.printStackTrace();                            
    }
    findavgTime(processes, n, burst_time, arrival_time);
    }
}