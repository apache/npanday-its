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

public class NPandayIT13018TransitiveDependenciesTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT13018TransitiveDependenciesTest()
    {
        super( "[1.2,)" );
    }

    public void testLadderBuild()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT13018/LadderBuild" );
        Verifier verifier = getVerifier( testDir );

        verifier.deleteArtifact( "test", "ladder1", "2.0.0.0", "dll" );

        verifier.executeGoal( "install" );
        verifier.assertFilePresent(
            new File( testDir, getAssemblyFile( "ladder1", "2.0.0.0", "dll" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
    
    public void testTransitiveDependenciesNotOnCompile()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT13018/TransDependency" );
        Verifier verifier = getVerifier( testDir );

        verifier.deleteArtifact( "test", "NPandayIT13018", "1.0.0.0", "dll" );

        verifier.executeGoal( "install" );
        verifier.assertFilePresent(
            new File( testDir, getAssemblyFile( "NPandayIT13018", "1.0.0.0", "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "NPandayIT13018", "1.0.0.0",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}