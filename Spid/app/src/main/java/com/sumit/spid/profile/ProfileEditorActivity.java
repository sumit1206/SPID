package com.sumit.spid.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;
import com.sumit.spid.profile.profiledata.ProfileDetailsData;

public class ProfileEditorActivity extends AppCompatActivity {

    TextView fieldName;
    EditText fieldValue;
    TextView save;
    ImageView thumbnail;
    ProfileDetailsData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);
        data = (ProfileDetailsData) getIntent().getSerializableExtra("profilevalue");
        fieldName = findViewById(R.id.edit_field_name);
        fieldValue = findViewById(R.id.edit_field_value);
        thumbnail = findViewById(R.id.edit_thumbnail);
        save = findViewById(R.id.edit_save);

        fieldValue.requestFocus();
        thumbnail.setImageResource(data.getItemThumbnail());
        fieldName.setText(data.getProfileFieldName());
        fieldValue.setHint(data.getProfileFieldValue());

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = fieldValue.getText().toString().trim();
                if(value.equalsIgnoreCase("")){
                    fieldValue.setError(data.getProfileFieldName()+" cannot be empty!");
                }else{
                    CookiesAdapter cookiesAdapter = new CookiesAdapter(ProfileEditorActivity.this);
                    cookiesAdapter.openWritable();
                    cookiesAdapter.updateProfileValue(data.getTableAttribute(),value,new User(ProfileEditorActivity.this).getPhoneNumber());
                    cookiesAdapter.close();
                    startActivity(new Intent(ProfileEditorActivity.this,ProfileActivity.class));
                }
            }
        });
    }
}
