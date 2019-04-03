package com.hcidev.hciv2.controlleradapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcidev.hciv2.R;
import com.hcidev.hciv2.model.Note;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Friendsadapter extends RecyclerView.Adapter<Friendsadapter.ProfileHolder> {

    ArrayList<Note> noteadapter;
    DownloadManager downloadManager;

    public Friendsadapter(ArrayList<Note> noteadapter){
        this.noteadapter = noteadapter;
    }

    @NonNull
    @Override
    public Friendsadapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friendsnote_card,viewGroup,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(final Friendsadapter.ProfileHolder profileHolder, int position) {

         final Note notes = noteadapter.get(position);
         profileHolder.noteTitle.setText(notes.getNote_title());
         profileHolder.notecontent.setText(notes.getNote_content());

        //profileHolder.noteTitle.setText(pojorespone.getNotes().get(0).getNoteTitle());


          String  docx = ".docx";
          String pdf = ".pdf";
          String pptx = ".pptx";
          String doc = ".doc";

//
         if(notes.getNote_type().equals(docx)){
             profileHolder.notetypes.setImageResource(R.drawable.ic_docx64);
         }
         else if(notes.getNote_type().equals(pdf)) {
             profileHolder.notetypes.setImageResource(R.drawable.ic_npdf64);
         }
         else if(notes.getNote_type().equals(pptx)){
             profileHolder.notetypes.setImageResource(R.drawable.ic_pptx64);
         }else if(notes.getNote_type().equals(doc)){
            profileHolder.notetypes.setImageResource(R.drawable.ic_doc64);
         }
         else {
            profileHolder.notetypes.setImageResource(R.drawable.ic_document64);
         }

        profileHolder.buttondownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = notes.getNote_link();
                String noFile = "";

                if(notes.getNote_link().equals(noFile)){
                    Toast.makeText(v.getContext(),"no such file to download", Toast.LENGTH_LONG).show();
                }
                else {
                    downloadManager =(DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse("http://172.20.10.4/hcirestapi/assets/pdf/"+path);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle("Downloading " +path);
                    request.setDescription("Download files");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    downloadManager.enqueue(request);
                    Toast.makeText(v.getContext()," Downloading Files "+path, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public class ProfileHolder extends RecyclerView.ViewHolder{

        TextView followers;
        TextView followings;
        TextView mynote;
        TextView noteTitle;
        TextView notecontent;

        ImageView notetypes;
        Button buttondownload;

        public ProfileHolder(View profileview){
            super(profileview);

            followers = profileview.findViewById(R.id.followerId);
            followings = profileview.findViewById(R.id.followingid);
            mynote = profileview.findViewById(R.id.mynoteid);

            noteTitle = profileview.findViewById(R.id.textTitle);
            notecontent = profileview.findViewById(R.id.textContent);

            notetypes = profileview.findViewById(R.id.imgTypes);
            buttondownload = profileview.findViewById(R.id.dButton);

        }
    }


    @Override
    public int getItemCount() {
        return noteadapter.size();
    }
}
