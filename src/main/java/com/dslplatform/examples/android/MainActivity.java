package com.dslplatform.examples.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import com.dslplatform.examples.android.R;
import com.dslplatform.examples.android.AndroidBooks.Book;
import com.dslplatform.examples.android.utilities.BookArrayAdapter;

import org.slf4j.Logger;

import com.dslplatform.client.Bootstrap;
import com.dslplatform.patterns.ServiceLocator;

public class MainActivity extends Activity {
	
	public static ServiceLocator locator;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.list_books);
		
		try {
			locator = init();
			
			//get all books from the database
			final List<Book> bookList = Book.search();
			final BookArrayAdapter bookAdapter = new BookArrayAdapter(this,
			R.layout.book_layout, bookList);
			listView.setAdapter(bookAdapter);
			listView.setTextFilterEnabled(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
