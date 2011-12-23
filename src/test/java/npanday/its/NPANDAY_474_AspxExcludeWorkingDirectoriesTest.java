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
import java.util.Arrays;
import java.util.List;

public class NPANDAY_474_AspxExcludeWorkingDirectoriesTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_474_AspxExcludeWorkingDirectoriesTest()
    {
        super( "[1.4.1-incubating,)", "[v3.5,)" );
    }

    public void testExcludeWorkingDirectories()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPANDAY_474_AspxExcludeWorkingDirectoriesTest" );

        File svnDir = new File( testDir, ".svn" );
        svnDir.mkdirs();
        new File( svnDir, "entries" ).createNewFile();
        new File( svnDir, "prop-base" ).mkdir();

        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        File zipFile = new File( testDir, getAssemblyFile( "WcfService1", "1.0.0", "zip" ) );
        verifier.assertFilePresent( zipFile.getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        assertTrue( new File( testDir, ".svn" ).exists() );
        assertTrue( new File( testDir, ".references" ).exists() );

        List<String> unexpectedEntries = Arrays.asList( ".svn", ".references" );
        assertNoZipEntries( zipFile, unexpectedEntries );

        String assembly = new File( testDir, "target/WcfService1/bin/WcfService1.dll" ).getCanonicalPath();
        assertClassPresent( assembly, "Service1" );
    }
}
