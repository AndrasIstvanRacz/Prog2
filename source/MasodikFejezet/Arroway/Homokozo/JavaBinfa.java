import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
class JavaBinfa
{
    public JavaBinfa ()
    {
    	root = new Node('/');
    	currentNode = root;
    }
  
    public void insert (char b)
    {
       
        if (b == '0')
        {
           
            if (currentNode.getLeftChild () == null)	
            {
                
                Node n = new Node ('0');
                
                
                
                currentNode.newLeftChild (n);
                
                currentNode = root;
            }
            else			
            {
                
                currentNode = currentNode.getLeftChild ();
            }
        }
        
        else
        {
            if (currentNode.getRightChild () == null)
            {
                Node n = new Node ('1');
                currentNode.newRightChild (n);
                currentNode = root;
            }
            else
            {
                currentNode = currentNode.getRightChild ();
            }
        }
    }
    
   
    public int getDepth ()
    {
    	 	depth = maxDepth = 0;
    	    recDepth (root);
    	    return maxDepth - 1;
    }
    public double getMean ()
    {
    	depth = sumOfMean = numberOfNodes = 0;
        recMean (root);
        mean = ((double) sumOfMean) / numberOfNodes;
        return mean;
    }
    public double getVariance ()
    {
    	mean = getMean ();
        sumOfVar = 0.0;
        depth = numberOfNodes = 0;

        recVar (root);

        if (numberOfNodes - 1 > 0)
            variance = Math.sqrt (sumOfVar / (numberOfNodes - 1));
        else
            variance = Math.sqrt (sumOfVar);

        return variance;
    }

    public void recDepth (Node  n)
    {
        if (n != null)
        {
            ++depth;
            if (depth > maxDepth)
                maxDepth = depth;
            recDepth (n.getRightChild ());
            
            
            recDepth (n.getLeftChild ());
            --depth;
        }
    }


public void recMean (Node  n)
{
    if (n != null)
    {
        ++depth;
        recMean (n.getRightChild ());
        recMean (n.getLeftChild ());
        --depth;
        if (n.getRightChild () == null && n.getLeftChild () == null)
        {
            ++numberOfNodes;
            sumOfMean += depth;
        }
    }
}

public void recVar (Node  n)
{
    if (n != null)
    {
        ++depth;
        recVar (n.getRightChild ());
        recVar (n.getLeftChild ());
        --depth;
        if (n.getRightChild () == null && n.getLeftChild () == null)
        {
            ++numberOfNodes;
            sumOfVar += ((depth - mean) * (depth - mean));
        }
    }
}
    public void printTree (PrintWriter os)
    {
        depth = 0;
        printTree (root, os);
    }

    class Node
    {
      
        Node (char b)
        {
        	if(b == '0' || b == '1')
        		value = b;
        	else
        	value = '/';
        };
 
        Node getLeftChild () 
        {
            return leftChild;
        }
        
        Node getRightChild () 
        {
            return rightChild;
        }
        
        void newLeftChild (Node gy)
        {
            leftChild = gy;
        }
        
        void newRightChild (Node gy)
        {
            rightChild = gy;
        }
      
        char getValue () 
        {
            return value;
        }
       

        
        private char value;
       
        private Node leftChild;
        private Node rightChild;
    };

   
    Node currentNode;
    
    private int depth, sumOfMean, numberOfNodes;
    private double sumOfVar;
    

    
    public void printTree (Node  n, PrintWriter os)
    {
        
        if (n != null)
        {
            ++depth;
            printTree (n.getLeftChild (), os);
            
            
            for (int i = 0; i < depth; ++i)
                os.print("---");
            os.print(n.getValue () + "(" + depth + ")\n");
            printTree (n.getRightChild (), os);
            --depth;
        }
    }
   
	protected Node root;
    protected int maxDepth;
    protected double mean, variance;


    public static void usage ()
    {
        System.out.println("Usage: lzwtree in_file -o out_file");
    }

    public static void  main (String[] args) throws FileNotFoundException, IOException
    {
        
        if (args.length  < 3)
        {
            usage ();
            return;
        }

        String inFile = args[0];

        
        if(!args[1].equals("-o"))
        {
        	 System.out.println(args[1]);
        	 usage ();
             return;
        }

        
        java.io.FileInputStream input = new java.io.FileInputStream(new java.io.File(inFile));

        
        if (input == null)
        {
            System.out.println("Nem létezik");
            usage ();
            return;
        }
        PrintWriter output = new PrintWriter(args[2]);

        byte[] b = new byte[1];		
        JavaBinfa bincurrentNode = new JavaBinfa();		
        while (input.read(b) != -1)
        {
            if (b[0] == 0x0a)
            {
            break;
            }
        }
        
        boolean inComment = false;
        while (input.read (b) != -1) 
        {
            if (b[0] == 0x3e) 
            {			
                inComment = true;
                continue;
            }
            if (b[0] == 0x0a) 
            {			
                inComment = false;
                continue;
            }
            if (inComment)
            {
                continue;
            }
            if (b[0] == 0x4e)
            {
                continue;
            }
            for (int i = 0; i < 8; ++i)
            {
                if ((b[0] & 0x80) != 0)
                {
                    bincurrentNode.insert('1');
                } 
                else
                {
                    bincurrentNode.insert('0');
                }
                b[0] <<= 1;
            }
        }
        
        bincurrentNode.printTree(output);		

        output.print("depth " + bincurrentNode.getDepth () + "\n");
        output.print("mean " + bincurrentNode.getMean () + "\n");
        output.print("var " + bincurrentNode.getVariance () + "\n");

        output.close ();
        input.close ();

      
    }

    
};
