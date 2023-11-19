package edu.poly.springshop.controller;

import edu.poly.springshop.domain.Category;
import edu.poly.springshop.dto.CategoryDto;
import edu.poly.springshop.service.CategoryService;
import edu.poly.springshop.service.MapValidationErrorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    @Autowired
    public MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto dto, BindingResult result) {
        // ResponseEntity<?>: chứa các thông tin trạng thái khi phản hồi
        //<?>: đại diện cho một kiểu dữ liệu không xác định cụ thể (wildcard type).
        //@RequestBody: nhận tông tin từ client gửi tới và đưa vào đối tượng dto
        //@Valid:
        //BindingResult result: ghi nhận các thông tin trong quá trình kiểm tra lõi nhập từ client và lưu vào biến result

//        cách 1: if (result.hasErrors()) {
//            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
//        }
        //result.hasErrors: trước khi lưu thông tin sẽ kiểm tra có lõi không?
        //ResponseEntity<>: nếu lõi sẽ trả trạng thái như sau
        //result.getAllErrors(): lấy các thông tin lõi từ body
        //HttpStatus.BAD_REQUEST: trả trạng lại yêu cầu
//        cách 2:

        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
        if (responseEntity != null) {
            return responseEntity;
        }

        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);   //sao chép từ dto sang entity

        entity = categoryService.save(entity);
        dto.setId(entity.getId());


        return new ResponseEntity<>(dto, HttpStatus.CREATED);
        //phản hồi thông tin mã lõi cho clinet là dto
        //HttpStatus.CREATED: tình trạng thông tin

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto dto) {
        // sử dụng @PathVariable để lấy giá trị của biến "id" từ đường dẫn URL. Nó chỉ định rằng giá trị của "id" sẽ được truyền vào biến Long "id" trong phương thức
        // Tham số thứ hai là một đối tượng CategoryDto được truyền vào từ phần thân của yêu cầu HTTP. Đối tượng này được chuyển đổi từ dữ liệu JSON được gửi trong phần thân của yêu cầu PUT. Thường được sử dụng để cập nhật thông tin của đối tượng.
        // ResponseEntity<?>: chứa các thông tin trạng thái khi phản hồi từ client
        //<?>: đại diện cho một kiểu dữ liệu không xác định cụ thể (wildcard type).
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);   //sao chép từ dto sang entity

        entity = categoryService.update(id, entity);        //cập nhật cần truyền id
        dto.setId(entity.getId());


        return new ResponseEntity<>(dto, HttpStatus.CREATED);
        //phản hồi thông tin mã lõi cho clinet là dto
        //HttpStatus.CREATED: tình trạng thông tin

    }

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);

    }

    @GetMapping("/page")
    public ResponseEntity<?> getCategories(
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
        //@PageableDefault: dùng phân trang
        //size = 5: số lượng chứa category trong 1 trang
        //sort = "name": tìm kiếm theo tên
        //direction = Sort.Direction.ASC: giá trị tăng dần (ASC) cảu tên
        //Pageable: sử dụng để hỗ trợ phân trang dữ liệu khi xử lý các yêu cầu HTTP. Pageable chứa các thông tin về trang như số trang, kích thước trang, thuộc tính sắp xếp, hướng sắp xếp, và các thông tin khác liên quan đến phân trang.
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> getCategories(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>("Category with Id " + id + " was deleted", HttpStatus.OK);
    }

}

