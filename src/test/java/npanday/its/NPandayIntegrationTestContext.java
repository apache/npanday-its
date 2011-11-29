package npanday.its;

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
