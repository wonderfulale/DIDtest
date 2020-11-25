package Zk;

import java.math.BigInteger;;
import java.util.ArrayList;


/*计算r = v - cx(mod p)*/
public class Response {
    public static ArrayList<BigInteger> computeR(ArrayList<BigInteger> parameter , ArrayList<BigInteger> c, ArrayList<BigInteger> m , int num) {

        ArrayList<BigInteger> v = new ArrayList<>();
        ArrayList<BigInteger> r = new ArrayList<>();
        BigInteger p = parameter.get(0);
        //获取 v
        for (int i = 1; i < 2 * num; i++) {
            BigInteger vn = parameter.get(i+1);
            v.add(vn);
            i++;

        }
        //计算响应r = v - cx(mod p)
        for (int i = 0; i < num; i++) {
            //计算c*x
            BigInteger bigcx = c.get(i).multiply(m.get(i));
            //计算v - cx
            BigInteger bigv = v.get(i).subtract(bigcx);
            BigInteger bigr = bigv.mod(p);
            r.add(bigr);
            System.out.println("r=" + bigr.toString());
        }
        return r;
    }
}


