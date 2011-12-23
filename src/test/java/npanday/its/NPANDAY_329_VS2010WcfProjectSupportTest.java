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

public class NPANDAY_329_VS2010WcfProjectSupportTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_329_VS2010WcfProjectSupportTest()
    {
        super( "[1.4.0-incubating,)", "[v4.0.30319,)" );
    }

    public void testWCF2010Project()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(),
                                                                 "/NPANDAY_329_VS2010WcfProjectSupportTest" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        File zipFile = new File( testDir, getAssemblyFile( "WcfService1", "1.0.0", "zip" ) );
        verifier.assertFilePresent( zipFile.getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        List<String> expectedEntries = Arrays.asList( "bin/WcfService1.dll", "bin/System.Web.DynamicData.dll",
                                                      "bin/System.Web.Entity.dll",
                                                      "bin/System.Web.ApplicationServices.dll", "Service1.svc",
                                                      "Web.config", "Web.Debug.config", "Web.Release.config" );

        assertZipEntries( zipFile, expectedEntries );

        String assembly = new File( testDir, "target/WcfService1/bin/WcfService1.dll" ).getCanonicalPath();
        assertClassPresent( assembly, "Service1" );
    }
}
