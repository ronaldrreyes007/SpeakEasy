import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class ImageDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ImageDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_IMAGES = "images"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE_URI = "image_uri"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_IMAGES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_IMAGE_URI TEXT," +
                "$COLUMN_DESCRIPTION TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_IMAGES")
        onCreate(db)
    }

    fun insertImage(imageUri: String, description: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_IMAGE_URI, imageUri)
        values.put(COLUMN_DESCRIPTION, description)
        db.insert(TABLE_IMAGES, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllImages(): List<ImageModel> {
        val images = ArrayList<ImageModel>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_IMAGES", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val imageUri = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                images.add(ImageModel(id, imageUri, description))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return images
    }
}