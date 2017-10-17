import java.math.BigInteger;
import java.util.Scanner;


public class InfiniteSequence {
    public long findSequence(String A) {
        long ans=0;
        BigInteger c= new BigInteger("1");
        String b="1";
        while (true)
        {
            long temp=b.indexOf(A);
            if(temp!=-1)
            {
                ans=temp+1;
                break;
            }
            else
            {
                for(int i=0;i<10000;i++) {
                    c = c.add(BigInteger.ONE);
                    b += c.toString();
                }
            }
        }



        return ans;
    }
}
