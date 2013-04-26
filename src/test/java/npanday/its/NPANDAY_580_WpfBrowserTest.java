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

public class NPANDAY_580_WpfBrowserTest
    extends AbstractNPandayIntegrationTestCase
{
    public NPANDAY_580_WpfBrowserTest()
    {
        super( "[1.5.0-incubating,)", "[v4.0.30319,)" );
    }

    public void test()
        throws Exception
    {
        NPandayIntegrationTestContext context = createDefaultTestContext();
        Verifier verifier = context.getPreparedVerifier();

        verifier.executeGoal( "install" );
        verifier.assertArtifactPresent(context.getGroupId(), "NPANDAY_580_WpfBrowserTest", "1.0.0-SNAPSHOT", "exe" );
        verifier.assertArtifactPresent(context.getGroupId(), "NPANDAY_580_WpfBrowserTest", "1.0.0-SNAPSHOT", "exe.manifest" );
        verifier.assertArtifactPresent(context.getGroupId(), "NPANDAY_580_WpfBrowserTest", "1.0.0-SNAPSHOT", "xbap" );

        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

}

