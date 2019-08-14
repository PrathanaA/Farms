package com.FarmPe.Farms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class ReadSms extends BroadcastReceiver {
   static SmsListener smsListener;

    Boolean b;
    String abcd,xyz;
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("kkkkkkkkkkkkkkkkkkk");
        Bundle data  = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();

            // b=sender.endsWith("WNRCRP");  //Just to fetch otp sent from WNRCRP
            String messageBody = smsMessage.getMessageBody();
            abcd=messageBody.replaceAll("[^0-9]","");

            System.out.println("qqqqqqqqqqqqqqqqqqqqq"+abcd);// here abcd contains otp

            //Pass on the text to our listener.
            //if(b==true) {
            try {
                smsListener.messageReceived(abcd);  // attach value to interface
            }catch (Exception e){

            }
           // }
           // else
           // {
           // }
        }
    }
    public static void bindListener(SmsListener listener) {
        smsListener = listener;
    }
}
