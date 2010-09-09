
namespace NPanday.IT {
	using NUnit.Framework;
	using System;

	[TestFixture]
	public class It0003Test2  {
		private String hello = "hello";

		[SetUp]
		protected void SetUp() {
		}

		[Test]
		public void TestSample() {
			Assert.AreEqual("hello", hello);	
		}
		
	}

}