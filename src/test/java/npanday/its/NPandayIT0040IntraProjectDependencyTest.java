package npanday.its;

/*
 * Copyright 2009
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

public class NPandayIT0040IntraProjectDependencyTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT0040IntraProjectDependencyTest()
    {
        super( "[1.0.2,)" );
    }

    public void testIntraProjectDependency()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT0040IntraProjectDependencyTest" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        File libDir = new File( testDir, "ClassLibrary3" );
        verifier.assertFilePresent(
            new File( libDir, getAssemblyFile( "ClassLibrary3", "1.0-SNAPSHOT", "dll" ) ).getAbsolutePath() );
        verifier.assertFilePresent( new File( libDir, "target/test-assemblies/ClassLibrary1.dll" ).getAbsolutePath() );
        verifier.assertFilePresent( new File( libDir, "target/test-assemblies/ClassLibrary2.dll" ).getAbsolutePath() );
        verifier.assertFilePresent( new File( libDir, "target/test-assemblies/ClassLibrary3.dll" ).getAbsolutePath() );
        verifier.assertFilePresent(
            new File( libDir, "target/test-assemblies/NUnit.Framework.dll" ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
