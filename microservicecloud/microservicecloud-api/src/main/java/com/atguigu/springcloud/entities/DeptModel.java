package com.atguigu.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张斌文
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dept")
public class DeptModel extends Model<DeptModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "deptno", type = IdType.AUTO)
    private Long deptno;
    @TableField(value = "dname")
    private String dname;
    @TableField(value = "db_source")
    private String dbSource;


    @Override
    protected Serializable pkVal() {
        return this.deptno;
    }

}
