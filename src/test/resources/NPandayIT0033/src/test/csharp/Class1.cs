using System;
using System.Collections.Generic;
using System.Text;
using NUnit.Framework;

namespace NPanday.IT
{
[TestFixture]
    public class Class1
    {  
       [Test]
        public void testd()
        {
            new Module1.VBClass();
            Assert.AreEqual(10, 10);
        }
    }
}
