package com.jessecotton.jenkins.plugin.homedirectoryredirector;

import hudson.Extension;
import hudson.FilePath;
import hudson.Plugin;
import hudson.model.*;
import jenkins.model.Jenkins;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

@Extension
public class HomeDirectoryRedirector extends Plugin implements Describable<HomeDirectoryRedirector> {

    public static final String PLUGIN_NAME = "Home Directory Redirector Plugin";

    public String getDisplayName() {
        return PLUGIN_NAME;
    }

    public Descriptor<HomeDirectoryRedirector> getDescriptor() {
        return (HomeDirectoryRedirectorDescriptorImpl) Jenkins.getInstance().getDescriptorOrDie(HomeDirectoryRedirector.class);
    }

    @Extension
    public static final class HomeDirectoryRedirectorDescriptorImpl extends Descriptor<HomeDirectoryRedirector> {

        public boolean enableRedirection;
        public boolean enableSkeletonCopy;
        public String skeletonDirectory;

        public HomeDirectoryRedirectorDescriptorImpl() {
            load();
        }

        public boolean getEnableRedirection() {
            return enableRedirection;
        }

        public void setEnableRedirection(boolean enableRedirection) {
            this.enableRedirection = enableRedirection;
        }

        public boolean getEnableSkeletonCopy() {
            return enableSkeletonCopy;
        }

        public void setEnableSkeletonCopy(boolean enableSkeletonCopy) {
            this.enableSkeletonCopy = enableSkeletonCopy;
        }

        public String getSkeletonDirectory() {
            return skeletonDirectory;
        }

        public void setSkeletonDirectory(String skeletonDirectory) {
            this.skeletonDirectory = skeletonDirectory;
        }

        public boolean configure(StaplerRequest req, JSONObject formData) throws Descriptor.FormException {
            enableRedirection = formData.getBoolean("enableRedirection");
            enableSkeletonCopy = formData.getBoolean("enableSkeletonCopy");
            skeletonDirectory = formData.getString("skeletonDirectory");
            save();
            return super.configure(req, formData);
        }
    }
}
