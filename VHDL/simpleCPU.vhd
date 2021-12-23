LIBRARY ieee; 
USE ieee.std_logic_1164.all; 
USE ieee.std_logic_signed.all;
LIBRARY WORK;
use work.alu16_package.all;

ENTITY simpleCPU IS 
    PORT ( DIN : IN STD_LOGIC_VECTOR(15 DOWNTO 0); 
           Resetn, Clock, Run : IN STD_LOGIC; 
           Done : BUFFER STD_LOGIC; 
           BusWires : BUFFER STD_LOGIC_VECTOR(15 DOWNTO 0)); 
END simpleCPU; 

ARCHITECTURE Behavior OF simpleCPU IS 

COMPONENT regn
  GENERIC (n : INTEGER := 16); 
  PORT ( R : IN STD_LOGIC_VECTOR(n-1 DOWNTO 0); 
  Rin, Clock : IN STD_LOGIC; 
  Q : BUFFER STD_LOGIC_VECTOR(n-1 DOWNTO 0)); 
END COMPONENT;

COMPONENT upcount 
  PORT ( Clear, Clock : IN STD_LOGIC; 
  Q : OUT STD_LOGIC_VECTOR(1 DOWNTO 0));
END COMPONENT;
COMPONENT decide3to8 
	PORT ( W : IN STD_LOGIC_VECTOR(2 DOWNTO 0); 
  En : IN STD_LOGIC; 
  Y : OUT STD_LOGIC_VECTOR(0 TO 7));
END COMPONENT;
	SIGNAL X,Y,Rin,Rout : STD_LOGIC_VECTOR(0 TO 7);
	SIGNAL Clear, High, AddSub: STD_LOGIC;
	SIGNAL IRin, Ain, Gin, Gout, DINout, Overflow : STD_LOGIC;
	SIGNAL Tstep_Q : STD_LOGIC_VECTOR(1 DOWNTO 0);
	SIGNAL I,z : STD_LOGIC_VECTOR(2 DOWNTO 0);
	SIGNAL R0,R1,R2,R3,R4,R5,R6,R7 : STD_LOGIC_VECTOR(15 DOWNTO 0);
	SIGNAL A, Sum, G : STD_LOGIC_VECTOR(15 DOWNTO 0);
	SIGNAL IR : STD_LOGIC_VECTOR(1 TO 9);
	SIGNAL Sel : STD_LOGIC_VECTOR(1 TO 10);
-- components & signals 
BEGIN 
  High <= '1'; 
  Clear <= (NOT(Resetn) OR Done OR (NOT(Run)AND NOT(TStep_Q(1)) AND NOT(TStep_Q(0))))OR Overflow;
  Tstep: upcount PORT MAP (Clear, Clock, Tstep_Q);
  I <= IR(1 TO 3);
  decX: decide3to8 PORT MAP (IR(4 TO 6), High, X);
  decY: decide3to8 PORT MAP (IR(7 TO 9), High, Y);
	-- Instruction Table
	--  000: mv			Rx,Ry
	--  001: mvi		Rx,#D
	--  010: and        Rx,Ry	
	--  011: or         Rx,Ry	
	--  100: add		Rx,Ry				: Rx <- [Rx] + [Ry]
	--  101: sub		Rx,Ry				: Rx <- [Rx] - [Ry]
	--  110: xor        Rx,Ry	
	--  111: not        Rx,Ry
	-- 	OPCODE format: III XXX YYY, where 
	-- 	III = instruction, XXX = Rx, and YYY = Ry. For mvi,
	-- 	a second word of data is loaded from DIN
  controlsignals: PROCESS (Tstep_Q, I, X, Y) 
  BEGIN 
	z<="000";
	Done<='0';
	Ain<='0';
	Gin<='0';
	Gout<='0';
	AddSub<='0';
	IRin<='0';
	DINout<='0';
	Rin<="00000000";
	Rout<="00000000";
    CASE Tstep_Q IS 
      WHEN "00" =>  --store DIN in IR as long as Tstep_Q = 0 
        IRin <= '1'; 
      WHEN "01" =>  --define signals in time step T1 
        CASE I IS 
			   WHEN "000" => -- mv
					Rout<=Y;
					Rin<=X;
					Done<='1';
				WHEN "001" => -- mvi
					DINout<='1';
					Rin<=X;
					Done<='1';
				WHEN "100" => --ADD
					Rout<=X;
					Ain<='1';
				WHEN "101" => --SUB
					Rout<=X;
					Ain<='1';
				WHEN OTHERS =>--and,or,xor,nor
					Rout <= X;
					Ain <= '1';
        END CASE; 
      WHEN "10" =>  --define signals in time step T2 
        CASE I IS 
				WHEN "010" => --AND
					Rout<=Y;
					z<="000";
					Gin<='1';
				WHEN "011" => --OR
					Rout<=Y;
					z<="001";
					Gin<='1';

				WHEN "100" => --ADD
					Rout<=Y;
					z<="011";
					Gin<='1';
				WHEN "101" => --SUB
					Rout<=Y;
					z<="010";
					Gin<='1';
				WHEN "110" => --XOR
					Rout<=Y;
					z<="100";
					Gin<='1';
				WHEN "111" => --NOR
					Rout<=Y;
					z<="101";
					Gin<='1';
				WHEN OTHERS =>
        END CASE; 
      WHEN "11" =>  --define signals in time step T3 
        CASE I IS 
				WHEN "010" => --AND
					Rin<=X;
					Gout<='1';
					Done<='1';
				WHEN "011" => --OR
					Rin<=X;
					Gout<='1';
					Done<='1';
				WHEN "100" => --ADD
					Rin<=X;
					Gout<='1';
					Done<='1';
				WHEN "101" => --SUB
					Rin<=X;
					Gout<='1';
					Done<='1';
				WHEN "110" => --XOR
					Rin<=X;
					Gout<='1';
					Done<='1';
				WHEN "111" => --NOR
					Rin<=X;
					Gout<='1';
					Done<='1';
				When Others =>
				
        END CASE; 
    END CASE; 
  END PROCESS; 

  reg_0: regn PORT MAP (BusWires, Rin(0), Clock, R0); 
  reg_1: regn PORT MAP (BusWires, Rin(1), Clock, R1);
  reg_2: regn PORT MAP (BusWires, Rin(2), Clock, R2);
  reg_3: regn PORT MAP (BusWires, Rin(3), Clock, R3);
  reg_4: regn PORT MAP (BusWires, Rin(4), Clock, R4);
  reg_5: regn PORT MAP (BusWires, Rin(5), Clock, R5);
  reg_6: regn PORT MAP (BusWires, Rin(6), Clock, R6);
  reg_7: regn PORT MAP (BusWires, Rin(7), Clock, R7);
  reg_A: regn PORT MAP (BusWires, Ain, Clock, A);
  reg_IR: regn GENERIC MAP(n => 9) PORT MAP(DIN(15 DOWNTO 7), IRin, Clock, IR);
