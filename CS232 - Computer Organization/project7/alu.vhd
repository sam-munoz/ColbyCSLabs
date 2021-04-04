-- Author: Samuel Munoz
-- Purpose: Creates ALU circuit for the CPU
-- Date: 11/03/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity alu is
	port (
		srcA : in  unsigned(15 downto 0);         -- input A
		srcB : in  unsigned(15 downto 0);         -- input B
		op   : in  std_logic_vector(2 downto 0);  -- operation
		cr   : out std_logic_vector(3 downto 0);  -- condition outputs
		dest : out unsigned(15 downto 0)          -- output value
	);
end entity;

architecture rtl of alu is
	-- create internal variables
	signal tdest : unsigned(16 downto 0);  
	
	begin
		process (srcA, srcB, op)
		begin  -- process
			case op is
				when "000" =>        -- addition     tdest = srcA + srcB
					tdest <= (srcA(15) & srcA) + (srcB(15) & srcB);

				when "001" =>        -- subtraction  tdest = srcA - srcB
					tdest <= (srcA(15) & srcA) - (srcB(15) & srcB);

				when "010" =>        -- and          tdest = srcA and srcB
					tdest <= unsigned( std_logic_vector('0' & srcA) and std_logic_vector('0' & srcB) );
					
				when "011" =>        -- or           tdest = srcA or srcB
					tdest <= unsigned( std_logic_vector('0' & srcA) or std_logic_vector('0' & srcB) );

				when "100" =>        -- xor          tdest = srcA xor srcB
					tdest <= unsigned( std_logic_vector('0' & srcA) xor std_logic_vector('0' & srcB) );

				when "101" =>        -- shift        tdest = srcA shifted left arithmetic by one if srcB(0) is 0, otherwise right
					if srcB(0) = '0' then
						tdest <= srcA(15) & unsigned( shift_left(signed(srcA), 1) );
					else
						tdest <= srcA(0) & unsigned( shift_right(signed(srcA), 1) );
					end if;

				when "110" =>        -- rotate       tdest = srcA rotated left by one if srcB(0) is 0, otherwise right
					if srcB(0) = '0' then
						tdest <= srcA(15) & srcA(14 downto 0) & srcA(15); -- rotate left
					else
						tdest <= srcA(0) & srcA(0) & srcA(15 downto 1); -- rotate right 
					end if;

				when "111" =>        -- pass         tdest = srcA
					tdest <= '0' & srcA;

				when others =>
					null;
			end case;
		end process;

  	-- connect the low 16 bits of tdest to dest here
	dest <= tdest(15 downto 0);

  	-- set the four CR output bits here
	cr(0) <= '1' when tdest(15 downto 0) = 0 else '0';
	cr(1) <= '1' when ( (op = "000") and ( srcA(15) = srcB(15) and not srcA(15) = tdest(15) ) ) or ( (op = "001") and (not (srcA(15) = srcB(15)) and not (srcA(15) = tdest(15) ) ) ) else '0';
	cr(2) <= '1' when tdest(15) = '1' else '0';
	cr(3) <= '1' when tdest(16) = '1' else '0';

end rtl;
