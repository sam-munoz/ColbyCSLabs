/*
 * File: Program.cs
 * Purpose: To show all the different data types in C#
 * Author: Samuel Munoz
 * Date: 03-03-2020
 */

using System;

namespace task3
{
	interface NotUsed {
		void printNotUsed();
	}

	class Person {
		private string name;

		public Person(string s) { name = s; } 
		public string getName() { return name; }
		public void setName(string s) { name = s; }
	}

    class Program
    {
        static void Main(string[] args)
        {
            // boolean value
            bool boolean = true;
            // 8-bit unsigned integer
            byte b = 1;
            // 16-bit Unicode character
            char c = 'a';
            // 128-bit precise decimal values with 28-29 significant digits
            decimal dec = 2;
            // 64-bit double-precision floating point type
            double dou = 3;
            // 32-bit single-precision floating point type
            float f = 4;
            // 32-bit signed integer type
            int i = 5;
            // 64-bit signed integer type
            long l = 6;
            // sbyte: 8-bit signed integer type
            sbyte sb = 7;
            // 16-bit signed integer type
            short s = 8;
            // 32-bit unsigned integer type
            uint ui = 9;
            // 64-bit unsigned integer type
            ulong ul = 10;
		// 16-bit unsigned integer type
            ushort us = 11;

            // Object type: object is base class for all other objects
            object o = 100; // o is a reference to the object created
            // Dynamic type: it is placeholder that can changes type as variable's type is determined during runtime
            dynamic d_int = 20;
            dynamic d_char = 'd';
            // String types
            string str = "New string";
            // Pointers
            // int *ptr = &i; Cannot run since pointers can only be runned in unsafe context
            // Classes
            Person p = new Person("Juan");

            Console.WriteLine("Value Types");
            Console.WriteLine("Boolean:\tsize: " + sizeof(bool) + "\t\tvalue: " + boolean + "\t\t!: " + !boolean);
            Console.WriteLine("Byte:\t\tsize: " + sizeof(byte) + "\t\tvalue: " + b + "\t\t+: " + (b+1) + "\t-: " + (b-1) + "\t*: " + (b*2) + "\t/: " + (b/2) + "\t%: " + (b%2));
            Console.WriteLine("Character:\tsize: " + sizeof(char) + "\t\tvalue: " + c + "\t\t+: " + (c+1) + "\t-: " + (c-1) + "\t*: " + (c*2) + "\t/: " + (c/2) + "\t%: " + (c%2));
            Console.WriteLine("Decimal:\tsize: " + sizeof(decimal) + "\tvalue: " + dec + "\t\t+: " + (dec+1) + "\t-: " + (dec-1) + "\t*: " + (dec*2) + "\t/: " + (dec/2) + "\t%: " + (dec%2));
            Console.WriteLine("Double:\t\tsize: " + sizeof(double) + "\t\tvalue: " + dou + "\t\t+: " + (dou+1) + "\t-: " + (dou-1) + "\t*: " + (dou*2) + "\t/: " + (dou/2) + "\t%: " + (dou%2));
            Console.WriteLine("Float:\t\tsize: " + sizeof(float) + "\t\tvalue: " + f + "\t\t+: " + (f+1) + "\t-: " + (f-1) + "\t*: " + (f*2) + "\t/: " + (f/2) + "\t%: " + (f%2));
            Console.WriteLine("Integer:\tsize: " + sizeof(int) + "\t\tvalue: " + i + "\t\t+: " + (i+1) + "\t-: " + (i-1) + "\t*: " + (i*2) + "\t/: " + (i/2) + "\t%: " + (i%2));
            Console.WriteLine("Long:\t\tsize: " + sizeof(long) + "\t\tvalue: " + l + "\t\t+: " + (l+1) + "\t-: " + (l-1) + "\t*: " + (l*2) + "\t/: " + (l/2) + "\t%: " + (l%2));
            Console.WriteLine("Sbyte:\t\tsize: " + sizeof(sbyte) + "\t\tvalue: " + sb + "\t\t+: " + (sb+1) + "\t-: " + (sb-1) + "\t*: " + (sb*2) + "\t/: " + (sb/2) + "\t%: " + (sb%2));
            Console.WriteLine("Short:\t\tsize: " + sizeof(short) + "\t\tvalue: " + s + "\t\t+: " + (s+1) + "\t-: " + (s-1) + "\t*: " + (s*2) + "\t/: " + (s/2) + "\t%: " + (s%2));
            Console.WriteLine("Uint:\t\tsize: " + sizeof(uint) + "\t\tvalue: " + ui + "\t\t+: " + (ui+1) + "\t-: " + (ui-1) + "\t*: " + (ui*2) + "\t/: " + (ui/2) + "\t%: " + (ui%2));
            Console.WriteLine("Ulong:\t\tsize: " + sizeof(ulong) + "\t\tvalue: " + ul + "\t\t+: " + (ul+1) + "\t-: " + (ul-1) + "\t*: " + (ul*2) + "\t/: " + (ul/2) + "\t%: " + (ul%2));
            Console.WriteLine("Ushort:\t\tsize: " + sizeof(ushort) + "\t\tvalue: " + us + "\t\t+: " + (us+1) + "\t-: " + (us-1) + "\t*: " + (us*2) + "\t/: " + (us/2) + "\t%: " + (us%2));

            Console.WriteLine("\nReference Type");
            Console.WriteLine("Object\t\tReference: " + o);
            Console.WriteLine("Dynamics:");
            Console.WriteLine("\t\tInteger: " + d_int);
            Console.WriteLine("\t\tCharacter: " + d_char);
            Console.WriteLine("\nString\t\tValue: " + str);
            Console.WriteLine("Classes\t\tName: " + p.getName());
        }
    }
}

/*
Source
https://www.tutorialspoint.com/csharp/csharp_data_types.htm
https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/classes-and-structs/classes
https://docs.microsoft.com/en-us/dotnet/csharp/tour-of-csharp/interfaces
*/