/*
 * File: Project.cs
 * Purpose: To test a linked list in C#
 * Author: Samuel Munoz
 * Date: 03-22-2020
 */

using System;

namespace Project_5
{
   	public partial class Program
    {
    	static bool compFunc<T>(T v1, T v2) {
    		return v1.Equals(v2);
    	}

    	// I honestly have no clue how to modify generics in C#
    	static T mapFunc<T>(T data) {
    		return data;
    	}

        static void Main(string[] args)
        {
            LinkedList<int> l = new LinkedList<int>();
            CompFunc<int> compFunc = new CompFunc<int>(Program.compFunc);
            MapFunc<int> mapFunc = new MapFunc<int>(Program.mapFunc);
            LinkedList<char> c = new LinkedList<char>();
            CompFunc<char> ccompFunc = new CompFunc<char>(Program.compFunc);
            MapFunc<char> cmapFunc = new MapFunc<char>(Program.mapFunc);

            Console.WriteLine("------------------ Int List -----------------------");

            for(int i=0;i < 5;i++)
            	l.push(i+1);
            // for(int i=5;i < 10;i++)
            // 	l.append(i+1);
            Console.WriteLine("Linked List: " + l.ToString());
            Console.WriteLine("Size: " + l.size());

            Console.WriteLine("\nPop: " + l.pop());
            Console.WriteLine("Remove: " + l.remove(2, compFunc));
            Console.WriteLine(); // new line printed on console

            Console.WriteLine("Modified Linked List: " + l.ToString());
            Console.WriteLine("Size: " + l.size());

            l.map(mapFunc);
            Console.WriteLine("\nMapped");
            Console.WriteLine("Modified Linked List: " + l.ToString());
            Console.WriteLine("Size: " + l.size());
            Console.WriteLine(); // new line printed on console

            l.clear();
            Console.WriteLine("\nCleared");
            Console.WriteLine(); // new line printed on console

            Console.WriteLine("Modified Linked List: " + l.ToString());
            Console.WriteLine("Size: " + l.size());

            Console.WriteLine("---------------------------------------------------");

            Console.WriteLine("\n------------------ Char List ----------------------");

            for(int i=97;i < 102;i++)
                c.push((char) i);
            for(int i=102;i < 107;i++)
                c.append((char) i);
            Console.WriteLine("Linked List: " + c.ToString());
            Console.WriteLine("Size: " + c.size());

            Console.WriteLine("\nPop: " + c.pop());
            Console.WriteLine("Remove: " + c.remove('b', ccompFunc));
            Console.WriteLine(); // new line printed on console

            Console.WriteLine("Modified Linked List: " + c.ToString());
            Console.WriteLine("Size: " + c.size());

            c.map(cmapFunc);
            Console.WriteLine("\nMapped");
            Console.WriteLine("Modified Linked List: " + c.ToString());
            Console.WriteLine("Size: " + c.size());
            Console.WriteLine(); // new line printed on console

            c.clear();
            Console.WriteLine("\nCleared");
            Console.WriteLine(); // new line printed on console

            Console.WriteLine("Modified Linked List: " + c.ToString());
            Console.WriteLine("Size: " + c.size());

            Console.WriteLine("----------------------------------------------------");
        }
    }
}

/*
Source
https://www.geeksforgeeks.org/nested-classes-in-c-sharp/
https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/generics/
https://www.dotnetperls.com/substring
https://www.c-sharpcorner.com/UploadFile/3d39b4/partial-classes-in-C-Sharp-with-real-example/
http://www.net-informations.com/q/faq/type.html
*/