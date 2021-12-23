library ieee;
use ieee.std_logic_1164.all;
USE ieee.numeric_std.all;

package basic_components is
	component alu_1_bit 
    port (a,b,Cin,ainvert, binvert  : in std_logic;
	 operation : in std_logic_vector ( 1 downto 0);
	 Cout,result : out std_logic);
end component;
    component d2
        port(in1, in2: in std_logic;
            out1 : out  std_logic);
    end component;

    component O2
       port (in1, in2: in std_logic; out1: out std_logic);     
    end component;

    component XO2
       port (in1, in2: in std_logic; out1: out std_logic);     
    end component;

    component MUX2T1
        port (in1, in2, s: in std_logic; out1: out std_logic);
    end component;

    component MUX4T1
        port(outAND, outOR, sum, outXOR: in std_logic;
		  operation: in std_logic_vector(1 downto 0); 
		  Result: out std_logic);
    end component;

    component AddSub
        port (in1, in2, Cin: in std_logic; out1, Cout: out std_logic);
    end component;
	 
	 component ControlCircuit
		port (
            opcode      :in std_logic_vector (2 downto 0);
            signala, signalb : out  std_logic;
            operation : out std_logic_vector(1 downto 0);
            CarryIn : out std_logic);               
end component;
	 

end package basic_components;

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;


entity ControlCircuit is 
    port (
            opcode      :in std_logic_vector (2 downto 0);
            signala, signalb : out  std_logic;
            operation : out std_logic_vector(1 downto 0);
            CarryIn : out std_logic);               
end ControlCircuit;

architecture model_conc9 of ControlCircuit is    
begin
 process(opcode)
 begin

case opcode is 

    --AND--
    when "000"=>
        operation <= "00";
        signala   <= '0';
        signalb   <= '0';
        CarryIn  <= '0';

    --OR--
    when "001" =>
        operation <= "01";
        signala   <= '0';
        signalb      <= '0';
        CarryIn  <= '0';

    --ADD--         
    when "011" =>
        operation <= "10";
        signala   <= '0';
        signalb      <= '0';
        CarryIn  <= '0';

    --SUB--
    when "010" =>
        operation <= "10";
        signala   <= '0';
        signalb      <='1';
        CarryIn  <= '1';

    --NOR--
    when "101"=>
        operation <= "00";
        signala   <= '1';
        signalb      <= '1';
        CarryIn  <= '0';

    --xor
    when "100" =>
        operation <= "11";
        signala   <= '0';
        signalb      <= '0';
        CarryIn  <= '0';

    --Adiafores times--
when others =>
        operation <= "00";
        signala   <= '0';
        signalb      <= '0';
        CarryIn  <= '0';
    end case;
    end process;
end model_conc9;


library ieee;
use ieee.std_logic_1164.all; --COMPONENT AND
    entity d2 is 
        port (in1, in2: in std_logic; out1 : out std_logic); 
    end d2;

    architecture model of d2 is 
    begin 
        out1 <= in1 and in2;
    end model;
library ieee;
use ieee.std_logic_1164.all; --COMPONENT OR
    entity O2 is
        port (in1, in2: in std_logic; out1: out std_logic);
    end O2;
library ieee;
use ieee.std_logic_1164.all;
    architecture model2 of O2 is 
    begin 
        out1 <= in1 or in2;
    end model2;
library ieee;
use ieee.std_logic_1164.all; --COMPONENT XOR
    entity XO2 is
        port (in1, in2: in std_logic; out1: out std_logic);
    end XO2;

    architecture model3 of XO2 is 
    begin 
        out1 <=   in1 xor in2;
    end model3;
library ieee;
use ieee.std_logic_1164.all; --MULTIPLEXER 2 TO 1
    entity MUX2T1 is 
        port (in1, in2,s: in std_logic; out1: out std_logic);
    end MUX2T1;

    architecture model4 of MUX2T1 is 
    begin 
        out1 <= in1 when s = '0' else
                in2;
    end model4;
library ieee;
use ieee.std_logic_1164.all; --MULTIPLEXER 4 TO 1
    entity MUX4T1 is
        port(outAND, outOR, sum, outXOR: in std_logic;
		  operation: in std_logic_vector(1 downto 0); 
		  Result: out std_logic);
    end MUX4T1;

    architecture model5 of MUX4T1 is 
    begin
        with operation select
        Result<= outAND when "00",
                 outOR  when "01",
              sum    when "10",
                   outXOR when OTHERS;
    end model5; 
library ieee;
use ieee.std_logic_1164.all;
    entity AddSub is 
        port(in1, in2, Cin: in std_logic; out1, Cout: out std_logic);
    end AddSub;

    architecture model6 of AddSub is 
    begin 
        out1 <= (in1 and not in2 and not Cin) or (not in1 and in2 and not Cin)
                 or (Cin and not in1 and not in2) or (in1 and in2 and Cin);
        Cout <= (in1 and in2 ) or (in2 and Cin) or (Cin and in1);
	end model6;
	
library ieee;
use ieee.std_logic_1164.all;
use work.basic_components.all;
USE ieee.numeric_std.all;


entity alu_1_bit is --1-bit 
    port (a, b,Cin,ainvert, binvert  : in std_logic;
	 operation : in std_logic_vector ( 1 downto 0);
	 Cout,result : out std_logic);
end alu_1_bit;

architecture structural of alu_1_bit is 
    signal aRes, bRes, OrRes, AndRes, AddRes, XorRes : std_logic;
	
begin
    stage1: MUX2T1 port map(a, not a, ainvert, aRes);
    stage2: MUX2T1 port map (b, not b, binvert, bRes);
    stage3: d2 port map(aRes, bRes, AndRes);
    stage4: O2 port map(aRes, bRes, OrRes);
    stage5: AddSub port map(aRes, bRes, Cin, AddRes,Cout);
    stage6: XO2 port map(aRes, bRes, XorRes);
    stage7: MUX4T1 port map(AndRes, OrRes, AddRes, XorRes, operation, result);
	 
end structural;