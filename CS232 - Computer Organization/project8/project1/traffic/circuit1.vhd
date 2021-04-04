-- Copyright (C) 1991-2012 Altera Corporation
-- Your use of Altera Corporation's design tools, logic functions 
-- and other software and tools, and its AMPP partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Altera Program License 
-- Subscription Agreement, Altera MegaCore Function License 
-- Agreement, or other applicable license agreement, including, 
-- without limitation, that your use is for the sole purpose of 
-- programming logic devices manufactured by Altera and sold by 
-- Altera or its authorized distributors.  Please refer to the 
-- applicable agreement for further details.

-- PROGRAM		"Quartus II 32-bit"
-- VERSION		"Version 12.1 Build 177 11/07/2012 SJ Full Version"
-- CREATED		"Sun Sep 13 11:13:02 2020"

LIBRARY ieee;
USE ieee.std_logic_1164.all; 

LIBRARY work;

ENTITY circuit1 IS 
	PORT
	(
		I3 :  IN  STD_LOGIC;
		I2 :  IN  STD_LOGIC;
		I1 :  IN  STD_LOGIC;
		I0 :  IN  STD_LOGIC;
		C :  IN  STD_LOGIC;
		R :  IN  STD_LOGIC;
		NSR :  OUT  STD_LOGIC;
		NSG :  OUT  STD_LOGIC;
		NSY :  OUT  STD_LOGIC;
		EWR :  OUT  STD_LOGIC;
		EWG :  OUT  STD_LOGIC;
		EWY :  OUT  STD_LOGIC;
		A :  OUT  STD_LOGIC;
		B :  OUT  STD_LOGIC;
		D :  OUT  STD_LOGIC;
		E :  OUT  STD_LOGIC
	);
END circuit1;

ARCHITECTURE bdf_type OF circuit1 IS 

COMPONENT counter
	PORT(clock : IN STD_LOGIC;
		 aclr : IN STD_LOGIC;
		 q : OUT STD_LOGIC_VECTOR(3 DOWNTO 0)
	);
END COMPONENT;

SIGNAL	q :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	SYNTHESIZED_WIRE_0 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_1 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_20 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_21 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_22 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_23 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_9 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_10 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_11 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_12 :  STD_LOGIC;


BEGIN 



NSR <= I3 OR SYNTHESIZED_WIRE_0;


b2v_inst1 : counter
PORT MAP(clock => C,
		 aclr => R);


EWR <= SYNTHESIZED_WIRE_1 OR SYNTHESIZED_WIRE_20;


SYNTHESIZED_WIRE_1 <= SYNTHESIZED_WIRE_21 AND SYNTHESIZED_WIRE_22 AND SYNTHESIZED_WIRE_23 AND I3;


SYNTHESIZED_WIRE_12 <= SYNTHESIZED_WIRE_22 OR SYNTHESIZED_WIRE_21;


EWY <= I3 AND I2 AND I1;


NSG <= SYNTHESIZED_WIRE_20 AND SYNTHESIZED_WIRE_9 AND SYNTHESIZED_WIRE_10;


SYNTHESIZED_WIRE_9 <= I1 OR I0 OR I2;


EWG <= I3 AND SYNTHESIZED_WIRE_11 AND SYNTHESIZED_WIRE_12;


SYNTHESIZED_WIRE_0 <= SYNTHESIZED_WIRE_20 AND SYNTHESIZED_WIRE_21 AND SYNTHESIZED_WIRE_22 AND SYNTHESIZED_WIRE_23;


SYNTHESIZED_WIRE_11 <= I1 OR I0 OR I2;


SYNTHESIZED_WIRE_20 <= NOT(I3);



SYNTHESIZED_WIRE_21 <= NOT(I2);



SYNTHESIZED_WIRE_22 <= NOT(I1);



SYNTHESIZED_WIRE_23 <= NOT(I0);



SYNTHESIZED_WIRE_10 <= SYNTHESIZED_WIRE_22 OR SYNTHESIZED_WIRE_21;


NSY <= SYNTHESIZED_WIRE_20 AND I2 AND I1;

A <= q(3);
B <= q(2);
D <= q(0);
E <= q(1);

END bdf_type;