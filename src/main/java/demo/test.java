package demo;

public class test {

    public static final String A = "ab";
    public static final String B = "cd";


    public static void main(String[] args){
        String s = A+B;
        String t = "abcd";
        System.out.println(s==t);
    }
}
