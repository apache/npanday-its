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

public class NPANDAY_452_SilverlightAppTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_452_SilverlightAppTest()
    {
        super( "[1.5.0-incubating,)" );

        skipIfMissingMSBuildTask( "Microsoft/Silverlight/v3.0/Microsoft.Silverlight.CSharp.targets",
                                  "Silverlight v3.0 SDK is not present" );
    }

    public void test()
        throws Exception
    {
        NPandayIntegrationTestContext context = createDefaultTestContext();
        Verifier verifier = context.getPreparedVerifier();

        verifier.executeGoal( "install" );

        verifier.assertArtifactPresent(context.getGroupId(), "SilverlightClassLibrary1", "1.0-SNAPSHOT", "dll" );
        verifier.assertArtifactPresent(context.getGroupId(), "SilverlightApplication1", "1.0-SNAPSHOT", "xap" );
        verifier.assertArtifactPresent(context.getGroupId(), "SilverlightApplication2", "1.0-SNAPSHOT", "xap" );
        verifier.assertArtifactPresent(context.getGroupId(), "SilverlightApplication1.Web", "1.0-SNAPSHOT", "msdeploy.zip" );

        verifier.assertFilePresent( "SilverlightApplication1.Web/ClientBin/SilverlightApplication1.xap" );
        verifier.assertFilePresent( "SilverlightApplication1.Web/ClientBin/SilverlightApplication2.xap" );

        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

}

