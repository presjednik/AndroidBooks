package com.dslplatform.examples.android.AndroidBooks;

public final class AuthorsBookCount implements java.io.Serializable {
    @com.fasterxml.jackson.annotation.JsonCreator
    public AuthorsBookCount(
            @com.fasterxml.jackson.annotation.JsonProperty("author") final String author,
            @com.fasterxml.jackson.annotation.JsonProperty("BookCount") final long BookCount) {
        this();
        if (author != null) setAuthor(author);
        setBookCount(BookCount);
    }

    public AuthorsBookCount() {
        this.author = "";
    }

    private static final long serialVersionUID = 0x0097000a;

    private String author;

    @com.fasterxml.jackson.annotation.JsonProperty("author")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getAuthor() {
        return author;
    }

    public AuthorsBookCount setAuthor(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"author\" cannot be null!");
        this.author = value;

        return this;
    }

    private long BookCount;

    @com.fasterxml.jackson.annotation.JsonProperty("BookCount")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public long getBookCount() {
        return BookCount;
    }

    public AuthorsBookCount setBookCount(final long value) {
        this.BookCount = value;

        return this;
    }

    public AuthorsBookCount populate() throws java.io.IOException {
        return populate(com.dslplatform.client.Bootstrap.getLocator());
    }

    public AuthorsBookCount populate(
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        final com.dslplatform.client.ReportingProxy proxy = (locator != null
                ? locator
                : com.dslplatform.client.Bootstrap.getLocator())
                .resolve(com.dslplatform.client.ReportingProxy.class);
        final AuthorsBookCount result;
        try {
            result = proxy.populate(this).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        this.BookCount = result.BookCount;
        return this;
    }
}
