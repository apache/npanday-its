package npanday.its;

/*
 * Copyright 2010
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
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

public class NPandayITNet40Test
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayITNet40Test()
    {
        super( "[1.2.2-incubating,)", "[v4.0.30319,)" );
    }

    public void testNet40Project()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/Net40Project" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        verifier.assertFilePresent( new File( testDir, "ClassLibrary1/" +
            getAssemblyFile( "ClassLibrary1", "1.0.0", "dll" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}