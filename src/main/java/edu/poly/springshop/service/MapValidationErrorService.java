package edu.poly.springshop.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {
    public ResponseEntity<?> mapValidationFields(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;

    }
}
//BindingResult result: tham số truyền vào
//result.hasErrors()): kiểm tra hành động lõi nếu có lõi sẽ mapping
//Map<String, String> errorMap = new HashMap<>(): tạo đối tượng chứa 2 kiểu dữ liệu key= string, value=string
//for (FieldError error : result.getFieldErrors()): dùng vòng lặp duyệt qua từng phần
//errorMap.put: mapping với từng thông tin tương ứng bị lõi
//(error.getField(): lấy tên trường tương ứng bị lõi
//error.getDefaultMessage()): lấy thông báo lõi
//return new ResponseEntity: trả trạng thái về clinet
//errorMap: thông tin ánh xạ
//HttpStatus.BAD_REQUEST: trạng thái request