package model.android.AndroidBooks.repositories;

public class FooRepository
        extends
        com.dslplatform.client.ClientPersistableRepository<model.android.AndroidBooks.Foo> {
    public FooRepository(
            final com.dslplatform.patterns.ServiceLocator locator) {
        super(model.android.AndroidBooks.Foo.class, locator);
    }
}
