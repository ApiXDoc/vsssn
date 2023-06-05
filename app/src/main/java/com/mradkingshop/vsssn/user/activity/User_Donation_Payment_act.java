package com.mradkingshop.vsssn.user.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deeplabstudio.fcmsend.FCMSend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Donation_Payment_act extends Activity implements PaymentResultListener {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatePicker datePicker;

    public   static final String CHANNEL_ID="IPServiceChannel";
    private static final String CHANNEL_NAME= "IPService";
    private static final String CHANNEL_DESC= "IPService notification";

    private static String serverKey = "AAAAdFB0AZ4:APA91bEOxX9l2crJzQj1IAfPRakcgL63a1Sr61Vd6cxjdzEQZY4IFeFB9zCxmCfFuFieF7Bfc2F_4ev7fAkWpKRe2sVjLIJJfH5qiWC_2BkK7TuG60GmfP50NLz2EpQMcPdZLASD84tA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        datePicker=new DatePicker(this);
        FCMSend.SetServerKey(serverKey);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription(CHANNEL_DESC);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        progressDialog.setMessage("Please wait..........");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("user_list")
                .document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

             DocumentSnapshot documentSnapshot=task.getResult();
                String phone=documentSnapshot.getString("phone");

                startPayment(getIntent().getExtras().getString("donation_amount"),
                        getIntent().getExtras().getString("plane_name")
                        ,phone);

                progressDialog.dismiss();


            }
        });

    }

    private void startPayment(String final_price, String plane_name, String phone_number) {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "VSSSN");
            options.put("description", plane_name);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(final_price);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "ngovsssn@gmail.com");
            preFill.put("contact", phone_number);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }



    }

    @Override
    public void onPaymentSuccess(String s) {

        try {

            Plane_add_to_investmentList();

            }catch (Exception e){

            Toast.makeText(User_Donation_Payment_act.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }


    }

    private void Plane_add_to_investmentList() {

        progressDialog.setMessage("please wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String current_date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();



        if(getIntent().getExtras().getString("item_id").contentEquals("permanent")){



            firebaseFirestore.collection("admin").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    DocumentSnapshot documentSnapshot=task.getResult();

                    firebaseFirestore.collection("user_list").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot documentSnapshot1=task.getResult();

                            String message_token=documentSnapshot.getString("message_token");
                            String name=documentSnapshot1.getString("name");


                            String phone=documentSnapshot1.getString("phone");
                            String state=documentSnapshot1.getString("state");
                            String email_id=documentSnapshot1.getString("email_id");


                            Map<String,Object> map=new HashMap<>();
                            map.put("plane_name",getIntent().getExtras().getString("plane_name"));
                            map.put("donation_amount",getIntent().getExtras().getString("donation_amount"));
                            map.put("plane_image",getIntent().getExtras().getString("plane_image"));
                            map.put("number_pepople_donate",String.valueOf(Integer.parseInt(getIntent().getExtras().getString("number_pepople_donate"))+1));
                            map.put("timestamp", FieldValue.serverTimestamp());
                            map.put("donation_date",current_date);
                            map.put("name",name);
                            map.put("phone",phone);
                            map.put("user_uid",firebaseAuth.getCurrentUser().getUid());
                            map.put("admin_message_token",message_token);
                            map.put("state",state);
                            map.put("email_id",email_id);

                            firebaseFirestore.collection("donation_list")
                                    .document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {



                                    FirebaseMessaging.getInstance().subscribeToTopic("donator").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            progressDialog.dismiss();
                                            System.out.println("Subscription successful");

                                            notification_send(message_token,
                                                    getIntent().getExtras().getString("donation_amount"));

                                        }
                                    });




                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();

                                    Toast.makeText(getApplicationContext(), "data upload failed", Toast.LENGTH_SHORT).show();

                                }
                            });




                        }
                    });








                }
            });









        }else {


            firebaseFirestore.collection("plane_list")
                    .document(getIntent().getExtras().getString("item_id"))
                    .update("number_pepople_donate"
                            ,String.valueOf(Integer.parseInt(getIntent().getExtras().getString("number_pepople_donate"))+1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(@NonNull Void unused) {

                    firebaseFirestore.collection("admin").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot documentSnapshot=task.getResult();

                            firebaseFirestore.collection("user_list").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    DocumentSnapshot documentSnapshot1=task.getResult();

                                    String message_token=documentSnapshot.getString("message_token");
                                    String name=documentSnapshot1.getString("name");


                                    String phone=documentSnapshot1.getString("phone");
                                    String state=documentSnapshot1.getString("state");
                                    String email_id=documentSnapshot1.getString("email_id");


                                    Map<String,Object> map=new HashMap<>();
                                    map.put("plane_name",getIntent().getExtras().getString("plane_name"));
                                    map.put("donation_amount",getIntent().getExtras().getString("donation_amount"));
                                    map.put("plane_image",getIntent().getExtras().getString("plane_image"));
                                    map.put("number_pepople_donate",String.valueOf(Integer.parseInt(getIntent().getExtras().getString("number_pepople_donate"))+1));
                                    map.put("timestamp", FieldValue.serverTimestamp());
                                    map.put("donation_date",current_date);
                                    map.put("name",name);
                                    map.put("phone",phone);
                                    map.put("user_uid",firebaseAuth.getCurrentUser().getUid());
                                    map.put("admin_message_token",message_token);
                                    map.put("state",state);
                                    map.put("email_id",email_id);

                                    firebaseFirestore.collection("donation_list")
                                            .document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {



                                            FirebaseMessaging.getInstance().subscribeToTopic("donator").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    progressDialog.dismiss();
                                                    System.out.println("Subscription successful");

                                                    notification_send(message_token,
                                                            getIntent().getExtras().getString("donation_amount"));

                                                }
                                            });




                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();

                                            Toast.makeText(getApplicationContext(), "data upload failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });




                                }
                            });








                        }
                    });










                }
            });




        }





    }

    @Override
    public void onPaymentError(int i, String s) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();

    }

    private void notification_send(String token,String total_amt_st) {

        FirebaseMessaging.getInstance().subscribeToTopic(token).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("Subscription successful");
            }
        });


        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "ram");

        FCMSend.Builder build1 = new FCMSend.Builder(token)
                .setTitle("VSSSN :- Donation Recived")
                .setBody("Donation Amount ₹"+total_amt_st)
                .setData(data);

        build1.send();


        String result2 = build1.send().Result();


        if(ChekSuccess( result2).contentEquals("Success")){


            progressDialog.dismiss();
            Toast.makeText(User_Donation_Payment_act.this, "Thanks to Donate", Toast.LENGTH_SHORT).show();
            finish();

        }else {
            progressDialog.dismiss();


            progressDialog.dismiss();
            finish();
            Log.e("error_in_notification",result2);
        }


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
