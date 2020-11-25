package Zk.contract;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fisco.bcos.asset.contract.Asset;
import org.fisco.bcos.asset.contract.Asset.RegisterEventEventResponse;
import org.fisco.bcos.asset.contract.Asset.TransferEventEventResponse;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static Zk.Challenge.computeC;
import static Zk.Commit.commit;
import static Zk.ComputeY.computeY;
import static Zk.Parameter.parametergen;
import static Zk.Response.computeR;
import static Zk.Verify.restructR;
import static Zk.Verify.verify;

public class TestSolidity {
    static Logger logger = LoggerFactory.getLogger( TestSolidity.class);

    private Web3j web3j;

    private Credentials credentials;

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("address", address);
        final Resource contractResource = new ClassPathResource("contract.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }

    public String loadAssetAddr() throws Exception {
        // load Asset contact address from contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load Did contract address failed, please deploy it first. ");
        }
        logger.info(" load Did address from contract.properties, address is {}", contractAddress);
        return contractAddress;
    }

    public void initialize() throws Exception {

        // init the Service
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        Web3j web3j = Web3j.build(channelEthereumService, 1);

        // init Credentials
        Credentials credentials = GenCredential.create();

        setCredentials(credentials);
        setWeb3j(web3j);

        logger.debug(" web3j is " + web3j + " ,credentials is " + credentials);
    }

    private static BigInteger gasPrice = new BigInteger("30000000");
    private static BigInteger gasLimit = new BigInteger("30000000");

    public void deployTestAndRecordAddr() {

        try {
            Didtest didtest = Didtest.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
            System.out.println(" deploy Did success, contract address is " + didtest.getContractAddress());

            recordAssetAddr(didtest.getContractAddress());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy Did contract failed, error message is  " + e.getMessage());
        }
    }

    public void  addToParameterTest(BigInteger _newParameter){
        try {
            String contractAddress = loadAssetAddr();

            Didtest didtest = Didtest.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            Tuple1<BigInteger> result = didtest.addToParameter(_newParameter).send();
            if (result.getValue().compareTo(new BigInteger("0")) == 0) {
                System.out.printf(" addToParameter  successful \n");
            } else {
                System.out.printf("addToParameter  failed \n");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" addToParameter, error message is {}", e.getMessage());

            System.out.printf(" addToParameter failed, error message is %s\n", e.getMessage());
        }
    }

    public void  getParameterTest() {
        try {
            String contractAddress = loadAssetAddr();
            Didtest didtest = Didtest.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            List<Type> result = didtest.getParameter();
            System.out.printf("finsh");
            } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();

            logger.error(" getParameterTest exception, error message is {}", e.getMessage());
            System.out.printf(" getParameterTest failed, error message is %s\n", e.getMessage());
        }
    }

        public static void main (String[] args) throws Exception {

            TestSolidity client = new TestSolidity();
            client.initialize();
            client.deployTestAndRecordAddr();

            ArrayList<BigInteger> massage = new ArrayList<>() ;
            Scanner input = new Scanner(System.in);
            System.out.println("请输入需要验证属性的个数：");
            int num = input.nextInt();
            //生成g p v
            ArrayList<BigInteger> parameter = parametergen(2*num) ;
            for (int i = 0; i < num; i++) {
                client.addToParameterTest(parameter.get(i));
            }
            client.getParameterTest();

            System.exit(0);
        }
    }

}
