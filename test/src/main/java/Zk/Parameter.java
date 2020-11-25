package Zk;
/*生成大素数q和他的一个生成元g*/
import java.util.*;
import java.math.BigInteger;

public class Parameter {

    //将字符串格式转换成十进制表示形式的大整数
    public static BigInteger ONE=new BigInteger("1");
    public static BigInteger TWO=new BigInteger("2");

    public static void main(String[] args) {
        ArrayList<BigInteger> massage = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入需要验证属性的个数：");
        int num = input.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.println("请输入待证明消息m：");
            String m = input.next();
            //将m转换为BigInteger类型
            BigInteger bigm = new BigInteger(m);
            massage.add(bigm);
        }
        //生成g p v
        ArrayList<BigInteger> parameter = parametergen(2 * num);
        BigInteger p=parameter.get(0);
        System.out.println("p="+p.toString());
    }
        /*程序开始时间
        long startTime=System.currentTimeMillis();

        Scanner input=new Scanner(System.in);
        System.out.println("请输入需要生成承诺的个数：");
        int num = input.nextInt();
        //生成公共参数g,p
        ArrayList<BigInteger> parameter=parametergen(num);//需要填入素数的位数
        BigInteger p=parameter.get(0);
        System.out.println("p="+p.toString());
        System.out.println("请输入公共参数文件的存储位置：");
        String parakeep=input.next();//d:/BIDtext/parameter.txt
        //将公共参数以顺序p,g,v写入文件
        for (int i = 0 ; i < 2*num ; i++) {
            BigInteger g = parameter.get(i+1);
            BigInteger v = parameter.get(i+2);
            i++;
            writepara(parakeep, g);
            System.out.println("g=" + g.toString());
            writepara(parakeep,v);
            System.out.println("v=" + v.toString());
        }
        //获取程序结束时间
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间：="+(endTime-startTime)+"ms");

    }*/

    //生成公共参数
    public static ArrayList<BigInteger> parametergen(int num){
        //nbit表示生成素数的位长 ,num表示生成q阶生成元的个数。
        Scanner input=new Scanner(System.in);
        System.out.println("请输入需要生成g/p的位数：");
        int nbit = input.nextInt();
        ArrayList<BigInteger> parameter=new ArrayList<>();
        //生成素数p,也是模数p,并把p添加到列表中
        BigInteger p=getPrime(nbit, 40, new Random());
        parameter.add(p);
        System.out.println("p=" + p.toString());
        //在Zp中生成num个生成元，
        //先随机生成一个大整数g，其范围是[0,p-1]
        for (int i = 0 ; i < num; i++ ) {
            BigInteger g = randNum(p, new Random());
            //p1=(p-1)/2
            BigInteger p1 = p.subtract(ONE).divide(TWO);
            while (!g.modPow(p1, p).equals(ONE)) {//modPow为取幂且模，在这里为g^m1 mod m
                if (g.modPow(p1.multiply(TWO), p).equals(ONE)) {
                    g = g.modPow(TWO, p);
                } else {
                    g = randNum(p, new Random());
                }
            }
            parameter.add(g);
            System.out.println("g=" + g.toString());
        }

        return parameter;

    }

    //随机生成一个大素数
    public static BigInteger getPrime(int bitlen, int pro, Random rand ) {
        /* 参数解释
         * bitlen--bitlength为生成素数的位数
         * pro--probability为成功生成一个素数的概率，该概率大于(1-(1/2)^pro)
         * rand--random为随机比特，用其选择进行素数测试的候选数
         */

        //随机生成一个大整数
        BigInteger bigint=new BigInteger(bitlen, pro, rand);
        //p=bigint*2+1
        BigInteger p = bigint.multiply(TWO).add(BigInteger.ONE);

        //判断p是否为素数，当为素数的概率大于(1-(1/2)^pro)时，停止循环
        while(!p.isProbablePrime(pro)) {
            bigint=new BigInteger(bitlen, pro, rand);
            p = bigint.multiply(TWO).add(BigInteger.ONE);
        }

        //返回素数p;
        return p;
    }

    //随机生成一个大整数，且该大整数的范围是[0,N-1]
    public static BigInteger randNum(BigInteger N, Random rand) {
        return new BigInteger(N.bitLength()+100, rand).mod(N);
    }

    //将公共参数写入文件
    /*public static void writepara(String file, BigInteger x) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(String.valueOf(x)+"\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

}
