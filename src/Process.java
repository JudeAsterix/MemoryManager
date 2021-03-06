//Author: JPapello

public class Process
{
    private int processID; //The ID number of the process. 
    private int processSize; //The size of the process. 
    private int base, limit; //The base ad limit registers for the process, repsectively. 
    
    public Process()
    {
        processID = -1; //
        processSize = -1; //
        base = -1; //
        limit = -1; //
    }
    
    public Process(int processID,int processSize,int base,int limit)
    {
        this.processID = processID; //
        this.processSize = processSize; //
        this.base = base;
        this.limit = limit;
    }
    
    public Process(int processID, int processSize)
    {
        this.processID = processID; //
        this.processSize = processSize; //
        base = -1; //
        limit = -1; //
    }
    
    public int getID()
    {
        return processID;
    }
    
    public int getSize()
    {
        return processSize;
    }
    
    public int getBase()
    {
        return base;
    }
    
    public int getLimit()
    {
        return limit;
    }
    
    public void setID(int newID)
    {
        processID = newID;
    }
    
    public void setSize(int newSize)
    {
        processSize = newSize;
    }
    
    public void setBase(int newBase)
    {
        base = newBase;
    }
    
    public void setLimit(int newLimit)
    {
        limit = newLimit;
    }
    
    @Override
    public String toString()
    {
        return "ID = " + processID +
                "\nSize = " + processSize +
                "\nBase = " + base;
    }
    
    //This method will create a clone of the process which invokes the method. 
    public Process deepCopy()
    {
        return new Process(processID, processSize, base, limit);
    }
    
}
