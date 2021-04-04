# Template by Bruce A. Maxwell, 2015
#
# implements a simple assembler for the following assembly language
# 
# - One instruction or label per line.
#
# - Blank lines are ignored.
#
# - Comments start with a # as the first character and all subsequent
# - characters on the line are ignored.
#
# - Spaces delimit instruction elements.
#
# - A label ends with a colon and must be a single symbol on its own line.
#
# - A label can be any single continuous sequence of printable
# - characters; a colon or space terminates the symbol.
#
# - All immediate and address values are given in decimal.
#
# - Address values must be positive
#
# - Negative immediate values must have a preceeding '-' with no space
# - between it and the number.
#

# Language definition:
#
# LOAD D A   - load from address A to destination D
# LOADA D A  - load using the address register from address A + RE to destination D
# STORE S A  - store value in S to address A
# STOREA S A - store using the address register the value in S to address A + RE
# BRA L      - branch to label A
# BRAZ L     - branch to label A if the CR zero flag is set
# BRAN L     - branch to label L if the CR negative flag is set
# BRAO L     - branch to label L if the CR overflow flag is set
# BRAC L     - branch to label L if the CR carry flag is set
# CALL L     - call the routine at label L
# RETURN     - return from a routine
# HALT       - execute the halt/exit instruction
# PUSH S     - push source value S to the stack
# POP D      - pop form the stack and put in destination D
# OPORT S    - output to the global port from source S
# IPORT D    - input from the global port to destination D
# ADD A B C  - execute C <= A + B
# SUB A B C  - execute C <= A - B
# AND A B C  - execute C <= A and B  bitwise
# OR  A B C  - execute C <= A or B   bitwise
# XOR A B C  - execute C <= A xor B  bitwise
# SHIFTL A C - execute C <= A shift left by 1
# SHIFTR A C - execute C <= A shift right by 1
# ROTL A C   - execute C <= A rotate left by 1
# ROTR A C   - execute C <= A rotate right by 1
# MOVE A C   - execute C <= A where A is a source register
# MOVEI V C  - execute C <= value V
#

# 2-pass assembler
# pass 1: read through the instructions and put numbers on each instruction location
#         calculate the label values
#
# pass 2: read through the instructions and build the machine instructions
#

import sys

# define all constant dictionaries
op = {
        "load": "00000",
        "loada": "00001",
        "store": "00010",
        "storea": "00011",
        "bra": "00100000",
        "braz": "00110000",
        "bran": "00110010",
        "brao": "00110001",
        "brac": "00110011",
        "call": "00110100",
        "return": "0011100000000000",
        "halt": "0011110000000000",
        "push": "0100",
        "pop": "0101",
        "oport": "0110",
        "iport": "0111",
        "add": "1000",
        "sub": "1001",
        "and": "1010",
        "or": "1011",
        "xor": "1100",
        "shiftl": "11010",
        "shiftr": "11011",
        "rotl": "11100",
        "rotr": "11101",
        "move": "11110",
        "movei": "11111",
}

register = {
        "ra": "000",
        "rb": "001",
        "rc": "010",
        "rd": "011",
        "re": "100",
        "sp": "101",
        "pc": "110",
        "cr": "111",
        "ir": "111",
        "zeros": "110",
        "ones": "111",
} 

# converts d to an 8-bit 2-s complement binary value
def dec2comp8( d, linenum ):
    try:
        if d > 0:
            l = d.bit_length()
            v = "00000000"
            v = v[0:8-l] + format( d, 'b')
        elif d < 0:
            dt = 128 + d
            l = dt.bit_length()
            v = "10000000"
            v = v[0:8-l] + format( dt, 'b')[:]
        else:
            v = "00000000"
    except:
        print 'Invalid decimal number on line %d' % (linenum)
        exit()

    return v

# converts d to an 8-bit unsigned binary value
def dec2bin8( d, linenum ):
    if d > 0:
        l = d.bit_length()
        v = "00000000"
        v = v[0:8-l] + format( d, 'b' )
    elif d == 0:
        v = "00000000"
    else:
        print 'Invalid address on line %d: value is negative' % (linenum)
        exit()

    return v


# Tokenizes the input data, discarding white space and comments
# returns the tokens as a list of lists, one list for each line.
#
# The tokenizer also converts each character to lower case.
def tokenize( fp ):
    tokens = []

    # start of the file
    fp.seek(0)

    lines = fp.readlines()

    # strip white space and comments from each line
    for line in lines:
        ls = line.strip()
        uls = ''
        for c in ls:
            if c != '#':
                uls = uls + c
            else:
                break

        # skip blank lines
        if len(uls) == 0:
            continue

        # split on white space
        words = uls.split()

        newwords = []
        for word in words:
            newwords.append( word.lower() )

        tokens.append( newwords )

    return tokens


# reads through the file and returns a dictionary of all location
# labels with their line numbers
def pass1( tokens ):
    # create empty dictionary to keep store instructions with their line numbers
    table = {}

    # print 'Print table: %s' % (table.get('x'))

    # loop through all tokens and assign them their line numbers
    for i in range(0, len(tokens), 1):
        # if the line does not contain a symbol; move on to the next instruction
        if len(tokens[i]) > 1:
            continue
        
        # store symbol into temp variable
        symbol = tokens[i][0][:len(tokens[i][0])-1]
        # print symbol

        # otherwise, add to symbol to table; check if symbol is not in table
        if not table.has_key(symbol):
            # check if symbol is either the return or halt command; if so, move to next instruction
            if symbol == 'hal' or symbol == 'retur':
                continue

            table[symbol] = i

        # if symbol is in table; throw error message
        else:
            print 'Symbol \'%s\' is used twice at line %d. Please remove duplicate symbol.' % (symbol, i+1)
            sys.exit(0)

    # remove all line with symbols from tokens field
    sorted_array = sorted( table, key=table.get )

    # print sorted_array
    for i in range(0, len(table), 1):
        index = tokens.index( [ str( sorted_array[i] + ':' ) ] )
        # print index
        del tokens[index]
        table[sorted_array[i]] = table[sorted_array[i]] - i

    return table

