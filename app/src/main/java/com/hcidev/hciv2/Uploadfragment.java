package com.hcidev.hciv2;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hcidev.hciv2.fielpath.Filepath;
import com.hcidev.hciv2.fielpath.RealPathUtil;
import com.hcidev.hciv2.model.Note;
import com.hcidev.hciv2.restapi.Apiservice;
import com.hcidev.hciv2.restapi.Servehandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class Uploadfragment extends Fragment {




    EditText uploadtitle;
    EditText uploadcontent;
    Button uploadbrowser;
    Button uploadupload;
    TextView textpath;
    Retrofit retrofit;
    Apiservice apiservice;
    String mediapath;
    public static final String USERNAME = "USERNAME";
    SharedPreferences pref;

    private int PICK_FILE_REQUEST = 1;
    private static final int STORAGE_PERMISSION = 123;

    public Uploadfragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.uploadfragment, container, false);

        uploadtitle = (EditText) rootview.findViewById(R.id.edittextTitle);
        uploadcontent = (EditText) rootview.findViewById(R.id.edittextContent);
        uploadbrowser = (Button) rootview.findViewById(R.id.browserbutton);
        uploadupload = (Button) rootview.findViewById(R.id.UploadButton);
        textpath = (TextView) rootview.findViewById(R.id.txtpath);

        requestStoragePermission();



        uploadbrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        uploadupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediapath == null) {
                    Toast.makeText(getContext(), "Please select file first ", Toast.LENGTH_LONG).show();
                } else {
                    uploadnow();
                }

            }
        });

        return rootview;
    }

    public void showFileChooser() {
        Intent intent = new Intent();

        intent.setType("*/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_FILE_REQUEST);

        //textpaths.setText(uri.getPath());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri fileuri = data.getData();
            mediapath = Filepath.getRealPath(getContext(), fileuri);

            textpath.setText(mediapath);

        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {

        String[] projection = {MediaStore.Files.FileColumns.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int index_col = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        cursor.moveToFirst();
        String pathss = cursor.getString(index_col);
        cursor.close();
        return pathss;
    }

    public void uploadnow() {



        File note_link = new File(mediapath);
        Toast.makeText(getContext(), "test" + note_link, Toast.LENGTH_LONG).show();
        String note_title = uploadtitle.getText().toString();
        String note_content = uploadcontent.getText().toString();


        SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = getpref.edit();
        pref = getActivity().getApplication().getSharedPreferences("saveprofile",Context.MODE_PRIVATE);


        String note_username = pref.getString(USERNAME,"");


        Apiservice apiservice = Servehandler.getRetrofit().create(Apiservice.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), note_link);
        MultipartBody.Part body = MultipartBody.Part.createFormData("note_link", note_link.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("title"), note_title);
        RequestBody content = RequestBody.create(MediaType.parse("content"), note_content);
        RequestBody usernamex = RequestBody.create(MediaType.parse("username"), note_username);
        Call<Note> call = apiservice.uploadNote(body, filename, content, usernamex);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful()) {

                    Note notes = response.body();
                    Toast.makeText(getActivity(), "File successful uploaded", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "No such file thing to upload or the files is to big", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {

            }
        });

    }

    public static String getnewPath(final Context context, final Uri uri) {
        String id = DocumentsContract.getDocumentId(uri);
        if (!TextUtils.isEmpty(id)) {
            if (id.startsWith("raw:")) {
                return id.replaceFirst("raw:", "");
            }
            try {
                final boolean isOreo = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
                String stringContentURI;
                Uri contentUri;
                if(isOreo){
                    stringContentURI = "content://downloads/my_downloads";
                }else{
                    stringContentURI = "content://downloads/public_downloads";
                }
                contentUri = ContentUris.withAppendedId(
                        Uri.parse(stringContentURI), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } catch (NumberFormatException e) {
                //return null;
            }

        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {   column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( out != null ) {
                    out.close();
                }
                in.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
