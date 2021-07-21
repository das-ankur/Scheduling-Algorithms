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
public class RR {
    static process queue[] = new process[40];                           //Ready queue
    static int front=-1, rear=-1;                                       //Front and rear of the ready queue
    static int n=0;                                                     //no. of processes
    static int quantum = 2;                                            //Time quantum of the system
    static void insert(process p) {                                     //Insert a process in queue: Always inserts at the top index
        if(front == -1 && rear == -1) {                               
            front = 0;
            rear = 0;
        }
        else {
            rear++;
        }
        queue[rear] = p;
    }
    static process delete() {                                           //Delete a process from queue: Always deletes from the first index
        process current = queue[front];
        if(front == rear) {
            front = -1;
            rear = -1;
        }
        else {
            front++;
        }
        return current;
    }
    static void executeProcess() {
        int time = 0;                                            //System time
        int wasted = 0;                                          //Total wasted time
        process current = null;                                 //References a particular process temporarily
        int total_wtime = 0;                                    //holds total waiting time of processes
        int total_tatime = 0;                                   //holds total turn aroung time of processes
        int total_btime = 0;                                    //holds total burst time of processes
        int total_restime =  0;                                 //holds total response time of processes
        boolean flag;                                           //Works as indicator
        while(front != -1 && rear != -1) {                      //Executes process of  first queue which has the highest priority
            current = delete();                                 //Delete the process from queue which is executing
            process temp = current;
            if(current.atime>time) {                            //If arrival time is less than the system time
                flag = false;                                   //Calculate wasted time
                insert(current);
                do {                                            //Check which process is ready to execute                      
                    current = delete();
                    if(current.atime<=time) {
                        break;
                    }
                    if(current==temp) {
                        flag = true;
                        break;
                    }
                    insert(current);
                }while(true);
                if(flag) {                                          //If no process is ready to execute then system time will be wasted
                    while(time<current.atime) {                     //Check if any process presents in system or not
                        System.out.println("<System time\t"+time+"> No process is running.");
                        time++;
                        wasted++;
                    }
                }
            }
            if(current.ltime==current.atime) {                              //Calculates response time
                current.rtime = time - current.atime;
            }
            current.wtime += time -  current.ltime;                         //Calculate waiting time
            if(current.btime<=quantum) {                                    //When process brust time less than or equal than time quantum
                int i=0;
                while(i<current.btime) {                                    //Execute a process for defined time quamtum
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
                while(i<quantum) {                                                  //When process brust time greater than time quantum
                    System.out.println("<System time\t"+time+"> process "+current.id+" is running.");
                    current.btime--;
                    time++;
                    i++;
                }
                current.ltime = time;
                insert(current);
            }
        }
        //Summary of the run of processes
        System.out.println("Average waiting time:  " + (float)total_wtime/(float)n);
        System.out.println("Average turnaround time: " + (float)total_tatime/(float)n);
        System.out.println("Average response time: " + (float)total_restime/(float)n);
        System.out.println("Average CPU usage: " + (float)total_btime/(float)time*100.0);
    }
    public static void main(String[] args) {
        try {
            File myObj = new File("input.txt");                     //Insput should be read from "input.txt"
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {                        //Read input from a file: The file name should be "input.txt"
                String data = myReader.nextLine();
                String temp[] = data.split(" ");
                process t =new process(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));  
                insert(t);                               
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