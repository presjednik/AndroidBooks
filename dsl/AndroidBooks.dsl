module AndroidBooks
{
    aggregate Book(isbn)
    {
        String(14) isbn; //978-0590353427
        String title;
        List<String> authors;
        List<Review> reviews;
        String publisher;
        String language;
        Date publishedOn;
        Int pages;
        
        specification searchBooks 'b => b.isbn.Contains(pattern) || b.title.ToLower().Contains(pattern)
							|| b.authors.Any(a => a.ToLower().Contains(pattern))'
				{
					String pattern;
				}
		
		specification isbnEquals 'b => b.isbn.Equals(isbn)'
				{
					String isbn;
				}
    }

    entity Review
    {
        int rating;
        String comment;
        String reviewdBy;
    }

	report AuthorsBookCount
	{
		String author;
		count<Book> BookCount 'b => b.authors.Any(a => a.Equals(author))';
	}
}