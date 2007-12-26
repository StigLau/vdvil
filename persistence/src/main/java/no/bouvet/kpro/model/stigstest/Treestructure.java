package no.bouvet.kpro.model.stigstest;

import java.util.Collection;

/**
 * @deprecated Not in use yet
 */
public interface Treestructure <T> {

    public T getParent();

    public Collection<T> getChildren();
}
