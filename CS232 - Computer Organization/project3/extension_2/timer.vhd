-- Quartus II VHDL Template
-- Four-State Moore State Machine

-- A Moore machine's outputs are dependent only on the current state.
-- The output is written only when the state changes.  (State
-- transitions are synchronous.)

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity timer is

	port(
		clk		 : in	std_logic;
		reset	 : in	std_logic;
		start	 : in	std_logic;
		react  : in std_logic;
		t	 : out	std_logic_vector (7 downto 0);
		greenLED : out std_logic;
		redLED 	: out std_logic
	);

end entity;

architecture rtl of timer is

	-- Build an enumerated type for the state machine
	type state_type is (sIdle, sWait, sCount);

	-- Register to hold the current state
	signal state   : state_type := sIdle;
	
	-- Internal signal to keep track of time
	signal counter : unsigned (27 downto 0) := (others => '0');
	
	-- Local time variable to write outputs to. Cannot write values directly into t
	-- signal temp_time : 

begin

	-- Logic to advance to the next state
	process (clk, reset, counter)
	begin
		if reset = '0' then
			counter <= (others=>'0');
			state <= sIdle;
		elsif (rising_edge(clk)) then
			case state is
				-- update the machine when state is at Idle
				when sIdle =>
					if start = '0' then
						state <= sWait;
					end if;
				
				-- update the machine when state is at Wait
				when sWait =>
					if reset = '0' then
						state <= sIdle;
					elsif react = '0' then
						counter <= "1111111111111111111111111111";
						state <= sIdle;
					elsif counter = "0000000000000000010000000000" then
						state <= sCount;
					else
						-- increase the counter
						counter <= counter + "0000000000000000000000000001";
					end if;
					
				-- update the machine when state is at Count
				when sCount =>
					if reset = '0' then
						state <= sIdle;
					elsif react = '0' then
						state <= sIdle;
					else
						-- increase the counter
						counter <= counter + "0000000000000000000000000001";
					end if;
			end case;
		end if;
	end process;

	-- Output depends solely on the current state
	process (state, counter)
	begin
		case state is
			when sIdle =>
				redLED <= '0';
				greenLED <= '0';
				t(7) <= counter(17);
				t(6) <= counter(16);
				t(5) <= counter(15);
				t(4) <= counter(14);
				t(3) <= counter(13);
				t(2) <= counter(12);
				t(1) <= counter(11);
				t(0) <= counter(10);
			when sWait =>
				greenLED <= '0';
				redLED <= '1';
				t <= "00000000";
			when sCount =>
				greenLED <= '1';
				redLED <= '0';
				t(7) <= counter(17);
				t(6) <= counter(16);
				t(5) <= counter(15);
				t(4) <= counter(14);
				t(3) <= counter(13);
				t(2) <= counter(12);
				t(1) <= counter(11);
				t(0) <= counter(10);
		end case;
	end process;

end rtl;
