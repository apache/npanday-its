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

public class NPANDAY_208_MsBuildCopyReferencesTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_208_MsBuildCopyReferencesTest()
    {
        super( "[1.2,)" );
    }

    public void testMsBuildCopyReferences()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPANDAY_208_MsBuildCopyReferencesTest" );
        Verifier verifier = getVerifier( testDir );
        // TODO: would be better to ensure each IT has unique IDs for required test artifacts in a better namespace for deleting
        verifier.deleteArtifacts( "test" );

        verifier.executeGoal( "compile" );
        verifier.assertFilePresent(
            new File( testDir + "/.references/test/test-snapshot-1.0-SNAPSHOT/test-snapshot.dll" ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir + "/bin/Debug/NPandayIT11695.dll" ).getAbsolutePath() );
        verifier.assertFilePresent( new File( testDir + "/bin/Debug/test-snapshot.dll" ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
