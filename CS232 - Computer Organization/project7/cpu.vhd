-- Author: Samuel Munoz
-- Project 7
-- Date: 11/03/2020

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity cpu is
	port (
		-- inputs to circuit
		clock : in std_logic;
		reset : in std_logic;
		input_port : in std_logic_vector (7 downto 0);
		
		-- DEBUGGING PORTS
		pc_view : out std_logic_vector (7 downto 0);
		ir_view : out std_logic_vector (15 downto 0);
		ra_view : out std_logic_vector (15 downto 0);
		rb_view : out std_logic_vector (15 downto 0);
		rc_view : out std_logic_vector (15 downto 0);
		rd_view : out std_logic_vector (15 downto 0);
		re_view : out std_logic_vector (15 downto 0);
		-- ram_output_view : out std_logic_vector(15 downto 0);
		-- stack_ptr_view : out std_logic_vector(15 downto 0);
		-- mar_view : out std_logic_vector(7 downto 0);
		-- mbr_view : out std_logic_vector(15 downto 0);
		-- rom_output_view : out std_logic_vector (15 downto 0);
		-- cr_view : out std_logic_vector (3 downto 0);
		-- state_view : out std_logic_vector (3 downto 0);
		-- END DEBUGGING PORTS
		
		output_port : out std_logic_vector (15 downto 0)
	);
end entity;

