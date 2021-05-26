-- Author: Samuel Munoz
-- Date: 10/06/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity pldrom_prg4 is
	port 
	(
		addr : in std_logic_vector (3 downto 0);
		data : out std_logic_vector (9 downto 0)
	);

end entity;

architecture rtl of pldrom_prg4 is

begin

	data <= 
    -- set values for LR and ACC and shift right until all lights turn on
	 "0011101000" when addr = "0000" else -- move 1000 from the IR to the high 4 bits of the ACC
    "0010100000" when addr = "0001" else -- move 0000 from the IR to the low 4 bits of the ACC
    "0001000000" when addr = "0010" else -- move ACC to LR
    "0011100000" when addr = "0011" else -- move 0000 from the IR to the high 4 bits of the ACC
    "0010101000" when addr = "0100" else -- move 1000 from the IR to the low 4 bits of the ACC
    "0101100100" when addr = "0101" else -- shift right LR
    "0100110001" when addr = "0110" else -- subtract ACC from low 2 bits of IR by -1 and put it back into ACC
    "1100001001" when addr = "0111" else -- Break from loop if ACC = 0; go to line "1001"
    "1000000101" when addr = "1000" else -- Loop back to "0101"
	 "0110011100" when addr = "1001" else -- bit inverse
	 "1000000000";								  -- move to "0000"
		
end rtl;