// ImageDetailActivity.java
package com.example.speakeasy;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageDetailActivity extends AppCompatActivity {

    private EditText bodyPartEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        ImageView imageView = findViewById(R.id.detailImageView);
        //TextView textView = findViewById(R.id.bodyPartEditText;
        bodyPartEditText = findViewById(R.id.bodyPartEditText);
        saveButton = findViewById(R.id.saveButton);

        byte[] imageBytes = getIntent().getByteArrayExtra("imageBytes");
        String description = getIntent().getStringExtra("description");

        //imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        //textView.setText(description);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = event.getX();
                    float y = event.getY();
                    // Procesa la posición x e y para determinar la parte del cuerpo seleccionada
                    // Aquí puedes agregar lógica para determinar la parte del cuerpo
                    // Por simplicidad, vamos a mostrar las coordenadas en el EditText
                    bodyPartEditText.setText("Part selected at: " + x + ", " + y);
                }
                return true;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bodyPart = bodyPartEditText.getText().toString();
                // Guardar la parte del cuerpo seleccionada
                // Puedes agregar lógica aquí para guardar los datos en la base de datos o procesarlos como necesites
            }
        });
    }
}
