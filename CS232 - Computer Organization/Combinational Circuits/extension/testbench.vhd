-- Samuel Munoz
-- Fall 2020
-- CS 232 Project 1
-- Test file for prime number circuit

library ieee;
use ieee.std_logic_1164.all;

entity testbench is
end testbench;

architecture one of testbench is

  signal I4, I3, I2, I1, I0, f: std_logic;

  component circuit1
  port( 
    I4 :  IN  STD_LOGIC;
    I3 :  IN  STD_LOGIC;
    I2 :  IN  STD_LOGIC;
    I1 :  IN  STD_LOGIC;
    I0 :  IN  STD_LOGIC;
    F :  OUT  STD_LOGIC
    );
  end component;

begin

I4 <= 	'0',
	'1' after 800 ns;

I3 <= 	'0',
	'1' after 400 ns,
	'0' after 800 ns,
	'1' after 1200 ns;

I2 <= 	'0',
	'1' after 200 ns,
	'0' after 400 ns,
	'1' after 600 ns,
	'0' after 800 ns,
	'1' after 1000 ns,
	'0' after 1200 ns,
	'1' after 1400 ns;

I1 <= 	'0', 
	'1' after 100 ns,
	'0' after 200 ns,
	'1' after 300 ns,
	'0' after 400 ns,
	'1' after 500 ns,
	'0' after 600 ns,
	'1' after 700 ns,
	'0' after 800 ns,
	'1' after 900 ns,
	'0' after 1000 ns,
	'1' after 1100 ns,
	'0' after 1200 ns,
	'1' after 1300 ns,
	'0' after 1400 ns,
	'1' after 1500 ns;

I0 <= 	'0', 
	'1' after 50 ns, 
	'0' after 100 ns, 
	'1' after 150 ns,
	'0' after 200 ns,
	'1' after 250 ns,
	'0' after 300 ns,
	'1' after 350 ns,
	'0' after 400 ns,
	'1' after 450 ns,
	'0' after 500 ns,
	'1' after 550 ns,
	'0' after 600 ns,
	'1' after 650 ns,
	'0' after 700 ns,
	'1' after 750 ns,
	'0' after 800 ns,
	'1' after 850 ns,
	'0' after 900 ns,
	'1' after 950 ns,
	'0' after 1000 ns,
	'1' after 1050 ns,
	'0' after 1100 ns,
	'1' after 1150 ns,
	'0' after 1200 ns,
	'1' after 1250 ns,
	'0' after 1300 ns,
	'1' after 1350 ns,
	'0' after 1400 ns,
	'1' after 1450 ns,
	'0' after 1500 ns,
	'1' after 1550 ns,
	'0' after 1600 ns;
	
T0: circuit1 port map(I4, I3, I2, I1, I0, F);

end one;


