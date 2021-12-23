// SPDX-License-Identifier: Unlicensed

pragma solidity ^0.8.6;

import "./ERC721.sol";

contract Exodia is ERC721{
    
    
    address public owner;
    
    constructor() ERC721("Exodia", "EXD") {}

    
    function createItem(address _to) public{
        owner =_to;
        _safeMint(_to,2); // Assigns the Token to the Ethereum Address that is specified
    }
    
}