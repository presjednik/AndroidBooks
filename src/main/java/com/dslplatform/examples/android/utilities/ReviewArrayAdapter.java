package com.dslplatform.examples.android.utilities;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dslplatform.examples.android.R;
import com.dslplatform.examples.android.AndroidBooks.Review;

public class ReviewArrayAdapter extends ArrayAdapter<Review> {

	private List<Review> reviews;

	public ReviewArrayAdapter(Activity context, int resource,
			List<Review> objects) {
		super(context, resource, objects);
		this.reviews = objects;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		View v = view;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.review_layout, null);
		}

		// get the right review
		final Review review = reviews.get(position);

		// link the ImageView and TextViews with the according elements in the
		// review_layout
		final ImageView rating = (ImageView) v.findViewById(R.id.image_review);
		final TextView email = (TextView) v
				.findViewById(R.id.text_email_review);
		final TextView comment = (TextView) v
				.findViewById(R.id.text_comment_review);

		// choose the right start picture to display the rating of the user
		switch (review.getRating()) {
		case 1:
			rating.setImageResource(R.drawable.one_star);
			break;
		case 2:
			rating.setImageResource(R.drawable.two_star);
			break;
		case 3:
			rating.setImageResource(R.drawable.three_star);
			break;
		case 4:
			rating.setImageResource(R.drawable.four_star);
			break;
		case 5:
			rating.setImageResource(R.drawable.five_star);
			break;
		default:
			rating.setImageResource(R.drawable.five_star);
			break;
		}
		email.setText("by " + review.getReviewdBy());
		comment.setText(review.getComment());

		return v;
	}

}
