package com.dslplatform.examples.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.dslplatform.client.Bootstrap;
import com.dslplatform.examples.android.AndroidBooks.AuthorsBookCount;
import com.dslplatform.examples.android.AndroidBooks.Book;
import com.dslplatform.examples.android.utilities.BookArrayAdapter;
import com.dslplatform.examples.android.utilities.ReviewUtility;
import com.dslplatform.patterns.ServiceLocator;

public class MainActivity extends Activity {

	public static ServiceLocator locator;
	private ListView listView;
	private SearchView searchView;

	private final HashMap<String, Long> authorsBookCountMap = new HashMap<String, Long>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.list_books);

		try {
			locator = init();

			// get all books from the database
			final List<Book> bookList = Book.search();

			// send the authors list so we count the number of books for a user
			for (Book b : bookList) {
				countAuthorsBooks(b.getAuthors());
			}

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
	 * Counts the number of books associated with an author. Iterates through
	 * the list to count books for all authors of a book. Uses report for
	 * counting the books.
	 *
	 * @param list
	 *            - list of authors for a book
	 */
	private void countAuthorsBooks(List<String> list) {
		// initialize the report
		final AuthorsBookCount count = new AuthorsBookCount();
		for (String s : list) {
			count.setAuthor(s);
			try {
				// get the number of books for the user
				authorsBookCountMap.put(s, count.populate(locator)
						.getBookCount());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		case R.id.report:
			showAuthorsBookCount();
			return true;
		default:
			return false;
		}
	}

	/**
	 * Displays an AlertDialog which shows how many books an author has in our
	 * database.
	 */
	private void showAuthorsBookCount() {
		
		final List<String> listString = new ArrayList<String>();
		
		// adding data from the hashMap (where all the information about book
		// count for author is stored) to the listString holding only one string
		// so it could be displayed in the simple_list_item_1
		for (Map.Entry<String, Long> entry : authorsBookCountMap.entrySet()) {
			final StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(entry.getKey() + ": " + entry.getValue());
			listString.add(stringBuilder.toString());
		}
		
		final ListAdapter itemlist = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				listString.toArray(new String[listString.size()]));
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Authors book count");
		builder.setAdapter(itemlist, null);
		builder.setNegativeButton(R.string.close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create().show();
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
