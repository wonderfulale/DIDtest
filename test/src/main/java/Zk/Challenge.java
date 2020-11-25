package Zk;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Challenge {
    public static ArrayList<BigInteger> computeC(ArrayList<BigInteger> parameter , ArrayList<BigInteger> t , ArrayList<BigInteger> y , int num){
        /*parameter存储p/g/v
         * num--需要生成承诺的个数*/
        ArrayList<BigInteger> g = new ArrayList<>();
        ArrayList<BigInteger> c = new ArrayList<>();
        //获取g , v ,计算承诺t
        for (int i = 1; i < 2*num ; i++){
            BigInteger gn = parameter.get(i);
            g.add(gn);
            i++;
        }
        //将 y , g , t 转换为string类型,拼接成一个字符串,调用hashSHA计算挑战值
        for(int i = 0 ; i < num ; i++) {
            String gs = String.valueOf(g.get(i));
            String ys = String.valueOf(y.get(i));
            String ts = String.valueOf(t.get(i));
            String gyts = gs + ys + ts;
            try {
                c.add(hashSHA(gyts));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return c;

    }
    public static BigInteger hashSHA(String ch) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashInBytes = md.digest(ch.getBytes(StandardCharsets.UTF_8));
        //byte转化为String
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        String s = sb.toString();
        //String 转换成  BigInteger
        BigInteger bighash;
        bighash = new BigInteger(s,16);
        return bighash ;
    }
}
