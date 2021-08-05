package uz.usmonov.appclicksystems.Result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Result {

    private boolean success;

    private Object object;

    public Result(boolean success) {
        this.success = success;
    }
}
