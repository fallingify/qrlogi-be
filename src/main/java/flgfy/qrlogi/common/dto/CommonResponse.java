package flgfy.qrlogi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import flgfy.qrlogi.common.enums.ReturnCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private String returnCode;
    private String returnMessage;
    private T info;

    public CommonResponse(ReturnCode returnCode, T info) {
        this.returnCode = (returnCode != null) ? returnCode.getCode() : "UNKNOWN";
        this.returnMessage = (returnCode != null) ? returnCode.getText() : "Unknown error";
        this.info = info;
    }

    public static <T> CommonResponse<T> of(ReturnCode returnCode, T info) {
        if (returnCode == null) {
            returnCode = ReturnCode.UNKNOWN_ERROR;
        }
        return new CommonResponse<>(returnCode, info);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(ReturnCode.SUCCESS, null);
    }

    public static <T> CommonResponse<T> success(T info) {
        return new CommonResponse<>(ReturnCode.SUCCESS, info);
    }

    public static <T> CommonResponse<T> fail(ReturnCode returnCode, T info) {
        return new CommonResponse<>(returnCode, info);
    }

    public static <T> CommonResponse<T> fail(ReturnCode returnCode) {
        return new CommonResponse<>(returnCode, null);
    }

    public static <T> CommonResponse<T> fail(T info) {
        return new CommonResponse<>(ReturnCode.UNKNOWN_ERROR, info);
    }




}
