package Zk.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Didtest extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b5061019d806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806310ba7f4d1461005157806340f4f7b91461007e575b600080fd5b34801561005d57600080fd5b5061007c600480360381019080803590602001909291905050506100ea565b005b34801561008a57600080fd5b50610093610119565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156100d65780820151818401526020810190506100bb565b505050509050019250505060405180910390f35b600081908060018154018082558091505090600182039060005260206000200160009091929091909150555050565b6060600080548060200260200160405190810160405280929190818152602001828054801561016757602002820191906000526020600020905b815481526020019060010190808311610153575b50505050509050905600a165627a7a7230582002352ab4fe1755f862263b3fe3708a2896585147b090526ef7547c04e82c5fa60029"};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b5061019d806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806346fe5eb1146100515780638541f1a71461007e575b600080fd5b34801561005d57600080fd5b5061007c600480360381019080803590602001909291905050506100ea565b005b34801561008a57600080fd5b50610093610119565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156100d65780820151818401526020810190506100bb565b505050509050019250505060405180910390f35b600081908060018154018082558091505090600182039060005260206000200160009091929091909150555050565b6060600080548060200260200160405190810160405280929190818152602001828054801561016757602002820191906000526020600020905b815481526020019060010190808311610153575b50505050509050905600a165627a7a72305820ffbdfd66ec934ac5d6b8cf8b9a306fe7ccae63c3df83594943f7a588984c87b80029"};

    public static final String SM_BINARY = String.join("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_newParameter\",\"type\":\"uint256\"}],\"name\":\"addToParameter\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getParameter\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final String FUNC_ADDTOPARAMETER = "addToParameter";

    public static final String FUNC_GETPARAMETER = "getParameter";

    protected Didtest(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt addToParameter(BigInteger _newParameter) {
        final Function function = new Function(
                FUNC_ADDTOPARAMETER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_newParameter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void addToParameter(BigInteger _newParameter, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADDTOPARAMETER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_newParameter)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAddToParameter(BigInteger _newParameter) {
        final Function function = new Function(
                FUNC_ADDTOPARAMETER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_newParameter)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getAddToParameterInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDTOPARAMETER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public List getParameter() throws ContractException {
        final Function function = new Function(FUNC_GETPARAMETER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public static Didtest load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Didtest(contractAddress, client, credential);
    }

    public static Didtest deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Didtest.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
