package edu.poly.springshop.service;

import edu.poly.springshop.domain.Category;
import edu.poly.springshop.exception.CategoryException;
import edu.poly.springshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;      //dùng @Autowired tim thuộc tính từ CategoryRepository vào categoryRepository

    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }   //phương thức lưu lại sau khi entity truyên vào

    public Category update(Long id, Category entity) {  //truyền id vào để cập nhật
        Optional<Category> existed = categoryRepository.findById(id);
        //categoryRepository.findById(id): tìm kiếm các thông tin theo id

        if (existed.isEmpty()) {
            throw new CategoryException("Category id " + id + " does not existed");
        }
        //isEmpty: rồng
        //if (existed.isEmpty()): nếu trường hợp không tồn tại trong CSDL
        //throw new CatalogException: truyền thông tin vào lớp CatalogException chứa thông báo đến client

        try {
            Category existedCategory = existed.get();
            existedCategory.setName(entity.getName());
            existedCategory.setStatus(entity.getStatus());

            return categoryRepository.save(existedCategory);
            //existed.get(): sử dụng để trích xuất giá trị từ Optional và gán vào biến existedCategory theo kiểu đối tượng Category
            //existedCategory.setName(entity.getName()):đang cập nhật thông tin name của existedCategory để giống với thông tin từ entity của Category
            //categoryRepository.save(existedCategory): dùng categoryRepository để lưu (persist) existedCategory vào cơ sở dữ liệu
        } catch (Exception ex) {
            throw new CategoryException("Category is updated fail");
            //nếu trường hợp lõi sẽ trả thông tin đến client
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }//phương thức tìm kiếm, phân trang, sắp xếp dữ liệu

    public Category findById(Long id) {
        Optional<Category> found = categoryRepository.findById(id);

        if (found.isEmpty()) {
            throw new CategoryException("Category with id " + id + " does not exist");
        }
        return found.get();
    }

    public void deleteById(Long id){
        Category existed = findById(id);
        categoryRepository.delete(existed);
    }
}
