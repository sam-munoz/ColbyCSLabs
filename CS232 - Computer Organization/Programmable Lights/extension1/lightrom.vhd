-- Author: Samuel Munoz
-- Date: 09/29/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity lightrom is
	port 
	(
		addr : in std_logic_vector (3 downto 0);
		data : out std_logic_vector (2 downto 0)
	);

end entity;

architecture rtl of lightrom is

begin

	data <= 
      "011" when addr = "0000" else -- add 1 to LR
      "011" when addr = "0001" else -- add 1 to LR
      "011" when addr = "0010" else -- add 1 to LR
      "110" when addr = "0011" else -- rotate LR right
      "110" when addr = "0100" else -- rotate LR right
      "110" when addr = "0101" else -- rotate LR right
      "110" when addr = "0110" else -- rotate LR right
      "110" when addr = "0111" else -- rotate LR right
      "110" when addr = "1000" else -- rotate LR right
      "111" when addr = "1001" else -- rotate LR left
      "111" when addr = "1010" else -- rotate LR left
      "111" when addr = "1011" else -- rotate LR left
      "111" when addr = "1100" else -- rotate LR left
      "111" when addr = "1101" else -- rotate LR left
      "111" when addr = "1110" else -- rotate LR left
      "000";                        -- move all 0s
		
end rtl;


