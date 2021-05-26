-- Quartus II VHDL Template
-- Unsigned Adder

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity two_bit_adder is

	port 
	(
		a	   : in UNSIGNED  (3 downto 0);
		b	   : in UNSIGNED  (3 downto 0);
		result : out UNSIGNED (4 downto 0)
	);

end entity;

architecture rtl of two_bit_adder is
begin

	result <= ('0' & a) + ('0' & b);

end rtl;
