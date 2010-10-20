package npanday.its;

/*
 * Copyright 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;

public class NPandayIT330MVC2010ProjectTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT330MVC2010ProjectTest()
    {
        super( "[1.2,)", "[v4.0.30319,)" ); 

        File f = new File( System.getenv( "SYSTEMROOT" ), "assembly/GAC_MSIL/System.Web.MVC" );
        if ( !f.exists() || !f.isDirectory() )
        {
            skipReason = "MVC.NET is not installed";
            skip = true;
        }
    }

    public void testMVC2010Project()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPanday330MVC2010ProjectTest" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        		
		String assembly = new File( testDir + "/NPanday330MVC2010ProjectTest",
            getAssemblyFile( "NPanday330MVC2010ProjectTest", "1.0.0.0", "dll" ) ).getAbsolutePath();
        verifier.assertFilePresent( assembly );
		
		assertClassPresent( assembly, "NPanday330MVC2010ProjectTest.Controllers.AccountController" );
        assertClassPresent( assembly, "NPanday330MVC2010ProjectTest.Controllers.HomeController" );
        assertClassPresent( assembly, "NPanday330MVC2010ProjectTest.MvcApplication" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
