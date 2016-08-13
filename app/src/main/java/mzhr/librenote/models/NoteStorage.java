/*
 * Copyright (C) 2015 Mazhar Morshed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.If not, see <http://www.gnu.org/licenses/>.
 */

package mzhr.librenote.models;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Class for processing file accessing, creation, deletion and editing.
 */
public class  NoteStorage {

    /** Get a String array of all files in the app directory. */
    public String[] getNotes(Context context)
    {
        return context.fileList();
    }

    /** Creates a new text note and throws a new exception if it already exists. */
    public void createTextNote(Context context, String name, String content) throws Exception
    {
        try {
            context.openFileInput(name);
            throw new Exception("Cannot create file. File with same name already exists.");
        } catch (IOException exist_e) {
            FileOutputStream outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(content);
            writer.close();
        }
    }

    /** Read a file of the given file name and return content. */
    public String getTextNote(Context context, String name) {
        /* Get and open the file. */
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.openFileInput(name)));
        } catch (IOException open_e) {
            open_e.printStackTrace();
        }

        /* Read the content of the file. */
        String line;
        StringBuilder buffer = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (Exception read_e) {
            read_e.printStackTrace();
        }

        return buffer.toString();
    }

    /** Write content to the text note, replacing its old contents with new content. */
    public boolean editTextNote(Context context, String name, String content)
    {
        try {
            FileOutputStream outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** Delete the file of a specific filename. */
    public void removeTextNote(Context context, String name)
    {
        context.deleteFile(name);
    }

    /** Add all notes that don't exist in the note list, to the note list. */
    public void importNotes(Context context)
    {
        /* Continue only if the enviorment app directory exists. */
        File baseDir = new File(Environment.getExternalStorageDirectory() + File.separator + "LibreNote");
        if (baseDir.exists()) {
            File[] files = baseDir.listFiles();
            for (File file : files) {
                /* Get file content. */
                FileInputStream inputStream;
                try {
                    inputStream = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    /* Read the content of the file. */
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    try {
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line + "\n");
                        }
                    } catch (IOException read_e) {
                        read_e.printStackTrace();
                    }
                    String content = buffer.toString();

                    /* Write files to private app storage. */
                    try {
                        FileOutputStream outputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                        writer.write(content);
                        writer.close();
                    } catch (IOException write_e) {
                        write_e.printStackTrace();
                    }

                } catch (IOException open_e) {
                    open_e.printStackTrace();
                }
            }
        }
    }

    /** Write all notes to a LibreNotes directory on the homefolder. */
    public void exportNotes(Context context)
    {
        String[] notes = getNotes(context);

        /* Create LibreNote folder if it doesn't exist. */
        File baseDir = new File(Environment.getExternalStorageDirectory() + File.separator + "LibreNote");
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }

        /* Export all notes in the apps pirvate data to the enviorment folder LibreNotes. */
        for (String note : notes) {
            String content = getTextNote(context, note);
            try {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "LibreNote" + File.separator + note);
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(content.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
