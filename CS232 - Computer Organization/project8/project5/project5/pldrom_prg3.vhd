-- Author: Samuel Munoz
-- Date: 10/06/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity pldrom_prg3 is
	port 
	(
		addr : in std_logic_vector (3 downto 0);
		data : out std_logic_vector (9 downto 0)
	);

end entity;

architecture rtl of pldrom_prg3 is

begin

	data <= 
    -- set values for LR and subtract from LR one
	 "0011100001" when addr = "0000" else -- move 0001 from the IR to the high 4 bits of the ACC
    "0010100000" when addr = "0001" else -- move 0000 from the IR to the low 4 bits of the ACC
    "0001000000" when addr = "0010" else -- move ACC to LR
    "0100110101" when addr = "0011" else -- Subtract LR from low 2 bits of IR (-1) and put it back into the LR
    "1110000110" when addr = "0100" else -- Break from loop if LR = 0; go to line "0110"
    "1000000011" when addr = "0101" else -- Loop back to "0011" unconditionally
	 
	 -- flash on and off eight times the lights
    "0011100000" when addr = "0110" else -- move 0000 from the IR to the high 4 bits of ACC
    "0010101000" when addr = "0111" else -- move 1000 from the IR to the low 4 bits of ACC
    "0110011100" when addr = "1000" else -- xor LR with all 1s
    "0100110001" when addr = "1001" else -- subtract ACC from low 2 bits of IR by -1 and put it back into ACC
    "1100001111" when addr = "1010" else -- Break from loop if ACC = 0; go to line "1111"
    "1000001000" when addr = "1011" else -- Loop back to "1000"
    "1000000000";                        -- loop back to the beginning of the program	
		
end rtl;
