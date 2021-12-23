library ieee;
use ieee.std_logic_1164.all;
use work.basic_components.all;
USE ieee.numeric_std.all;

Package alu16_package is
	Component alu16
		port (
			  a:          in  std_logic_vector (15 downto 0);
			  b:          in  std_logic_vector (15 downto 0);
			  opcode:     in  std_logic_vector (2 downto 0) :="111";
			  result:     out std_logic_vector (15 downto 0);
			  carryout:   out std_logic
		 );
	end component;
end package;

library ieee;
use ieee.std_logic_1164.all;
use work.basic_components.all;
USE ieee.numeric_std.all;

entity alu16 is
    port (
        a:          in  std_logic_vector (15 downto 0);
        b:          in  std_logic_vector (15 downto 0);
        opcode:     in  std_logic_vector (2 downto 0) :="111";
        result:     out std_logic_vector (15 downto 0);
        carryout:   out std_logic
    );
end entity;

architecture wow of alu16 is

  

    signal ainvert:     std_logic;
    signal binvert:     std_logic;
    signal operation:   std_logic_vector (1 downto 0);
    signal carry:       std_logic_vector (16 downto 0);
begin
S1: ControlCircuit port map (opcode,ainvert,binvert,operation,carry(0));
S2: alu_1_bit port map (a(0),b(0),carry(0),ainvert,binvert,operation,carry(1),result(0));
S3 :alu_1_bit port map (a(1),b(1),carry(1),ainvert,binvert,operation,carry(2),result(1));
S4: alu_1_bit port map (a(2),b(2),carry(2),ainvert,binvert,operation,carry(3),result(2));
S5: alu_1_bit port map (a(3),b(3),carry(3),ainvert,binvert,operation,carry(4),result(3));
S6: alu_1_bit port map (a(4),b(4),carry(4),ainvert,binvert,operation,carry(5),result(4));
S7: alu_1_bit port map (a(5),b(5),carry(5),ainvert,binvert,operation,carry(6),result(5));
S8: alu_1_bit port map (a(6),b(6),carry(6),ainvert,binvert,operation,carry(7),result(6));
S9: alu_1_bit port map (a(7),b(7),carry(7),ainvert,binvert,operation,carry(8),result(7));
S10: alu_1_bit port map (a(8),b(8),carry(8),ainvert,binvert,operation,carry(9),result(8));
S11: alu_1_bit port map (a(9),b(9),carry(9),ainvert,binvert,operation,carry(10),result(9));
S12: alu_1_bit port map (a(10),b(10),carry(10),ainvert,binvert,operation,carry(11),result(10));
S13: alu_1_bit port map (a(11),b(11),carry(11),ainvert,binvert,operation,carry(12),result(11));
S14: alu_1_bit port map (a(12),b(12),carry(12),ainvert,binvert,operation,carry(13),result(12));
S15: alu_1_bit port map (a(13),b(13),carry(13),ainvert,binvert,operation,carry(14),result(13));
S16: alu_1_bit port map (a(14),b(14),carry(14),ainvert,binvert,operation,carry(15),result(14));
S17: alu_1_bit port map (a(15),b(15),carry(15),ainvert,binvert,operation,carry(16),result(15));

	carryout <= carry(16);

end architecture;