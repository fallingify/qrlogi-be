package flgfy.qrlogi.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorInfo {

    @Builder.Default
    private String exception = "알 수 없는 에러";

    @Builder.Default
    private String errorMessage = "에러 메시지 없음";

    @Builder.Default
    private int status = 500;

    @Builder.Default
    private String traceId = "unknown-trace-id";
}
