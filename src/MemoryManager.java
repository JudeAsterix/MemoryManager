/**
 * @author JPapello
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MemoryManager
{
    private static Queue waitingQueue = new Queue(10); //Creates a queue of length 10 for the waiting processes. 
    private static ArrayList<Process> memory = new ArrayList<Process>(); //Main memory is an arraylist of process. Initial capacity of 10. 
    private static ArrayList<Integer> holes = new ArrayList<Integer>(); //Creates an arraylist of the holes curently in memory. Initial capacity of 10. 
    
    private static final int MAX = 500; //The maximum address in memory
    
    public static void main(String[] args)
    {
        holes.add(MAX); //Since there are initially no processes in memory, the only hole which exists in memory is the entirety of memory (i.e. from 0 to maximum, which has a length of maxmimum) and so it is added to the list of holes in memory. 
        
        int choice = -1; //Indicator as to what opertion to perform. 
        while(choice != 6) //As long as the user does not choose to exit the program...
        {
            choice = -1; //Resetting the indicator each time...!
            try
            {
                do
                {
                    choice = Integer.parseInt(JOptionPane.showInputDialog("Select an option from the following menu:\nEnter 1 for First-Fit\nEnter 2 for Best-Fit\nEnter 3 for Worst-Fit\nEnter 4 to compact memeory\nEnter 5 to remove a process from memory\nEnter 6 to exit")); //The user is presented with the list of choices. 
                }
                while(choice < 1 || choice > 6); //Continue to ask for a value until a proper input has been entered!
            }
            catch (NumberFormatException e) //If the input cannot be properly translated into an integer...
            {
                JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the operation to perform!", "WARNING: NO INPUT DETECTED!", 2); //A message is shown to the user stating that no valid input was entered. 
            }
            finally
            {
                switch (choice) //Different procedures are performed depending on what the user chooses to do. 
                {
                    case 1:
                    {
                        firstFit(createNewProcess()); //Invoke the first-fit algorithm on a process created by the user via user input. 
                        break;
                    }
                    case 2:
                    {
                        bestFit(createNewProcess()); //Invoke the best-fit algorithm on a process created by the user via user input. 
                        break;
                    }
                    case 3:
                    {
                        worstFit(createNewProcess()); //Invoke the worst-fit algorithm on a process created by the user via user input. 
                        break;
                    }
                    case 4:
                    {
                        //compact(); //Compact memory. 
                        break;
                    }
                    case 5:
                    {
                        removeProcess(); //Remove the process with the specified ID number form memory. 
                    }
                }
                
                identifyHoles(); //Regardless of what happens, every hole within memory is identified. 
                
            }
        }
        
        
        
    }
    
    //This method will create a new process object via user input. 
    public static Process createNewProcess()
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
    public static void firstFit(Process process)
    {
        boolean fit; //Flag indicating whether the process passed in through the parameter is able to fit into memory.
        
        if (process.getSize() > MAX) //If the size of the process is larger than the maximum number of addresses in memory...
        {
            fit = false; //The process cannot possibly fit into memory, and so the flag is "lowered". 
        }
        else if (memory.isEmpty()) //Otherwise, if there are currently no processes in memory...
        {
            fit = true; //The process will always fit into memory, and so the flag is "raised". 
            process.setBase(0); //The base register of the process is set to 0
        }
        else //If both of the above conditions are false (i.e. the size of the process is at most the maximum address value and there is at least one process in memory)...
        {
            fit = false; //Initially, it is assumed that the process cannot fit into memory...
            
            //First, the memory is checked for a hole before the first process large enough to contain the new process. 
            if (process.getSize() < memory.get(0).getBase()) 
            {
                fit = true; //The process is able to fit into memory, so the flag is "raised"!
                process.setBase(0); //The base register of the new process is set to 0. 
            }
            
            if(!fit) //If, as of now, the proces cannot fit into memory...
            {
                //Then, this for loop will look for a hole in memory between two consecutive processes whih is large enough to contain the new process; if there is only one process in memory, then the code in the loop will not execute. 
                for (int i = 0; i < memory.size()-1; i++)
                {
                    if (memory.get(i+1).getBase() > memory.get(i).getLimit() && process.getSize() < memory.get(i+1).getBase() - memory.get(i).getLimit()) 
                    {
                        fit = true; //The process is able to fit into memory, so the flag is "raised"!
                        process.setBase(memory.get(i).getLimit()); //The base address of the new process is set to the limit address of THIS process. 
                        break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any farther. 
                    }
                }
            }
            
            if (!fit) //If, as of now, the proces cannot fit into memory...
            {
                //Finally, the memory is checked for a hole after the last process large enough to conatin the new process. 
                if(memory.get(memory.size()-1).getLimit() < MAX && process.getSize() < MAX - memory.get(memory.size()-1).getLimit()) 
                {
                    fit = true; //The process is able to fit into memory, so the flag is "raised"!
                    process.setBase(memory.get(memory.size()-1).getLimit()); //The base address of the new process is set to the limit register of the last process. 
                }
            }            
        }
                
        if(fit) //If the process can fit into memory... 
        {
            process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
            memory.add(process); //The process is added to memory. 
        }
        else //If the process cannot fit into memory...
        {
            waitingQueue.enque(process); //The process is added to the waiting queue. 
        }
    }
    
    //This method will invoke thre first-fit allocation algorithm, in which the input process is placed in the smallest hole which is large enough to contain it. 
    public static void bestFit(Process process)
    {
        boolean fit = false; //Flag indicating whether the process passed in through the parameter is able to fit into memory.
        int minimum = Integer.MAX_VALUE; //Desired minimum value
        int minimumIndex = -1; //Index in arraylist at which the desired minimum value is found
        for (int i = 0; i < holes.size(); i++)
        {
            if (process.getSize() <= holes.get(i) && holes.get(i) < minimum)
            {
                fit = true; //The process is able to fit into something!
                minimum = holes.get(i); //
                minimumIndex = i; //
                if (holes.get(i) == process.getSize()) //In the case where the size of the hole is exactly equal to the size of the process to fit into memory...
                {
                    break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any further. 
                }
            }
        }
        
        if (fit) //If the process can fit into memory...
        {
            //Find the process(es) which make the smallest hole and fit the new one between them.!
            if (memory.isEmpty()) //If the memory is empty...
            {
               process.setBase(0); //
            }
            else //Otherwise, if there is at least one process in memory...
            {
                if (minimumIndex == 0 && memory.get(0).getBase() > 0) //If THIS...
                {
                    process.setBase(0); //
                    process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                    memory.add(0, process); //The process is added to the beginning of memory. 
                }
                else if(minimumIndex == holes.size() && memory.get(0).getLimit() > MAX) //Otherwise, if THAT...
                {
                    process.setBase(memory.get(memory.size()-1).getLimit()); //
                    process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
                    memory.add(process); //The process is added to the end of memory. 
                }
                else //
                {
                    //This for loop will DO SOME STUFF!
                    for (int i = 0; i < memory.size()-1; i++)
                    {
                        if (memory.get(i+1).getBase() - memory.get(i).getLimit() == minimum) //If THIS...
                        {
                            process.setBase(memory.get(i).getLimit()); //
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
        boolean fit = false; //Flag indicating whether the process passed in through the parameter is able to fit into memory
        int maximum = Integer.MIN_VALUE; //Desired maximum value
        int maximumIndex = -1; //Index in arraylist at which the desired minimum value is found
        for (int i = 0; i < holes.size(); i++)
        {
            if (process.getSize() <= holes.get(i) && holes.get(i) > maximum)
            {
                fit = true; //The process is able to fit into something!
                maximum = holes.get(i);
                maximumIndex = i;
            }
        }
        
        if (fit) //If the process can fit into memory...
        {
            
            
            //process.setBase(memory.get(minimumIndex).getSize()); //The base register of the processs is set to XXXXX
            process.setLimit(process.getBase() + process.getSize()); //The limit of the new process is its base added to its size. 
            memory.add(process); //The process is added to memory. 
            //holes.set(minimumIndex, holes.get(minimumIndex) - process.getSize()); //The size of the hole is reduced by the size of the process. 
        }
        else //Otherwise, if the process cannot fit into memory... 
        {
            waitingQueue.enque(process); //The process is added to the waiting queue. 
        }
    }
    
    //This method will compact memory so that the holes between processes can be eliminated. MAY NOT BE NEEDED...!
    public static void compact()
    {
        //STUFF GOES HERE!
    }
    
    //This method will identify all of the holes currently in memory. 
    public static void identifyHoles()
    {
        holes.removeAll(holes); //The current list of holes is cleared so that it can be updated. 
        
        if (memory.isEmpty()) //If the memory is empty...
        {
            holes.add(MAX); //The length of the hole is the total number of addresses in memory; the entirety of memory is a hole! 
        }
        else //Otherwise, if there is at least one process in memory...
        {
            if (memory.get(0).getBase() > 0) //If there is a hole before the first process...
            {
                holes.add(memory.get(0).getBase()); //A hole with a size equal to the number of addresses between address 0 and the base register of the first process in memory is added to the list of holes. 
            }

            //This for loop will locate, and add, each hole that is found between two consecutive processes in memory. 
            for (int i = 0; i < memory.size()-1; i++)
            {
                if (memory.get(i+1).getBase() > memory.get(i).getLimit()) //If the limit register of the current process is smaller than the base register of the process immediately following it...
                {
                    holes.add(memory.get(i+1).getBase() - memory.get(i).getLimit()); //The size of the hole to add to the list of holes is equal to the number of addresses between the limit register of the current process and the base register of the process immediately following it. 
                }
            }

            if(memory.get(memory.size()-1).getLimit() < MAX) //If there is a hole after the last process...
            {
                holes.add(MAX - memory.get(memory.size()-1).getLimit()); //The size of the hole to add to the list of holes is equal to the number of adresses between the maximum memory address and the limit register of the last process in memory. 
            }
        }
        
    }
    
    //This method will remove the process with the specified ID numer from memory. 
    public static void removeProcess()
    {
        //First, the user specifies the ID number of the process to remove from memory. 
        int ID; //The ID number fo the process to remove. 
        do
        {
            try
            {
                ID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID number of the process to remove from memory.\nPlease input a value which is greater than or equal to 1.", "Enter ID number for new process.", 3)); //The user is prompted to enter a value for the size of the new process. 
                if (ID < 1) //If the input is out of bounds...
                {
                    JOptionPane.showMessageDialog(null, "WARNING: This input is out of bounds!\nPlease input a value which is greater than or equal to 1.", "WARNING: INVALID INPUT DETECTED!", 2); //The user is informed that the input is out of bounds, and so another, more appopriate input must be selected. 
                    ID = 0; //Set the ID of the process to 0 so that the loop can reset.
                }
            }
            catch(NumberFormatException e) //If the input cannot be properly translated into an integer...
            {
                JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the ID number of the process to remove!\nPlease input a value which is greater than or equal to 1.", "WARNING: INVALID INPUT DETECTED!", 2); //The user is informed that the input is invalid, and so another, more appopriate input must be selected. 
                ID = 0; //Set the ID of the process to 0 so that the loop can reset.
            }                            
        }
        while (ID < 1); //Continue to ask for a value until a proper input has been entered!
        
        //Now, the process with the specified ID nmber is sought for in memory and thereafter removed if it is found. 
        boolean flag = false; //Flag indicating whether the process with the specified ID is in memory; initially set to false.  
        if(!memory.isEmpty()) //If there is at least one proces within memory...
        {
            for (Process p : memory) //For every process currently in memory...
            {
                if (ID == p.getID()) //If the ID of the process to remove equals that of the process currently being observed...
                {
                    memory.remove(p); //The process currently being observed is removed from memory. 
                    flag |= true; //The process with the specified ID is, in fact, in memory. 
                    break; //IMMEDIATELY EXIT THE LOOP; we don't need to go any farther. 
                }
            }
        }
        
        if (!flag) //If the process with the specified ID unumber does not exist in memory...
        {
            JOptionPane.showMessageDialog(null, "WARNING: The proces with the specified ID does not exist in memory.", "WARNING: Process #" + ID + " not found!", JOptionPane.WARNING_MESSAGE); //A message is presented to the user stating that the process with the specified ID is not in memory. 
        }
    }
    
    
    
}
