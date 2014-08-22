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

import java.io.File;

public class NPANDAY_227_ResGenResourcesTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_227_ResGenResourcesTest()
    {
        super( "[1.5.0-incubating,)" );
    }

    public void testWithResourcesDirectory()
        throws Exception
    {
        Verifier verifier = getDefaultVerifier();
        String testDir = verifier.getBasedir();
        verifier.executeGoal( "package" );
        String assembly = new File( testDir,
                getAssemblyFile( "NPANDAY_227_ResGenResourcesTest", "1.0.0", "dll" ) ).getAbsolutePath();
        verifier.assertFilePresent( assembly );
        String path = "target/assembly-resources/resource/NPANDAY_227_ResGenResourcesTest.Resource1.resources";
        verifier.assertFilePresent( new File( testDir, path ).getAbsolutePath() );
        assertResourcePresent( assembly, "Resource1.resources" );
        assertClassPresent( assembly, "ClassLibrary1.Resource1" );
        verifier.verifyErrorFreeLog();
    }
}
