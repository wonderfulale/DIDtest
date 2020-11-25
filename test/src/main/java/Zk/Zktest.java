package Zk;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import static Zk.Challenge.computeC;
import static Zk.Commit.commit;
import static Zk.ComputeY.computeY;
import static Zk.Parameter.parametergen;
import static Zk.Response.computeR;
import static Zk.Verify.restructR;
import static Zk.Verify.verify;
//import static Zk.Verify.verify;

public class Zktest {
    public static void main(String[] args) {
        ArrayList<BigInteger> massage = new ArrayList<>() ;
        Scanner input = new Scanner(System.in);
        System.out.println("请输入需要验证属性的个数：");
        int num = input.nextInt();
        for(int i =0; i < num ; i++) {
            System.out.println("请输入待证明消息m：");
            String m = input.next();
            //将m转换为BigInteger类型
            BigInteger bigm = new BigInteger(m);
            massage.add(bigm);
        }
        //生成g p v
        ArrayList<BigInteger> parameter = parametergen(2*num) ;
        //计算承诺t = g^v
        ArrayList<BigInteger> t = commit(parameter,num) ;
        //计算y = g^m
        ArrayList<BigInteger> y = computeY(parameter,massage, num) ;
        //计算挑战c = H(g , y ,t )
        ArrayList<BigInteger> c = computeC(parameter, t, y, num) ;
        //计算响应r = v - cx(mod p)
        ArrayList<BigInteger> r = computeR(parameter, c, massage, num) ;
        //重构承诺t‘ = g^r*y^c
        ArrayList<BigInteger> rt = restructR(parameter, c, y, r, num) ;
        //验证 t ?= t'
        verify( t,   rt,  num);
        /*for (int i = 0; i < num; i++) {
            if (t.get(i) == rt.get(i)) {
                System.out.println("验证成功 " + i);
            } else {
                System.out.println("验证失败 " + i);
            }
        }*/
    }
}
