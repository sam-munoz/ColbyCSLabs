using System;
using System.Drawing;

namespace task2
{
    class Program
    {
        static void Main(string[] args)
        {
            // get if enough command line arguments are entered
            if(args.Length < 2)
            {
                Console.WriteLine("command: dotnet run <filename thread_count>");
                return;
            }
        }
    }
}
