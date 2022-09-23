/**
 * Class Javadoc
 */
public class Test60 {

    /**
     * Field Javadoc
     */
    private int a;

    /**
     * Constructor Javadoc
     * @param x the parameter
     */
    public Test60(int x) {
        a = x;
    }
    
    /**
     * Method Javadoc
     * @param x the parameter
     */
    public void m(int x) {
        a = x + 2;
    }
}

/**
 * Enum Javadoc
 */
enum Enum60 {

    /**
     * Field Constant Javadoc
     */
    X(100),

    /**
     * Enum Constant Javadoc
     */
    Y(100);

    /**
     * Enum Field Javadoc
     */
    private int x;

    /**
     * Enum Constructor Javadoc
     * @param x the parameter
     */
    Enum60(int x) {
        this.x = x;
    }
}