--ALU
	prakseis: alu16 PORT MAP(A, BusWires, z, Sum, Overflow);
	
	
		
  reg_G: regn PORT MAP(Sum, Gin, Clock, G);
  Sel<= Rout & Gout & DINout;
	WITH Sel SELECT
		BusWires<= R0 WHEN "1000000000",
					  R1 WHEN "0100000000",
					  R2 WHEN "0010000000",
					  R3 WHEN "0001000000",
					  R4 WHEN "0000100000",
					  R5 WHEN "0000010000",
					  R6 WHEN "0000001000",
					  R7 WHEN "0000000100",
					  G  WHEN "0000000010",
					  DIN WHEN OTHERS;
END Behavior; 

LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.std_logic_signed.all;


ENTITY regn IS 
  GENERIC (n : INTEGER := 16); 
  PORT ( R : IN STD_LOGIC_VECTOR(n-1 DOWNTO 0); 
  Rin, Clock : IN STD_LOGIC; 
  Q : BUFFER STD_LOGIC_VECTOR(n-1 DOWNTO 0)); 
END regn; 

ARCHITECTURE Behavior OF regn IS 
BEGIN 
  PROCESS (Clock) 
  BEGIN 
    IF Clock'EVENT AND Clock = '1' THEN 
      IF Rin = '1' THEN 
        Q <= R; 
      END IF; 
    END IF; 
  END PROCESS; 
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.std_logic_signed.all;

ENTITY upcount IS 
  PORT ( Clear, Clock : IN STD_LOGIC; 
  Q : OUT STD_LOGIC_VECTOR(1 DOWNTO 0)); 
END upcount; 

ARCHITECTURE Behavior OF upcount IS
 
 SIGNAL Count : STD_LOGIC_VECTOR(1 DOWNTO 0); 

BEGIN 
  PROCESS (Clock) 
  BEGIN 
    IF (Clock'EVENT AND Clock = '1') THEN 

     IF Clear = '1' THEN 
       Count <= "00"; 
	  ELSE
       Count <= Count + 1; 
     END IF; 

   END IF; 
  END PROCESS; 
  Q <= Count; 

END Behavior; 
LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.std_logic_signed.all;

ENTITY decide3to8 IS
  PORT ( W : IN STD_LOGIC_VECTOR(2 DOWNTO 0); 
  En : IN STD_LOGIC; 
  Y : OUT STD_LOGIC_VECTOR(0 TO 7)); 
END decide3to8; 

ARCHITECTURE Behavior OF decide3to8 IS 
BEGIN 
  PROCESS (W, En) 
  BEGIN 
    IF En = '1' THEN 
      CASE W IS 
			WHEN "000" => Y <= "10000000";
			WHEN "001" => Y <= "01000000";
			WHEN "010" => Y <= "00100000";
			WHEN "011" => Y <= "00010000";
			WHEN "100" => Y <= "00001000";
			WHEN "101" => Y <= "00000100";
			WHEN "110" => Y <= "00000010";
			WHEN "111" => Y <= "00000001";
      END CASE; 
    ELSE 
      Y <= "00000000"; 
    END IF; 
   END PROCESS; 
END Behavior;

