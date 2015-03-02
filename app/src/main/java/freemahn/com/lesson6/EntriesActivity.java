package freemahn.com.lesson6;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Freemahn on 22.01.2015.
 */


public class EntriesActivity
        extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_CHANNEL_ID = "extra_channel_id";
    private long channelId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelId = getIntent().getLongExtra(EXTRA_CHANNEL_ID, -1);
        if (channelId == -1) finish();
        getLoaderManager().initLoader(0, null, this);
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{"title"}, new int[]{android.R.id.text1}, 0));
        EntriesIntentService.startActionUpdateChannel(this, channelId);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor item = ((Cursor) l.getAdapter().getItem(position));

        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("link", item.getString(item.getColumnIndex("link")));
        intent.putExtra("description", item.getString(item.getColumnIndex("description")));
        intent.putExtra("title", item.getString(item.getColumnIndex("title")));

        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = new CursorLoader(this, Uri.parse(EntryContentProvider.NEWS_URI.toString() + "/" + channelId), null, null, null, null);
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{"title"}, new int[]{android.R.id.text1}, 0));
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        ((CursorAdapter) getListAdapter()).swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((CursorAdapter) getListAdapter()).swapCursor(null);
    }
}
