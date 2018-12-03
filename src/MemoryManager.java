/**
 * @author JPapello
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JOptionPane;

public class MemoryManager
{
    private Queue waitingQueue = new Queue(10); //Creates a queue of length 10 for the waiting processes. 
    private ArrayList<Process> memory = new ArrayList<Process>(); //Main memory is an arraylist of process. Initial capacity of 10. 
    private final int MAX = 500; //The maximum address in memory
    
    public MemoryManager()
    {
    }
    
    //This method will create a new process object via user input. 
    public Process createNewProcess()
    {
        Process P = new Process(); //A new process is created. 
        
        //First, the user will establish the ID number of the new process. 
        int ID; //The ID number of the new process. 
        boolean flag; //Flag to indicate whether the ID number for the new process is unique. 
        do
        {
            try
            {
                ID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID number of the process.\nPlease input a value which is greater than or equal to 1.", "Enter ID number for new process.", 3)); //The user is prompted to enter a value for the size of the new process. 
                if (ID < 1) //If the input is out of bounds...
                {
                    JOptionPane.showMessageDialog(null, "WARNING: This input is out of bounds!\nPlease input a value which is greater than or equal to 1.", "WARNING: INVALID INPUT DETECTED!", 2); //The user is informed that the input is out of bounds, and so another, more appopriate input must be selected. 
                    ID = 0; //Set the ID of the process to 0 so that the loop can reset.
                }
            }
            catch(NumberFormatException e) //If the input cannot be properly translated into an integer...
            {
                JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the ID number of the new process!\nPlease input a value which is greater than or equal to 1.", "WARNING: INVALID INPUT DETECTED!", 2); //The user is informed that the input is invalid, and so another, more appopriate input must be selected. 
                ID = 0; //Set the ID of the process to 0 so that the loop can reset.
            }
            
            //
            flag = false; //Initially, the flag is set to false. 
            if (ID != 0 && !memory.isEmpty()) //If the ID number is not zero and there is at least one process in memory...
            {
                for (Process p : memory) //For every process currently in memory...
                {
                    if (ID == p.getID()) //If the suggested ID is equl to the ID number of the current process...
                    {
                        flag |= true; //Another process with the same ID number has been found!
                        break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any farther. 
                    }
                }
                
                if (flag) //If another process with the same ID number exists. 
                {
                    JOptionPane.showMessageDialog(null, "WARNING: This ID number is already being used!\nPlease input a value which is greater than or equal to 1 which is not " + ID + ".", "WARNING: ID NUMBER " + ID + " IS ALREADY BEING USED!", 2); //The user is informed that the input is out of bounds, and so another, more appopriate input must be selected. 
                    ID = 0; //Set the ID of the process to 0 so that the loop can reset.
                }
            }
        }
        while (ID < 1); //Continue to ask for a value until a proper input has been entered!
        P.setID(ID); //The ID of the new process is set to be whatever value the user input.
        
        
        
        //Then, the user will establish the size of the new process. 
        int size; //The size of the new process. 
        do
        {
            try
            {
                size = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the size of the process.\nThis is a value between 1 and 1,024, inclusive.", "Enter the size of the new process.", 3)); //The user is prompted to enter a value for the size of the new process. 
                if (size < 1 || size > 1024) //If the input is out of bounds...
                {
                    JOptionPane.showMessageDialog(null, "WARNING: This input is out of bounds!\nPlease input a value between 1 and 1,024, inclusive.", "WARNING: INVALID INPUT DETECTED!", 2); //The user is informed that the input is out of bounds, and so another, more appopriate input must be selected. 
                }
            }
            catch(NumberFormatException e) //If the input cannot be properly translated into an integer...
            {
                JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the size of the new process!\nPlease input an integer value between 1 and 1,024, inclusive.", "WARNING: INVALID INPUT DETECTED!", 2); //The user is informed that the input is invalid, and so another, more appopriate input must be selected. 
                size = 0; //Set the sie of the process to 0 so that the loop can reset!
            }
        }
        while (size < 1 || size > 1024); //Continue to ask for a value until a proper input has been entered!
        P.setSize(size); //The size of the new process is set to be whatever value the user input. 
        
        return P; //The newly created process is returned. 
    }
    
    //This method will invoke thre first-fit allocation algorithm, in which the input process is placed in the first hole which is large enough to contain it. 
    public boolean firstFit(Process process)
    {
        boolean fit; //Flag indicating whether the process passed in through the parameter is able to fit into memory.
        ArrayList<Process> sortedProcesses = getProcessesSortedByBase();
        if (process.getSize() > MAX) //If the size of the process is larger than the maximum number of addresses in memory...
        {
            fit = false; //The process cannot possibly fit into memory, and so the flag is "lowered". 
        }
        else if (memory.isEmpty()) //Otherwise, if there are currently no processes in memory...
        {
            fit = true; //The process will always fit into memory, and so the flag is "raised". 
            process.setBase(0); //The base register of the process is set to 0
            process.setLimit(process.getSize());
        }
        else //If both of the above conditions are false (i.e. the size of the process is at most the maximum address value and there is at least one process in memory)...
        {
            fit = false; //Initially, it is assumed that the process cannot fit into memory...
            
            //First, the memory is checked for a hole before the first process large enough to contain the new process. 
            if (process.getSize() <= sortedProcesses.get(0).getBase()) 
            {
                fit = true; //The process is able to fit into memory, so the flag is "raised"!
                process.setBase(0); //The base register of the new process is set to 0. \
                process.setLimit(process.getSize());
            }
            
            if(!fit) //If, as of now, the proces cannot fit into memory...
            {
                //Then, this for loop will look for a hole in memory between two consecutive processes whih is large enough to contain the new process; if there is only one process in memory, then the code in the loop will not execute. 
                for (int i = 0; i < sortedProcesses.size()-1; i++)
                {
                    if (process.getSize() <= sortedProcesses.get(i+1).getBase() - sortedProcesses.get(i).getLimit()) 
                    {
                        fit = true; //The process is able to fit into memory, so the flag is "raised"!
                        process.setBase(memory.get(sortedProcesses.get(i).getID()).getLimit()); //The base address of the new process is set to the limit address of THIS process. 
                        process.setLimit(process.getBase() + process.getSize());
                        break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any farther. 
                    }
                }
            }
            
            if (!fit) //If, as of now, the process cannot fit into memory...
            {
                //Finally, the memory is checked for a hole after the last process large enough to conatin the new process. 
                if(sortedProcesses.get(memory.size()-1).getLimit() < MAX && process.getSize() <= MAX - sortedProcesses.get(memory.size()-1).getLimit()) 
                {
                    fit = true; //The process is able to fit into memory, so the flag is "raised"!
                    process.setBase((sortedProcesses.get(memory.size() - 1)).getLimit()); //The base address of the new process is set to the limit register of the last process. 
                    process.setLimit(process.getBase() + process.getSize());
                }
            }            
        }
                
        if(fit) //If the process can fit into memory... 
        {
            memory.add(process); //The process is added to memory. 
        }
        else //If the process cannot fit into memory...
        {
            process.setID(waitingQueue.getNumberOfNodes());
            waitingQueue.enque(process); //The process is added to the waiting queue. 
        }
        return fit;
    }
    /*
    //Th//This method will invoke the first-fit allocation algorithm, in which the input process is placed in the smallest hole which is large enough to contain it. 
    public static void bestFit(Process process)
    {
        boolean fit = false; //Flag indicating whether the process passed in through the parameter is able to fit into memory.
        int minimum = Integer.MAX_VALUE; //The size of the smallest hole cuurnetly in memory. 
        int minimumIndex = -1; //Index in arraylist at which the desired minimum value is found
        
        //This for loop will find the smallest hole currently in memory. 
        for (int i = 0; i < holes.size(); i++)
        {
            if (process.getSize() <= holes.get(i) && holes.get(i) < minimum) //If the size of the process can fit into the hole being observed and that hole has a smaller size than the recorded minimum value...
            {
                fit = true; //The process is able to fit into something!
                minimum = holes.get(i); //The minimum value is recorded. 
                minimumIndex = i; //The index in the arraylist where the minimum value is found is recorded as well. 
                if (holes.get(i) == process.getSize()) //In the case where the size of the hole is exactly equal to the size of the process to fit into memory...
                {
                    break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any further. 
                }
            }
        }
        
        if (fit) //If the process can fit into memory...
        {
            if (memory.isEmpty()) //If the memory is empty...
            {
               process.setBase(0); //The base register of the process is set to 0. 
            }
            else //Otherwise, if there is at least one process in memory...
            {
                if (minimumIndex == 0 && memory.get(0).getBase() > 0) //If the smallest hole is found in the beginning of memory and the base register of the first process is larger than 0...
                {
                    process.setBase(0); //The base register of the process is set to 0. 
                    process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                    memory.add(0, process); //The process is added to the beginning of memory. 
                }
                else if(minimumIndex == (holes.size()-1) && memory.get(memory.size()-1).getLimit() < MAX) //Otherwise, if the smallest hole is found at the end of memory and the limit register of the last process is smaller than the maximum number of addresses...
                {
                    process.setBase(memory.get(memory.size()-1).getLimit()); //The base register of the process is set to the limit register of the last process. 
                    process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                    memory.add(process); //The process is added to the end of memory. 
                }
                else //If neither of the above cases are true...
                {
                    //This for loop will look for the hole in memory between two processes whose length is equal to the smallest hole that was found. 
                    for (int i = 0; i < memory.size()-1; i++)
                    {
                        if (memory.get(i+1).getBase() - memory.get(i).getLimit() == minimum) //If the difference between the limit register of the current process and the base register of the process which follows it is equal to the minimum value...
                        {
                            process.setBase(memory.get(i).getLimit()); //The base register of the process is set to the limit register of the current process being observed. 
                            process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                            memory.add(i+1, process); //The process is added to the area in memory between the two processes. 
                            break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any further. 
                        }
                    }
                }
            }
            
            holes.set(minimumIndex, holes.get(minimumIndex) - process.getSize()); //The size of the hole is reduced by the size of the process. 
        }
        else //Otherwise, if the process cannot fit into memory... 
        {
            waitingQueue.enque(process); //The process is added to the waiting queue. 
        }
    }
    
    //This method will invoke thre first-fit allocation algorithm, in which the input process is placed in the largest hole which is large enough to contain it. 
    public static void worstFit(Process process)
    {
        boolean fit = false; //Flag indicating whether the process passed in through the parameter is able to fit into memory.
        int maximum = Integer.MIN_VALUE; //The size of the largest hole cuurnetly in memory. 
        int maximumIndex = -1; //Index in arraylist at which the desired maximum value is found
        
        //This for loop will find the largest hole currently in memory. 
        for (int i = 0; i < holes.size(); i++)
        {
            if (process.getSize() <= holes.get(i) && holes.get(i) > maximum) //If the size of the process can fit into the hole being observed and that hole has a larger size than the recorded maximum value...
            {
                fit = true; //The process is able to fit into something!
                maximum = holes.get(i); //The minimum value is recorded. 
                maximumIndex = i; //The index in the arraylist where the minimum value is found is recorded as well. 
                if (holes.get(i) == process.getSize()) //In the case where the size of the hole is exactly equal to the size of the process to fit into memory...
                {
                    break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any further. 
                }
            }
        }
        
        if (fit) //If the process can fit into memory...
        {
            if (memory.isEmpty()) //If the memory is empty...
            {
               process.setBase(0); //The base register of the process is set to 0. 
            }
            else //Otherwise, if there is at least one process in memory...
            {
                if (maximumIndex == 0 && memory.get(0).getBase() > 0) //If the largest hole is found in the beginning of memory and the base register of the first process is larger than 0...
                {
                    process.setBase(0); //The base register of the process is set to 0. 
                    process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                    memory.add(0, process); //The process is added to the beginning of memory. 
                }
                else if(maximumIndex == (holes.size()-1) && memory.get(memory.size()-1).getLimit() < MAX) //Otherwise, if the largest hole is found at the end of memory and the limit register of the last process is smaller than the maximum number of addresses...
                {
                    process.setBase(memory.get(memory.size()-1).getLimit()); //The base register of the process is set to the limit register of the last process. 
                    process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                    memory.add(process); //The process is added to the end of memory. 
                }
                else //If neither of the above cases are true...
                {
                    //This for loop will look for the hole in memory between two processes whose length is equal to the largest hole that was found. 
                    for (int i = 0; i < memory.size()-1; i++)
                    {
                        if (memory.get(i+1).getBase() - memory.get(i).getLimit() == maximum) //If the difference between the limit register of the current process and the base register of the process which follows it is equal to the maximum value...
                        {
                            process.setBase(memory.get(i).getLimit()); //The base register of the process is set to the limit register of the current process being observed. 
                            process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                            memory.add(i+1, process); //The process is added to the area in memory between the two processes. 
                            break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any further. 
                        }
                    }
                }
            }
            
            holes.set(maximumIndex, holes.get(maximumIndex) - process.getSize()); //The size of the hole is reduced by the size of the process. 
        }
        else //Otherwise, if the process cannot fit into memory... 
        {
            waitingQueue.enque(process); //The process is added to the waiting queue. 
        }
    }
    */
    
    public void compact()
    {
        ArrayList<Process> sorted = getProcessesSortedByBase();
        for(int i = 0; i < sorted.size(); i++)
        {
            if(i == 0)
            {
                memory.get(sorted.get(i).getID()).setBase(0);
                memory.get(sorted.get(i).getID()).setLimit(memory.get(sorted.get(i).getID()).getSize());
            }
            else
            {
                memory.get(sorted.get(i).getID()).setBase(memory.get(sorted.get(i - 1).getID()).getLimit());
                memory.get(sorted.get(i).getID()).setLimit(memory.get(sorted.get(i).getID()).getBase() + memory.get(sorted.get(i).getID()).getSize());
            }
        }
        checkIfCanDeque();
    }
    
    public ArrayList<Process> getProcessesSortedByBase()
    {
        Process[] processCopy = new Process[memory.size()];
        
        for(int i = 0; i < processCopy.length; i++)
        {
            processCopy[i] = memory.get(i).deepCopy();
        }
        
        Arrays.sort(processCopy, new Comparator<Process>()
        {
            public int compare(Process p1, Process p2) 
            {
                return (Integer.compare(p1.getBase(), p2.getBase()));
            }
        });
        
        return new ArrayList<Process>(Arrays.asList(processCopy));
    }
    
    public Process[] getProcesses()
    {
        return (Process[])(memory.toArray().clone());
    }
    
    public int getNumberOfProcessesInMemory()
    {
        return this.memory.size();
    }
    
    public int getNumberOfProcesses()
    {
        return this.memory.size() + this.waitingQueue.getNumberOfNodes();
    }
    
    public Object[][] outputTableData(int num)
    {
        Object[][] ret;
        if(num == 1)
        {
            ret = new Object[memory.size()][4];
            for(int i = 0; i < ret.length; i++)
            {
                ret[i][0] = (Integer)memory.get(i).getID();
                ret[i][1] = (Integer)memory.get(i).getBase();
                ret[i][2] = (Integer)memory.get(i).getSize();
                ret[i][3] = (Integer)0;
            }
        }
        else
        {
            Object[][] temp = new Object[waitingQueue.getSize()][3];
            int i = 0;
            while(waitingQueue.peekAt(i) != null)
            {
                temp[i][0] = waitingQueue.peekAt(i).getID();
                temp[i][1] = waitingQueue.peekAt(i).getSize();
                temp[i][2] = 0;
                i++;
            }
            ret = new Object[i][3];
            for(int j = 0; j < ret.length; j++)
            {
                ret[j][0] = temp[j][0];
                ret[j][1] = temp[j][1];
                ret[j][2] = temp[j][2];
            }
        }
        return ret;
    }
    
    public void removeProcess(int index)
    {
        memory.remove(index);
        for(int i = index; i < memory.size(); i++)
        {
            memory.get(i).setID(memory.get(i).getID() - 1);
        }
        checkIfCanDeque();
    }
    
    public void checkIfCanDeque()
    {
        ArrayList<Process> sorted = getProcessesSortedByBase();
        
        if(waitingQueue.getNumberOfNodes() > 0)
        {
            
        if(memory.isEmpty() || sorted.get(0).getBase() > waitingQueue.peek().getSize())
        {
            Process p = waitingQueue.deque();
            p.setID(memory.size());
            p.setBase(0);
            p.setLimit(p.getBase() + p.getSize());
            memory.add(p);
            JOptionPane.showMessageDialog(null, "The firstmost process of the Waiting Queue has been automatically placed into memory. Its ID number is now " + p.getID() + "." );
        }
        if(waitingQueue.isEmpty())
        {
            return;
        }
            
        sorted = getProcessesSortedByBase();
        for(int i = 0; i < sorted.size() - 1; i++)
        {
            int before = sorted.get(i).getID();
            int after = sorted.get(i + 1).getID();
            int a = memory.get(after).getBase();
            int b = memory.get(before).getLimit(); 
            int c = waitingQueue.peek().getSize();
            if(waitingQueue.isEmpty())
            {
                return;
            }
            while(waitingQueue.getNumberOfNodes() > 0 && a - b >= c)
            {
                System.out.println(a + " - " + b + " >= " + c);
                Process p = waitingQueue.deque();
                p.setID(memory.size());
                p.setBase(memory.get(before).getLimit());
                p.setLimit(p.getBase() + p.getSize());
                memory.add(p);
                JOptionPane.showMessageDialog(null, "The firstmost process of the Waiting Queue has been automatically placed into memory. Its ID number is now " + p.getID() + "." );
                
                sorted = getProcessesSortedByBase();
                i++;
                before = sorted.get(i).getID();
                sorted.get(i + 1).getID();
                a = memory.get(after).getBase();
                b = memory.get(before).getLimit();
                Process temp = waitingQueue.peek();
                if(temp != null)
                {
                    c = temp.getSize();
                }
            }
        }
        if(waitingQueue.isEmpty())
        {
            return;
        }
            sorted = getProcessesSortedByBase();
            int c = waitingQueue.peek().getSize();
            while(waitingQueue.getNumberOfNodes() > 0 && MAX - sorted.get(sorted.size() - 1).getLimit() >= c)
            {
                Process p = waitingQueue.deque();
                System.out.println(p.toString());
                p.setID(memory.size());
                p.setBase(sorted.get(sorted.size() - 1).getLimit());
                p.setLimit(p.getBase() + p.getSize());
                memory.add(p);
                System.out.println(memory.get(memory.size() - 1).toString());
                JOptionPane.showMessageDialog(null, "The firstmost process of the Waiting Queue has been automatically placed into memory. Its ID number is now " + p.getID() + "." );
                
                sorted = getProcessesSortedByBase();
                Process temp = waitingQueue.peek();
                if(temp != null)
                {
                    c = p.getSize();
                }
            }
        }
        
    }
    
    public void reset()
    {
        memory.clear();
        waitingQueue.clear();
    }
    
}
