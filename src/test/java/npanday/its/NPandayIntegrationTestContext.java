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

public class NPandayIntegrationTestContext {

    private Verifier verifier;

    private String groupId;

    private File testDir;

    public void setVerifier( Verifier verifier )
    {
        this.verifier = verifier;
    }

    /**
     * Creates a verifier and removes installed artifacts from the local repository.
     * @return The maven verifier for the project under test.
     */
    public Verifier getPreparedVerifier()
    {
        return verifier;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    /**
     * @return The group id of the project under test.
     */
    public String getGroupId()
    {
        return groupId;
    }

    public void setTestDir( File testDir )
    {
        this.testDir = testDir;
    }

    /**
     *
     * @return The test directory of the project under test.
     */
    public File getTestDir()
    {
        return testDir;
    }
}
