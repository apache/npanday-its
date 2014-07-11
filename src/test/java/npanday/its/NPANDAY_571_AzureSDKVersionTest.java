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

public class NPANDAY_571_AzureSDKVersionTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_571_AzureSDKVersionTest()
    {
        super( "[1.5.0-incubating,)", "[v4.0.30319,)" );

        skipIfMissingProgramFilesDirectory( "Microsoft SDKs\\Windows Azure\\.NET SDK\\2012-06", "Azure SDK 1.7 is not installed" );
    }

    public void test()
        throws Exception
    {
        Verifier verifier = getDefaultVerifier();

        verifier.executeGoal( "install" );
        final String a = "NPANDAY_571_AzureSDKVersionTest";
        final String v = "1.0-SNAPSHOT";
        verifier.assertArtifactPresent(context.getGroupId(), a, v, "cspkg" );

        verifier.assertFilePresent(
            verifier.getArtifactPath(
                context.getGroupId(), a, v, "cscfg", "generated"
            )
        );

        verifier.assertFilePresent(
            verifier.getArtifactPath(
                context.getGroupId(), a, v, "cscfg", "package"
            )
        );

        verifier.assertArtifactPresent( context.getGroupId(), "WorkerRole1", v, "dll" );
        verifier.assertArtifactPresent( context.getGroupId(), "WorkerRole1", v, "app.zip" );

        // TODO: check package contents

        verifier.verifyErrorFreeLog();
    }

}

