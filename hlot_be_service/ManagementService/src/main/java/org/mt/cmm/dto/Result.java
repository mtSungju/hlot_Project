package org.mt.cmm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

    private boolean isSuccess;
    private String message;
    private T data;

    @Builder
    public Result(boolean isSuccess, String message, T data){
        this.isSuccess = isSuccess;
        this.message = isSuccess ? "성공적으로 처리되었습니다." : message;
        this.data = data;
    }

    public static <T> Result resSuccess(T data){
        return Result.builder()
                .isSuccess(true)
                .message("성공적으로 처리되었습니다.")
                .data(data)
                .build();
    }

    public static <T> Result resError(String message){
        return  Result.builder()
                .isSuccess(false)
                .message(message)
                .data(null)
                .build();
    }

}
