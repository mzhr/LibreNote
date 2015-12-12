package mzhr.librenote.models;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

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
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
                outputStream.write(content.getBytes());
                outputStream.close();
            } catch (Exception write_e) {
                throw write_e;
            }
        }
    }

    public String[] getNotes(Context context)
    {
        return context.fileList();
    }

    /*
    public String openTextNote(Context context, String name, String content)
    {

    }

    public boolean editTextNote(Context context, String name, String content)
    {

    }

    public boolean removeTextNote(Context context, String name)
    {

    }
    */

}
