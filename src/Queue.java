public class Queue
{
    private Process[] data; //
    private int numberOfNodes; //
    private int size; //
    
    //Default constructor
    public Queue()
    {
        size = 100;
        numberOfNodes = 0;
        data = new Process[size];
    }
    
    //
    public Queue(int size)
    {
        this.size = size;
        numberOfNodes = 0;
        data = new Process[size];
    }
    
    //This method determines whether the queue is full.
    public boolean isFull()
    {
        return (numberOfNodes == size);
    }
    
    //This method determines whether the queue is empty. 
    public boolean isEmpty()
    {
        return (numberOfNodes == 0);
    }
    
    //This method will place an object into the queue. 
    public boolean enque(Process newNode)
    {
        if(isFull()) //If the queue is curently full...
        {
            return false; //OVERFLOW ERROR!!
        }
        else
        {
            data[numberOfNodes] = newNode.deepCopy(); //ERROR: rear is out of bounds!
            numberOfNodes++; //
        }
        return true; 
    }
    
    //This method will return the object at the head of the queue. 
    public Process deque()
    {
        Process frontLocation;
        if(isEmpty()) //If the queue is currently empty...
        {
            return null; //UNDERFLOW ERROR!!
        }
        else
        {
            frontLocation = data[0].deepCopy();
            data[0] = null;
            numberOfNodes--;
            for(int i = 0; i < numberOfNodes; i++)
            {
                data[i] = data[i + 1];
            }
            data[numberOfNodes] = null;
            return frontLocation;
        }
        
    }
    
    //This method will identify the object at the head of trhe queue. 
    public Process peek()
    {
        int frontLocation; //
        if(isEmpty()) //If the queue is curently empty...
        {
            return null; //UNDERFLOW ERROR!!
        }
        else
        {
            return data[0]; //
        }
    }
    
    public Process peekAt(int i)
    {
        return data[i];
    }
    
    //This method will identify all of th objects currently in the queue. 
    public void showAll()
    {
        for (int j = 0; j < numberOfNodes; j++)
        {
            System.out.println("Item #" + (j+1) + " in Queue:\n" + data[j].toString());
        }
    }
    
    public int getSize()
    {
        return this.size;
    }
    
    public int getNumberOfNodes()
    {
        return this.numberOfNodes;
    }
    
    public void clear()
    {
        this.numberOfNodes = 0;
        this.data = new Process[size];
    }
    
    //This method will reinitialize the queue. 
    /*
    public void reinitialize()
    {
        numberOfNodes = 0;
        front = 0;
        rear = 0;
    }
    */
}
