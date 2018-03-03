package com.jessecotton.jenkins.plugin.homedirectoryredirector;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.model.listeners.RunListener;
import hudson.util.RemotingDiagnostics;

import java.io.PrintStream;
import java.util.Arrays;

import jenkins.model.Jenkins;

@Extension
public class HomeDirectoryRedirectorRunListener extends RunListener<Run<?, ?>> {

    public void onStarted(Run run, TaskListener listener) {

        PrintStream logger = listener.getLogger();

        HomeDirectoryRedirector.HomeDirectoryRedirectorDescriptorImpl homeDirDescr = Jenkins.getInstance()
          .getDescriptorByType(HomeDirectoryRedirector.HomeDirectoryRedirectorDescriptorImpl.class);

        boolean redirectionEnabled = homeDirDescr.getEnableRedirection();
        boolean skeletonCopyEnabled = homeDirDescr.getEnableSkeletonCopy();
        String skeletonDirectory = homeDirDescr.getSkeletonDirectory();

        if (!(redirectionEnabled && skeletonCopyEnabled)) {
            return;
        }

        try {
            if (!HomeDirectoryRedirectorHelpers.isUnixRun(run)) {
                return;
            }

            String workspace = HomeDirectoryRedirectorHelpers.getWorkspaceForRun(run);

            String script = "import org.apache.commons.io.FileUtils; " +
                "FileUtils.copyDirectory(new File('" + skeletonDirectory  + "'), new File('" + workspace + "'))";

            Executor executor = run.getExecutor();
            Computer computer = executor.getOwner();
            Node node = computer.getNode();

            RemotingDiagnostics.executeGroovy(script, node.getChannel());
        } catch(Exception e) {
            logger.println("HomeDirectoryRedirector: exception encountered during skeleton file copy");
            e.printStackTrace(logger);
        }
    }
}
