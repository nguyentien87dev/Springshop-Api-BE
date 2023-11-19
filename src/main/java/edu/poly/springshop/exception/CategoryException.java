package edu.poly.springshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryException extends RuntimeException {
    public CategoryException(String message) {
        super(message);
    }
}
//tạo contructor khởi tạo massage
//CategoryExceptio: được sử dụng trong các tinh huống category bị lõi
//@ResponseStatus(HttpStatus.BAD_REQUEST): hiển thị trạng thái trả về cho client biết là yêu cầu xử lý phía service có vấn đề
//trong quá tình xử lý sẽ xử dụng AOP để đưa và phản hồi thông báo 1 cách thân thiện về cho client