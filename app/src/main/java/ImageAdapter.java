import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.speakeasy.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ImageDatabaseHelper imageDatabaseHelper;//bd
    private List<ImageModel> images;//bd

    private List<Uri> imageUris = new ArrayList<>();
    private final int[] imageIds = {
            R.drawable.ardor,
            R.drawable.dolor,
            R.drawable.fastidio,
            R.drawable.fiebre,
            R.drawable.mareo,
            R.drawable.picazon,
            R.drawable.temblor,

            // Agrega más imágenes aquí
    };
    private final String[] imageTitles = {
            "Ardor",
            "Dolor",
            "Fastidio",
            "Fiebre",
            "Mareo",
            "Picazon",
            "Temblor",

            // Agrega más títulos aquí
    };

    public ImageAdapter(Context context) {
        this.context = context;
        this.imageDatabaseHelper = new ImageDatabaseHelper(context);//bd
        this.images = imageDatabaseHelper.getAllImages();//bd
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView textView;
        View gridItemView = convertView;
        if (convertView == null) {
            /*imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(16, 16, 16, 16);*/
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridItemView = inflater.inflate(R.layout.grid_item, parent,false);
        } else {
            gridItemView = convertView;
        }

        //imageView.setImageResource(imageIds[position]);
        imageView = gridItemView.findViewById(R.id.imageView);
        textView = gridItemView.findViewById(R.id.textView1);

        if (position < imageIds.length) {//Se agrega para agregar imagenes manualmente
        imageView.setImageResource(imageIds[position]);
        textView.setText(imageTitles[position]);  // Aquí estableces el título
        } else {//Se agrega para agregar imagenes manualmente
            Uri userImageUri = imageUris.get(position - imageIds.length);
            // Utiliza Glide o Picasso para cargar la imagen desde la URI.
            Glide.with(context).load(userImageUri).into(imageView);
            textView.setText("Imagen de usuario");  // Puedes personalizar el título aquí.
        }
        return gridItemView;
        //return imageView;
    }
    public void agregarImagen(Uri imageUri) {
        imageUris.add(imageUri);
        notifyDataSetChanged(); // Asegura que el adaptador se actualice.
    }

}