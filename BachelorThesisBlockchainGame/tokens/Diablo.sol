// SPDX-License-Identifier: Unlicensed

pragma solidity ^0.8.6;

import "./ERC721.sol";

contract Diablo is ERC721{
    
    
    address public owner;
    
    constructor() ERC721("Diablo", "DVL") {}

    
    function createItem(address _to) public{
        owner = _to;
        _safeMint(_to,1); // Assigns the Token to the Ethereum Address that is specified
    }
    
}