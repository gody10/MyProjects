var io = require('socket.io')(process.env.PORT || 52300);
const net = require("net");
const ABIs = require('./ABIs.json');
var test;
var CardMap = [];
const server = net.createServer(socket => {

    async function ExecuteContract(contract,name){

        var token = await contract.methods.balanceOf('0xDD5B26f0ecb1F7d501a77168752Dc7e72D04ea54').call(); 
        CardMap[name] = token
    }
     
     socket.on("data", data => { //when you accept a socket from java server
        console.log("Mpika");
        const WalletProvider = require("@truffle/hdwallet-provider");
        const androidData = data.toString().split(":");
        console.log(androidData);
        const mnemonicphrase = androidData[1];
        const provider = new WalletProvider(mnemonicphrase, // 12 word code for
        'https://rinkeby.infura.io/v3/59a3349980734b09b5a8bca63ecebfd0'); //rinkeby address from infura
        const Web3 = require('web3');
        const web3 = new Web3();
        web3.setProvider(provider);
        const ABI1 = ABIs["HUMAN"]; //human
        const contractInstance1 = new web3.eth.Contract(ABI1, '0xc1e3931F6c6aE3F5D4570522CeaF4D252fabfD48');
        ExecuteContract(contractInstance1,"Human");
        const ABI2 = ABIs["WOODELF"]; //Woodelf
        const contractInstance2 = new web3.eth.Contract(ABI2, '0x425718aEDf0Ec927dcCeC7CBf7D6d44FDE8df930');
        ExecuteContract(contractInstance2,"Woodelf");
        const ABI3 = ABIs["ELF"]; //Elf
        const contractInstance3 = new web3.eth.Contract(ABI3, '0xa2558C970b3A53676FAA64962ac6d3C9047decD9');
        ExecuteContract(contractInstance3,"Elf");
        const ABI4 = ABIs["DWARF"]; //Dwarf
        const contractInstance4 = new web3.eth.Contract(ABI4, '0xB104CeD90061144B1b865810EC442F3A5d8579D3');
        ExecuteContract(contractInstance4,"Dwarf");
        const ABI5 = ABIs["HEALER"]; // healer
        const contractInstance5 = new web3.eth.Contract(ABI5, '0x235142235538b6323DB8FCAE08b56311520a1829');
        ExecuteContract(contractInstance5,"Healer");
        const ABI6 = ABIs["TREEMAN"]; // treeman
        const contractInstance6 = new web3.eth.Contract(ABI6, '0x175917e5578D8f93Da73b75d55676b56587C0234');
        ExecuteContract(contractInstance6,"Treeman");
        const ABI7 = ABIs["SLIME"]; // slime
        const contractInstance7 = new web3.eth.Contract(ABI7, '0x16008dECf9c08Ada6c70a5a7B3c93707173d1736');
        ExecuteContract(contractInstance7,"Slime");
        const ABI8 = ABIs["DOG"]; // dog
        const contractInstance8 = new web3.eth.Contract(ABI8, '0xBBadCBC4dDba7705e6e5c88119CB0034ACFC8840');
        ExecuteContract(contractInstance8,"Dog");
        const ABI9 = ABIs["DEMON"]; // demon
        const contractInstance9 = new web3.eth.Contract(ABI9, '0x1f462E8bf3Df98C3313DADEba5BE7d81Dc6650f3');
        ExecuteContract(contractInstance9,"Demon");
        const ABI10 = ABIs["PRIEST"]; // priest
        const contractInstance10 = new web3.eth.Contract(ABI10, '0x12C29299E3DDc1Dc7decA054887B5CbBd4FDdDB1');
        ExecuteContract(contractInstance10,"Priest");
        const ABI11 = ABIs["HEAL"]; // heal
        const contractInstance11 = new web3.eth.Contract(ABI11, '0x144701f5D552352B678f3bF1983443eEB6d835EC');
        ExecuteContract(contractInstance11,"Heal");
        const ABI12 = ABIs["FIREBALL"]; // fireball
        const contractInstance12 = new web3.eth.Contract(ABI12, '0xd08c3BAC17e6E50EE6B467Dad7BbF40163616ec0');
        ExecuteContract(contractInstance12,"Fireball");
        const ABI13 = ABIs["EXODIA"]; // exodia
        const contractInstance13 = new web3.eth.Contract(ABI13, '0x4C298F128571B0E4adbc90B9C75c5b531f15B46e');
        ExecuteContract(contractInstance13,"Exodia");
        const ABI14 = ABIs["DIABLO"]; // diablo
        const contractInstance14 = new web3.eth.Contract(ABI14, '0x312e077B3b3Ef353c285caC9c9251e4CC3239A4f');
        ExecuteContract(contractInstance14,"Diablo");
        const ABI15 = ABIs["SUNJINWOO"]; // sunjinwoo
        const contractInstance15 = new web3.eth.Contract(ABI15, '0x8C479ABEac295c12992d8ad61fF37c80F24AF013');
        ExecuteContract(contractInstance15,"Sunjinwoo");
        const ABI16 = ABIs["HYGEIA"]; // hygeia
        const contractInstance16 = new web3.eth.Contract(ABI16, '0x0Db746b2E4F504332A814DF079eb012a8270f194');
        ExecuteContract(contractInstance16,"Hygieia");

     });
});
 console.log("JsServer is Open");
 server.listen(7667,"192.168.2.8")
 
console.log('GameServer has Started');

io.on('connection',function(socket){ //when you accept a socket from unity
    console.log("Connection Made!");

    console.log("Sending data");
    for (const [key, value] of Object.entries(CardMap)) {
        socket.emit('send',{WalletMap : key});
        socket.emit('send',{WalletMap : value});
        console.log("I have sent " + value + key + "s!");
      }
    


    socket.on('disconnect',function(socket){
        console.log("Unity Disconnected");
    })
});







