public class Queue
{
    private Process[] data; //
    private int numberOfNodes; //
    private int front, rear; //
    private int size; //
    
    //Default constructor
    public Queue()
    {
        size = 100;
        numberOfNodes = 0;
        front = 0;
        rear = 0;
        data = new Process[size];
    }
    
    //
    public Queue(int size)
    {
        this.size = size;
        numberOfNodes = 0;
        front = 0;
        rear = 0;
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
            numberOfNodes = numberOfNodes + 1; //
            data[rear] = newNode.deepCopy(); //ERROR: rear is out of bounds!
            rear = (rear + 1) % size; // Garbage Collection!!       
        }
        return true; 
    }
    
    //This method will return the object at the head of the queue. 
    public Process deque()
    {
        int frontLocation;
        if(isEmpty()) //If the queue is currently empty...
        {
            return null; //UNDERFLOW ERROR!!
        }
        else
        {
            frontLocation = front;
            front = (front + 1) % size; //Garbage Collection!!
            numberOfNodes = numberOfNodes - 1;
            return data[frontLocation];
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
            frontLocation = front; //
            return data[frontLocation]; //
        }
    }
    
    //This method will identify all of th objects currently in the queue. 
    public void showAll()
    {
        for (int j = 0; j < numberOfNodes; j++)
        {
            System.out.println("Item #" + (j+1) + " in Queue:\n" + data[j].toString());
        }
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
