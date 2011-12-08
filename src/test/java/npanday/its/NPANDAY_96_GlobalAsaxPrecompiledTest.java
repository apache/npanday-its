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
import java.util.Arrays;
import java.util.List;

public class NPANDAY_96_GlobalAsaxPrecompiledTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_96_GlobalAsaxPrecompiledTest()
    {
        super( "[1.4.0-incubating,)", "[v3.5,)" );
    }

    public void testGlobalAsaxPrecompiled()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/NPANDAY_96_GlobalAsaxPrecompiledTest" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        File zipFile = new File( testDir, getAssemblyFile( "GlobalASAXExample", "1.0.0", "zip" ) );
        verifier.assertFilePresent( zipFile.getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        List<String> expectedEntries = Arrays.asList( "bin/GlobalASAXExample.dll", "Default.aspx", "Global.asax",
                                                      "Web.config" );
        assertZipEntries( zipFile, expectedEntries );

        List<String> unexpectedEntries = Arrays.asList( "bin/App_global.asax.dll", "bin/App_global.asax.compiled",
                                                        "Global.asax.cs", "Default.aspx.cs", "Default.aspx.designer.cs",
                                                        "pom.xml", "GlobalASAXExample.csproj",
                                                        "Properties/AssemblyInfo.cs" );
        assertNoZipEntries( zipFile, unexpectedEntries );

        String assembly = new File( testDir, "target/GlobalASAXExample/bin/GlobalASAXExample.dll" ).getCanonicalPath();
        assertClassPresent( assembly, "_Default" );
    }
}
