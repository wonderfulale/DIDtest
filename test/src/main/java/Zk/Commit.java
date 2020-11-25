package Zk;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*计算承诺t = g^v*/
public class Commit{
    public static ArrayList<BigInteger> commit(ArrayList<BigInteger> parameter , int num){
        /*parameter存储p/g/v
         * num--需要生成承诺的个数*/
        ArrayList<BigInteger> g = new ArrayList<>();
        ArrayList<BigInteger> v = new ArrayList<>() ;
        ArrayList<BigInteger> t = new ArrayList<>() ;
        ArrayList<BigInteger> y = new ArrayList<>() ;
        ArrayList<BigInteger> massage = new ArrayList<>() ;
        ArrayList<BigInteger> c = new ArrayList<>() ;
        ArrayList<BigInteger> r = new ArrayList<>() ;
        BigInteger p = parameter.get(0);
        //获取g , v ,计算承诺t
        for (int i = 1; i < 2*num ; i++){
            BigInteger gn = parameter.get(i);
            g.add(gn);
            i++;
            BigInteger vn = parameter.get(i);
            v.add(vn);
            BigInteger comt = gn.modPow(vn , p);
            t.add(comt);
            System.out.println("t: " + comt.toString());
        }

        return t;

    }
}
