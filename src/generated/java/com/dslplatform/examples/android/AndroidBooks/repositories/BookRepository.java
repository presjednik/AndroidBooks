package com.dslplatform.examples.android.AndroidBooks.repositories;

public class BookRepository
        extends
        com.dslplatform.client.ClientPersistableRepository<com.dslplatform.examples.android.AndroidBooks.Book> {
    public BookRepository(
            final com.dslplatform.patterns.ServiceLocator locator) {
        super(com.dslplatform.examples.android.AndroidBooks.Book.class, locator);
    }
}
