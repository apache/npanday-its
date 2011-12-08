using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace TestGlobal
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            TextBox1.Text = Application["Hits"] as string;

            Response.Write("<br/>ApplicationInstance: " +
            Context.ApplicationInstance.GetType().FullName);
        }
    }
}
