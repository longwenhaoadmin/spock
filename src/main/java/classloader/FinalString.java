package classloader;

public class FinalString {
    public static final String A = "ab";
    public static final String B = "cd";

	/*public static final String A ;
	public static final String B ;
	static{
		A = "ab";
		B = "cd";
		System.out.println("静态块:"+A+B);
	}*/

    public static void main(String[] args) {
        String t = "abcd";
        String s = A + B;
        System.out.println(s);
        System.out.println(A + "  " + B);

        System.out.println(s == t);
        System.out.println(t.equals(s));
    }
}
