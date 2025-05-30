package flgfy.qrlogi.common.exception;

import flgfy.qrlogi.common.enums.ReturnCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ReturnCode errorCode;

    public BusinessException(ReturnCode errorCode) {
        super(errorCode.getText());
        this.errorCode = errorCode;
    }

}
