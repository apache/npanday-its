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

public class NPandayITNet35Test
    extends AbstractNPandayIntegrationTestCase
{
    public NPandayITNet35Test()
    {
        super( "[1.0.2,)", "[v3.5,)" );
    }

    public void testNet35Project()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/Net35Project" );
        Verifier verifier = getVerifier( testDir );
        verifier.executeGoal( "install" );
        verifier.assertFilePresent( new File( testDir, "ClassLibrary1/" +
            getAssemblyFile( "ClassLibrary1", "1.0.0", "dll" ) ).getAbsolutePath() );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}