package com.example.xyzreader.ui;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private Cursor mCursor;
    private long mStartId;



    private ArticleDetailFragment articleDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_article_detail);
      getSupportLoaderManager().initLoader(0, null, this);
      //if (savedInstanceState == null) {
        if (getIntent() != null && getIntent().getData() != null) {
          mStartId = ItemsContract.Items.getItemId(getIntent().getData());
          //mSelectedItemId = mStartId;
        }
      //}
      articleDetailFragment = ArticleDetailFragment.newInstance(mStartId);
      getSupportFragmentManager().beginTransaction().add(R.id.frame_detail,articleDetailFragment,"ARTICLE_DETAIL").commit();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;

        // Select the start ID
        if (mStartId > 0) {
            mCursor.moveToFirst();
            // TODO: optimize
            while (!mCursor.isAfterLast()) {
                if (mCursor.getLong(ArticleLoader.Query._ID) == mStartId) {
                    final int position = mCursor.getPosition();
                    break;
                }
                mCursor.moveToNext();
            }
            mStartId = 0;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
    }





}
