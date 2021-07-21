import java.util.*;
import java.io.*;
class process {                 //This  class contains all different attributes of a process
    int id;                     //process id
    int atime;                  //arrival time
    int btime;                  //burst time
    String type;                //Type of the process
    int wtime;                  //waiting time
    int tatime;                 //turnaround time
    process(int id, int atime, int btime, String type) {            //Initializes different attributes of a process
        this.id = id;
        this.atime = atime; 
        this.btime = btime;
        this.type = type;
    }
}
public class Priority {
    static process queue0[] = new process[10];                      //Holds System Process
    static process queue1[] = new process[10];                      //Holde Interactive process
    static process queue2[] = new process[10];                      //Holds normal process
    static process queue3[] = new process[10];                      //Holds batch process
    static int front0 = -1, rear0 = -1;                             //front and rear  of queue0
    static int front1 = -1, rear1 = -1;                             //front and rear  of queue1
    static int front2 = -1, rear2 = -1;                             //front and rear  of queue2
    static int front3 = -1, rear3 = -1;                             //front and rear  of queue3
    static process p[] = new process[40];                           //Contain all the process temporarily
    static int n=0;                                                 //no. of processes
    static void insert0(process p) {                                //Insert a process in first queue: Always inserts at the top position
        if(front0 == -1 && rear0 == -1) {                               
            front0 = 0;
            rear0 = 0;
        }
        else {
            rear0++;
        }
        queue0[rear0] = p;
    }
    static void insert1(process p) {                                //Insert a process in second queue: Always inserts at the top position
        if(front1 == -1 && rear1 == -1) {
            front1 = 0;
            rear1 = 0;
        }
        else {
            rear1++;
        }
        queue1[rear1] = p;
    }
    static void insert2(process p) {                                //Insert a process in third queue: Always inserts at the top position
        if(front2 == -1 && rear2 == -1) {
            front2 = 0;
            rear2 = 0;
        }
        else {
            rear2++;
        }
        queue2[rear2] = p;
    }
    static void insert3(process p) {                                //Insert a process in fourth queue: Always inserts at the top position
        if(front3 == -1 && rear3 == -1) {
            front3 = 0;
            rear3 = 0;
        }
        else {
            rear3++;
        }
        queue3[rear3] = p;
    }
    static process delete0() {                                      //Delete a process from first queue: Always deletes from the first index
        process current = queue0[front0];
        if(front0 == rear0) {
            front0 = -1;
            rear0 = -1;
        }
        else {
            front0++;
        }
        return current;
    }
    static process delete1() {                                      //Delete a process from second queue: Always deletes from the first index
        process current = queue1[front1];
        if(front1 == rear1) {
            front1 = -1;
            rear1 = -1;
        }
        else {
            front1++;
        }
        return current;
    }
    static process delete2() {                                      //Delete a process from third queue: Always deletes from the first index
        process current = queue2[front2];
        if(front2 == rear2) {
            front2 = -1;
            rear2 = -1;
        }
        else {
            front2++;
        }
        return current;
    }
    static process delete3() {                                     //Delete a process from fourth queue: Always deletes from the first index
        process current = queue3[front3];
        if(front3 == rear3) {
            front3 = -1;
            rear3 = -1;
        }
        else {
            front3++;
        }
        return current;
    }
    static void executeProcess() {                               //Execute all process and print summary
        int time = 0;                                            //System time
        int wasted = 0;                                          //Total wasted time
        process current;                                        //References a particular process temporarily
        int t = 0;
        while(front0 != -1 && rear0 != -1) {                    //Executes process of  first queue which has the highest priority
            current = delete0();                                //Delete the process from queue which is eecuting
            p[n++] = current;
            if(current.atime>time) {                            //Calculate wasted time
                wasted += current.atime - time;
                time = current.atime;
            }
            current.wtime = time - current.atime;               //Calculate waiting time = system time - arrival time
            time = time + current.btime;                        //System time after execution of the process
            current.tatime = time - current.atime;              //Turnaround time of the process = system time - arrival time
        }
        while(front1 != -1 && rear1 != -1) {                    //Executes process of  first queue which has the second highest priority
            current = delete1();                                //Delete the process from queue which is eecuting
            p[n++] = current;
            if(current.atime>time) {                            //Calculate wasted time
                wasted += current.atime - time;
                time = current.atime;
            }
            current.wtime = time - current.atime;               //Calculate waiting time = system time - arrival time
            time = time + current.btime;                        //System time after execution of the process
            current.tatime = time - current.atime;              //Turnaround time of the process = system time - arrival time
        }
        while(front2 != -1 && rear2 != -1) {                    //Executes process of  first queue which has the third highest priority
            current = delete2();                                //Delete the process from queue which is eecuting
            p[n++] = current;
            if(current.atime>time) {                            //Calculate wasted time
                wasted += current.atime - time;
                time = current.atime;
            }
            current.wtime = time - current.atime;               //Calculate waiting time = system time - arrival time
            time = time + current.btime;                        //System time after execution of the process
            current.tatime = time - current.atime;              //Turnaround time of the process = system time - arrival time
        }
        while(front3 != -1 && rear3 != -1) {                    //Executes process of  first queue which has the least priority
            current = delete3();                                //Delete the process from queue which is eecuting
            p[n++] = current;
            if(current.atime>time) {                            //Calculate wasted time
                wasted += current.atime - time;
                time = current.atime;           
            }
            current.wtime = time - current.atime;               //Calculate waiting time = system time - arrival time
            time = time + current.btime;                        //System time after execution of the process
            current.tatime = time - current.atime;              //Turnaround time of the process = system time - arrival time
        }
        int total_wtime = 0;
        int total_tatime = 0;
        int total_btime = 0;
        for(int i=0;i<n;i++) {                                  //Calculate total waiting time, burst time and turnaround time
            total_wtime += p[i].wtime;
            total_tatime += p[i].tatime;
            total_btime += p[i].btime;
        }
        System.out.println("\nProcess\tArrival Time\tBrust Time\tType\t\tTurnaround Time\tWaiting Time");     //Summary of each process
		for(int i=0;i<n;i++)
		{
		    if(p[i].type.equals("INTERACTIVE"))
		        System.out.println("  "+p[i].id+"\t\t"+p[i].atime+"\t"+p[i].btime+"\t\t"+p[i].type+"\t\t"+p[i].tatime+"\t\t"+p[i].wtime);
		    else
			    System.out.println("  "+p[i].id+"\t\t"+p[i].atime+"\t"+p[i].btime+"\t\t"+p[i].type+"\t\t\t"+p[i].tatime+"\t\t"+p[i].wtime);
		}
        System.out.println("\n\nSystem Summary...\n");                          //Summary of the system while processes are executing
        for(int i=0;i<n;i++)  {
            if(t<p[i].atime) {
                while(t<p[i].atime) {                                                   //Print while processes are not executing in the system
                    System.out.println("<System time\t"+t+"> No process is running.");
                    t++;
                }
            }
            int j =0;
            while(j<p[i].btime) {                                                   
                System.out.println("<System time\t"+t+"> process "+p[i].id+" is running.");
                t++;
                j++;
            }
            System.out.println("<System time\t"+t+"> process "+p[i].id+" is finished...");
        }
        System.out.println("Average waiting time:  " + (float)total_wtime/(float)n);
        System.out.println("Average turnaround time: " + (float)total_tatime/(float)n);
        System.out.println("Average response time: " + (float)total_tatime/(float)n);
        System.out.println("Average CPU usage: " + (float)total_btime/(float)time*100.0);
    }
    public static void main(String[] args)  {
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {                        //Read input from a file: The file name should be "input.txt"
                String data = myReader.nextLine();
                String temp[] = data.split(" ");
                process p =new process(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3]);  
                if(temp[3].equals("SYSTEM"))
                    insert0(p);                                     //Insert system processes in queue0 which has the highest priority
                else if(temp[3].equals("INTERACTIVE"))               
                    insert1(p);                                     //Insert system processes in queue1 which has the second highest priority
                else if (temp[3].equals("NORMAL"))
                    insert2(p);                                     //Insert system processes in queue1 which has the third highest priority
                else
                    insert3(p);                                     //Insert system processes in queue1 which has the least priority
            }
            myReader.close();
        } catch (FileNotFoundException e) {                         //Throw exception if the file isn't found
            System.out.println("An error occurred.");
            e.printStackTrace();                                    //Display the report of exception
        }
        executeProcess();
    }
}