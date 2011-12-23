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

public class NPandayIT0002NetModuleDependencyTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayIT0002NetModuleDependencyTest()
    {
        super( "[1.0.2,)" );
    }

    public void testNetModuleDependency()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPandayIT0002NetModuleDependencyTest" );
        File testModuleDir = new File( testDir, "dependency" );
        Verifier verifier = getVerifier( testModuleDir );
        verifier.executeGoal( "install" );
        verifier.assertFilePresent(
            new File( testModuleDir, getAssemblyFile( "dependency", "1.0.0.0", "netmodule" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        testModuleDir = new File( testDir, "library" );
        verifier = getVerifier( testModuleDir );
        verifier.executeGoal( "install" );
        verifier.assertFilePresent(
            new File( testModuleDir, getAssemblyFile( "dependency", "1.0.0.0", "netmodule" ) ).getAbsolutePath() );
        verifier.assertFilePresent(
            new File( testModuleDir, getAssemblyFile( "library", "1.0.0.0", "dll" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
