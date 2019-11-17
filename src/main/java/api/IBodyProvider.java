package api;

import java.util.function.Supplier;

@FunctionalInterface
public interface IBodyProvider{
    String provide();
}
