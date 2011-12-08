using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.SessionState;

namespace TestGlobal
{
    public class Global : System.Web.HttpApplication
    {

        protected void Application_OnStart(object sender, EventArgs e)
        {
            Application["Hits"] = "100";
        }

        protected void Session_OnStart(object sender, EventArgs e)
        {

        }

        protected void Application_OnBeginRequest(object sender, EventArgs e)
        {
            string s = Application["Hits"] as string;
            int a = Convert.ToInt32(s);
            a++;

            Application["Hits"] = a.ToString();
        }

        protected void Application_OnAuthenticateRequest(object sender, EventArgs e)
        {

        }

        protected void Application_OnError(object sender, EventArgs e)
        {
            Application["Hits"] = "200";
        }

        protected void Session_OnEnd(object sender, EventArgs e)
        {

        }

        protected void Application_OnEnd(object sender, EventArgs e)
        {

        }
    }
}