package com.dslplatform.examples.android.AndroidBooks;

public class Book implements java.io.Serializable,
        com.dslplatform.patterns.AggregateRoot {
    public Book() {
        URI = java.util.UUID.randomUUID().toString();
        this.isbn = "";
        this.title = "";
        this.authors = new java.util.ArrayList<String>();
        this.reviews = new java.util.ArrayList<com.dslplatform.examples.android.AndroidBooks.Review>();
        this.publisher = "";
        this.language = "";
        this.publishedOn = org.joda.time.LocalDate.now();
        this.pages = 0;
    }

    private transient com.dslplatform.patterns.ServiceLocator _serviceLocator;

    private String URI;

    @com.fasterxml.jackson.annotation.JsonProperty("URI")
    public String getURI() {
        return this.URI;
    }

    @Override
    public int hashCode() {
        return URI.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;
        final Book other = (Book) obj;

        return URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return "Book(" + URI + ')';
    }

    private static final long serialVersionUID = 0x0097000a;

    @com.fasterxml.jackson.annotation.JsonCreator
    private Book(
            @com.fasterxml.jackson.annotation.JacksonInject("_serviceLocator") final com.dslplatform.patterns.ServiceLocator _serviceLocator,
            @com.fasterxml.jackson.annotation.JsonProperty("URI") final String URI,
            @com.fasterxml.jackson.annotation.JsonProperty("isbn") final String isbn,
            @com.fasterxml.jackson.annotation.JsonProperty("title") final String title,
            @com.fasterxml.jackson.annotation.JsonProperty("authors") final java.util.List<String> authors,
            @com.fasterxml.jackson.annotation.JsonProperty("reviews") final java.util.List<com.dslplatform.examples.android.AndroidBooks.Review> reviews,
            @com.fasterxml.jackson.annotation.JsonProperty("publisher") final String publisher,
            @com.fasterxml.jackson.annotation.JsonProperty("language") final String language,
            @com.fasterxml.jackson.annotation.JsonProperty("publishedOn") final org.joda.time.LocalDate publishedOn,
            @com.fasterxml.jackson.annotation.JsonProperty("pages") final int pages) {
        this._serviceLocator = _serviceLocator;
        this.URI = URI != null ? URI : new java.util.UUID(0L, 0L).toString();
        this.isbn = isbn == null ? "" : isbn;
        this.title = title == null ? "" : title;
        this.authors = authors == null
                ? new java.util.ArrayList<String>()
                : authors;
        this.reviews = reviews == null
                ? new java.util.ArrayList<com.dslplatform.examples.android.AndroidBooks.Review>()
                : reviews;
        this.publisher = publisher == null ? "" : publisher;
        this.language = language == null ? "" : language;
        this.publishedOn = publishedOn == null ? new org.joda.time.LocalDate(1,
                1, 1) : publishedOn;
        this.pages = pages;
    }

    public boolean isNewAggregate() {
        return _serviceLocator == null;
    }

    public static Book find(final String uri) throws java.io.IOException {
        return find(uri, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static Book find(
            final String uri,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.CrudProxy.class)
                    .read(Book.class, uri).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Book> find(final Iterable<String> uris)
            throws java.io.IOException {
        return find(uris, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Book> find(
            final Iterable<String> uris,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .find(Book.class, uris).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Book> search() throws java.io.IOException {
        return search(null, null, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Book> search(
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        return search(null, null, locator);
    }

    public static java.util.List<Book> search(
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(limit, offset,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Book> search(
            final Integer limit,
            final Integer offset,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .search(Book.class, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Book> search(
            final com.dslplatform.patterns.Specification<Book> specification)
            throws java.io.IOException {
        return search(specification, null, null,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Book> search(
            final com.dslplatform.patterns.Specification<Book> specification,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        return search(specification, null, null, locator);
    }

    public static java.util.List<Book> search(
            final com.dslplatform.patterns.Specification<Book> specification,
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(specification, limit, offset,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Book> search(
            final com.dslplatform.patterns.Specification<Book> specification,
            final Integer limit,
            final Integer offset,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .search(specification, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count() throws java.io.IOException {
        return count(com.dslplatform.client.Bootstrap.getLocator());
    }

    public static long count(
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .count(Book.class).get().longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count(
            final com.dslplatform.patterns.Specification<Book> specification)
            throws java.io.IOException {
        return count(specification,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static long count(
            final com.dslplatform.patterns.Specification<Book> specification,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .count(specification).get().longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private void updateWithAnother(final Book result) {
        this.URI = result.URI;

        this.isbn = result.isbn;
        this.title = result.title;
        this.authors = result.authors;
        this.reviews = result.reviews;
        this.publisher = result.publisher;
        this.language = result.language;
        this.publishedOn = result.publishedOn;
        this.pages = result.pages;
    }

    public Book create() throws java.io.IOException {
        return create(_serviceLocator != null
                ? _serviceLocator
                : com.dslplatform.client.Bootstrap.getLocator());
    }

    public Book create(com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        final Book result;
        try {
            com.dslplatform.client.CrudProxy proxy = (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.CrudProxy.class);
            result = proxy.create(this).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        this.updateWithAnother(result);
        this._serviceLocator = locator != null
                ? locator
                : com.dslplatform.client.Bootstrap.getLocator();
        return this;
    }

    public Book update() throws java.io.IOException {
        if (_serviceLocator == null)
            throw new java.io.IOException(
                    "Can't update newly created aggregate root");
        final Book result;
        try {
            com.dslplatform.client.CrudProxy proxy = _serviceLocator
                    .resolve(com.dslplatform.client.CrudProxy.class);
            result = proxy.update(this).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        this.updateWithAnother(result);
        return this;
    }

    public Book delete() throws java.io.IOException {
        if (_serviceLocator == null)
            throw new java.io.IOException(
                    "Can't delete newly created aggregate root");
        try {
            com.dslplatform.client.CrudProxy proxy = _serviceLocator
                    .resolve(com.dslplatform.client.CrudProxy.class);
            return proxy.delete(Book.class, URI).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private String isbn;

    @com.fasterxml.jackson.annotation.JsonProperty("isbn")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"isbn\" cannot be null!");
        com.dslplatform.examples.android.Guards.checkLength(value, 14);
        this.isbn = value;

        return this;
    }

    private String title;

    @com.fasterxml.jackson.annotation.JsonProperty("title")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getTitle() {
        return title;
    }

    public Book setTitle(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"title\" cannot be null!");
        this.title = value;

        return this;
    }

    private java.util.List<String> authors;

    @com.fasterxml.jackson.annotation.JsonProperty("authors")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public java.util.List<String> getAuthors() {
        return authors;
    }

    public Book setAuthors(final java.util.List<String> value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"authors\" cannot be null!");
        com.dslplatform.examples.android.Guards.checkNulls(value);
        this.authors = value;

        return this;
    }

    private java.util.List<com.dslplatform.examples.android.AndroidBooks.Review> reviews;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("reviews")
    public java.util.List<com.dslplatform.examples.android.AndroidBooks.Review> getReviews() {
        return reviews;
    }

    public Book setReviews(
            final java.util.List<com.dslplatform.examples.android.AndroidBooks.Review> value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"reviews\" cannot be null!");
        com.dslplatform.examples.android.Guards.checkNulls(value);
        this.reviews = value;

        return this;
    }

    private String publisher;

    @com.fasterxml.jackson.annotation.JsonProperty("publisher")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getPublisher() {
        return publisher;
    }

    public Book setPublisher(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"publisher\" cannot be null!");
        this.publisher = value;

        return this;
    }

    private String language;

    @com.fasterxml.jackson.annotation.JsonProperty("language")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getLanguage() {
        return language;
    }

    public Book setLanguage(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"language\" cannot be null!");
        this.language = value;

        return this;
    }

    private org.joda.time.LocalDate publishedOn;

    @com.fasterxml.jackson.annotation.JsonProperty("publishedOn")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public org.joda.time.LocalDate getPublishedOn() {
        return publishedOn;
    }

    public Book setPublishedOn(final org.joda.time.LocalDate value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"publishedOn\" cannot be null!");
        this.publishedOn = value;

        return this;
    }

    private int pages;

    @com.fasterxml.jackson.annotation.JsonProperty("pages")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public int getPages() {
        return pages;
    }

    public Book setPages(final int value) {
        this.pages = value;

        return this;
    }

    public static class searchBooks implements
            com.dslplatform.patterns.Specification<Book>, java.io.Serializable {
        public searchBooks(
                final String pattern) {
            setPattern(pattern);
        }

        public searchBooks() {
            this.pattern = "";
        }

        public java.util.List<Book> search() throws java.io.IOException {
            return search(null, null,
                    com.dslplatform.client.Bootstrap.getLocator());
        }

        public java.util.List<Book> search(
                final com.dslplatform.patterns.ServiceLocator locator)
                throws java.io.IOException {
            return search(null, null, locator);
        }

        public java.util.List<Book> search(
                final Integer limit,
                final Integer offset) throws java.io.IOException {
            return search(limit, offset,
                    com.dslplatform.client.Bootstrap.getLocator());
        }

        public java.util.List<Book> search(
                final Integer limit,
                final Integer offset,
                final com.dslplatform.patterns.ServiceLocator locator)
                throws java.io.IOException {
            try {
                return (locator != null
                        ? locator
                        : com.dslplatform.client.Bootstrap.getLocator())
                        .resolve(com.dslplatform.client.DomainProxy.class)
                        .search(this, limit, offset, null).get();
            } catch (final InterruptedException e) {
                throw new java.io.IOException(e);
            } catch (final java.util.concurrent.ExecutionException e) {
                throw new java.io.IOException(e);
            }
        }

        public long count() throws java.io.IOException {
            return count(com.dslplatform.client.Bootstrap.getLocator());
        }

        public long count(final com.dslplatform.patterns.ServiceLocator locator)
                throws java.io.IOException {
            try {
                return (locator != null
                        ? locator
                        : com.dslplatform.client.Bootstrap.getLocator())
                        .resolve(com.dslplatform.client.DomainProxy.class)
                        .count(this).get().longValue();
            } catch (final InterruptedException e) {
                throw new java.io.IOException(e);
            } catch (final java.util.concurrent.ExecutionException e) {
                throw new java.io.IOException(e);
            }
        }

        private static final long serialVersionUID = 0x0097000a;

        private String pattern;

        @com.fasterxml.jackson.annotation.JsonProperty("pattern")
        @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
        public String getPattern() {
            return pattern;
        }

        public searchBooks setPattern(final String value) {
            if (value == null)
                throw new IllegalArgumentException(
                        "Property \"pattern\" cannot be null!");
            this.pattern = value;

            return this;
        }

    }

    public Book(
            final String isbn,
            final String title,
            final java.util.List<String> authors,
            final java.util.List<com.dslplatform.examples.android.AndroidBooks.Review> reviews,
            final String publisher,
            final String language,
            final org.joda.time.LocalDate publishedOn,
            final int pages) {
        setIsbn(isbn);
        setTitle(title);
        setAuthors(authors);
        setReviews(reviews);
        setPublisher(publisher);
        setLanguage(language);
        setPublishedOn(publishedOn);
        setPages(pages);
    }

}
