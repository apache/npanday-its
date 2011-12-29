<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="HelloWorld" generation="1" functional="0" release="0" Id="c46cb1ba-bb3a-44c1-a60a-cecd4ec35117" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
  <groups>
    <group name="HelloWorldGroup" generation="1" functional="0" release="0">
      <settings>
        <aCS name="HelloWorld_WorkerRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/HelloWorld/HelloWorldGroup/MapHelloWorld_WorkerRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="HelloWorld_WorkerRoleInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/HelloWorld/HelloWorldGroup/MapHelloWorld_WorkerRoleInstances" />
          </maps>
        </aCS>
      </settings>
      <maps>
        <map name="MapHelloWorld_WorkerRole:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/HelloWorld/HelloWorldGroup/HelloWorld_WorkerRole/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapHelloWorld_WorkerRoleInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/HelloWorld/HelloWorldGroup/HelloWorld_WorkerRoleInstances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="HelloWorld_WorkerRole" generation="1" functional="0" release="0" software="C:\Workbench\NPanday\svn-npanday-its\src\test\resources\NPANDAY_480_CloudServiceWithWorkerRole\HelloWorld\csx\Release\roles\HelloWorld_WorkerRole" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaWorkerHost.exe " memIndex="1792" hostingEnvironment="consoleroleadmin" hostingEnvironmentVersion="2">
            <settings>
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;HelloWorld_WorkerRole&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;HelloWorld_WorkerRole&quot; /&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/HelloWorld/HelloWorldGroup/HelloWorld_WorkerRoleInstances" />
            <sCSPolicyFaultDomainMoniker name="/HelloWorld/HelloWorldGroup/HelloWorld_WorkerRoleFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
      </components>
      <sCSPolicy>
        <sCSPolicyFaultDomain name="HelloWorld_WorkerRoleFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyID name="HelloWorld_WorkerRoleInstances" defaultPolicy="[1,1,1]" />
      </sCSPolicy>
    </group>
  </groups>
</serviceModel>