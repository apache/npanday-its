using System.Reflection;
using System.Resources;
using NUnit.Framework;

namespace ClassLibrary1
{
    [TestFixture]
    public class Class1
    {
        [Test]
        public void test()
        {
            ResourceManager resman = new ResourceManager( "ClassLibrary1.Resource1", Assembly.GetExecutingAssembly() );
            Assert.AreEqual("Value1", resman.GetString( "Key1" ) );
            Assert.AreEqual("Value2", resman.GetString( "Key2" ) );
        }
    }
}
