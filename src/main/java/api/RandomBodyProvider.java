package api;

import java.util.Random;

public class RandomBodyProvider implements IBodyProvider {
    private int length;
    public RandomBodyProvider(int length){
        this.length=length;
    }
    @Override
    public String provide() {
        int low='a';
        int max='z';
        Random random=new Random();
        StringBuilder s= new StringBuilder();
        for (int i=0;i<length;i++) {
            s.append((char) (low + random.nextInt(max - low)));
        }
        return s.toString();
    }
}
