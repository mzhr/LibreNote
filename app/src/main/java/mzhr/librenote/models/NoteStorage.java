package mzhr.librenote.models;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Class for processing file accessing, creation, deletion and editing.
 */
public class NoteStorage {

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

    public String[] getNotes(Context context)
    {
        return context.fileList();
    }


    public String getTextNote(Context context, String name) {
        FileInputStream inputStream;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.openFileInput(name)));
        } catch (IOException open_e) {
            open_e.printStackTrace();
        }
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
            context.deleteFile(name);
    }

}
