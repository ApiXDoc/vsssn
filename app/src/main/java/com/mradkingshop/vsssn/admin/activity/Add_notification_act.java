package com.mradkingshop.vsssn.admin.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.deeplabstudio.fcmsend.FCMSend;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.user.activity.User_Donation_Payment_act;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Add_notification_act extends Activity {

    EditText title,body,link_et;
    Spinner type_notification,add_link;
    CardView ok;
    String notification_type_st,link_type_st;
    public   static final String CHANNEL_ID="IPServiceChannel";
    private static final String CHANNEL_NAME= "IPService";
    private static final String CHANNEL_DESC= "IPService notification";

    private static String serverKey = "AAAAdFB0AZ4:APA91bEOxX9l2crJzQj1IAfPRakcgL63a1Sr61Vd6cxjdzEQZY4IFeFB9zCxmCfFuFieF7Bfc2F_4ev7fAkWpKRe2sVjLIJJfH5qiWC_2BkK7TuG60GmfP50NLz2EpQMcPdZLASD84tA";

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notification_form);


        title=findViewById(R.id.title);
        body=findViewById(R.id.body);
        link_et=findViewById(R.id.link_et);
        type_notification=findViewById(R.id.type_notification);
        add_link=findViewById(R.id.add_link);
        ok=findViewById(R.id.ok);

        FCMSend.SetServerKey(serverKey);
        progressDialog=new ProgressDialog(this);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription(CHANNEL_DESC);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }


        add_link_set_up();

        type_notification_set_up();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(title.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Please Enter Your Title", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(body.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Please Enter Your Body Content", Toast.LENGTH_SHORT).show();

                }else if(notification_type_st.contentEquals("no")){

                    Toast.makeText(getApplicationContext(), "Please Select Type Of Notifcation", Toast.LENGTH_SHORT).show();

                }else if(link_type_st.contentEquals("no_link")){

                    Toast.makeText(getApplicationContext(), "Please Select Link Type", Toast.LENGTH_SHORT).show();
                }

                else if(notification_type_st.contentEquals("All")){

                    if(link_type_st.contentEquals("no")){

                        notification("user","no");

                    }else if(link_type_st.contentEquals("yes")){

                        notification("user","yes");

                    }

                }else if(notification_type_st.contentEquals("Donators")){

                    if(link_type_st.contentEquals("no")){

                        notification("donator","no");

                    }else if(link_type_st.contentEquals("yes")){


                        notification("donator","yes");

                    }

                }else if(notification_type_st.contentEquals("Members")){


                    if(link_type_st.contentEquals("no")){

                        notification("user","no");

                    }else if(link_type_st.contentEquals("yes")){

                        notification("user","yes");

                    }

                }



            }
        });



    }

    private void notification(String topic,String link) {


        progressDialog.setMessage("Please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


        if(link.contentEquals("yes")){

            HashMap<String, String> data = new HashMap<>();
            data.put("key", link_et.getText().toString());

            FCMSend.Builder build = new FCMSend.Builder(topic, true)
                    .setTitle(title.getText().toString())
                    .setBody(body.getText().toString())
                    .setData(data); // Optional
            build.send();



            String result2 = build.send().Result();
            if(ChekSuccess( result2).contentEquals("Success")){


                progressDialog.dismiss();

                Toast.makeText(Add_notification_act.this, "Notification is Sended", Toast.LENGTH_SHORT).show();

            }else {

                progressDialog.dismiss();

                Toast.makeText(Add_notification_act.this, "Notification is Sended", Toast.LENGTH_SHORT).show();

                Log.e("error_in_notification",result2);
            }


        }else if(link.contentEquals("no")){


            FCMSend.Builder build = new FCMSend.Builder(topic, true)
                    .setTitle(title.getText().toString())
                    .setBody(body.getText().toString());

            build.send();



            String result2 = build.send().Result();
            if(ChekSuccess( result2).contentEquals("Success")){


                progressDialog.dismiss();

                Toast.makeText(Add_notification_act.this, "Notification is Sended", Toast.LENGTH_SHORT).show();

            }else {

                progressDialog.dismiss();

                Toast.makeText(Add_notification_act.this, "Notification is Sended", Toast.LENGTH_SHORT).show();

                Log.e("error_in_notification",result2);
            }


        }



    }

    private void type_notification_set_up() {

        type_notification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(adapterView.getItemAtPosition(i).toString().contentEquals("Select Your Category")){

                    notification_type_st="no";

                }else if(adapterView.getItemAtPosition(i).toString().contentEquals("All")) {

                    notification_type_st="All";


                }else if(adapterView.getItemAtPosition(i).toString().contentEquals("Donators")) {

                    notification_type_st="Donators";


                }else if(adapterView.getItemAtPosition(i).toString().contentEquals("Members")) {

                    notification_type_st="Members";


                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void add_link_set_up() {

        add_link.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(adapterView.getItemAtPosition(i).toString().contentEquals("Add Link")){

                    link_et.setVisibility(View.GONE);

                    link_type_st="no_link";


                }else if(adapterView.getItemAtPosition(i).toString().contentEquals("Yes")){

                    link_et.setVisibility(View.VISIBLE);

                    link_type_st="yes";

                }
                else if(adapterView.getItemAtPosition(i).toString().contentEquals("No")){

                    link_et.setVisibility(View.GONE);
                    link_type_st="no";


                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private String ChekSuccess(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String success = object.getString("success");
            if (success.equals("1")) {
                return "Success";
            } else if (success.equals("0")) {
                return "Unsuccessful";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Unsuccessful";
    }
}
