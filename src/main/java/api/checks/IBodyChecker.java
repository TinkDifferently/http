package api.checks;

@FunctionalInterface
public interface IBodyChecker {
    void check(String responseBody);
}
