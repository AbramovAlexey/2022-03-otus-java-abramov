package hw5;

class TestLoggingImpl implements TestLogging{

    @Override
    public void calculation(int param1) {
        System.out.println("Result = " + param1);
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("Result = " + (param1 + param2));
    }

    @Override
    public void calculation(int param1, int param2, int param3) {
        System.out.println("Result = " + (param1 + param2 + param3));
    }

}