package com.dslplatform.examples.android.AndroidBooks;

public class Review implements java.io.Serializable {
    public Review() {
        URI = java.util.UUID.randomUUID().toString();
        this.rating = 0;
        this.comment = "";
        this.reviewdBy = "";
        this.Bookisbn = "";
        this.Index = 0;
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
        final Review other = (Review) obj;

        return URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return "Review(" + URI + ')';
    }

    private static final long serialVersionUID = 0x0097000a;

    @com.fasterxml.jackson.annotation.JsonCreator
    private Review(
            @com.fasterxml.jackson.annotation.JacksonInject("_serviceLocator") final com.dslplatform.patterns.ServiceLocator _serviceLocator,
            @com.fasterxml.jackson.annotation.JsonProperty("URI") final String URI,
            @com.fasterxml.jackson.annotation.JsonProperty("rating") final int rating,
            @com.fasterxml.jackson.annotation.JsonProperty("comment") final String comment,
            @com.fasterxml.jackson.annotation.JsonProperty("reviewdBy") final String reviewdBy,
            @com.fasterxml.jackson.annotation.JsonProperty("Bookisbn") final String Bookisbn,
            @com.fasterxml.jackson.annotation.JsonProperty("Index") final int Index) {
        this._serviceLocator = _serviceLocator;
        this.URI = URI != null ? URI : new java.util.UUID(0L, 0L).toString();
        this.rating = rating;
        this.comment = comment == null ? "" : comment;
        this.reviewdBy = reviewdBy == null ? "" : reviewdBy;
        this.Bookisbn = Bookisbn == null ? "" : Bookisbn;
        this.Index = Index;
    }

    private int rating;

    @com.fasterxml.jackson.annotation.JsonProperty("rating")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public int getRating() {
        return rating;
    }

    public Review setRating(final int value) {
        this.rating = value;

        return this;
    }

    private String comment;

    @com.fasterxml.jackson.annotation.JsonProperty("comment")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getComment() {
        return comment;
    }

    public Review setComment(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"comment\" cannot be null!");
        this.comment = value;

        return this;
    }

    private String reviewdBy;

    @com.fasterxml.jackson.annotation.JsonProperty("reviewdBy")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getReviewdBy() {
        return reviewdBy;
    }

    public Review setReviewdBy(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"reviewdBy\" cannot be null!");
        this.reviewdBy = value;

        return this;
    }

    private String Bookisbn;

    @com.fasterxml.jackson.annotation.JsonProperty("Bookisbn")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getBookisbn() {
        return Bookisbn;
    }

    public Review setBookisbn(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"Bookisbn\" cannot be null!");
        com.dslplatform.examples.android.Guards.checkLength(value, 14);
        this.Bookisbn = value;

        return this;
    }

    private int Index;

    @com.fasterxml.jackson.annotation.JsonProperty("Index")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public int getIndex() {
        return Index;
    }

    public Review setIndex(final int value) {
        this.Index = value;

        return this;
    }

    public Review(
            final int rating,
            final String comment,
            final String reviewdBy) {
        setRating(rating);
        setComment(comment);
        setReviewdBy(reviewdBy);
    }

}
