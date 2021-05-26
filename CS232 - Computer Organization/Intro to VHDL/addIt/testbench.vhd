-- Author: Samuel Munoz
-- Date: 09/21/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity testbench is
end entity;

architecture behavior of testbench is

	
	component two_bit_adder
		port(            
			a, b: in unsigned (3 downto 0); 
			result : out unsigned (4 downto 0)        
		);
	end component;

	
	signal I0, I1 : unsigned (3 downto 0);
	signal output : unsigned (4 downto 0);
	constant num_iterations : integer := 16;

	begin
		tester : two_bit_adder port map(a => I0, b => I1, result => output);        
		process begin
			-- set inital state for inputs			
			I0 <= "0000";
			I1 <= "0000";
			-- output <= "00000";

			for i in 1 to num_iterations loop   			
				for j in 1 to num_iterations loop
					-- output <= ("0" & I0) + ("0" & I1);
					wait for 50 ns;
					I1 <= I1 + "0001";
				end loop;
				I0 <= I0 + to_unsigned(0001, 4);
				I1 <= "0000";
				wait for 5 ns;
			end loop;
			wait;
		end process;
end behavior;
