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

public class NPandayIT0001VerifyInstallationTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT0001VerifyInstallationTest()
    {
        super( "[1.0.2,)" );
    }

    public void testCompiler()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT0001" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        String assembly = new File( testDir, getAssemblyFile( "NPandayIT0001", "1.0.0.0", "dll" ) ).getAbsolutePath();
        verifier.assertFilePresent( assembly );
        assertClassPresent( assembly, "It0001" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}