architecture rtl of cpu is
	-- define all circuits needed by the cpu
	-- RAM component
	component DataRAM is
		port (
			address : in std_logic_vector (7 downto 0);
			clock : in std_logic;
			data : in std_logic_vector (15 downto 0);
			wren : in std_logic;
			q : out std_logic_vector (15 downto 0)
		);
	end component;
	
	-- ROM component
	component ProgramROM is
		port (
			address : in std_logic_vector (7 downto 0);
			clock : in std_logic;
			q : out std_logic_vector(15 downto 0)
		);
	end component;

	-- ALU component
	component alu is
		port (
			srcA : in  unsigned(15 downto 0);         -- input A
			srcB : in  unsigned(15 downto 0);         -- input B
			op   : in  std_logic_vector(2 downto 0);  -- operation
			cr   : out std_logic_vector(3 downto 0);  -- conditional register
			dest : out unsigned(15 downto 0)          -- output value
		);
	end component;

	-- define all internal variables
	signal stack_ptr : unsigned (15 downto 0);
	signal ir : std_logic_vector (15 downto 0);
	signal pc : unsigned (7 downto 0);
	signal cr_cpu : unsigned (3 downto 0); -- conditional register for the CPU
	signal cr_alu : std_logic_vector (3 downto 0); -- conditional register for ALU
	signal mbr : unsigned (15 downto 0);
	signal mar : unsigned (7 downto 0); -- memory address register
	signal ram_output : std_logic_vector (15 downto 0);
	signal ram_wren : std_logic;
	signal ra : unsigned (15 downto 0); -- General Purpose Register (GPR) A
	signal rb : unsigned (15 downto 0); -- GPR B
	signal rc : unsigned (15 downto 0); -- GPR C
	signal rd : unsigned (15 downto 0); -- GPR D
	signal re : unsigned (15 downto 0); -- GPR E
	signal srcA : unsigned (15 downto 0); -- srcA for the ALU
	signal srcB : unsigned (15 downto 0); -- srcB for the ALU
	signal op : unsigned (2 downto 0); -- stores opcode for the alu
	signal rom_output : std_logic_vector (15 downto 0); -- used to store output from ROM
	signal alu_output : unsigned (15 downto 0); -- stores output of the ALU
	signal state : unsigned (3 downto 0) := "0000"; -- stores the state the cpu is in
	signal outreg : std_logic_vector (15 downto 0);
	signal start_clk : unsigned (2 downto 0) := "111";

	begin
		-- make instance of the ALU, RAM, and ROM
		alu0 : alu port map(srcA, srcB, op => std_logic_vector(op), cr => cr_alu, dest => alu_output);
		ram0 : DataRAM port map(address => std_logic_vector(mar), clock => clock, data => std_logic_vector(mbr), wren => ram_wren, q => ram_output); 
		rom0 : ProgramROM port map(address => std_logic_vector(pc), clock => clock, q => rom_output);  

		-- map internal variables with their corresponding debugging output ports
		pc_view <= std_logic_vector(pc);
		ir_view <= ir;
		ra_view <= std_logic_vector(ra);
		rb_view <= std_logic_vector(rb);
		rc_view <= std_logic_vector(rc);
		rd_view <= std_logic_vector(rd);
		re_view <= std_logic_vector(re);
		output_port <= outreg;
		-- ram_output_view <= ram_output;
		-- stack_ptr_view <= std_logic_vector(stack_ptr);
		-- mar_view <= std_logic_vector(mar);
		-- mbr_view <= std_logic_vector(mbr);
		-- cr_view <= std_logic_vector(cr_cpu);
		-- rom_output_view <= rom_output;
		-- state_view <= std_logic_vector(state);

 		-- create state machine for cpu
 		process(clock, reset)
 		begin
 			if reset = '0' then
				pc <= (others => '0');
				ir <= (others => '0');
				mar <= (others => '0');
				mbr <= (others => '0');
				ra <= (others => '0');
				rb <= (others => '0');
				rc <= (others => '0');
				rd <= (others => '0');
				re <= (others => '0');
				outreg <= (others => '0');
				stack_ptr <= (others => '0');
				cr_cpu <= (others => '0');
				start_clk <= (others => '0');

 			elsif rising_edge(clock) then
 				-- state machine goes here
 				case state is
 					-- start state
 					-- this state should be used whenever reset has been pressed;
 					-- wait three clock cycles to complete memory startup
 					when "0000" =>
						start_clk <= start_clk + 1;
						if start_clk = "111" then
 							state <= "0001";		
							start_clk <= "000";
						end if;
 
 					-- fetch state
 					-- get next instruction for ROM
 					-- increase PC
 					-- goto Execute-Step state
 					when "0001" =>
 						ir <= rom_output;
 						pc <= pc + 1;
 						state <= "0010";
 
 					-- execute-setup state
 					-- read instruction and direct state machine to the right state to perform operation
 					when "0010" =>
 						-- read opcode
 						case ir(15 downto 12) is
 							-- load from RAM
 							when "0000" =>
 								-- store lower bits from ir to mar
 								mar <= unsigned( ir(7 downto 0) );
 								
 								-- check if the R-bit (in instructions) is 1 or not
 								if ir(11) = '1' then
 									mar <= mar + re(7 downto 0);
 								end if;
 
 							-- store to RAM / writing to RAM
 							when "0001" =>
 								-- store lower bits from ir to mar
 								mar <= unsigned( ir(7 downto 0) );
 								
 								-- check if the R-bit is 1 or not
 								if ir(11) = '1' then
 									mar <= mar + re(7 downto 0);
 								end if;
 								
 								-- store into mbr the value to be loaded
 								if ir(10 downto 8) = "000" then
 									mbr <= ra;
 								elsif ir(10 downto 8) = "001" then
 									mbr <= rb;
 								elsif ir(10 downto 8) = "010" then
 									mbr <= rc;
 								elsif ir(10 downto 8) = "011" then
 									mbr <= rd;
 								elsif ir(10 downto 8) = "100" then
 									mbr <= re;
 								elsif ir(10 downto 8) = "101" then
 									mbr <= stack_ptr; 
 								end if;
 
 							-- unconditional branching
 							when "0010" =>
 								pc <= unsigned( ir(7 downto 0) );
 
 							-- conditional branching / call / return / exit
 							when "0011" =>
 								-- determine which command to call
 								case ir(11 downto 10) is
 									-- conditional branching
 									when "00" =>
 										-- check for given register
 										case ir(9 downto 8) is
 											-- check bit cr(0)
 											when "00" =>
 												if cr_cpu(0) = '1' then
 													pc <= unsigned( ir(7 downto 0) ); 
 												end if;
 
 											-- check bit cr_cpu(1)
 											when "01" =>
 												if cr_cpu(1) = '1' then
 													pc <= unsigned( ir(7 downto 0) ); 
 												end if;
 
 											-- check bit cr_cpu(2)
 											when "10" =>
 												if cr_cpu(2) = '1' then
 													pc <= unsigned( ir(7 downto 0) ); 
 												end if; 
 
 											-- check bit cr_cpu(3)
 											when "11" =>
 												if cr_cpu(3) = '1' then
 													pc <= unsigned( ir(7 downto 0) ); 
 												end if;  
 
 											when others =>
 												null;
 										end case;
 
 									-- call (calling a function)
 									when "01" =>
 										-- set pc value to the lowers bits of ir
 										pc <= unsigned( ir(7 downto 0) );
 
 										-- going to push cr_cpu and pc to stack; storing data to stack by loading the mbr register (bits: UUUU CCCC PPPPPPPP; CCCC - bits for cr_cpu, PPPPPPPP - bits for pc)
 										mbr <= "0000" & cr_cpu & pc;
 
 										-- use mar has a old frame pointer; thus store the old stack pointer to mar
 										mar <= stack_ptr(7 downto 0);
 
 										-- increase stack pointer
										if not( stack_ptr = "111111111111" ) then
	 										stack_ptr <= stack_ptr + 1;
										end if;
 										
 									-- return (finished calling a function; returning to location where the function was called)
 									when "10" =>
 										-- set mar back to the location prior to calling function
 										mar <= stack_ptr(7 downto 0) - 1;
 										
 										-- decrease the stack pointer (we are poping an element off the stack)
 										if not( stack_ptr = 0 ) then 
 											stack_ptr <= stack_ptr - 1;
 										end if;
 
 									-- exit (i.e. move into halt state and terminate the cpu from running any instructions)
 									when "11" =>
 										state <= "1000";
 
 									when others =>
 										null;
 
 								end case;
 
 							-- push
 							when "0100" =>
 								-- make mbr be the correct value
 								if ir(11 downto 9) = "000" then
 									mbr <= ra;
 								elsif ir(11 downto 9) = "001" then
 									mbr <= rb;
 								elsif ir(11 downto 9) = "010" then
 									mbr <= rc;
 								elsif ir(11 downto 9) = "011" then
 									mbr <= rd;
 								elsif ir(11 downto 9) = "100" then
 									mbr <= re;
 								elsif ir(11 downto 9) = "101" then
 									mbr <= stack_ptr; 
 								elsif ir(11 downto 9) = "110" then
 									-- mbr <= pc; -- mistake in instruction; conflicting information provided in documentation
 								elsif ir(11 downto 9) = "111" then
 									-- mbr <= cr_cpu; -- mistake in instruction; conflicting information provided in documentation
 								end if;	
 
 								-- make the mar equal the value of the stack pointer
 								mar <= stack_ptr(7 downto 0);
 								
 								-- increase the stack pointer
 								stack_ptr <= stack_ptr + 1;
 
 							-- pop
 							when "0101" =>
 								-- set mar to be stack_ptr once it has already decreased
 								mar <= stack_ptr(7 downto 0) - 1; 
 
 								-- decrease stack if the stack is not empty
 								if not( stack_ptr = 0 ) then
 									stack_ptr <= stack_ptr - 1;
 								end if;

 							-- store to output
 							when "0110" =>
 								null; -- this step is not used
 
 							-- load from input
 							when "0111" =>
 								null; -- this step is not used
 
 							-- add
 							when "1000" =>
 								-- get the first right input
 								if ir(11 downto 9) = "000" then
 									srcA <= ra;
 								elsif ir(11 downto 9) = "001" then
 									srcA <= rb;
 								elsif ir(11 downto 9) = "010" then
 									srcA <= rc;
 								elsif ir(11 downto 9) = "011" then
 									srcA <= rd;
 								elsif ir(11 downto 9) = "100" then
 									srcA <= re;
 								elsif ir(11 downto 9) = "101" then
 									srcA <= stack_ptr;
 								elsif ir(11 downto 9) = "110" then
 									srcA <= (others => '0');
 								elsif ir(11 downto 9) = "111" then
 									srcA <= (others => '1');
 								end if;
 
 								-- get the second right input 
 								if ir(8 downto 6) = "000" then
 									srcB <= ra;
 								elsif ir(8 downto 6) = "001" then
 									srcB <= rb;
 								elsif ir(8 downto 6) = "010" then
 									srcB <= rc;
 								elsif ir(8 downto 6) = "011" then
 									srcB <= rd;
 								elsif ir(8 downto 6) = "100" then
 									srcB <= re;
 								elsif ir(8 downto 6) = "101" then
 									srcB <= stack_ptr;
 								elsif ir(8 downto 6) = "110" then
 									srcB <= (others => '0');
 								elsif ir(8 downto 6) = "111" then
 									srcB <= (others => '1');
 								end if;
 								
 								-- set the op bits for alu
 								op <= "000"; 
 
 							-- subtract
 							when "1001" =>
 								-- get the first right input
 								if ir(11 downto 9) = "000" then
 									srcA <= ra;
 								elsif ir(11 downto 9) = "001" then
 									srcA <= rb;
 								elsif ir(11 downto 9) = "010" then
 									srcA <= rc;
 								elsif ir(11 downto 9) = "011" then
 									srcA <= rd;
 								elsif ir(11 downto 9) = "100" then
 									srcA <= re;
 								elsif ir(11 downto 9) = "101" then
 									srcA <= stack_ptr;
 								elsif ir(11 downto 9) = "110" then
 									srcA <= (others => '0');
 								elsif ir(11 downto 9) = "111" then
 									srcA <= (others => '1');
 								end if;
 
 								-- get the second right input 
 								if ir(8 downto 6) = "000" then
 									srcB <= ra;
 								elsif ir(8 downto 6) = "001" then
 									srcB <= rb;
 								elsif ir(8 downto 6) = "010" then
 									srcB <= rc;
 								elsif ir(8 downto 6) = "011" then
 									srcB <= rd;
 								elsif ir(8 downto 6) = "100" then
 									srcB <= re;
 								elsif ir(8 downto 6) = "101" then
 									srcB <= stack_ptr;
 								elsif ir(8 downto 6) = "110" then
 									srcB <= (others => '0');
 								elsif ir(8 downto 6) = "111" then
 									srcB <= (others => '1');
 								end if;
 								
 								-- set op bit for alu
 								op <= "001";
 
 							-- and
 							when "1010" =>
 								-- get the first right input
 								if ir(11 downto 9) = "000" then
 									srcA <= ra;
 								elsif ir(11 downto 9) = "001" then
 									srcA <= rb;
 								elsif ir(11 downto 9) = "010" then
 									srcA <= rc;
 								elsif ir(11 downto 9) = "011" then
 									srcA <= rd;
 								elsif ir(11 downto 9) = "100" then
 									srcA <= re;
 								elsif ir(11 downto 9) = "101" then
 									srcA <= stack_ptr;
 								elsif ir(11 downto 9) = "110" then
 									srcA <= (others => '0');
 								elsif ir(11 downto 9) = "111" then
 									srcA <= (others => '1');
 								end if;
 
 								-- get the second right input 
 								if ir(8 downto 6) = "000" then
 									srcB <= ra;
 								elsif ir(8 downto 6) = "001" then
 									srcB <= rb;
 								elsif ir(8 downto 6) = "010" then
 									srcB <= rc;
 								elsif ir(8 downto 6) = "011" then
 									srcB <= rd;
 								elsif ir(8 downto 6) = "100" then
 									srcB <= re;
 								elsif ir(8 downto 6) = "101" then
 									srcB <= stack_ptr;
 								elsif ir(8 downto 6) = "110" then
 									srcB <= (others => '0');
 								elsif ir(8 downto 6) = "111" then
 									srcB <= (others => '1');
 								end if;
 
 								-- set the op bits for alu
 								op <= "010";
 
 							-- or
 							when "1011" =>
 								-- get the first right input
 								if ir(11 downto 9) = "000" then
 									srcA <= ra;
 								elsif ir(11 downto 9) = "001" then
 									srcA <= rb;
 								elsif ir(11 downto 9) = "010" then
 									srcA <= rc;
 								elsif ir(11 downto 9) = "011" then
 									srcA <= rd;
 								elsif ir(11 downto 9) = "100" then
 									srcA <= re;
 								elsif ir(11 downto 9) = "101" then
 									srcA <= stack_ptr;
 								elsif ir(11 downto 9) = "110" then
 									srcA <= (others => '0');
 								elsif ir(11 downto 9) = "111" then
 									srcA <= (others => '1');
 								end if;
 
 								-- get the second right input 
 								if ir(8 downto 6) = "000" then 
 									srcB <= ra;
 								elsif ir(8 downto 6) = "001" then 
 									srcB <= rb;
 								elsif ir(8 downto 6) = "010" then 
 									srcB <= rc;
 								elsif ir(8 downto 6) = "011" then 
 									srcB <= rd;
 								elsif ir(8 downto 6) = "100" then 
 									srcB <= re;
 								elsif ir(8 downto 6) = "101" then 
 									srcB <= stack_ptr;
 								elsif ir(8 downto 6) = "110" then 
 									srcB <= (others => '0');
 								elsif ir(8 downto 6) = "111" then 
 									srcB <= (others => '1');
 								end if;
 
 								-- set op bits for alu
 								op <= "011";
 
 							-- exclusive-or (i.e. xor)
 							when "1100" =>
 								-- get the first right input
 								if ir(11 downto 9) = "000" then
 									srcA <= ra;
 								elsif ir(11 downto 9) = "001" then
 									srcA <= rb;
 								elsif ir(11 downto 9) = "010" then
 									srcA <= rc;
 								elsif ir(11 downto 9) = "011" then
 									srcA <= rd;
 								elsif ir(11 downto 9) = "100" then
 									srcA <= re;
 								elsif ir(11 downto 9) = "101" then
 									srcA <= stack_ptr;
 								elsif ir(11 downto 9) = "110" then
 									srcA <= (others => '0');
 								elsif ir(11 downto 9) = "111" then
 									srcA <= (others => '1');
 								end if;
 
 								-- get the second right input 
 								if ir(8 downto 6) = "000" then
 									srcB <= ra;
 								elsif ir(8 downto 6) = "001" then
 									srcB <= rb;
 								elsif ir(8 downto 6) = "010" then
 									srcB <= rc;
 								elsif ir(8 downto 6) = "011" then
 									srcB <= rd;
 								elsif ir(8 downto 6) = "100" then
 									srcB <= re;
 								elsif ir(8 downto 6) = "101" then
 									srcB <= stack_ptr;
 								elsif ir(8 downto 6) = "110" then
 									srcB <= (others => '0');
 								elsif ir(8 downto 6) = "111" then
 									srcB <= (others => '1');
 								end if;
 
 								-- set op bits for alu
 								op <= "100";
 
 							-- shift 
 							when "1101" =>
 								-- get the correct first input
 								if ir(10 downto 8) = "000" then
 									srcA <= ra;
 								elsif ir(10 downto 8) = "001" then
 									srcA <= rb;
 								elsif ir(10 downto 8) = "010" then
 									srcA <= rc;
 								elsif ir(10 downto 8) = "011" then
 									srcA <= rd;
 								elsif ir(10 downto 8) = "100" then
 									srcA <= re;
 								elsif ir(10 downto 8) = "101" then
 									srcA <= stack_ptr;
 								elsif ir(10 downto 8) = "110" then
 									srcA <= (others => '0');
 								elsif ir(10 downto 8) = "111" then
 									srcA <= (others => '1');
 								end if;
 
 								-- make srcB register contain the correct value for shifting
 								-- if we are suppose to shift left, then make srcB(0) equal 0
 								if ir(11) = '0' then
 									srcB(0) <= '0';
 								-- otherwise, shift right by making srcB(0) equal 1
 								else
 									srcB(0) <= '1';
 								end if;
 
 								-- set op bits for alu
 								op <= "101";
 
 							-- rotate
 							when "1110" =>
 								-- get the correct first input
 								if ir(10 downto 8) = "000" then
 									srcA <= ra;
 								elsif ir(10 downto 8) = "001" then
 									srcA <= rb;
 								elsif ir(10 downto 8) = "010" then
 									srcA <= rc;
 								elsif ir(10 downto 8) = "011" then
 									srcA <= rd;
 								elsif ir(10 downto 8) = "100" then
 									srcA <= re;
 								elsif ir(10 downto 8) = "101" then
 									srcA <= stack_ptr;
 								elsif ir(10 downto 8) = "110" then
 									srcA <= (others => '0');
 								elsif ir(10 downto 8) = "111" then
 									srcA <= (others => '1');
 								end if;
 
 								-- make srcB register contain the correct value for shifting
 								-- if we are suppose to shift left, then make srcB equal 0
 								if ir(11) = '0' then
 									srcB <= (others => '0');
 								-- otherwise, shift right by making srcB not equal 0
 								else
 									srcB <= (others => '1');
 								end if;
 
 								-- set op bits for alu
 								op <= "110";
 
 							-- move
 							when "1111" =>
 								-- set srcA to the right bits
 								-- treat next 8 bits as some value
 								if ir(11) = '1' then
 									srcA <= ir(10) & ir(10) & ir(10) & ir(10) & ir(10) & ir(10) & ir(10) & ir(10) & unsigned( ir(10 downto 3) );

 								-- copy value from one register to some new location
 								else
 									if ir(10 downto 8) = "000" then
 										srcA <= ra;
 									elsif ir(10 downto 8) = "001" then
 										srcA <= rb;
 									elsif ir(10 downto 8) = "010" then
 										srcA <= rc;
 									elsif ir(10 downto 8) = "011" then
 										srcA <= rd;
 									elsif ir(10 downto 8) = "100" then
 										srcA <= re;
 									elsif ir(10 downto 8) = "101" then
 										srcA <= stack_ptr;
 									elsif ir(10 downto 8) = "110" then
 										-- srcA <= pc; -- conflicting sizes
 									elsif ir(10 downto 8) = "111" then
 										srcA <= unsigned(ir);
 									end if;
 								end if;
 
 								-- set op bits for alu
 								op <= "111";
 
 							-- null
 							when others =>
 								null;
 						end case;
 
 						-- move to next step
 						state <= "0011";

					-- execute-alu state
					when "0011" =>
						case ir(15 downto 12) is
							-- store to RAM
							when "0001" =>
								-- enable writing to RAM
								ram_wren <= '1';
								
								-- send to the next right state
								state <= "0100";
							
							-- call (calling a function) and direct correct state for return
							when "0011" =>
								-- make sure that the inside bits match up for call function
								if ir(11 downto 10) = "01" then
									-- enable writing to RAM
									ram_wren <= '1';
									
									-- send machine to the next right state
									state <= "0100";
									
								-- directing return to the next state
								else
									state <= "0100";
								end if;
							
							-- push
							when "0100" =>
								-- enable writing to RAM
								ram_wren <= '1';
								
								-- send machine to right state (execute-mem_wait)
								state <= "0100";

							-- pop
							when "0101" =>
								state <= "0100";
							
							-- all other operations do nothing in this stage and none of them require to go into the execute-mem_wait stage; thus move into execute-write stage
							when others =>
								state <= "0101";
						end case;

					-- execute-MemWait; this state is only used to give the RAM time to read
					when "0100" =>
						state <= "0101"; 

					-- execute-write state
					when "0101" =>
						case ir(15 downto 12) is
							-- load from RAM
							when "0000" =>
								start_clk <= start_clk + 1;
								if start_clk = "010" then
									-- move into correct state
									state <= "0001";
									start_clk <= "000";

									-- store output into correct register
									if ir(10 downto 8) = "000" then
										ra <= unsigned(ram_output);
									elsif ir(10 downto 8) = "001" then
										rb <= unsigned(ram_output);
									elsif ir(10 downto 8) = "010" then
										rc <= unsigned(ram_output);
									elsif ir(10 downto 8) = "011" then
										rd <= unsigned(ram_output);
									elsif ir(10 downto 8) = "100" then
										re <= unsigned(ram_output);
									elsif ir(10 downto 8) = "101" then
										stack_ptr <= unsigned(ram_output);
									end if;
								end if;
								
							-- store from RAM
							when "0001" =>
								-- disable writing to RAM
								ram_wren <= '0';
								
								-- wait for RAM
								-- move to right state
								state <= "0001";
							
							-- unconditional branching
							when "0010" =>
								-- need clock cycle to find the next instruction for pc
								-- move to right state
								state <= "0001";
							
							-- condition branching / call / return / exit
							when "0011" =>
								-- check which instruction is called
								case ir(11 downto 10) is
									-- condition branching
									when "00" =>
										-- need clock cycle to find the next instruction for pc
										-- move to right state
										state <= "0001";
								
									-- call
									when "01" => 
										-- disable writing to RAM
										ram_wren <= '0';
										
										-- wait for RAM
										-- move into right state
										state <= "0001";
									
									-- return
									when "10" =>
										-- set pc to the correct bits from ram_output
										pc <= unsigned( ram_output(7 downto 0) );
										
										-- set cr_cpu to the correct bits from ram_output
										cr_cpu <= unsigned( ram_output(11 downto 8) );
									
										-- move to next state
										state <= "0110";

									-- exit (should not get here
									when "11" =>
										state <= "1000";

									-- other (never can happen)
									when others =>
										null; -- skip if code gets here
								end case;
								
							-- push
							when "0100" =>
								-- disable writing to the ram
								ram_wren <= '0';
							
								-- move to right state
								state <= "0001";
							-- pop
							when "0101" =>
								-- store the output of ram to right destination
								if ir(11 downto 9) = "000" then
									ra <= unsigned(ram_output);
								elsif ir(11 downto 9) = "001" then
									rb <= unsigned(ram_output);
								elsif ir(11 downto 9) = "010" then
									rc <= unsigned(ram_output);
								elsif ir(11 downto 9) = "011" then
									rd <= unsigned(ram_output);
								elsif ir(11 downto 9) = "100" then
									re <= unsigned(ram_output);
								elsif ir(11 downto 9) = "101" then
									stack_ptr <= unsigned(ram_output);
								elsif ir(11 downto 9) = "110" then
									null;
									-- pc <= unsigned(ram_output);
								elsif ir(11 downto 9) = "111" then
									null;
									-- cr_cpu <= unsigned(ram_output);
								end if;

								-- move to right state
								state <= "0001";
							
							-- store to output
							when "0110" =>
								if ir(11 downto 9) = "000" then
									outreg <= std_logic_vector(ra);
								elsif ir(11 downto 9) = "001" then
									outreg <= std_logic_vector(rb);
								elsif ir(11 downto 9) = "010" then
									outreg <= std_logic_vector(rc);
								elsif ir(11 downto 9) = "011" then
									outreg <= std_logic_vector(rd);
								elsif ir(11 downto 9) = "100" then
									outreg <= std_logic_vector(re);
								elsif ir(11 downto 9) = "101" then
									outreg <= std_logic_vector(stack_ptr);
								elsif ir(11 downto 9) = "110" then
									outreg <= std_logic_vector(pc);
								elsif ir(11 downto 9) = "111" then
									outreg <= ir;
								end if;

								-- move to right state
								state <= "0001";

							-- load from input
							when "0111" =>
								-- store output to correct destination
								if ir(11 downto 9) = "000" then
									ra <= input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & unsigned(input_port);
								elsif ir(11 downto 9) = "001" then
									rb <= input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & unsigned(input_port);
								elsif ir(11 downto 9) = "010" then
									rc <= input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & unsigned(input_port);
								elsif ir(11 downto 9) = "011" then
									rd <= input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & unsigned(input_port);
								elsif ir(11 downto 9) = "100" then
									re <= input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & unsigned(input_port);
								elsif ir(11 downto 9) = "101" then
									stack_ptr <= input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & input_port(7) & unsigned(input_port);
								end if;

								-- move to right state
								state <= "0001";
							
							-- add
							when "1000" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= alu_output;
								elsif ir(2 downto 0) = "001" then
									rb <= alu_output;
								elsif ir(2 downto 0) = "010" then
									rc <= alu_output;
								elsif ir(2 downto 0) = "011" then
									rd <= alu_output;
								elsif ir(2 downto 0) = "100" then
									re <= alu_output;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= alu_output;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- subtract
							when "1001" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= alu_output;
								elsif ir(2 downto 0) = "001" then
									rb <= alu_output;
								elsif ir(2 downto 0) = "010" then
									rc <= alu_output;
								elsif ir(2 downto 0) = "011" then
									rd <= alu_output;
								elsif ir(2 downto 0) = "100" then
									re <= alu_output;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= alu_output;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- and
							when "1010" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= alu_output;
								elsif ir(2 downto 0) = "001" then
									rb <= alu_output;
								elsif ir(2 downto 0) = "010" then
									rc <= alu_output;
								elsif ir(2 downto 0) = "011" then
									rd <= alu_output;
								elsif ir(2 downto 0) = "100" then
									re <= alu_output;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= alu_output;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- or
							when "1011" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= alu_output;
								elsif ir(2 downto 0) = "001" then
									rb <= alu_output;
								elsif ir(2 downto 0) = "010" then
									rc <= alu_output;
								elsif ir(2 downto 0) = "011" then
									rd <= alu_output;
								elsif ir(2 downto 0) = "100" then
									re <= alu_output;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= alu_output;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- exclusive-or (i.e. xor)
							when "1100" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= alu_output;
								elsif ir(2 downto 0) = "001" then
									rb <= alu_output;
								elsif ir(2 downto 0) = "010" then
									rc <= alu_output;
								elsif ir(2 downto 0) = "011" then
									rd <= alu_output;
								elsif ir(2 downto 0) = "100" then
									re <= alu_output;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= alu_output;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- shift
							when "1101" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= alu_output;
								elsif ir(2 downto 0) = "001" then
									rb <= alu_output;
								elsif ir(2 downto 0) = "010" then
									rc <= alu_output;
								elsif ir(2 downto 0) = "011" then
									rd <= alu_output;
								elsif ir(2 downto 0) = "100" then
									re <= alu_output;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= alu_output;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- rotate
							when "1110" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= alu_output;
								elsif ir(2 downto 0) = "001" then
									rb <= alu_output;
								elsif ir(2 downto 0) = "010" then
									rc <= alu_output;
								elsif ir(2 downto 0) = "011" then
									rd <= alu_output;
								elsif ir(2 downto 0) = "100" then
									re <= alu_output;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= alu_output;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- move
							when "1111" =>
								-- store output of alu into the correct destination
								if ir(2 downto 0) = "000" then
									ra <= srcA;
								elsif ir(2 downto 0) = "001" then
									rb <= srcA;
								elsif ir(2 downto 0) = "010" then
									rc <= srcA;
								elsif ir(2 downto 0) = "011" then
									rd <= srcA;
								elsif ir(2 downto 0) = "100" then
									re <= srcA;
								elsif ir(2 downto 0) = "101" then
									stack_ptr <= srcA;
								end if;

								-- store cr flags in to the cr_cpu field
								cr_cpu <= unsigned(cr_alu);

								-- move to right state
								state <= "0001";
							
							-- null case (not possible)
							when others =>
								null;
						end case;

					-- execute-returnpause 1 state
					when "0110" =>
						-- here because of return instruction
						-- waiting for ROM
						state <= "0111";

					-- execute-returnpause 2 state
					when "0111" =>
						-- here because of return instruction
						-- waiting for ROM
						state <= "0001";

					-- halt state
					when "1000" =>
						null; -- terminate the cpu from running anything

 					-- null state
 					when others =>
 						null;
				end case;
			end if;
		end process;

end rtl;
