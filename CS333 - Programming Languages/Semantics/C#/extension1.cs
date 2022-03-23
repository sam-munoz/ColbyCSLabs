/*
 * File: Program.cs
 * Purpose: To show how polymorphism works in C#
 * Author: Samuel Munoz
 * Date: 03-11-2020
 */

using System;

namespace extension1
{
    class Program
    {
    	// this class shows static polymorphism and specifically overloading methods
		public class OLM {
			public static void printData(int i) {
				Console.WriteLine("Data: " + i);	
			}
			
			public static void printData(string s) {
				Console.WriteLine("Data: " + s);	
			}
			
			public static void printData(bool b) {
				Console.WriteLine("Data: " + b);
			}
		}
	
		// this section is about dyanmic polymorphism which is about how abstract classes can be used to provide a partial interface in child classes
		public abstract class Abstract {
			public abstract void printMe1();
			public virtual void printMe2() {
				Console.WriteLine("printMe from Abstract class");	
			}
		}
	
		public class Child: Abstract {
			public override void printMe1() {
				Console.WriteLine("This printMe statment comes from the Child class");
			}
			
			public override void printMe2() {
				Console.WriteLine("printMe from Child class");
			}
		}
		
		public class NewChild: Abstract {
			public override void printMe1() {
				Console.WriteLine("This printMe statment comes from the NewChild class");
			}
		}

        static void Main(string[] args)
        {
            // these methods show how the class OLM overloads the method printData
			OLM.printData(12);
			OLM.printData(true);
			OLM.printData("fdsafda");
			
			// these methods show how the Child class overrides the Child class
			Child c1 = new Child();
			NewChild nc = new NewChild();
			
			// printMe1 shows how both Child and NewChild class override the Abstract class
			c1.printMe1();
			nc.printMe1();
			
			// printMe2 shows how the Child class overrides the Abstract class method printMe2, but NewChild does not override it, so the class uses the Abstract class method
			c1.printMe2();
			nc.printMe2();
        }
    }
}

/*
Sources
https://www.tutorialspoint.com/csharp/csharp_polymorphism.htm
*/