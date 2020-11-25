package Zk;

import java.math.BigInteger;
import java.util.ArrayList;


public class Verify {

    public static ArrayList<BigInteger> restructR(ArrayList<BigInteger> parameter, ArrayList<BigInteger> c, ArrayList<BigInteger> y, ArrayList<BigInteger> r, int num) {
        //重构t = g^r*y^c
        ArrayList<BigInteger> rt = new ArrayList<>();
        ArrayList<BigInteger> g = new ArrayList<>();
        BigInteger p = parameter.get(0);
        //获取g
        for (int i = 1; i < 2 * num; i++) {
            BigInteger gn = parameter.get(i);
            g.add(gn);
            i++;
        }
        // BigInteger ONE=new BigInteger("1");
        for (int i = 0; i < num; i++) {
            //计算g^r
            BigInteger gr = g.get(i).modPow(r.get(i), p);
            //计算y^c
            BigInteger yc = y.get(i).modPow(c.get(i), p);
            BigInteger bigrt = gr.multiply(yc);
            rt.add(bigrt);
            System.out.println("gr=" + g.get(i).toString());
            System.out.println("yc=" + yc.toString());
        }
        return rt;
    }

    public static int verify(ArrayList<BigInteger> t, ArrayList<BigInteger>  rt, int num) {
        for (int i = 0; i < num; i++) {
            if (t.get(i) == rt.get(i)) {
                System.out.println("验证成功 " + i);
            } else {
                System.out.println("验证失败 " + i);
            }
        }
        return 0;
    }

}