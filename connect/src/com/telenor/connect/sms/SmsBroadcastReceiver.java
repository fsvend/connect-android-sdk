package com.telenor.connect.sms;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

/**
 * This class is a {@link BroadcastReceiver} and will listen for {@code SMS_RECEIVED_ACTION}.
 * It will call {@code smsHandler.receivedSms} with the phone number and message body on the
 * messages that are received.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private final SmsHandler smsHandler;

    /**
     * @param smsHandler the {@link SmsHandler} that will called upon when new SMS arrive.
     */
    public SmsBroadcastReceiver(SmsHandler smsHandler) {
        this.smsHandler = smsHandler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        SmsMessage[] messages = getSmsMessages(intent, bundle);
        String messageBody = getMessageBodies(messages);
        String originatingAddress = messages[0].getOriginatingAddress();
        smsHandler.receivedSms(originatingAddress, messageBody);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static SmsMessage[] getSmsMessages(Intent intent, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Telephony.Sms.Intents.getMessagesFromIntent(intent);
        }
        return getSmsMessages(bundle);
    }

    private static SmsMessage[] getSmsMessages(Bundle bundle) {
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < messages.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }
        return messages;
    }

    private static String getMessageBodies(SmsMessage[] messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (SmsMessage message : messages) {
            stringBuilder.append(message.getMessageBody());
        }
        return stringBuilder.toString();
    }


}
