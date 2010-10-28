using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Globalization;

namespace ClassLibrary1
{
    public class Class1
    {
        static void Main(string[] args)
        {
            string iString = "3:17:14:48,153";
            string format = "G";
            TimeSpan t;
            CultureInfo culture = CultureInfo.CurrentCulture;

            t = TimeSpan.ParseExact(iString, format, culture);
            Console.WriteLine("'{0}' --> {1}", iString, t);       
		}		
    }
}
