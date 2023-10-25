package com.example.speakeasy

import ImageAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.AdapterView
import android.widget.GridView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.speakeasy.ui.theme.SpeakEasyTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {
    private val gridView: GridView? = null
    private val REQUEST_IMAGE_PICK = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView: GridView = findViewById(R.id.gridView)
        val adapter = ImageAdapter(this)
        gridView.adapter = adapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Aquí puedes abrir la actividad correspondiente a la imagen seleccionada
            // Por ejemplo, puedes usar un Intent para abrir Image1Activity, Image2Activity, etc.
        }
        val fabAddImage: FloatingActionButton = findViewById(R.id.fabAddImage)
        fabAddImage.setOnClickListener {
            // Cuando se hace clic en el botón, abre la galería para seleccionar una imagen.
            cargarImagenDesdeGaleria()
        }
    }
   private fun cargarImagenDesdeGaleria() {
        // Abre la galería y permite al usuario seleccionar una imagen.
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // Obtiene la URI de la imagen seleccionada.
            val imageUri: Uri? = data.data

            // Aquí puedes guardar la URI en una lista o base de datos para mostrarla en tu menú.
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpeakEasyTheme {
        Greeting("Android")
    }
}