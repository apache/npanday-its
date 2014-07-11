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

import junit.framework.TestCase;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.FileUtils;
import org.apache.maven.it.util.ResourceExtractor;
import org.apache.maven.it.util.cli.CommandLineException;
import org.apache.maven.it.util.cli.CommandLineUtils;
import org.apache.maven.it.util.cli.Commandline;
import org.apache.maven.it.util.cli.StreamConsumer;
import org.apache.maven.it.util.cli.WriterStreamConsumer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

public abstract class AbstractNPandayIntegrationTestCase
    extends TestCase
{
    protected boolean skip;

    protected String skipReason;

    private static final String NPANDAY_MAX_FRAMEWORK_VERSION_PROPERTY = "npanday.framework.version";

    private static final String NPANDAY_VERSION_SYSTEM_PROPERTY = "npanday.version";

    protected static DefaultArtifactVersion version = checkVersion();

    private static DefaultArtifactVersion frameworkVersion = checkFrameworkVersion();

    private static boolean debugMaven = Boolean.valueOf( System.getProperty( "debug.maven", "false" ) );

    private static boolean debugOutput = Boolean.valueOf( System.getProperty( "npanday.log.debug", "false" ) );

    private static boolean forceVersion = Boolean.valueOf( System.getProperty( "npanday.version.force", "false" ) );

    private static final Pattern PATTERN = Pattern.compile( "(.*?)-(RC[0-9]+|SNAPSHOT)" );

    private static String disasmArg;

    private static String disasmExec = findDisasmExec();

    protected NPandayIntegrationTestContext context;

    private Verifier verifier;

    protected AbstractNPandayIntegrationTestCase()
    {
        this( "(0,)" );
    }

    protected AbstractNPandayIntegrationTestCase( String versionRangeStr )
    {
        VersionRange versionRange = createVersionRange(versionRangeStr);

        if ( !checkNPandayVersion( versionRange, version ) && !forceVersion )
        {
            skip = true;
            skipReason = "NPanday version " + version + " not in range " + versionRange;
        }
    }

    protected AbstractNPandayIntegrationTestCase( String versionRangeStr, String frameworkVersionStr )
    {
        this( versionRangeStr );

        VersionRange versionRange = createVersionRange(frameworkVersionStr);

        if ( frameworkVersion != null && !versionRange.containsVersion( frameworkVersion ) && !forceVersion )
        {
            skip = true;
            skipReason = "Framework version " + frameworkVersion + " not in range " + versionRange;
        }
    }

    protected void setUp() throws IOException, VerificationException {
        context = new NPandayIntegrationTestContext();

        File testDir = ResourceExtractor.simpleExtractResources(getClass(), "/" + getClass().getSimpleName());

        context.setGroupId("NPanday.ITs." + getClass().getSimpleName());
        context.setTestDir(testDir);

        // Verifier not created yet because it steals System.out before we can print the test name in runTest
    }

    protected Verifier getDefaultVerifier() throws VerificationException, IOException {
        return getVerifier(context.getTestDir());
    }

    protected static boolean checkNPandayVersion( VersionRange versionRange, DefaultArtifactVersion version )
    {
        if (version == null) {
            // run all tests
            return true;
        }

        String v = version.toString();

        Matcher m = PATTERN.matcher(v);
        if ( m.matches() )
        {
            return versionRange.containsVersion( new DefaultArtifactVersion( m.group( 1 ) ) );
        }
        else
        {
            return versionRange.containsVersion( version );
        }
    }

    private static DefaultArtifactVersion checkVersion()
    {
        DefaultArtifactVersion version = null;
        String v = System.getProperty( NPANDAY_VERSION_SYSTEM_PROPERTY );
        if ( v != null )
        {
            version = new DefaultArtifactVersion( v );
            System.out.println( "Using NPanday version " + version );
        }
        else
        {
            System.out.println( "No NPanday version given" );
        }
        return version;
    }

    private static DefaultArtifactVersion checkFrameworkVersion()
    {
        DefaultArtifactVersion version = null;
        String v = System.getProperty( NPANDAY_MAX_FRAMEWORK_VERSION_PROPERTY );
        if ( v != null )
        {
            version = new DefaultArtifactVersion( v );
            System.out.println( "Using Framework versions <= " + version );
        }
        else
        {
            // TODO: this is not necessarily accurate. While it gets all those available, the max should actually be
            //       the one in the path (which can be obtained from the output for csc, but there may be other better
            //       ways such as a small C# app to interrogate it.
            //       It may be best to have an NPanday plugin that can reveal it then call that first to set it,
            //       reusing the vendor info

            File versions = new File( System.getenv( "systemroot" ) + "\\Microsoft.NET\\Framework" );
            if ( versions.exists() )
            {
                List<DefaultArtifactVersion> frameworkVersions = new ArrayList<DefaultArtifactVersion>();
                String[] list = versions.list( new java.io.FilenameFilter()
                {
                    public boolean accept( File parent, String name )
                    {
                        File f = new File( parent, name );
                        // Mscorlib.dll can be used to detect 2.0 SDK, Microsoft.CompactFramework.Build.Tasks.dll for 3.5 SDK
                        // Having just the runtime (without these files) is not sufficient
                        return f.isDirectory() && ( new File( f, "Mscorlib.dll" ).exists() || new File( f,
                                                                                                        "Microsoft.CompactFramework.Build.Tasks.dll" ).exists() );
                    }
                } );
                if ( list != null && list.length > 0 )
                {
                    for ( String frameworkVersion : list )
                    {
                        frameworkVersions.add( new DefaultArtifactVersion( frameworkVersion ) );
                    }
                    Collections.sort( frameworkVersions );
                    System.out.println( "Available framework versions: " + frameworkVersions );
                    version = frameworkVersions.get( frameworkVersions.size() - 1 );
                    System.out.println( "Selected framework version: " + version );
                }
            }
            if ( version == null )
            {
                System.out.println( "No Framework version given - attempting to use all" );
            }
        }
        return version;
    }

    protected static VersionRange createVersionRange( String versionRangeStr )
    {
        VersionRange versionRange;
        try
        {
            versionRange = VersionRange.createFromVersionSpec( versionRangeStr );
        }
        catch ( InvalidVersionSpecificationException e )
        {
            throw new RuntimeException( "Invalid version range: " + versionRangeStr + " - " + e.getMessage(), e );
        }
        return versionRange;
    }

    protected static void assertZipEntries( File zipFile, List<String> expectedEntries )
        throws IOException
    {
        assertZipEntries( zipFile, expectedEntries, true );
    }

    protected static void assertNoZipEntries( File zipFile, List<String> unexpectedEntries )
        throws IOException
    {
        assertZipEntries( zipFile, unexpectedEntries, false );
    }

    protected static void assertZipEntries( File zipFile, List<String> expectedEntries, boolean expected )
        throws IOException
    {
        ZipFile zip = new ZipFile( zipFile );
        try
        {
            for ( String name : expectedEntries )
            {
                if ( expected )
                {
                    assertNotNull( "expected " + name + " to be present in zip", zip.getEntry( name ) );
                }
                else
                {
                    assertNull( "expected " + name + " to be absent in zip", zip.getEntry( name ) );
                }
            }
        }
        finally
        {
            zip.close();
        }
    }

    protected static String translateMsDeployPath( String basedir, String path )
    {
        String p = new File( new File( basedir, "target/packages" ), path ).getAbsolutePath();
        p = p.replace( '\\', '/' );
        p = "Content/C_C" + p.substring( 2 ); // transform C:
        // Note: above is an assumption for where you can run these tests - not sure how other drive letters transformed
        // by Web Deploy
        return p;
    }

    protected void runTest()
        throws Throwable
    {
        System.out.print( String.format("%1$-70s", getITName() + "." + getName()));

        if ( skip )
        {
            System.out.println( "Skipping (" + skipReason + ")" );
            return;
        }

        try
        {
            super.runTest();

            // always reset the streams to get stdout/err back
            verifier.resetStreams();

            System.out.println( "OK" );
        }
        catch ( Throwable t )
        {
            // don't cross the streams!
            verifier.resetStreams();

            System.out.println( "FAILURE" );

            throw t;
        }
        finally {
            verifier = null;
        }
    }

    private String getITName()
    {
        String simpleName = getClass().getSimpleName();
        simpleName = simpleName.endsWith( "Test" ) ? simpleName.substring( 0, simpleName.length() - 4 ) : simpleName;
        return simpleName;
    }

    protected Verifier getVerifier(File testDirectory)
        throws VerificationException, IOException {
        if ( verifier != null ) {
            throw new IllegalStateException( "Previous verifier has not been reset - call resetVerifier()" );
        }

        if ( debugMaven )
        {
            verifier = new Verifier( testDirectory.getAbsolutePath() ) {
                public String getExecutable() { return super.getExecutable() + "Debug"; }
            };
        }
        else
        {
            verifier = new Verifier( testDirectory.getAbsolutePath() );
        }
        List<String> cliOptions = new ArrayList<String>( 2 );
        if (version != null) {
            cliOptions.add( "-Dnpanday.version=" + version );
        }
        if ( debugOutput )
        {
            cliOptions.add( "-X" );
        }
        verifier.setCliOptions( cliOptions );

        verifier.deleteArtifacts(context.getGroupId());

        return verifier;
    }

    protected static void overrideNPandaySettings( Verifier verifier ) {
        verifier.getCliOptions().add("-Dnpanday.settings=" +
                new File(verifier.getBasedir(), "npanday-settings.xml").getAbsolutePath());
    }

    protected void resetVerifier() {
        verifier.resetStreams();
        verifier = null;
    }

    protected String getCommentsFile()
    {
        return "target/comments.xml";
    }

    protected String getBuildSourcesGenerated( String fileName )
    {
        return getBuildFile("build-sources", fileName);
    }

    protected String getBuildFile( String buildDirectory, String fileName )
    {
        return "target/" + buildDirectory + "/" + fileName;
    }

    protected String getAssemblyFile( String assemblyName, String type )
    {
        return getAssemblyFile(assemblyName, null, type, null);
    }

    protected String getAssemblyFile( String assemblyName, String version, String type )
    {
        return getAssemblyFile(assemblyName, version, type, null);
    }

    protected String getAssemblyFile( String assemblyName, String version, String type, String classifier )
    {
        return getAssemblyFilePath( "target", assemblyName, type );
    }

    protected void clearRdfCache()
        throws IOException
    {
        FileUtils.deleteDirectory(new File(System.getProperty("user.home"), ".m2/uac/rdfRepository"));
    }

    protected void deleteArtifact( Verifier verifier, String groupId, String artifactId, String version, String type )
        throws IOException
    {
        FileUtils.deleteDirectory( new File( System.getProperty( "user.home" ), ".m2/uac/gac_msil/" + artifactId + "/" + version + "__" + groupId ) );
        verifier.deleteArtifact(groupId, artifactId, version, type);
    }

    protected void assertSubsystem( String assembly, int subsystem )
        throws VerificationException
    {
        assertEquals( "Subsystem was not " + subsystem + " as expected in assembly " + assembly, subsystem, getSubsystem( assembly ) );
    }

    private int getSubsystem( String assembly )
        throws VerificationException
    {
        String output = runILDisasm( assembly );

        for ( String line : output.split( "\n" ) )
        {
            line = line.trim();

            if ( line.startsWith( ".subsystem 0x" ) )
            {
                String value = line.substring( 13, line.indexOf( ' ', 13 ) );
                return Integer.parseInt( value );
            }
        }
        throw new VerificationException( "Unable to determine subsystem" );
    }

    protected void assertClassPresent( String assembly, String className )
        throws VerificationException
    {
        if ( !isClassPresent( assembly, className ) )
        {
            fail( "Unable to find class " + className + " in assembly " + assembly );
        }
    }

    protected void assertClassNotPresent( String assembly, String className )
        throws VerificationException
    {
        if ( isClassPresent( assembly, className ) )
        {
            fail( "Found unexpected class " + className + " in assembly " + assembly );
        }
    }

    private boolean isClassPresent( String assembly, String className )
        throws VerificationException
    {
        String output = runILDisasm( assembly );

        String currentClassName = className;

        int index = className.indexOf( '.' );
        String namespace = index > 0 ? className.substring( 0, index ) : "";
        for ( String line : output.split( "\n" ) )
        {
            line = line.trim();
            // mono disassembles like this instead
            if ( line.startsWith( ".namespace " + namespace ) )
            {
                currentClassName = className.substring( namespace.length() + 1 );
            }

            if ( line.startsWith( ".class " ) && line.endsWith( currentClassName ) )
            {
                return true;
            }
        }
        return false;
    }

    private String runILDisasm( String assembly )
        throws VerificationException
    {
        if ( disasmArg != null )
        {
            return execute( disasmExec, new String[]{disasmArg, assembly} );
        }
        else
        {
            return execute( disasmExec, new String[]{assembly} );
        }
    }

    private static String findDisasmExec() {
        String value = null;

        for (String path : new String[] { System.getenv("ProgramFiles"), System.getenv("ProgramFiles(x86)")}) {
            File[] versions = new File(path, "Microsoft SDKs\\Windows").listFiles();
            if (versions != null) {
                for (File f : versions) {
                    File ildasm = new File(f, "bin\\ildasm.exe");
                    if (ildasm.exists()) {
                        value = ildasm.getAbsolutePath();
                        disasmArg = "/text";
                        System.out.println("Found ildasm at " + value + " for disassembly");
                    }
                }
            }
        }
        if (value == null) {
            System.out.println("Using monodis from PATH for disassembly");
            value = "monodis";
            disasmArg = null;
        }

        return value;
    }

    private String execute( String executable, String[] args )
        throws VerificationException
    {
        try
        {
            Commandline cli = new Commandline();
            cli.setExecutable( executable );
            for ( String arg : args )
            {
                cli.createArgument().setValue( arg );
            }

            Writer logWriter = new StringWriter();

            StreamConsumer out = new WriterStreamConsumer( logWriter );

            StreamConsumer err = new WriterStreamConsumer( logWriter );

            int ret = CommandLineUtils.executeCommandLine( cli, out, err );

            logWriter.close();

            String output = logWriter.toString();
            if ( ret > 0 )
            {
                StringBuilder error = new StringBuilder( "Command failed with exit code: " + ret + "\n" );
                error.append("Command: ").append(Commandline.toString(cli.getCommandline())).append("\n");
                error.append("--- OUTPUT ---\n");
                error.append(output);
                error.append("--------------");

                throw new VerificationException(String.valueOf(error));
            }

            return output;
        }
        catch ( CommandLineException e )
        {
            throw new VerificationException( e );
        }
        catch ( IOException e )
        {
            throw new VerificationException( e );
        }
    }

    protected String getTestAssemblyFile( String artifactId, String version, String type )
    {
        String basedir = "target/test-assemblies";
        return getAssemblyFilePath(basedir, artifactId, type);
    }

    private String getAssemblyFilePath( String basedir, String artifactId, String type )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( basedir );
        sb.append( "/" );
        sb.append( artifactId );
        sb.append( "." );
        sb.append( type );
        return sb.toString();
    }

    protected String getAssemblyResourceFile( String fileName )
    {
        return getBuildFile( "assembly-resources", fileName );
    }

    protected void assertResourcePresent( String assembly, String resource )
        throws VerificationException
    {
        if ( !isResourcePresent( assembly, resource ) )
        {
            fail( "Unable to find resource " + resource + " in assembly " + assembly );
        }
    }

    protected void assertResourcePresent( String assembly, String assemblyName, String resource )
        throws VerificationException
    {
        if ( !isResourcePresent( assembly, assemblyName, resource ) )
        {
            fail( "Unable to find resource " + resource + " in assembly " + assembly );
        }
    }

    private boolean isResourcePresent( String assembly, String resource )
        throws VerificationException
    {
        return isResourcePresent(assembly, getAssemblyName(assembly), resource);
    }

    private boolean isResourcePresent( String assembly, String assemblyName, String resource )
        throws VerificationException
    {
        String output = runILDisasm( assembly );

        String prefix = ".mresource public ";
        String value = assemblyName + "." + resource.replace( '/', '.' );
        for ( String line : output.split( "\n" ) )
        {
            line = line.trim();
            if ( line.startsWith( prefix ) )
            {
                line = line.substring( prefix.length() );
                if ( line.equals( value ) || line.equals( "'" + value + "'" ) )
                {
                    return true;
                }
            }
        }
        return false;
    }

    private String getAssemblyName( String assembly )
    {
        return assembly.substring( assembly.lastIndexOf( File.separatorChar ) + 1, assembly.lastIndexOf( '.' ) );
    }

    protected void assertPublicKey( String assembly )
        throws VerificationException
    {
        if ( !hasPublicKey( assembly ) )
        {
            fail( "Couldn't find public key in assembly " + assembly );
        }
    }

    private boolean hasPublicKey( String assembly )
        throws VerificationException
    {
        String output = runILDisasm(assembly);

        boolean insideCorrectAssembly = false;
        for ( String line : output.split( "\n" ) )
        {
            if ( line.startsWith( ".assembly " + getAssemblyName( assembly ) ) )
            {
                insideCorrectAssembly = true;
            }
            else if ( line.startsWith( "}" ) )
            {
                insideCorrectAssembly = false;
            }
            else if ( insideCorrectAssembly && line.trim().startsWith( ".publickey" ) )
            {
                return true;
            }
        }
        return false;
    }

    private String getAssemblyFrameworkVersion( File assembly )
        throws VerificationException
    {
        String output = runILDisasm(assembly.getAbsolutePath());

        String prefix = "// Metadata version: v";
        for ( String line : output.split( "\n" ) )
        {
            line = line.trim();
            if ( line.startsWith( prefix ) )
            {
                return line.substring( prefix.length() ).trim();
            }
        }
        return null;
    }

    private boolean isAssemblyFrameworkVersion( File assembly, String versionRangeStr )
        throws VerificationException
    {
        String frameworkVersion = getAssemblyFrameworkVersion( assembly );
        VersionRange versionRange = createVersionRange( versionRangeStr );
        return versionRange.containsVersion( new DefaultArtifactVersion( frameworkVersion ) );
    }

    protected void assertAssemblyFrameworkVersion( File assembly, String versionRangeStr )
        throws VerificationException
    {
        String frameworkVersion = getAssemblyFrameworkVersion( assembly );
        VersionRange versionRange = createVersionRange(versionRangeStr);
        if ( !versionRange.containsVersion( new DefaultArtifactVersion( frameworkVersion ) ) )
        {
            fail( "Framework version " + frameworkVersion + " is not in range " + versionRangeStr );
        }
    }

    protected static boolean checkNPandayVersion( String versionRangeStr )
    {
        return checkNPandayVersion( createVersionRange( versionRangeStr ), version ) || forceVersion;
    }

    protected NPandayIntegrationTestContext createDefaultTestContext()
        throws IOException, VerificationException
    {
        NPandayIntegrationTestContext context = new NPandayIntegrationTestContext();

        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/" + getClass().getSimpleName() );

        context.setGroupId("NPanday.ITs." + getClass().getSimpleName());
        context.setTestDir(testDir);

        return context;
    }

    protected void skipIfMissingGAC( String gac, String message )
    {
        File f = new File( System.getenv( "SYSTEMROOT" ), "assembly/GAC_MSIL/" + gac );
        if ( !f.exists() || !f.isDirectory() )
        {
            skipReason = message;
            skip = true;
        }
    }

    protected void skipIfMissingProgramFilesDirectory( String directory, String message )
    {
        File f = new File( System.getenv( "PROGRAMFILES" ), directory );
        if ( !f.exists() || !f.isDirectory() )
        {
            skipReason = message;
            skip = true;
        }
    }

    protected void skipIfMissingMSBuildTask( String path, String message )
    {
        File f = new File( System.getenv( "PROGRAMFILES" ), "MSBuild" );
        f = new File( f, path );
        if ( !f.exists() )
        {
            skipReason = message;
            skip = true;
        }
    }

    protected void skipIfXdtNotPresent()
    {
        File f = new File( System.getenv( "PROGRAMFILES" ), "MSBuild" );
        f = new File( f, "Microsoft/VisualStudio" );
        File[] versions = f.listFiles();
        if ( versions != null ) {
            for ( File v : versions )
            {
                if ( new File( v, "Web/Microsoft.Web.Publishing.Tasks.dll" ).exists() )
                {
                    return;
                }
            }
        }

        skipReason = "Visual Studio 2010 (or above) with web platform is not installed";
        skip = true;
    }

    protected void skipIfMissingWebDeployV2() {
        skipIfMissingProgramFilesDirectory("IIS/Microsoft Web Deploy V2", "Web Deploy 2.0 not installed");
    }

    protected void skipIfMissingAzureSDK(String sdkVersion) {
        if ("1.6".equals(sdkVersion)) {
            skipIfMissingProgramFilesDirectory( "Windows Azure SDK", "Azure SDK is not installed" );
        }
        else if ("1.7".equals(sdkVersion)) {
            skipIfMissingProgramFilesDirectory( "Microsoft SDKs\\Windows Azure\\.NET SDK\\2012-06", "Azure SDK is not installed" );
        }
        else {
            throw new IllegalArgumentException("Unknown SDK version: " + sdkVersion);
        }
    }

    protected void skipIfMissingMVC2() {
        skipIfMissingGAC( "System.Web.MVC", "MVC.NET is not installed" );
    }
}
