-- Author: Samuel Munoz
-- Date: 10/11/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity pld2 is

	port(
		clk	 : in	std_logic;
		reset	 : in	std_logic;
		speed  : in std_logic;
		hold 	 : in std_logic;
		lightsig : out std_logic_vector (7 downto 0)
		-- IRView : out std_logic_vector (9 downto 0);
		-- PCView : out std_logic_vector (3 downto 0); -- here only for debugging purposes
		-- ACCView : out std_logic_vector (7 downto 0)
	);

end entity;

architecture rtl of pld2 is

	-- Create component of the pldrom circuit
	component pldrom
		port(
			addr : in std_logic_vector (3 downto 0);
			data : out std_logic_vector (9 downto 0)
		);
	end component;

	-- Build an enumerated type for the state machine
	type state_type is (sFetch, sExecute1, sExecute2);

	-- create internal variables that are needed
	signal state   : state_type;
	signal IR : std_logic_vector (9 downto 0);
	signal PC : unsigned (3 downto 0);
	signal LR : unsigned (7 downto 0);
	signal ROMvalue : std_logic_vector (9 downto 0);
	signal slowclock : std_logic;
	signal counter: unsigned (24 downto 0);
	signal SRC : unsigned (7 downto 0);
	signal ACC : unsigned (7 downto 0);

begin

	-- create an instance of the pldrom circuit
	prom : pldrom port map( addr => std_logic_vector(PC), data => ROMvalue );
	
	-- used to slow down the clock
	process(clk, reset) 
		begin
			if reset = '0' then
			  counter <= "0000000000000000000000000";
			elsif (rising_edge(clk)) then
			  counter <= counter + 1;
			end if;
	end process;
	
	-- EXTENSION 2 ---------------------------------------
	-- process to control speed of board
	process(speed, counter)
		begin
			if speed = '0' then
				slowclock <= counter(18);
			else
				slowclock <= counter(20);
			end if;
	end process;
	-- END OF EXTENSION 2 --------------------------------
	
	-- slowclock <= counter(20);
	
	-- Logic to advance to the next state
	process (reset, state, IR, PC, LR, ROMvalue, slowclock, hold)
	begin
		if reset = '0' then
			IR <= (others => '0');
			PC <= (others => '0');
			LR <= (others => '0');
			state <= sFetch;
			
		-- EXTENSION 1 -----------------
		elsif hold = '0' then
			while hold = '0' loop
			end loop;
		-- END OF EXTENSION 1 ----------
			
		elsif (rising_edge(slowclock)) then
			case state is
				when sFetch =>
					IR <= ROMvalue;
					PC <= PC + 1;
					state <= sExecute1;
					
				when sExecute1 =>
					-- check for opcode
					case IR(9 downto 8) is 
						-- move operator
						when "00" =>
							-- check for source
							case IR(5 downto 4) is
								-- if ACC is selected
								when "00" =>
									SRC <= ACC;
								
								-- if LR is selected 
								when "01" =>
									SRC <= LR;
									
								-- if lower 4 bits sign extended selected
								when "10" =>
									SRC <= unsigned( IR(3) & IR(3) & IR(3) & IR(3) & IR(3 downto 0) );
									
								-- if all 1s are selected
								when others =>
									SRC <= (others => '1');
									
							end case;
							
						-- binary/uniary operator
						when "01" =>
							-- check for sources
							case IR(4 downto 3) is
								-- SRC <= ACC
								when "00" =>
									SRC <= ACC;
								
								-- SRC <= LR
								when "01" =>
									SRC <= LR;
						
								-- SRC <= IR low 2 bits sign extended
								when "10" =>
									SRC <= unsigned( IR(1) & IR(1) & IR(1) & IR(1) & IR(1) & IR(1) & IR(1 downto 0) );
								
								-- SRC <= all 1s
								when others =>
									SRC <= (others => '1');
							end case;
							
						-- unconditional operator
						when "10" =>
							PC <= unsigned( IR(3 downto 0) );
							
						-- conditional operator
						when others =>
							case IR(7) is
								when '0' =>
									if ACC = "00000000" then
										PC <= unsigned( IR(3 downto 0) );
									end if;
								
								when others =>
									if LR = "00000000" then
										PC <= unsigned( IR(3 downto 0) );
									end if;
							end case;
							
					end case;
					
					-- change state
					state <= sExecute2;
					
				when sExecute2 =>
					-- check for opcode
					case IR(9 downto 8) is
						-- move operator
						when "00" =>
							-- check for destination
							case IR(7 downto 6) is
								-- destination: ACC
								when "00" =>
									ACC <= SRC;
								
								-- destination: LR
								when "01" =>
									LR <= SRC;
								
								-- destination: ACC lower 4 bits
								when "10" =>
									ACC(3 downto 0) <= SRC(3 downto 0);
									
								-- destination: ACC higher 4 bits
								when others =>
									ACC(7 downto 4) <= SRC(3 downto 0);
							end case;
						
						-- binary/uniary operator
						when "01" =>
							-- check for operation
							case IR(7 downto 5) is
								-- operation: add
								when "000" =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: add
										when '0' =>
											ACC <= ACC + SRC;
										
										-- destination: LR; operation: add
										when others =>
											LR <= LR + SRC;
									end case;
									
								-- operation: subtract
								when "001" =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: subtract
										when '0' =>
											ACC <= ACC - SRC;
										
										-- destination: LR; operation: subtract
										when others =>
											LR <= LR - SRC;
									end case;
								
								-- operation: shift left
								when "010" =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: shift left
										when '0' =>
											ACC <= shift_left( ACC, 1 );
										
										-- destination: LR; operation: shift left
										when others =>
											LR <= shift_left( LR, 1 );
									end case;
								
								-- operation: shift right (maintain sign bit)
								when "011" =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: shift right
										when '0' =>
											ACC <= unsigned( shift_right( signed(ACC), 1 ) );
										
										-- destination: LR; operation: shift right
										when others =>
											LR <= unsigned( shift_right( signed(LR), 1 ) );
											
									end case;
								
								-- operation: xor
								when "100" =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: xor
										when '0' =>
											ACC <= ACC xor SRC;
										
										-- destination: LR; operation: xor
										when others =>
											LR <= LR xor SRC;
									end case;
								
								-- operation: and
								when "101" =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: and
										when '0' =>
											ACC <= ACC and SRC;
										
										-- destination: LR; operation: and
										when others =>
											LR <= LR and SRC;
									end case;
								
								-- operation: rotate left
								when "110" =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: rotate left
										when '0' =>
											ACC <= ACC(6 downto 0) & ACC(7);
										
										-- destination: LR; operation: rotate left
										when others =>
											LR <= LR(6 downto 0) & LR(7);
									end case;
								
								-- operation: rotate right
								when others =>
									-- check for destination
									case IR(2) is
										-- destination: ACC; operation: rotate right
										when '0' =>
											ACC <= ACC(0) & ACC(7 downto 1);
										
										-- destination: LR; operation: rotate right
										when others =>
											LR <= LR(0) & LR(7 downto 1);
									end case;
							end case;
								
						
						-- conditional/unconditional branches do nothing
						when others =>
							SRC <= SRC;
					end case;
					
					-- change state
					state <= sFetch;
							
			end case;
		end if;
	end process;

	-- Output depends solely on the current state
	process (IR, LR, PC, ACC)
	begin
		-- IRView <= IR;
		lightsig <= std_logic_vector(LR);
		-- PCView <= std_logic_vector(PC);
		-- ACCView <= std_logic_vector(ACC);
	end process;

end rtl;
