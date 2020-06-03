package Binfa;

public class LZWBinFa {

	  public LZWBinFa() {

	    fa = gyoker;

	  }

	  public void egyBitFeldolg(char b) {

	    if (b == '0') {
	      
	      if (fa.nullasGyermek() == null)
	      {
	        Csomopont uj = new Csomopont('0');
	        fa.ujNullasGyermek(uj);
	        fa = gyoker;
	      } else
	      {
	        fa = fa.nullasGyermek();
	      }
	    }
	     else {
	      if (fa.egyesGyermek() == null) {
	        Csomopont uj = new Csomopont('1');
	        fa.ujEgyesGyermek(uj);
	        fa = gyoker;
	      } else {
	        fa = fa.egyesGyermek();
	      }
	    }
	  }

	  public void kiir() {

	    melyseg = 0;

	    kiir(gyoker, new java.io.PrintWriter(System.out), melyseg);

	  }

	  public void kiir(java.io.PrintWriter os) {
	    melyseg = 0;
	    kiir(gyoker, os , melyseg);
	  }


	  class Csomopont {

	    public Csomopont(char betu) {
	      this.betu = betu;
	      balNulla = null;
	      jobbEgy = null;
	    }

	    ;
	    

	    public Csomopont nullasGyermek() {
	      return balNulla;
	    }

	    public Csomopont egyesGyermek() {
	      return jobbEgy;
	    }

	    public void ujNullasGyermek(Csomopont gy) {
	      balNulla = gy;
	    }

	    public void ujEgyesGyermek(Csomopont gy) {
	      jobbEgy = gy;
	    }

	    public char getBetu() {
	      return betu;
	    }

	    private char betu;
	   
	    private Csomopont balNulla = null;
	    private Csomopont jobbEgy = null;
	   
	  };

	  private Csomopont fa = null;
	  private int melyseg, atlagosszeg, atlagdb;
	  private double szorasosszeg;
	  
	  public void kiir(Csomopont elem, java.io.PrintWriter os , int melyseg) {
	   
	    if (elem != null) {
	      ++melyseg;
	      kiir(elem.egyesGyermek(), os, melyseg);
	      for (int i = 0; i < melyseg; ++i) {
	        os.print("---");
	      }
	      os.print(elem.getBetu());
	      os.print("(");
	      os.print(melyseg - 1);
	      os.println(")");
	      kiir(elem.nullasGyermek(), os, melyseg);
	      --melyseg;
	    }
	  }

	  protected Csomopont gyoker = new Csomopont('/');
	  int maxMelyseg;
	  double atlag, szoras;


	  public int getMelyseg() {
	    melyseg = maxMelyseg = 0;
	    rmelyseg(gyoker);
	    return maxMelyseg - 1;
	  }

	  public double getAtlag() {
	    melyseg = atlagosszeg = atlagdb = 0;
	    ratlag(gyoker);
	    atlag = ((double) atlagosszeg) / atlagdb;
	    return atlag;
	  }

	  public double getSzoras() {
	    atlag = getAtlag();
	    szorasosszeg = 0.0;
	    melyseg = atlagdb = 0;

	    rszoras(gyoker);

	    if (atlagdb - 1 > 0) {
	      szoras = Math.sqrt(szorasosszeg / (atlagdb - 1));
	    } else {
	      szoras = Math.sqrt(szorasosszeg);
	    }

	    return szoras;
	  }

	  public void rmelyseg(Csomopont elem) {
	    if (elem != null) {
	      ++melyseg;
	      if (melyseg > maxMelyseg) {
	        maxMelyseg = melyseg;
	      }
	      rmelyseg(elem.egyesGyermek());
	      rmelyseg(elem.nullasGyermek());
	      --melyseg;
	    }
	  }

	  public void ratlag(Csomopont elem) {
	    if (elem != null) {
	      ++melyseg;
	      ratlag(elem.egyesGyermek());
	      ratlag(elem.nullasGyermek());
	      --melyseg;
	      if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
	        ++atlagdb;
	        atlagosszeg += melyseg;
	      }
	    }
	  }

	  public void rszoras(Csomopont elem) {
	    if (elem != null) {
	      ++melyseg;
	      rszoras(elem.egyesGyermek());
	      rszoras(elem.nullasGyermek());
	      --melyseg;
	      if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
	        ++atlagdb;
	        szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
	      }
	    }
	  }


	  public static void usage() {
	    System.out.println("Usage: lzwtree in_file -o out_file");
	  }


	  public static void main(String args[]) {
	 
	    if (args.length != 3) {
	      usage();
	      System.exit(-1);
	    }
	    
	    String inFile = args[0];
	   
	    if (!"-o".equals(args[1])) {
	      usage();
	      System.exit(-1);
	    }

	    try {

	      java.io.FileInputStream beFile =
	              new java.io.FileInputStream(new java.io.File(args[0]));
	      
	      java.io.PrintWriter kiFile =
	              new java.io.PrintWriter(
	              new java.io.BufferedWriter(
	              new java.io.FileWriter(args[2])));

	      
	      byte[] b = new byte[1];
	      
	      LZWBinFa binFa = new LZWBinFa();

	      
	      while (beFile.read(b) != -1) {
	        if (b[0] == 0x0a) {
	          break;
	        }
	      }
	      
	      boolean kommentben = false;

	      while (beFile.read(b) != -1) {

	        if (b[0] == 0x3e) {
	          kommentben = true;
	          continue;
	        }

	        if (b[0] == 0x0a) {
	          kommentben = false;
	          continue;
	        }

	        if (kommentben) {
	          continue;
	        }

	        if (b[0] == 0x4e)
	        {
	          continue;
	        }

	        for (int i = 0; i < 8; ++i) {
	          if ((b[0] & 0x80) != 0)
	          {
	            binFa.egyBitFeldolg('1');
	          } else 
	          {
	            binFa.egyBitFeldolg('0');
	          }
	          b[0] <<= 1;
	        }

	      }

	     
	      binFa.kiir(kiFile);

	      kiFile.println("depth = " + binFa.getMelyseg());
	      kiFile.println("mean = " + binFa.getAtlag());
	      kiFile.println("var = " + binFa.getSzoras());

	      kiFile.close();
	      beFile.close();

	    } catch (java.io.FileNotFoundException fnfException) {
	      fnfException.printStackTrace();
	    } catch (java.io.IOException ioException) {
	      ioException.printStackTrace();
	    }

	  }
	}