package npanday.its;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.Verifier;

import java.io.File;

public class NPANDAY_330_VS2010MvcProjectSupportTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_330_VS2010MvcProjectSupportTest()
    {
        super( "[1.4.0-incubating,)", FRAMEWORK_V4_0 );

        skipIfMissingMVC2();
    }

    public void testMVC2010Project()
        throws Exception
    {
        Verifier verifier = getDefaultVerifier();
        String testDir = verifier.getBasedir();
        verifier.executeGoal( "install" );
        		
		String assembly = new File( testDir + "/NPanday330MVC2010ProjectTest",
            getAssemblyFile( "NPanday330MVC2010ProjectTest", "1.0.0.0", "dll" ) ).getAbsolutePath();
        verifier.assertFilePresent( assembly );
		
		assertClassPresent( assembly, "NPanday330MVC2010ProjectTest.Controllers.AccountController" );
        assertClassPresent( assembly, "NPanday330MVC2010ProjectTest.Controllers.HomeController" );
        assertClassPresent( assembly, "NPanday330MVC2010ProjectTest.MvcApplication" );
        verifier.verifyErrorFreeLog();
    }
}
