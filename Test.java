public class Test {
    public static void main(String[] args) {
        try {
            Thread.sleep(5_000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int rc;
        String exitCode = System.getenv("TEST_RC");
        if (exitCode == null) {
            rc = 10;
        } else {
            rc = Integer.parseInt(exitCode);
        }
        System.out.println("Exit with " + rc);
        System.exit(rc);
    }
}
