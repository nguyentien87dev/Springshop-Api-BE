package edu.poly.springshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //IDENTITY: tự tăng
    @Column(name = "id", nullable = false)      //nullable:false: không rỗng
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated
    @Column(name = "status", nullable = false)
    private CategoryStatus status;
//    categoryStatus của đối tượng Category sẽ được ánh xạ vào cột "category_status" trong bảng cơ sở dữ liệu
}