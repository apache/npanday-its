package npanday.its;

/*
 * Copyright 2009
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

public class NPandayIT12549WpfProjectTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT12549WpfProjectTest()
    {
        super( "[1.2,)", "[v3.5,)" ); // 1.2+
    }

    public void testWpfProject()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT12549" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "test" );
        String assembly = new File( testDir,
            getAssemblyFile( "NPandayIT12549", "1.0.0", "exe" ) ).getAbsolutePath();
        verifier.assertFilePresent( assembly );
        String path = "target/assembly-resources/resource/NPandayIT12549.Properties.Resources.resources";
        verifier.assertFilePresent( new File( testDir, path ).getAbsolutePath() );
        path = "target/assembly-resources/resource/NPandayIT12549.g.resources";
        verifier.assertFilePresent( new File( testDir, path ).getAbsolutePath() );
        assertResourcePresent( assembly, "Properties.Resources.resources" );
        assertResourcePresent( assembly, "g.resources" );
        assertClassPresent( assembly, "NPandayIT12549.Properties.Resources" );
        assertClassPresent( assembly, "NPandayIT12549.Properties.Settings" );
        assertClassPresent( assembly, "NPandayIT12549.App" );
        assertClassPresent( assembly, "NPandayIT12549.Window1" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}