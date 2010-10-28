package npanday.its;

/*
 * Copyright 2010
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
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

public class NPandayIT0036InstalledArtifactsVerificationTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT0036InstalledArtifactsVerificationTest()
    {
        super( "[1.0.2,)" );
    }

    public void testIT0036InstalledArtifacts()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT0036" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        verifier.assertFilePresent(
            new File( testDir, getAssemblyFile( "NPandayIT0036", "1.0.0.0", "exe" ) ).getAbsolutePath() );
        verifier.assertFilePresent(
            new File( testDir, getAssemblyFile( "NPandayIT0036-test", "1.0.0.0", "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "test-assemblies/NPandayIT0036", "1.0.0.0",
                                                                        "exe" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "test-assemblies/NPandayIT0036-test", "1.0.0.0",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "test-assemblies/NUnit.Framework", "1.0.0.0",
                                                                        "dll" ) ).getAbsolutePath() );

        verifier.assertFilePresent( new File( testDir, getBuildSourcesMain( "Module1.vb" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getBuildSourcesMain( "folder/Module2.vb" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getBuildSourcesMain( "should-not-be-copied.txt" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getBuildSourcesMain( "should-not-be-copied.xml" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getBuildSourcesMain( "folder/should-not-be-copied-2.txt" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getBuildSourcesMain( "folder/should-not-be-copied-2.xml" ) ).getAbsolutePath() );

        verifier.assertFilePresent( new File( testDir, getTestSourcesMain( "Module1.vb" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getTestSourcesMain( "folder/Module2.vb" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getTestSourcesMain( "should-not-be-copied-test.txt" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getTestSourcesMain( "should-not-be-copied-test.xml" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getTestSourcesMain( "folder/should-not-be-copied-test-2.txt" ) ).getAbsolutePath() );
        verifier.assertFileNotPresent(
            new File( testDir, getTestSourcesMain( "folder/should-not-be-copied--test-2.xml" ) ).getAbsolutePath() );

        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
