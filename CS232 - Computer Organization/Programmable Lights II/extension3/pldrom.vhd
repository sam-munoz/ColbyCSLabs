-- Author: Samuel Munoz
-- Date: 10/06/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity pldrom is
	port 
	(
		addr : in std_logic_vector (3 downto 0);
		data : out std_logic_vector (9 downto 0)
	);

end entity;

architecture rtl of pldrom is

begin

data <= 
    	-- set values for LR and ACC and shift right until all lights turn on
	"0001101111" when addr = "0000" else -- move "0001" to LR
	"1100100100" when addr = "0001" else -- if LR < 0, then goto "0100"
 	"1101100110" when addr = "0010" else -- if LR = 0, then goto "0110"
	"1110101000" when addr = "0011" else -- if LR > 0, then goto "1000"
	"0010100100" when addr = "0100" else -- move "0100" to lower 4 bits of ACC
	"1000001001" when addr = "0101" else -- goto line "1001"
	"0010100010" when addr = "0110" else -- move "0010" to lower 4 bits of ACC
	"1000001001" when addr = "0111" else -- goto line "1001"
	"0010100001" when addr = "1000" else -- move "0001" to lower 4 bits of ACC
	"1000001001"; 			     -- loop to itself
end rtl;
