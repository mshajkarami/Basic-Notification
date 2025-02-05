package ir.hajkarami.basicnotification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText cookies;
    Button buy_btn;

    String CHANEL_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Create createNotificationChanel if device using API 26+
        createNotificationChanel();

        buy_btn = findViewById(R.id.buy_btn);
        cookies = findViewById(R.id.coolies);

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                String numberOfCookies = cookies.getText().toString();

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE );

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANEL_ID)
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentTitle("App Basic Notification")
                        .setContentText("you just bought " + numberOfCookies + "cookies!")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
                notificationManagerCompat.notify(1, builder.build());

            }
        });

    }

    public void createNotificationChanel() {
        // Create Notification Chanel Only On API level 26+
        // NotificationChanel is new Class and not in a support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "My Chanel Name";
            String description = "My Chanel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel chanel = new NotificationChannel(CHANEL_ID, name, importance);
            chanel.setDescription(description);
            // Register the Chanel with the System
            // you cannot change importance or other notification behaviors after this

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(chanel);

        }
    }
}