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

import java.io.File;

import org.apache.maven.it.Verifier;
import org.apache.maven.it.VerificationException;
import org.apache.maven.it.util.ResourceExtractor;

public class NPandayIT0004NUnitTestVerification
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT0004NUnitTestVerification()
    {
        super( "(1.1,)" );
    }

    public void testIT0004NUnitVerification()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT0004" );
        Verifier verifier = getVerifier( testDir );
		verifier.executeGoal( "install" );
		
		verifier.assertFilePresent( new File( testDir + "/" +
			getAssemblyFile( "NPandayIT0004-test", "0.0.0.0", "dll", null ) ).getAbsolutePath() );

		verifier.assertFilePresent( new File( testDir + "/" +
			getAssemblyFile( "NPandayIT0004/nunit-reports/TEST-NPandayIT0004-1-SNAPSHOT", null, "xml", null ) ).getAbsolutePath() );	
		
		verifier.assertFilePresent( new File( testDir + "/" +
			getAssemblyFile( "NPandayIT0004/test-assemblies/NUnit.Framework", null, "dll", null ) ).getAbsolutePath() );
			
		verifier.verifyErrorFreeLog();
		verifier.resetStreams();
    }
}
