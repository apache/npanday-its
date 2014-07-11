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

public class NPANDAY_254_WebAppWithCultureResTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_254_WebAppWithCultureResTest()
    {
        super( "[1.5.0-incubating,)", FRAMEWORK_V4_0 );

        skipIfMissingWebDeployV2();
        skipIfXdtNotPresent();
    }

    public void test()
        throws Exception
    {
        Verifier verifier = getDefaultVerifier();
        verifier.executeGoal( "install" );

        verifier.assertArtifactPresent(context.getGroupId(), "WebAppWithCultureRes", "1.0.0-SNAPSHOT", "msdeploy.zip" );

        String binDir = translateMsDeployPath(verifier.getBasedir(), "WebAppWithCultureRes/bin");
        List<String> entries = Arrays.asList(
            binDir + "/en-GB/WebAppWithCultureRes.resources.dll",
            binDir + "/es-ES/WebAppWithCultureRes.resources.dll"
        );
        File zipFile = new File(verifier.getArtifactPath(context.getGroupId(), "WebAppWithCultureRes", "1.0.0-SNAPSHOT", "msdeploy.zip"));
        assertZipEntries(zipFile, entries);

        verifier.verifyErrorFreeLog();
    }

}

