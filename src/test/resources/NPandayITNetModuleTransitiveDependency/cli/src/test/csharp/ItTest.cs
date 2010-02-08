
namespace NPanday.IT {
	using NUnit.Framework;
	using System;

	[TestFixture]
	public class ItTest  {
		private Lib lib = new Lib();

		[SetUp]
		protected void SetUp() {
		}

		[Test]
		public void TestSample() {
			Assert.AreEqual("it0002", lib.getItValue());
		}

	}

}