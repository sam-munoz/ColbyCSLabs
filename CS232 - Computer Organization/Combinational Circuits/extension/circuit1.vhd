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
-- CREATED		"Sat Sep 12 15:51:52 2020"

LIBRARY ieee;
USE ieee.std_logic_1164.all; 

LIBRARY work;

ENTITY circuit1 IS 
	PORT
	(
		I4 :  IN  STD_LOGIC;
		I3 :  IN  STD_LOGIC;
		I2 :  IN  STD_LOGIC;
		I1 :  IN  STD_LOGIC;
		I0 :  IN  STD_LOGIC;
		F :  OUT  STD_LOGIC
	);
END circuit1;

ARCHITECTURE bdf_type OF circuit1 IS 

SIGNAL	SYNTHESIZED_WIRE_16 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_17 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_18 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_3 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_4 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_5 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_6 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_7 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_8 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_13 :  STD_LOGIC;


BEGIN 



SYNTHESIZED_WIRE_16 <= NOT(I4);



SYNTHESIZED_WIRE_8 <= SYNTHESIZED_WIRE_16 AND SYNTHESIZED_WIRE_17 AND SYNTHESIZED_WIRE_18 AND I1;


F <= SYNTHESIZED_WIRE_3 OR SYNTHESIZED_WIRE_4 OR SYNTHESIZED_WIRE_5 OR SYNTHESIZED_WIRE_6 OR SYNTHESIZED_WIRE_7 OR SYNTHESIZED_WIRE_8;


SYNTHESIZED_WIRE_3 <= SYNTHESIZED_WIRE_16 AND I0 AND SYNTHESIZED_WIRE_17;


SYNTHESIZED_WIRE_17 <= NOT(I3);



SYNTHESIZED_WIRE_5 <= SYNTHESIZED_WIRE_17 AND SYNTHESIZED_WIRE_18 AND I0;


SYNTHESIZED_WIRE_18 <= NOT(I2);



SYNTHESIZED_WIRE_4 <= SYNTHESIZED_WIRE_13 AND I3 AND I2 AND I0;


SYNTHESIZED_WIRE_13 <= NOT(I1);



SYNTHESIZED_WIRE_6 <= I4 AND I2 AND I1 AND I0;


SYNTHESIZED_WIRE_7 <= SYNTHESIZED_WIRE_16 AND SYNTHESIZED_WIRE_18 AND I1 AND I0;


END bdf_type;