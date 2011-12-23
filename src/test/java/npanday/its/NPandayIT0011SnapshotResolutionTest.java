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
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;

public class NPandayIT0011SnapshotResolutionTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT0011SnapshotResolutionTest()
    {
        super( "[1.0.2,)" );
    }

    public void testUniqueSnapshotResolution()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT0011SnapshotResolutionTest/unique" );
        Verifier verifier = getVerifier( testDir );

        verifier.deleteArtifact( "test", "test-snapshot", "1.0-SNAPSHOT", "dll" );

        verifier.executeGoal( "install" );
        verifier.assertFilePresent(
            new File( testDir, getAssemblyFile( "test-unique-snapshot", "1.0-SNAPSHOT", "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "test-unique-snapshot-test", "1.0-SNAPSHOT",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent(
            new File( testDir, "target/test-assemblies/test-unique-snapshot.dll" ).getAbsolutePath() );
        verifier.assertFilePresent(
            new File( testDir, "target/test-assemblies/test-unique-snapshot-test.dll" ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, "target/test-assemblies/test-snapshot.dll" ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

    public void testNonUniqueSnapshotResolution()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT0011SnapshotResolutionTest/non-unique" );
        Verifier verifier = getVerifier( testDir );

        verifier.deleteArtifact( "test", "test-snapshot", "1.0-SNAPSHOT", "dll" );

        verifier.executeGoal( "install" );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "test-non-unique-snapshot", "1.0-SNAPSHOT",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "test-non-unique-snapshot-test", "1.0-SNAPSHOT",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent(
            new File( testDir, "target/test-assemblies/test-non-unique-snapshot.dll" ).getAbsolutePath() );
        verifier.assertFilePresent(
            new File( testDir, "target/test-assemblies/test-non-unique-snapshot-test.dll" ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, "target/test-assemblies/test-snapshot.dll" ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
