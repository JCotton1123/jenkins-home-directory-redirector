package com.jessecotton.jenkins.plugin.homedirectoryredirector;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.*;
import hudson.slaves.WorkspaceList;

import java.io.PrintStream;
import java.io.IOException;
import java.util.Map;

import jenkins.model.Jenkins;

@Extension
public class HomeDirectoryRedirectorEnvironmentContributor extends EnvironmentContributor {

    public void buildEnvironmentFor(Run r, EnvVars envVars, TaskListener listener) {

        PrintStream logger = listener.getLogger();

        HomeDirectoryRedirector.HomeDirectoryRedirectorDescriptorImpl homeDirDescr = Jenkins.getInstance()
            .getDescriptorByType(HomeDirectoryRedirector.HomeDirectoryRedirectorDescriptorImpl.class);

        boolean redirectionEnabled = homeDirDescr.getEnableRedirection();

        if (!redirectionEnabled) {
            return;
        }

        try {
            if (!HomeDirectoryRedirectorHelpers.isUnixRun(r)) {
                return;
            }

            String workspace = HomeDirectoryRedirectorHelpers.getWorkspaceForRun(r);
            envVars.put("HOME", workspace.toString());
            return;
        }
        catch(Exception e) {
            logger.println("HomeDirectoryRedirector: exception encountered while retrieving workspace");
            e.printStackTrace(logger);
        }

        listener.getLogger().println("HomeDirectoryRedirector: unable to determine job workspace and set HOME");
    }
}
