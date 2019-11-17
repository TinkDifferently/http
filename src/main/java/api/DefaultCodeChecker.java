package api;

public class DefaultCodeChecker implements ICodeChecker {

    @Override
    public void check(int code) {
        if (code == 200) {
            System.out.println("GOOD REQUEST");
        } else {
            System.out.println("BAD REQUEST");
        }
    }
}
