package com.dslplatform.examples.android;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;

import com.dslplatform.client.Bootstrap;
import com.dslplatform.examples.android.AndroidBooks.Book;
import com.dslplatform.examples.android.utilities.BookArrayAdapter;
import com.dslplatform.examples.android.utilities.ReviewUtility;
import com.dslplatform.patterns.ServiceLocator;

public class MainActivity extends Activity {

	public static ServiceLocator locator;
	private ListView listView;
	private SearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.list_books);

		try {
			locator = init();

			// get all books from the database
			final List<Book> bookList = Book.search();
			final BookArrayAdapter bookAdapter = new BookArrayAdapter(this,
					R.layout.book_layout, bookList);
			listView.setAdapter(bookAdapter);
			listView.setTextFilterEnabled(true);

			// adding a OnItemClickListener to call the function
			// for displaying reviews for the book
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// get the clicked book
					final Book book = (Book) parent.getAdapter().getItem(
							position);
					try {
						// call the method which displays the AlertDialog with
						// the reviews
						ReviewUtility.showReviewsForBook(book.getIsbn(),
								MainActivity.this);
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Call to initialize an instance of ServiceLocator with a dsl-project.props
	 */
	public static ServiceLocator init() throws IOException {
		final ServiceLocator locator = Bootstrap.init(MainActivity.class
				.getResourceAsStream("/dsl-project.props"));

		// an example how to resolve components from the ServiceLocator
		locator.resolve(Logger.class).info("Locator has been initialized.");
		return locator;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			onSearchRequested();
			return true;
		default:
			return false;
		}
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			MenuItem searchItem = menu.findItem(R.id.search);
			searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
		} else {
			Log.d("OLDER THAN HONEYCOMB",
					"Android version is older than Honeycomb");
		}
		return true;
	}

}
