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
import java.util.Arrays;
import java.util.List;

public class NPANDAY_121_ResGenWithErrorInFileNameTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_121_ResGenWithErrorInFileNameTest()
    {
        super( "[1.0.2,)" );
    }

    public void testResGenWithErrorInFileName()
        throws Exception
    {
        Verifier verifier = getDefaultVerifier();
        verifier.executeGoal( "install" );
        File zipFile = new File( verifier.getBasedir(), "npanday-9903/" + getAssemblyFile( "npanday-9903", "1.0.0", "zip" ) );
        verifier.assertFilePresent( zipFile.getAbsolutePath() );
        verifier.verifyErrorFreeLog();

        List<String> expectedEntries = Arrays.asList( "bin/npanday-9903.dll", "Default.aspx", "error.aspx",
                                                      "error.asp.resx", "My Project/Application.myapp",
                                                      "My Project/Resources.resx", "My Project/Settings.settings",
                                                      "Web.config" );

        assertZipEntries( zipFile, expectedEntries );

        String assembly = new File( verifier.getBasedir(), "npanday-9903/target/npanday-9903/bin/npanday-9903.dll" ).getCanonicalPath();
        assertResourcePresent( assembly, "npanday_9903", "Resources.resources" );
        assertResourcePresent( assembly, "npanday_9903","error.asp.resources" );
        assertClassPresent( assembly, "_Default" );
        assertClassPresent( assembly, "_error" );
        assertClassPresent( assembly, "My.MyApplication" );
        assertClassPresent( assembly, "My.MySettings" );
    }
}
