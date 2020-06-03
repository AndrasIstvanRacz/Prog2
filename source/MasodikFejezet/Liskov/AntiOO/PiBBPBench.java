
public class PiBBPBench {
    
    public static double d16Sj(int d, int j) {
        
        double d16Sj = 0.0d;
        
        for(int k=0; k<=d; ++k)
            d16Sj += (double)n16modk(d-k, 8*k + j) / (double)(8*k + j);
        
        
        
        return d16Sj - Math.floor(d16Sj);
    }
    
    public static long n16modk(int n, int k) {
        
        int t = 1;
        while(t <= n)
            t *= 2;
        
        long r = 1;
        
        while(true) {
            
            if(n >= t) {
                r = (16*r) % k;
                n = n - t;
            }
            
            t = t/2;
            
            if(t < 1)
                break;
            
            r = (r*r) % k;
            
        }
        
        return r;
    }
    
    public static void main(String args[]) {
        
        double d16Pi = 0.0d;
        
        double d16S1t = 0.0d;
        double d16S4t = 0.0d;
        double d16S5t = 0.0d;
        double d16S6t = 0.0d;
        
        int jegy = 0;
        
        long delta = System.currentTimeMillis();
        
        for(int d=1000000; d<1000001; ++d) {
            
            d16Pi = 0.0d;
            
            d16S1t = d16Sj(d, 1);
            d16S4t = d16Sj(d, 4);
            d16S5t = d16Sj(d, 5);
            d16S6t = d16Sj(d, 6);
            
            d16Pi = 4.0d*d16S1t - 2.0d*d16S4t - d16S5t - d16S6t;
            
            d16Pi = d16Pi - Math.floor(d16Pi);
            
            jegy = (int)Math.floor(16.0d*d16Pi);
            
        }
        
        System.out.println(jegy);
        delta = System.currentTimeMillis() - delta;
        System.out.println(delta/1000.0);
    }
} 