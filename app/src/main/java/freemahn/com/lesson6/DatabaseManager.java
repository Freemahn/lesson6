package freemahn.com.lesson6;

/**
 * Created by Freemahn on 06.01.2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.database.Cursor;


public class DatabaseManager {
    public static final String DB_NAME = "mydatabase";
    public static final int VERSION = 1;
    public static final String TABLE_CHANNELS = "channel";
    public static final String TABLE_NEWS = "news";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_CHANNEL_NAME = "name";
    public static final String COLUMN_CHANNEL_ID = "channel_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String CREATE_TABLE_CHANNELS = "CREATE TABLE " + TABLE_CHANNELS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CHANNEL_NAME + " TEXT, " + COLUMN_LINK + " TEXT)";
    public static final String CREATE_TABLE_NEWS = "CREATE TABLE " + TABLE_NEWS + " (" + COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CHANNEL_ID + " INTEGER NOT NULL, " + COLUMN_TITLE + " TEXT," +
            " " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_LINK + " TEXT, time INTEGER NOT NULL, FOREIGN KEY (" + COLUMN_CHANNEL_ID + ") " +
            "REFERENCES " + TABLE_CHANNELS + " (" + COLUMN_ID + ") ON DELETE CASCADE, UNIQUE (" + COLUMN_LINK + ") ON CONFLICT IGNORE)";

    private static DatabaseManager mInstance = null;
    private Context context;
    private SQLiteDatabase db;

    private DatabaseManager(Context context) {
        this.context = context;
    }

    public static DatabaseManager getOpenedInstance(Context context) {
        if (mInstance == null)
            mInstance = new DatabaseManager(context.getApplicationContext()).open();
        return mInstance;
    }

    private DatabaseManager open() {
        DBHelper mDbHelper = new DBHelper(context);
        db = mDbHelper.getWritableDatabase();
        return this;
    }


    private static class DBHelper extends SQLiteOpenHelper {
        Context context;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
            this.context = context;
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            db.execSQL("PRAGMA foreign_keys=ON");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_CHANNELS);
            sqLiteDatabase.execSQL(CREATE_TABLE_NEWS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        }
    }

    public String getLinkByChannelId(long channelId) {
        Cursor c = db.query("channel", new String[]{"link"}, "_id" + "=" + channelId,
                null, null, null, null);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToFirst();
        String result = c.getString(c.getColumnIndex("link"));
        c.close();
        return result;
    }

    public long createChannel(ContentValues channel) {
        return db.insert(TABLE_CHANNELS, null, channel);
    }


    public boolean changeChannel(ContentValues channel, long channelId) {
        return db.update(TABLE_CHANNELS, channel, COLUMN_ID + "=" + channelId, null) == 1;
    }

    public Cursor getNewsByChannelId(long channelId) {
        return db.query("news", new String[]{COLUMN_ID, COLUMN_LINK, COLUMN_DESCRIPTION, COLUMN_TITLE},
                COLUMN_CHANNEL_ID + "=" + channelId, null, null, null, "time" + " DESC");
    }

    public Cursor getAllChannels() {
        return db.query(TABLE_CHANNELS, new String[]{COLUMN_ID, COLUMN_CHANNEL_NAME, COLUMN_LINK}, null, null, null, null, null);
    }

    public long createNews(ContentValues news) {
        return db.insert(TABLE_NEWS, null, news);
    }

    public boolean deleteChannel(long channelId) {
        return db.delete(TABLE_CHANNELS, COLUMN_ID + "=" + channelId, null) == 1;
    }
}

