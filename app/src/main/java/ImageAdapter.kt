import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.speakeasy.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import android.content.Intent
import com.example.speakeasy.ImageDetailActivity

class ImageAdapter(private val context: Context) : BaseAdapter() {
    private val imageDatabaseHelper: ImageDatabaseHelper // bd
    private var images: List<ImageModel> // bd

    private val imageUris = ArrayList<Uri>()
    private val imageIds = intArrayOf(
        R.drawable.ardor,
        R.drawable.dolor,
        R.drawable.fastidio,
        R.drawable.fiebre,
        R.drawable.mareo,
        R.drawable.picazon,
        R.drawable.temblor
        // Agrega más imágenes aquí
    )
    private val imageTitles = arrayOf(
        "Ardor",
        "Dolor",
        "Fastidio",
        "Fiebre",
        "Mareo",
        "Picazon",
        "Temblor"
        // Agrega más títulos aquí
    )

    init {
        imageDatabaseHelper = ImageDatabaseHelper(context) // bd local
        images = imageDatabaseHelper.getAllImages() // bd local
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var gridItemView = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            gridItemView = inflater.inflate(R.layout.grid_item, parent, false)
        }

        val imageView = gridItemView?.findViewById(R.id.imageView) as ImageView
        val textView = gridItemView.findViewById(R.id.textView1) as TextView
        val image = images[position]

        /*imageView.setImageResource(image.id) // Cambia esto si las imágenes se almacenan de manera diferente
        textView.text = image.description;*/
        val imageBitmap = BitmapFactory.decodeByteArray(image.imageBlob, 0, image.imageBlob.size)
        imageView.setImageBitmap(imageBitmap)
        textView.text = image.description

        // Añadimos el OnClickListener aquí al darle click al primer menu va a esta intent
        gridItemView.setOnClickListener {
            val intent = Intent(context, ImageDetailActivity::class.java).apply {
                putExtra("imageBytes", image.imageBlob)
                putExtra("description", image.description)
            }
            context.startActivity(intent)
        }

        return gridItemView
    }

    fun agregarImagen(imageBytes: ByteArray, description: String) {
        try {
            imageDatabaseHelper.insertImage(imageBytes, description)
            images = imageDatabaseHelper.getAllImages()
            notifyDataSetChanged()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getBytesFromInputStream(inputStream: InputStream): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    // Agrega tus funciones adicionales según sea necesario
}
