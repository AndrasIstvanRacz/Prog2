package szoves;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

	public aspect szoves {
	
		int melyseg = 0;
		public pointcut kiir(LZWBinFa.Csomopont elem, java.io.PrintWriter os)
		: call(public void kiir(LZWBinFa.Csomopont, java.io.PrintWriter)) && args(elem,os);
	
	after (LZWBinFa.Csomopont elem, java.io.PrintWriter os) : kiir(elem,os)
		{
		try {
			
			preOrder(elem,new PrintWriter("pre-order.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		melyseg = 0;
		try {
			postOrder(elem,new PrintWriter("post-order.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	public void preOrder(LZWBinFa.Csomopont elem, java.io.PrintWriter os){
		if (elem != null) {

		      for (int i = 0; i < melyseg; ++i) {
		        os.write("---");
		      }
		      os.print(elem.getBetu());
		      os.print("(");
		      os.print(melyseg - 1);
		      os.println(")");
		      ++melyseg;
		      preOrder(elem.egyesGyermek(), os);
		      preOrder(elem.nullasGyermek(), os);
		      --melyseg;
		      os.flush();
		    }
	}
	
	public void postOrder(LZWBinFa.Csomopont elem, java.io.PrintWriter os){
		
		if (elem != null) {
		      ++melyseg;
		      postOrder(elem.egyesGyermek(), os);
		      postOrder(elem.nullasGyermek(), os);
		      --melyseg;
		 
		      for (int i = 0; i < melyseg; ++i) {
		        os.print("---");
		      }
		      os.print(elem.getBetu());
		      os.print("(");
		      os.print(melyseg - 1);
		      os.println(")");
		      os.flush();

		    }
	}
}
