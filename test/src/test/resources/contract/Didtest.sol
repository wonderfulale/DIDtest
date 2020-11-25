pragma solidity ^0.4.0;
 
contract Parameter{
     
    uint[] parameter;
    function getParameter() public view returns(uint[]) {
        return parameter;
    }
    function addToParameter(uint _newParameter)  public
    {
        parameter.push(_newParameter);
    }
    
}
