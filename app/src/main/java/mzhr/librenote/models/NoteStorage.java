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

    public String[] getNotes(Context context)
    {
        /* Get a name list of all files in the app directory. */
        return context.fileList();
    }

    public void createTextNote(Context context, String name, String content) throws Exception
    {
        /* This function creates a new text note and throws a new exception if it already exists. */
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

    public String getTextNote(Context context, String name) {
        /* Read a file of the given file name and return content. */

        /* Get and open the file. */
        FileInputStream inputStream;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.openFileInput(name)));
        } catch (IOException open_e) {
            open_e.printStackTrace();
        }

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

        return content;
    }

    public boolean editTextNote(Context context, String name, String content)
    {
        /* Write content to the text note, replacing its old contents with new
           content. */
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

    public void removeTextNote(Context context, String name)
    {
        /* Delete the file of a specific filename. */
        context.deleteFile(name);
    }

    public void importNotes(Context context)
    {

    }

    public void exportNotes(Context context)
    {
        String[] notes = getNotes(context);

        /* Create LibreNote folder if it doesn't exist. */
        File baseDir = new File(Environment.getExternalStorageDirectory() + File.separator + "LibreNote");
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }

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
