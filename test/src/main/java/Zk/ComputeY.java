package Zk;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class ComputeY {
    public static ArrayList<BigInteger> computeY(ArrayList<BigInteger> parameter , ArrayList<BigInteger> m, int num){
        /*parameter存储p/g/v
         * num--需要生成承诺的个数*/
        Scanner input = new Scanner(System.in);
        ArrayList<BigInteger> g = new ArrayList<>();
        ArrayList<BigInteger> y = new ArrayList<>() ;
        BigInteger p = parameter.get(0);
        //获取g
        for (int i = 1; i < 2*num ; i++){
            BigInteger gn = parameter.get(i);
            g.add(gn);
            //y = g^m
            i++;
        }
        //计算承诺y=g^m
        for(int i = 0; i < num ; i++){
            BigInteger yn = g.get(i).modPow(m.get(i), p);
            y.add(yn);
        }
        return y;

    }
}
