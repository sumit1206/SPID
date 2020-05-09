package com.sumit.spid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class BugReport {
    Context context;
    String body;
    public BugReport(Context context) {
        this.context = context;
        this.body = "";
    }
    public void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"shivam.manna@redmangoanalytics.com"};
        String[] CC = {"sumit.mondal@redmangoanalytics.com"};
        String subject = "Bug report SPID Android application";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
//            Send mail...
            context.startActivity(Intent.createChooser(emailIntent, "SPID bug report"));
//            context.finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareApp() {
        Log.i("Share SPID", "");

        String body = "Sharing body";
        String link = "this is the link";

        String[] TO = {""};
        String[] CC = {""};
        String subject = "SPID  app";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body+"\n"+link);

        try {
//            Send mail...
            context.startActivity(Intent.createChooser(emailIntent, "sharing SPID Pegions"));
//            context.finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no share client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareAnything(String body) {
        Log.i("Share SPID OTP", "");

        String[] TO = {""};
        String[] CC = {""};
        String subject = "OTP for collecting parcel";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
//            Send mail...
            context.startActivity(Intent.createChooser(emailIntent, "sharing SPID Pegions"));
//            context.finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no share client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage(String body) {
        Log.i("Send email", "");

        String[] TO = {"shivam.manna@redmangoanalytics.com"};
        String[] CC = {"sumit.mondal@redmangoanalytics.com"};
        String subject = "Report SPID Android application";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
//            Send mail...
            context.startActivity(Intent.createChooser(emailIntent, "SPID bug report"));
//            context.finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
