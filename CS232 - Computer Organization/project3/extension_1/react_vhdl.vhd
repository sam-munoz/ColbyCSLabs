-- Author: Samuel Munoz
-- Date: 09/27/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-- define the inputs and outputs required for the board
entity react_vhdl is
	port(
		clk : in std_logic;
		reset : in std_logic;
		start : in std_logic;
		react : in std_logic;
		gLED : out std_logic;
		rLED : out std_logic;
		display0 : out std_logic_vector (6 downto 0);
		display1 : out std_logic_vector (6 downto 0)
	);
end entity;

-- define behavior of the board
architecture behavior of react_vhdl is
	
	-- create component for the display driver
	component display_driver
		port(            
			a	   : in UNSIGNED  (3 downto 0);
			result : out UNSIGNED (6 downto 0)      
		);
	end component;
	
	-- create component for the timer
	component timer
		port(
			clk		 : in	std_logic;
			reset	 : in	std_logic;
			start	 : in	std_logic;
			react  : in std_logic;
			t	 : out	std_logic_vector (7 downto 0);
			greenLED : out std_logic;
			redLED 	: out std_logic
		);
	end component;
		
	-- create local variables
	signal timer_bus : std_logic_vector (7 downto 0);
	signal display_bus0 : unsigned (6 downto 0);
	signal display_bus1 : unsigned (6 downto 0);
	signal temp_g : std_logic;
	signal temp_r : std_logic;
	
	-- begin defining circuit behavior
	begin
		-- create an instance of the timer signal
		t : timer port map(clk, reset, start, react, t => timer_bus, greenLED => temp_g, redLED => temp_r);
	
		-- create two instances of the display_drivers
		d0 : display_driver port map( a => unsigned( timer_bus (3 downto 0) ), result => display_bus0 );
		d1 : display_driver port map( a => unsigned( timer_bus (7 downto 4) ), result => display_bus1 );
	     
		process (display_bus0, display_bus1, temp_g, temp_r)
		begin
			display0 <= std_logic_vector( display_bus0 );
			display1 <= std_logic_vector( display_bus1 );
			gLED <= temp_g;
			rLED <= temp_r;
		end process;
end behavior;