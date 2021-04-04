-- Author: Samuel Munoz
-- Project 6: Stack-Based Calculator
-- Date: 10/24/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-- define ports for this circuit
entity calculator is
	port (
		clock, reset, b2, b3, b4 : in std_logic;
		op : in std_logic_vector (1 downto 0);
		data : in std_logic_vector (7 downto 0);
		digit0, digit1 : out std_logic_vector (6 downto 0);
		stackview : out std_logic_vector (3 downto 0)
	);
end entity;

-- define behavior of circuit
architecture rtl of calculator is
	-- component go here
	component memram is
		port (
			address : in std_logic_vector (3 downto 0);
			clock : in std_logic;
			data : in std_logic_vector (7 downto 0);
			wren : in std_logic ;
			q : out std_logic_vector (7 downto 0)
		);
	end component;
	
	component display_driver is
		port (
			a : in unsigned (3 downto 0);
			result : out std_logic_vector (6 downto 0)
		);
	end component;
	
	-- internal signals
	signal ram_input : std_logic_vector (7 downto 0);
	signal ram_output : std_logic_vector (7 downto 0);
	signal ram_wren : std_logic;
	signal stack_ptr : unsigned (3 downto 0);
	signal mbr : std_logic_vector (7 downto 0);
	signal state : unsigned (2 downto 0) := "000";
	signal slowclock : std_logic;
	signal counter : unsigned (24 downto 0);
	constant start_iteration : unsigned (7 downto 0) := "00000010";
	
	-- start process
	begin
	
	-- map memram to local variable
	ram : memram port map(address => std_logic_vector(stack_ptr), clock => clock, data => ram_input, wren => ram_wren, q => ram_output);
	d0 : display_driver port map(a => unsigned( mbr(3 downto 0) ), result => digit0 );
	d1 : display_driver port map(a => unsigned( mbr(7 downto 4) ), result => digit1);
	
	-- control speed of clock
	process(clock, reset) 
		begin
			if reset = '0' then
			  counter <= "0000000000000000000000000";
			elsif (rising_edge(clock)) then
			  counter <= counter + 1;
			end if;
	end process;
	slowclock <= counter(19);
	
	-- main process
	process(slowclock, reset)
		begin
		-- check if reset was pressed
		if reset = '0' then
			-- set all values to zero
			stack_ptr <= (others => '0');
			mbr <= (others => '0');
			ram_input <= (others => '0');
			ram_wren <= '0';
			state <= (others => '0');
			
		elsif rising_edge(slowclock) then 
			case state is
				-- waiting for inputs from the board
				when "000" =>
					-- when capture button is pressed: store data on the board to the mbr
					if b2 = '0' then
						mbr <= data;
						state <= "111";
					
					-- when the enter button is pressed: store mbr into ram_input (stack). then allow the ram to write to itself move to the enter state ("001")
					elsif b3 = '0' then
					
						-- EXTENSION 1
						-- check for stack overflow; if there is, do nothing: This is an error: stack overflow error
						if	stack_ptr = "1111" then
							state <= "111";
						-- otherwise, add to stack as needed
						else
							ram_input <= mbr;
							ram_wren <= '1';
							state <= "001";
						end if;
						
					-- when the action button is pressed: need to pop the first element in the stack from stack and then compute operation move to the action state ("011")
					elsif b4 = '0' then
						-- check if stack is empty: do nothing and goto state "111" (wait for inputs to be released). This is an error: board requires elements in the stack to compute operations
						if stack_ptr = "0000" then
							state <= "111";
						-- otherwise, continue to compute operation
						else
							stack_ptr <= stack_ptr - 1;
							state <= "100";
						end if;
					end if;
				
				-- increases the stack_ptr whenever something has been written to the stack
				when "001" => 
					ram_wren <= '0';
					stack_ptr <= stack_ptr + 1;
					state <= "111";		
				
				-- waiting two cycles to read from ram
				when "100" =>
					state <= "101";
				
				-- waiting one cycle to read from ram
				when "101" => 
					state <= "110";
				
				-- read from ram and compute operation. store output into mbr
				when "110" =>
					-- figure out what operation to compute
					case op is 
						-- if operation is to add
						when "00" =>
							mbr <= std_logic_vector( unsigned(ram_output) + unsigned(mbr) );
							state <= "111";
							
						-- if operation is to subtract
						when "01" => 
							mbr <= std_logic_vector( unsigned(ram_output) - unsigned(mbr) );
						
						-- if operation is mutliply
						when "10" =>
							mbr <= std_logic_vector( unsigned(ram_output) * unsigned(mbr) ) (7 downto 0);
							
						-- if operation is divide
						--when others =>
							-- mbr <= std_logic_vector( unsigned(ram_output) / unsigned(mbr) ) (7 downto 0);

						-- EXTENSION 2: Add operations - mod operator 
						when others =>
							while not ( unsigned(mbr) = 0 ) and unsigned(ram_output) >= unsigned(mbr) loop 
								ram_output <= std_logic_vector( unsigned(ram_output) - unsigned(mbr) );
							end loop;
							-- store result back into mbr
							mbr <= ram_output;

						-- EXTENSION 2: Add operations - expontent operator 
						-- when others =>
							-- for i in start_iteration to unsigned(mbr) loop
								-- ram_output <= std_logic_vector( unsigned(ram_output) * unsigned(ram_output) ) (7 downto 0);
							-- end loop;
							-- store result back into mbr
							-- mbr <= ram_output;
					end case;
					
					-- goto state "111"
					state <= "111";
				
				-- returns to waiting for inputs once all inputs have been released
				when "111" =>
					if b2 = '1' and b3 = '1' and b4 = '1' then
						state <= "000";
					end if;
				
				-- go back to waiting for inputs
				when others =>
					state <= "000";
			end case;
		end if;
	end process;

	stackview <= std_logic_vector(stack_ptr);
	
end rtl;
