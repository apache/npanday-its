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
import org.apache.maven.it.util.FileUtils;
import org.apache.maven.it.util.IOUtil;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class NPANDAY_268_TransitiveDependenciesTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_268_TransitiveDependenciesTest()
    {
        super( "[1.2,)" );
    }

    public void testLadderBuild()
        throws Exception
    {
        File testDir = unzipResources( "/NPANDAY_268_TransitiveDependenciesTest/LadderBuild.zip" );
        Verifier verifier = getVerifier( testDir );

        verifier.deleteArtifact( "test", "ladder1", "2.0.0.0", "dll" );

        verifier.executeGoal( "install" );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "ladder1", "2.0.0.0",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

    public void testTransitiveDependenciesNotOnCompile()
        throws Exception
    {
        File testDir = unzipResources( "/NPANDAY_268_TransitiveDependenciesTest/TransDependency.zip" );
        Verifier verifier = getVerifier( testDir );

        verifier.deleteArtifact( "test", "NPandayIT13018", "1.0.0.0", "dll" );

        verifier.executeGoal( "install" );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "NPandayIT13018", "1.0.0.0",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir, getAssemblyFile( "NPandayIT13018", "1.0.0.0",
                                                                        "dll" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
