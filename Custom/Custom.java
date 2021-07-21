import java.util.*;
import java.io.*;
class process {                 //This  class contains all different attributes of a process
    int id;                     //process id
    int atime;                  //arrival time
    int btime;                  //burst time
    int ibtime;                 //stores initial burst time
    int wtime;                  //waiting time
    int tatime;                 //turnaround time
    int ltime;                  //last observed by process
    int rtime;                  //response time of each process
    process(int id, int atime, int btime) {            //Initializes different attributes of a process
        this.id = id;
        this.atime = atime; 
        this.btime = btime;
        this.wtime = 0;
        this.ltime = atime;
        this.ibtime = btime;
    }
}
public class Custom {
    static process queue0[] = new process[10];                      //Holds System Process
    static process queue1[] = new process[10];                      //Holde Interactive process
    static process queue2[] = new process[10];                      //Holds normal process
    static process queue3[] = new process[10];                      //Holds batch process
    static int front0 = -1, rear0 = -1;                             //front and rear  of queue0
    static int front1 = -1, rear1 = -1;                             //front and rear  of queue1
    static int front2 = -1, rear2 = -1;                             //front and rear  of queue2
    static int front3 = -1, rear3 = -1;                             //front and rear  of queue3 static process processes[] = new process[40];                  //Contain all the completed process temporarily
    static int front =0, rear = 0;                                  //front and rear of processes queue
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
        process current = null;                                 //References a particular process temporarily
        int total_wtime = 0;                                    //holds total waiting time of processes
        int total_tatime = 0;                                   //holds total turn aroung time of processes
        int total_btime = 0;                                    //holds total burst time of processes
        int total_restime =  0;                                 //holds total response time of processess
        while(front0 != -1 && rear0 != -1) {                    //Executes process of  first queue which has the highest priority
            current = delete0();                                //Delete the process from queue which is executing
            if(current.atime>time) {                            //Calculate wasted time
                while(time<current.atime) {                     //Check if any process presents in system or not
                    System.out.println("<System time\t"+time+"> No process is running.");
                    time++;
                    wasted++;
                }
            }
            if(current.ltime==current.atime) {                      //Calculates response time
                current.rtime = time - current.atime;
            }
            current.wtime += time -  current.ltime;
            if(current.btime<=3) {                                   //Executes the process
                int i=0;
                while(i<current.btime) {
                    System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                    time++;
                    i++;
                }
                current.btime = 0;
                current.ltime = time;
                System.out.println("<System time\t"+current.ltime+"> process "+current.id+" is finished...");
                current.tatime = current.ltime - current.atime; //Calculate the turnaround time, waiting time and response time of completed process
                total_tatime = total_tatime + current.tatime;                           
                total_wtime = total_wtime + current.wtime;
                total_btime = total_btime + current.ibtime;
                total_restime = total_restime + current.rtime;
                //Print details of finished process
                System.out.println("Arrival Time: "+current.atime+" Burst Time: "+current.ibtime+" Response Time: "+current.rtime+" Waiting Time: "+current.wtime+" Turnaround Time: "+current.tatime);
            }
            else {
                int i=0;
                while(i<3) {           //For first queue quantum time is 3
                    System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                    current.btime--;
                    time++;
                    i++;
                }
                current.ltime = time;
                insert1(current);
            }
        }
        while(front1 != -1 && rear1 != -1) {                    //Executes process of  first queue which has the highest priority
            current = delete1();                                //Delete the process from queue which is executing
            current.wtime += time -  current.ltime;
            if(current.btime<=4) {                             //Executes the process
                int i=0;
                while(i<current.btime) {
                    System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                    time++;
                    i++;
                }
                current.btime = 0;
                current.ltime = time;
                System.out.println("<System time\t"+current.ltime+"> process "+current.id+" is finished...");
                current.tatime = current.ltime - current.atime; 
                total_tatime = total_tatime + current.tatime;                           
                total_wtime = total_wtime + current.wtime;
                total_btime = total_btime + current.ibtime;
                total_restime = total_restime + current.rtime;
                System.out.println("Arrival Time: "+current.atime+" Burst Time: "+current.ibtime+" Response Time: "+current.rtime+" Waiting Time: "+current.wtime+" Turnaround Time: "+current.tatime);
            }
            else {
                int i=0;
                while(i<4) {                                    //For second queue quantum time is 4
                    System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                    current.btime--;
                    time++;
                    i++;
                }
                current.ltime = time;
                insert2(current);
            }
        }
        while(front2 != -1 && rear2 != -1) {                    //Executes process of  first queue which has the highest priority
            current = delete2();                                //Delete the process from queue which is executing
            current.wtime += time -  current.ltime;
            if(current.btime<=5) {                                   //Executes the process
                int i=0;
                while(i<current.btime) {
                    System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                    time++;
                    i++;
                }
                current.btime = 0;
                current.ltime = time;
                System.out.println("<System time\t"+current.ltime+"> process "+current.id+" is finished...");
                current.tatime = current.ltime - current.atime; 
                total_tatime = total_tatime + current.tatime;                           
                total_wtime = total_wtime + current.wtime;
                total_btime = total_btime + current.ibtime;
                total_restime = total_restime + current.rtime;
                System.out.println("Arrival Time: "+current.atime+" Burst Time: "+current.ibtime+" Response Time: "+current.rtime+" Waiting Time: "+current.wtime+" Turnaround Time: "+current.tatime);
            }
            else {
                int i=0;
                while(i<5) {                                        //For third queue quantum time is 5
                    System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                    current.btime--;
                    time++;
                    i++;
                }
                current.ltime = time;
                insert3(current);
            }
        }
        while(front3 != -1 && rear3 != -1) {                    //Executes process of  first queue which has the highest priority
            current = delete3();                                //Delete the process from queue which is executing
            current.wtime += time -  current.ltime;            //Executes the process
            int i=0;
            while(i<current.btime) {                               //Last queue execute the processin non-preemptive approach
                System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                time++;
                i++;
            }
            current.btime = 0;
            current.ltime = time;
            System.out.println("<System time\t"+current.ltime+"> process "+current.id+" is finished...");
            current.tatime = current.ltime - current.atime; 
            total_tatime = total_tatime + current.tatime;                           
            total_wtime = total_wtime + current.wtime;
            total_btime = total_btime + current.ibtime;
            total_restime = total_restime + current.rtime;
            System.out.println("Arrival Time: "+current.atime+" Burst Time: "+current.ibtime+" Response Time: "+current.rtime+" Waiting Time: "+current.wtime+" Turnaround Time: "+current.tatime);
        }
        System.out.println("Average waiting time:  " + (float)total_wtime/(float)n);
        System.out.println("Average turnaround time: " + (float)total_tatime/(float)n);
        System.out.println("Average response time: " + (float)total_restime/(float)n);
        System.out.println("Average CPU usage: " + (float)total_btime/(float)time*100.0);
    }
    public static void main(String[] args)  {
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {                        //Read input from a file: The file name should be "input.txt"
                String data = myReader.nextLine();
                String temp[] = data.split(" ");
                process p =new process(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));  
                insert0(p);
                n++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {                         //Throw exception if the file isn't found
            System.out.println("An error occurred.");
            e.printStackTrace();                                    //Display the report of exception
        }
        executeProcess();                                           //Execute all the processes in the system
    }
}