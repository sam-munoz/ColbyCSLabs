-- Author: Samuel Munoz
-- Date: 09/29/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity lights is

	port(
		clk		 : in	std_logic;
		reset	 : in	std_logic;
		lightsig : out std_logic_vector (7 downto 0);
		IRView : out std_logic_vector (2 downto 0)
		-- PCView : out std_logic_vector (3 downto 0) -- here only for debugging purposes
	);

end entity;

architecture rtl of lights is

	-- Create component of the lightrom circuit
	component lightrom
		port(
			addr : in std_logic_vector (3 downto 0);
			data : out std_logic_vector (2 downto 0)
		);
	end component;

	-- Build an enumerated type for the state machine
	type state_type is (sFetch, sExecute);

	-- create internal variables that are needed
	signal state   : state_type;
	signal IR : std_logic_vector (2 downto 0);
	signal PC : unsigned (3 downto 0);
	signal LR : unsigned (7 downto 0);
	signal ROMvalue : std_logic_vector (2 downto 0);
	signal slowclock : std_logic;
   signal counter: unsigned (24 downto 0);	

begin

	-- create an instance of the lightrom circuit
	lrom : lightrom port map( addr => std_logic_vector(PC), data => ROMvalue );

	-- used to slow down the clock
	process(clk, reset) 
		begin
			if reset = '0' then
			  counter <= "0000000000000000000000000";
			elsif (rising_edge(clk)) then
			  counter <= counter + 1;
			end if;
	end process;
		
  	-- slowclock <= counter(22);
	slowclock <= clk;
	
	-- Logic to advance to the next state
	process (slowclock, reset, state, IR, PC, LR, ROMvalue)
	begin
		if reset = '0' then
			IR <= (others => '0');
			PC <= (others => '0');
			LR <= (others => '0');
			state <= sFetch;
			
		elsif (rising_edge(slowclock)) then
			case state is
				when sFetch=>
					IR <= ROMvalue;
					PC <= PC + 1;
					state <= sExecute;
					
				when sExecute=>
					case IR is
						when "000" =>
							LR <= "00000000";
						when "001" =>
							LR <= shift_right( unsigned(LR), 1 );
						when "010" =>
							LR <= shift_left( unsigned(LR), 1 );
						when "011" =>
							LR <= LR + 1;
						when "100" =>
							LR <= LR - 1;
						when "101" =>
							LR <= not LR;
						when "110" =>
							LR <= LR(0) & LR(7 downto 1);
						when others =>
							LR <= LR(6 downto 0) & LR(7);
					end case;
					state <= sFetch;
			end case;
		end if;
	end process;

	-- Output depends solely on the current state
	process (IR, LR, PC)
	begin
		IRView <= IR;
		lightsig <= std_logic_vector(LR);
		-- PCView <= std_logic_vector(PC);
	end process;

end rtl;
