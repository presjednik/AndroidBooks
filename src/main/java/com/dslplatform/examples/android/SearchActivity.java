package com.dslplatform.examples.android;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.dslplatform.examples.android.AndroidBooks.Book;
import com.dslplatform.examples.android.AndroidBooks.repositories.BookRepository;
import com.dslplatform.examples.android.utilities.BookArrayAdapter;
import com.dslplatform.examples.android.utilities.ReviewUtility;
import com.dslplatform.patterns.Specification;

public class SearchActivity extends Activity {

	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.list_books);

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Method for handling the intent. Getting the search query and using the
	 * specification to get books for the query.
	 * 
	 * @param intent
	 *            - intent from whom we get the query String
	 */
	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			final String query = intent.getStringExtra(SearchManager.QUERY)
					.toLowerCase(Locale.ENGLISH);

			try {
				// create a searchBooks specification with query String
				final BookRepository bookRepository = new BookRepository(
						MainActivity.locator);
				final Specification<Book> searchBooks = new Book.searchBooks(
						query);
				final Future<List<Book>> futureResults = bookRepository
						.search(searchBooks);

				final List<Book> bookList = futureResults.get();

				final BookArrayAdapter bookAdapter = new BookArrayAdapter(this,
						R.layout.book_layout, bookList);

				listView.setTextFilterEnabled(true);
				listView.setAdapter(bookAdapter);

				// adding a OnItemClickListener to call the function
				// for displaying reviews for the book
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// get the clicked book
						final Book book = (Book) parent.getAdapter().getItem(
								position);
						try {
							// call the method which displays the AlertDialog
							// with the reviews
							ReviewUtility.showReviewsForBook(book.getIsbn(),
									SearchActivity.this);
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}
				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

}
