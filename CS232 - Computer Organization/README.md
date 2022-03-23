This README file is intented to outline what each project is about. The projects below are listed in chronological order these projects were completed.

---

**Project 1: Combinational Circuits**  
This project focused on introducing basic digital desgin techniques. For this project, I created to two different circuits. The first determined if the 4 input are read as an unsigned integer, is that number prime. The second program controlled four different lights and the state of the lights changed depending the 4 input. In the instruction webpage, there is a table showing how the lights should change depending on the inputs. These two circuits were created by using boolean logic concepts and k-maps.   
[Project 1 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab01/assignment.php)

**Project 2: Intro to VHDL**  
This project focused on introducing VHDL and give more experience with digital design techniques. I would create one circuit that controls a 7-segment display using techniques used in the previous project. With that circuit, I created a driver that controls the display and an application that would add two unsigned integers in VHDL. The latter would add two single-digit hexadecimal numbers and display 2-digit hexadecimal number. The results would displayed using the display driver.  
[Project 2 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab02/assignment.php)

**Project 3: Reaction Timer**  
This project focused on using a state machine to create a reaction-based timer. This reaction-based timer program was written in VHDL and used enumerated type to represent the different states. The machine would initial be in an idle state. When the start button, the machine moves into wait state (indicated by a red LED). The machine begins to increase the value of an internal value and moves out of the wait state after the internal counter eaches some value. The machine moves into the count state (indicated by a green LED). The internal counter will continue to increase in value until the react button is pressed. Then, the user can see how long it took them to press the button.  
[Project 3 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab03/assignment.php)

**Project 4: Programmable Lights**  
This project focuses on creating simple programmable devices. I developed a device that controls the state of several LEDs and implements a small instruction set. Using ROM, you can use the instruction set to create applications that sequentally run instructions from the instruction set. The machine is driven by a state machine that alters between fetching and executing commands.  
[Project 4 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab04/assignment.php)

**Project 5: Programmable Lights II**  
This project is focused on expanding the previous project by adding MOVE command, condition branching, and uncondition branching to the instruction. To make these change, the syntax of the instructions is entirely rechanged categorizing instructions into four groups. The first category is all instructions that move the stored value of one register to another. The second categroy implements binary instruction and stores the result in an internal register. Most of these instruction were developed in the previous project. The last two instructions are conditional and unconditional branch which allows the an application to jump to any line of code.   
[Project 5 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab05/assignment.php)

**Project 6: Stack-based Calculator**  
This project is focused on using RAM as a part of our application. I implemented a stack-based calculator where the stack is stored in the RAM. The applications works by designating switches on the board to either store values or select an operation. The user would enter a value of one of the operands on the board and a capture button would allow the machine to read and display value set by the switches. The enter button would push the captured value into the stack. If at least two values are stored in the stack, then pressing the action would compute a mathematical operation with the top two entries being the operands and the result is displayed on the screen.. The operation computed depends on the two switches that selects an operation.   
[Project 6 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab06/assignment.php)

**Project 7: Generic CPU**  
This project focuses on implementing a simple CPU. This project takes all the aspects of the preivous projects and implements in this CPU. This CPU consists of an ALU, RAM, and variety of registers. The ALU implements most of the instructions in Programmable Lights projects. The RAM implements the stack features used in project 6. The CPU is based on a state machine that allocates different for reading instructions, reading and writing values to register and/or the stack, and computing arthematical operations. Due to the design of this CPU, the source code of thi s CPU had to be written from scratch.   
[Project 7 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab07/assignment.php)

**Project 8: Assembler**  
This project focuses on using mnemonic languages to more easily create machine code. For this project, I created an assembler program in Python that converts the synatx of the assembly language provided in the project instructions into the machine code for the CPU from the previous project. This mnemonic language can call all commands from the CPU's instruction set.   
[Project 8 Instructions](https://cs.colby.edu/courses/F20/cs232-labs/labs/lab08/assignment.php)
