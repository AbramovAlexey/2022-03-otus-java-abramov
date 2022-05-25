package hw5;

import hw5.annotation.Log;

public interface TestLogging {
    @Log
    void calculation(int param);
    void calculation(int param, int param2);
    @Log
    void calculation(int param, int param2, int param3);
}