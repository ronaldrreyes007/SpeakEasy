package com.example.speakeasy

import android.content.ContentResolver
import ImageAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.AdapterView
import android.widget.GridView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.speakeasy.ui.theme.SpeakEasyTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainActivity : ComponentActivity() {
    //private val gridView: GridView? = null
    private val REQUEST_IMAGE_PICK = 1
    private lateinit var adapter: ImageAdapter // Declaración del adaptador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ImageAdapter(this)
        val gridView: GridView = findViewById(R.id.gridView)
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
    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // Obtiene la URI de la imagen seleccionada.
            val imageUri: Uri? = data.data



                if (imageUri != null) {
                    val imageBytes: ByteArray? = obtenerBytesDeImagen(imageUri)
                    val imageName = obtenerNombreDeArchivo(imageUri)
                    if (imageBytes != null) {
                        adapter.agregarImagen(imageBytes, imageName)
                    }
                }


            // Aquí puedes guardar la URI en una lista o base de datos para mostrarla en tu menú.

           /* if (imageUri != null) {
                adapter.agregarImagen(imageUri, "Descripción de la imagen") // Puedes ajustar la descripción según tus necesidades.
            }*/



        }
    }
    @SuppressLint("Range")
    private fun obtenerNombreDeArchivo(uri: Uri): String {
        val contentResolver: ContentResolver = contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        return try {
            if (cursor != null && cursor.moveToFirst()) {
                val displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val extensionIndex = displayName?.lastIndexOf(".")

                if (extensionIndex != null && extensionIndex != -1) {
                    // Si se encuentra una extensión, se quita para guardar el nombre sin extensión.
                    return displayName.substring(0, extensionIndex)
                } else {
                    // Si no se encuentra una extensión, se devuelve el nombre completo.
                    return displayName ?: "Imagen sin nombre"
                }
            } else {
                "Imagen sin nombre"
            }
        } finally {
            cursor?.close()
        }
    }
    private fun obtenerBytesDeImagen(imageUri: Uri): ByteArray? {
        try {
            val inputStream = contentResolver.openInputStream(imageUri)
            if (inputStream != null) {
                val buffer = ByteArray(4096) // Tamaño del búfer, puedes ajustarlo según tus necesidades.
                var bytesRead: Int
                val output = ByteArrayOutputStream()
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }
                return output.toByteArray()
            } else {
                // Manejo de error o retorno adecuado para el caso de inputStream nulo
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Manejo de error o retorno adecuado en caso de excepción
        }
        return null
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