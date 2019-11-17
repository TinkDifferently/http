package api;

public enum StandardCodeChecker {
    STATUS_200("STATUS_200"), STATUS_404("STATUS_404"), STATUS_502("STATUS_502"), DOMAIN_EXISTS("DOMAIN_EXISTS");
    class StandardCodeCheckerImpl implements ICodeChecker{
        private int expectedCode;
        StandardCodeCheckerImpl(int expectedCode){
            this.expectedCode=expectedCode;
        }
        @Override
        public void check(int code) {
            if (code == expectedCode) {
                System.out.println("GOOD REQUEST");
            } else {
                System.out.println("BAD REQUEST");
            }
        }
    }
    public ICodeChecker checker;

    StandardCodeChecker(String status){
    switch (status){
        case "STATUS_200": checker= new StandardCodeCheckerImpl(200); break;
        case "STATUS_404": checker= new StandardCodeCheckerImpl(404); break;
        case "STATUS_502": checker= new StandardCodeCheckerImpl(502); break;
        case "DOMAIN_EXISTS": checker=(i)-> System.out.println(String.format("Получен код '%d', домен существует",i)); break;
        default:
    }
    }
}
