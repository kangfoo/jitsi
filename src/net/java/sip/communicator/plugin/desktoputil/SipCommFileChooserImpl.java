/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Copyright @ 2015 Atlassian Pty Ltd
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
package net.java.sip.communicator.plugin.desktoputil;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

/**
 * Implements <tt>SipCommFileChooser</tt> for Swing's <tt>JFileChooser</tt>.
 *
 * @author Valentin Martinet
 */
public class SipCommFileChooserImpl
    extends JFileChooser
    implements SipCommFileChooser
{
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 0L;

    /**
     * Parent component of this dialog (JFrame, Frame, etc)
     */
    private Component parent;

    /**
     * Constructor
     *
     * @param title title for this dialog
     * @param operation 'Save file' or 'Load file' operation
     */
    public SipCommFileChooserImpl(String title, int operation)
    {
        super();

        this.setDialogTitle(title);
        this.setDialogType(operation);
    }

    /**
     * Initializes a new <tt>SipCommFileChooserImpl</tt> instance.
     *
     * @param parent
     * @param path
     * @param title
     * @param operation
     */
    public SipCommFileChooserImpl(
        Component parent, String path, String title, int operation)
    {
        this(title, operation);

        this.parent = parent;
        this.setStartPath(path);
    }

    /**
     * Returns the selected file by the user from the dialog.
     *
     * @return File the selected file from the dialog
     */
    public File getApprovedFile()
    {
        return this.getSelectedFile();
    }

    /**
     * Sets the default path to be considered for browsing among files.
     *
     * @param path the default start path for this dialog
     */
    public void setStartPath(String path)
    {
        // If the path is null, we have nothing more to do here.
        if (path == null)
            return;

        // If the path is an URL extract the path from the URL in order to
        // remove the "file:" part, which doesn't work with methods provided
        // by the file chooser.
        try
        {
            URL url = new URL(path);

            path = url.getPath();
        }
        catch (MalformedURLException e) {}

        File file = new File(path);

        setCurrentDirectory(file);

        /*
         * If the path doesn't exist, the intention of the caller may have been
         * to also set a default file name.
         */
        if ((file != null) && !file.isDirectory())
            setSelectedFile(file);
        else
            setSelectedFile(null);
    }

    /**
     * Shows the dialog and returns the selected file.
     *
     * @return File the selected file in this dialog
     */
    public File getFileFromDialog()
    {
        int choice = -1;

        if(this.getDialogType() == JFileChooser.OPEN_DIALOG)
            choice = this.showOpenDialog(this.getParentComponent());
        else
            choice = this.showSaveDialog(this.getParentComponent());

        return
            (choice == JFileChooser.APPROVE_OPTION) ? getSelectedFile() : null;
    }

    /**
     * Returns the parent component of this dialog
     *
     * @return Component dialog's parent component
     */
    public Component getParentComponent()
    {
        return this.parent;
    }

    /**
     * Adds a file filter to this dialog.
     *
     * @param filter the filter to add
     */
    public void addFilter(SipCommFileFilter filter)
    {
        this.addChoosableFileFilter(filter);
    }

    /**
     * Sets a file filter to this dialog.
     *
     * @param filter the filter to add
     */
    public void setFileFilter(SipCommFileFilter filter)
    {
        super.setFileFilter(filter);
    }

    /**
     * Returns the filter the user has chosen for saving a file.
     *
     * @return SipCommFileFilter the used filter when saving a file
     */
    public SipCommFileFilter getUsedFilter()
    {
        return (SipCommFileFilter)this.getFileFilter();
    }

    /**
     * Change the selection mode for the file choose.
     * Possible values are DIRECTORIES_ONLY or FILES_ONLY, default is
     * FILES_ONLY.
     *
     * @param mode the mode to use.
     */
    public void setSelectionMode(int mode)
    {
        super.setFileSelectionMode(mode);
    }
}