def pass2( tokens, labels ):
    # store final machine code
    code = []

    # iterate through tokens and convert each line of assemble code into machine code
    for i in range(0, len(tokens), 1):
        # check on the number tokens per line; if length one, then this is either a return or halt instruction
        if len(tokens[i]) == 1:
            if op.has_key( tokens[i][0] ):
                code.append( op[tokens[i][0]] )
            else:
                print 'At line %d, %s is not a real instruction. Please only use the supported instructions' % (i, tokens[i][0])
                sys.exit(0)

        # check if list has two tokens; if so, this is a bra, braz, bran, brao, brac, call, push, pop, oport, or iport
        elif len(tokens[i]) == 2:
            if op.has_key( tokens[i][0] ):
                line = op[ tokens[i][0] ]
            else:
                print 'At line %d, %s is not a real instruction. Please only use the supported instructions' % (i, tokens[i][0])
                sys.exit(0)
            
            # check if the second token is a register; if so convert to binary; this instruction must be push, pop, oport, or iport
            if register.has_key( tokens[i][1] ):
                line += register[ tokens[i][1] ]
                line += "000000000"
        
            # check if the second token is a symbol
            elif labels.has_key( tokens[i][1] ):
                line += dec2comp8( labels[ tokens[i][1] ], i )  

            # otherwise, the second token must be an intermediate value. this instruction must be a bra, braz, bran, brao, brac, or call
            else:
                line += dec2bin8( tokens[i][1], i )

            # add line to list of machine code
            code.append(line)

        # check if the list has three tokens; if so, this must be a load, loada, store, storea, shiftl, shiftr, rotl, rotr, move, or movei
        elif len(tokens[i]) == 3:
            if op.has_key( tokens[i][0] ):
                line = op[ tokens[i][0] ]
            else:
                print 'At line %d, %s is not a real instruction. Please only use the supported instructions' % (i, tokens[i][0])
                sys.exit(0)
                
            # check if the next token is a register; if so, then the instruction must not be movei
            if register.has_key( tokens[i][1] ):
                line += register[ tokens[i][1] ]

                # check if the next token is a register; is so, then the instruction must be shiftl, shiftr, rotl, rotr, or move
                # all these instructions have the same format. therefore, they all use the same format to make machine code
                if register.has_key( tokens[i][2] ):
                    line += "00000"
                    line += register[ tokens[i][2] ]

                # otherwise, the instruction must be load, loada, store, or storea
                # all these instruction have the same format. therefore, to easy to convert to machine code
                else:
                    line += dec2bin8( int(tokens[i][2]), i )

            # if next token is a intermediate value, then instruction must be movei. format has needed
            else:
                line += dec2comp8( int(tokens[i][1]), i )
                line += register[ tokens[i][2] ]

            # add to machine code list
            code.append(line)

        # check if the list has four tokens; if so, then the instruction must be a add, sub, or, or xor instruction
        elif len(tokens[i]) == 4:
            # check if instruction token contains a real instruction 
            if op.has_key( tokens[i][0] ):
                line = op[ tokens[i][0] ]
            else:
                print 'At line %d, %s is not a real instruction. Please only use the supported instructions' % (i, tokens[i][0])
                sys.exit(0)

            if register.has_key( tokens[i][1] ):
                line += register[ tokens[i][1] ]
            else:
                print 'At line %d, %s is not a register. Please only use the provided registers' % (i, tokens[i][1])
                sys.exit(0)

            if register.has_key( tokens[i][2] ):
                line += register[ tokens[i][2] ]
            else:
                print 'At line %d, %s is not a register. Please only use the provided registers' % (i, tokens[i][2])
                sys.exit(0)

            line += "000"
            if register.has_key( tokens[i][3] ):
                line += register[ tokens[i][3] ]
            else:
                print 'At line %d, %s is not a register. Please only use the provided registers' % (i, tokens[i][3])
                sys.exit(0)

            code.append(line)

        # any other length is invalid and an error message must display if this occurs
        else:
            print 'At line %d, instruction cannot contain %d number of tokens. Please check to make sure that instruction follows the correct format.' % (i, len(tokens[i]))
            sys.exit(0)

    return code

def main( argv ):
    if len(argv) < 2:
        print 'Usage: python %s <filename>' % (argv[0])
        exit()

    fp = file( argv[1], 'rU' )

    tokens = tokenize( fp )

    fp.close()

    # execute pass1 and pass2 then print it out as an MIF file
    labels = pass1(tokens)
    code = pass2(tokens, labels) 

    # print machine code
    print 'DEPTH = 256;'
    print 'WIDTH = 16;'
    print 'ADDRESS_RADIX = HEX;'
    print 'DATA_RADIX = BIN;'
    print 'CONTENT'
    print 'BEGIN'
    for i in range(0, len(code), 1):
        print '%02X : %s;' % (i, code[i])
    print '[%02X..FF] : 1111111111111111;' % (len(code))
    print 'END'

if __name__ == "__main__":
    main(sys.argv)
    
