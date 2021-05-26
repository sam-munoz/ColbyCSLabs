-- Samuel Munoz
-- 09/27/2020

library ieee;
use ieee.std_logic_1164.all;

-- a test bench has no inputs or outputs
entity reacttest is
end reacttest;

-- architecture
architecture test of reacttest is

  -- internal signals for everything we want to send to or receive from the
  -- test circuit
  signal Reset, Start, React: std_logic;
  signal rled: std_logic;
  signal gled: std_logic;

  -- the component statement for timer
  component timer
    port(
      clk		 : in	std_logic;
      reset	 : in	std_logic;
      start	: in std_logic;
      react	: in std_logic;
		t : out std_logic_vector (7 downto 0);
      redLED	 : out	std_logic;
      greenLED : out std_logic
      );
  end component;

  -- signals for making the clock
  constant num_cycles : integer := 50000;
  signal clk : std_logic := '1';
  
  -- signal for storing time output
  signal store_time : std_logic_vector (7 downto 0);

begin

  -- these are timed signal assignments to create specific patterns
  Reset <= '1', '0' after 5 ns, '1' after 10 ns, '0' after 49995 ns, '1' after 50000 ns;
  Start <=  '1' , '0' after 25 ns, '1' after 30 ns, '0' after 50005 ns, '1' after 50010 ns;
  React <=  '1', '0' after 40005 ns, '1' after 40010 ns, '0' after 54995 ns, '1' after 55000 ns;

  -- we can use a process and a for loop to generate a clock signal
  process begin
    for i in 1 to num_cycles loop
      clk <= not clk;
      wait for 5 ns;
      clk <= not clk;
      wait for 5 ns;
    end loop;
    wait;
  end process;

  -- this instantiates a bright circuit and sets up the inputs and outputs
  t0: timer port map (clk, reset => Reset, start => Start,  react => React, t => store_time, redLED => rled, greenLED => gled);
end test;
