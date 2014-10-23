package com.dslplatform.examples.android.utilities;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ListView;

import com.dslplatform.examples.android.MainActivity;
import com.dslplatform.examples.android.R;
import com.dslplatform.examples.android.AndroidBooks.Book;
import com.dslplatform.examples.android.AndroidBooks.Review;
import com.dslplatform.examples.android.AndroidBooks.repositories.BookRepository;
import com.dslplatform.patterns.Specification;

public class ReviewUtility {

	/**
	 * 
	 * Gets all reviews for a book using DSL specifications. Displays all the
	 * reviews in a AlertDialog.
	 * 
	 * @param isbn
	 *            - isbn of the book we want the reviews
	 * @param context
	 *            - activity context which called the method
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void showReviewsForBook(final String isbn,
			final Activity context) throws InterruptedException,
			ExecutionException {

		// create the book repository
		final BookRepository bookRepository = new BookRepository(
				MainActivity.locator);

		// create the specification
		final Specification<Book> isbnEquals = new Book.isbnEquals(isbn);

		// get the book which matches the 'isbn'
		final Future<List<Book>> futureResults = bookRepository
				.search(isbnEquals);

		// get all reviews for the book
		final List<Review> reviews = futureResults.get().get(0).getReviews();

		// initialize the adapter which is representing a review
		final ReviewArrayAdapter adapter = new ReviewArrayAdapter(context,
				R.layout.review_layout, reviews);

		// create a ListView which is the AlertDialog going to use
		final ListView listView = new ListView(context);
		// set the created adapter for ListView
		listView.setAdapter(adapter);

		// build the AlertDialog with the created ListView
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Reviews").setView(listView)
				.setNegativeButton(R.string.close, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		// show the dialog
		builder.create().show();
	}
}
