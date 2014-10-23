package com.dslplatform.examples.android.utilities;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.dslplatform.examples.android.MainActivity;
import com.dslplatform.examples.android.R;
import com.dslplatform.examples.android.AndroidBooks.Book;
import com.dslplatform.examples.android.AndroidBooks.Review;
import com.dslplatform.examples.android.AndroidBooks.repositories.BookRepository;
import com.dslplatform.patterns.Specification;

public class ReviewUtility {

	private static Spinner spinnerRating;
	private static EditText editReviewedBy;
	private static EditText editComment;

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
				})
				.setPositiveButton(R.string.add_review, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// creates a new AddDialogFragment for adding a review
						createAddReviewDialog(isbn, context).show();
					}
				});
		// show the dialog
		builder.create().show();
	}

	/**
	 * Creates a new AlertDialog with which you can add a new review.
	 * 
	 * @param isbn
	 *            - isbn of the book we are updating
	 * @param context
	 *            - activity context which called the method
	 * @return AlertDialog for adding new review for according book
	 */
	private static AlertDialog createAddReviewDialog(final String isbn,
			final Activity context) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final LayoutInflater inflater = context.getLayoutInflater();
		final View v = inflater.inflate(R.layout.add_review_layout, null);

		spinnerRating = (Spinner) v.findViewById(R.id.spinner_rating);
		editReviewedBy = (EditText) v.findViewById(R.id.edit_reviwedBy);
		editComment = (EditText) v.findViewById(R.id.edit_comment);

		builder.setView(v).setTitle("Add review")
				.setNegativeButton(R.string.close, null)
				.setPositiveButton(R.string.add_review, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						addReviewToDatabase(isbn);
					}
				});
		return builder.create();
	}

	/**
	 * Adds a new review to the database. First it uses a Specification to get
	 * the book to which we are going to add the review, than it gets the
	 * reviews, adds the new one and at the end it updates the book.
	 *
	 * @param isbn
	 *            - isbn of the book to which we are adding a new review
	 */
	private static void addReviewToDatabase(final String isbn) {

		// specification for getting the book
		final BookRepository bookRepository = new BookRepository(
				MainActivity.locator);
		final Specification<Book> isbnEquals = new Book.isbnEquals(isbn);
		final Future<List<Book>> futureResults = bookRepository
				.search(isbnEquals);

		// creating new Review that needs to be added
		final Review review = new Review();
		review.setRating(Integer.parseInt(String.valueOf(spinnerRating
				.getSelectedItem())));
		review.setReviewdBy(editReviewedBy.getText().toString());
		review.setComment(editComment.getText().toString());

		try {
			// getting the book, adding the review and updating
			final Book book = futureResults.get().get(0);
			book.getReviews().add(review);
			book.update();
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
	}
}
