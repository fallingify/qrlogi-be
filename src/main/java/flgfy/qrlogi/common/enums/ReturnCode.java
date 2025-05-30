package flgfy.qrlogi.common.enums;

import org.springframework.http.HttpStatus;


public enum ReturnCode {

    BAD_REQUEST("400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_RESOURCE("409", "이미 존재하는 리소스입니다.", HttpStatus.CONFLICT),
    FORBIDDEN("403", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND("404", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SUCCESS("0000", "요청 처리 성공", HttpStatus.OK),
    UNAUTHORIZED("401", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    UNKNOWN_ERROR("9999", "알 수 없는 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR("422", "입력값이 유효하지 않습니다.", HttpStatus.UNPROCESSABLE_ENTITY);

    private final String code;
    private final String text;
    private final HttpStatus httpStatus;

    ReturnCode(String code, String text, HttpStatus httpStatus) {
        this.code = code;
        this.text = text;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
