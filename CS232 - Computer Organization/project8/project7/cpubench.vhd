library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity cpubench is
end cpubench;

architecture test of cpubench is
  constant num_cycles : integer := 400;

  signal iport: std_logic_vector( 7 downto 0) := "00000000";
  signal oport: std_logic_vector(15 downto 0) := "0000000000000000";

  signal PC: std_logic_vector( 7 downto 0) := "00000000";
  signal IR: std_logic_vector(15 downto 0) := "0000000000000000";
  signal RA: std_logic_vector(15 downto 0) := "0000000000000000";
  signal RB: std_logic_vector(15 downto 0) := "0000000000000000";
  signal RC: std_logic_vector(15 downto 0) := "0000000000000000";
  signal RD: std_logic_vector(15 downto 0) := "0000000000000000";
  signal RE: std_logic_vector(15 downto 0) := "0000000000000000";
  -- signal ram_output_view : std_logic_vector(15 downto 0);
  -- signal stack_ptr_view : std_logic_vector(15 downto 0);
  -- signal mar_view : std_logic_vector(7 downto 0);
  -- signal mbr_view : std_logic_vector(15 downto 0);
  -- signal rom_output_view : std_logic_vector(15 downto 0);
  -- signal cr_view : std_logic_vector(3 downto 0);
  -- signal state_view : std_logic_vector(3 downto 0);
  
  signal clk: std_logic := '1';
  signal reset: std_logic;
  
  -- component statement for the ALU
  component cpu
    port (
      clock   : in  std_logic;                       -- main clock
      reset : in  std_logic;                       -- reset button
      input_port : in  std_logic_vector(7 downto 0);    -- input port
    
      pc_view : out std_logic_vector( 7 downto 0);
      ir_view : out std_logic_vector(15 downto 0);
      ra_view : out std_logic_vector(15 downto 0);
      rb_view : out std_logic_vector(15 downto 0);
      rc_view : out std_logic_vector(15 downto 0);
      rd_view : out std_logic_vector(15 downto 0);
      re_view : out std_logic_vector(15 downto 0);
      -- ram_output_view : out std_logic_vector(15 downto 0);
      -- stack_ptr_view : out std_logic_vector(15 downto 0);
      -- mar_view : out std_logic_vector(7 downto 0);
      -- mbr_view : out std_logic_vector(15 downto 0);
      -- rom_output_view : out std_logic_vector(15 downto 0);
      -- cr_view : out std_logic_vector(3 downto 0);
      -- state_view : out std_logic_vector(3 downto 0);

      output_port : out std_logic_vector(15 downto 0));  -- output port
  end component;
  
begin

  -- start off with a short reset
  reset <= '0', '1' after 1 ns;

  -- create a clock
  process
  begin
    for i in 1 to num_cycles loop
      clk <= not clk;
      wait for 1 ns;
      
      clk <= not clk;
      wait for 1 ns;
    end loop;
    wait;
  end process;

  iport <= "00000000";

  cpuinstance: cpu
    port map( clock => clk,
	      reset => reset,
	      input_port => iport,
	      pc_view => PC,
	      ir_view => IR,
	      ra_view => RA,
	      rb_view => RB,
	      rc_view => RC,
	      rd_view => RD,
	      re_view => RE,
	      -- ram_output_view => ram_output_view,
	      -- stack_ptr_view => stack_ptr_view,
	      -- mar_view => mar_view,
	      -- mbr_view => mbr_view,
	      -- rom_output_view => rom_output_view,
	      -- cr_view => cr_view,	      
	      -- state_view => state_view,
	      output_port => oport
   );

  
end test;

