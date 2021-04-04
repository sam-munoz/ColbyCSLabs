-- Samuel Munoz
-- Fall 2020
-- CS 232 Project 1
-- Test file for prime number circuit

library ieee;
use ieee.std_logic_1164.all;

entity testbench is
end testbench;

architecture one of testbench is

  signal I3, I2, I1, I0, NSR, NSG, NSY, EWR, EWG, EWY, R, C, O3, O2, O1, O0: std_logic;

  component circuit1
  port( 
    I3 :  IN  STD_LOGIC;
    I2 :  IN  STD_LOGIC;
    I1 :  IN  STD_LOGIC;
    I0 :  IN  STD_LOGIC;
    R  :  IN  STD_LOGIC;
    C  :  IN  STD_LOGIC;
    NSR :  OUT  STD_LOGIC;
    NSG :  OUT  STD_LOGIC;
    NSY :  OUT  STD_LOGIC;
    EWR :  OUT  STD_LOGIC;
    EWG :  OUT  STD_LOGIC;
    EWY :  OUT  STD_LOGIC;
    O3  :  OUT  STD_LOGIC;
    O2  :  OUT  STD_LOGIC;
    O1  :  OUT  STD_LOGIC;
    O0  :  OUT  STD_LOGIC
    );
  end component;

begin

I3 <= 	'0',
	'1' after 400 ns;

I2 <= 	'0',
	'1' after 200 ns,
	'0' after 400 ns,
	'1' after 600 ns;
	
I1 <= 	'0', 
	'1' after 100 ns,
	'0' after 200 ns,
	'1' after 300 ns,
	'0' after 400 ns,
	'1' after 500 ns,
	'0' after 600 ns,
	'1' after 700 ns;

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
	'0' after 800 ns;

T0: circuit1 port map(I3, I2, I1, I0, NSR, NSG, NSY, EWR, EWG, EWY, R, C, O3, O2, O1, O0);

end one;


