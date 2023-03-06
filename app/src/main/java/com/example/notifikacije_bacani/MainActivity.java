package com.example.notifikacije_bacani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button basicBtn;
    Button expandableBtn;
    Button expandableBtnText;
    Button timeBtn;
    Button sendBtn;
    TextView textBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basicBtn = findViewById(R.id.BasicBtn);
        expandableBtn = findViewById(R.id.ExpandableBtn);
        expandableBtnText = findViewById(R.id.ExpandableBtnTxt);
        timeBtn = findViewById(R.id.TimeBtn);
        textBox = findViewById(R.id.sendTxt);
        sendBtn = findViewById(R.id.SendBtn);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification")
                        .setContentTitle("Obična notifikacija")
                        .setContentText("Ovo je primjer obicne notifikacije.")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1,builder.build());
            }
        });

        expandableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yoda);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification")
                        .setContentTitle("Expandable notifikacija")
                        .setContentText("Ovo je primjer Expandable notifikacije sa slikom.")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                        .setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(2,builder.build());
            }
        });

        expandableBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification")
                        .setContentTitle("Expandable notifikacija text")
                        .setContentText("Ovo je primjer Expandable notifikacije sa tekstom.")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("\n" +
                                "\n" +
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at turpis magna. Duis in condimentum eros. Phasellus gravida, lacus vel pretium maximus, lacus ex ullamcorper sem, vitae viverra lacus ante non mi. Morbi ornare, sem sit amet efficitur scelerisque, enim diam hendrerit quam, et congue justo justo id est. Sed eget eros lobortis, aliquam ex vitae, sollicitudin leo. Phasellus vel dictum metus. Integer imperdiet nulla ac quam iaculis venenatis. Aliquam erat volutpat. Vivamus molestie elit faucibus mauris luctus dapibus. Donec eget sem nec est imperdiet pretium accumsan at orci.\n" +
                                "\n" +
                                "Suspendisse hendrerit porttitor turpis, in viverra nisi faucibus molestie. Aliquam non leo fermentum, vulputate lacus a, aliquet orci. Integer imperdiet accumsan erat non congue. Ut sed risus non turpis facilisis dictum eget ut ipsum. Nullam eget vulputate ante. Nulla sit amet quam lobortis, pharetra ex non, tristique sapien. Nullam egestas quam dolor, id laoreet augue interdum sed. Nulla sit amet consequat arcu, quis pretium felis.\n" +
                                "\n" +
                                "Donec faucibus iaculis tellus, at sagittis orci pulvinar at. In posuere enim in iaculis accumsan. Nunc sit amet sollicitudin enim. Etiam faucibus posuere sem in tristique. Suspendisse vel augue tortor. Cras laoreet iaculis leo, eget imperdiet tortor commodo eu. Nam metus dui, dignissim at tincidunt sit. "))
                        .setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(3,builder.build());
            }
        });


        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int progressMax=100;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification")
                        .setContentTitle("Progress bar notifikacija")
                        .setContentText("Ovo je primjer Expandable notifikacije sa tekstom.")
                        .setProgress(progressMax, 0, false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground);


                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(4,builder.build());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(300);
                        for(int progress = 0; progress<=progressMax;progress+=5){
                            builder.setProgress(progressMax, progress, false);
                            managerCompat.notify(4, builder.build());
                            SystemClock.sleep(300);
                        }
                    }
                }).start();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = textBox.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                if(!text.isEmpty()){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification")
                            .setContentTitle("Poruka")
                            .setContentText(text)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentIntent(pendingIntent)
                            .addAction(R.drawable.ic_launcher_foreground, "Action", pendingIntent)
                            .setAutoCancel(true);


                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                    managerCompat.notify(5,builder.build());
                }
            }
        });


    }
}