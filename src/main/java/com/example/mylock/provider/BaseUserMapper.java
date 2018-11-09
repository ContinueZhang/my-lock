package com.example.mylock.provider;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Set;

/**
 * Created by continue on 2018/11/7.
 */
@RegisterMapper
public interface BaseUserMapper<T> {

    @SelectProvider(type = BaseUserProvider.class, method = "selectTAll")
    List<T> selectTAll();

    class BaseUserProvider extends MapperTemplate {
        public BaseUserProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }

        public String selectTAll(MappedStatement ms) {
            final Class<?> entityClass = getEntityClass(ms);
            //修改返回值类型为实体类型
            setResultType(ms, entityClass);
            Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
            for (EntityColumn column : columns) {

                if (column.getEntityField().isAnnotationPresent(JoinColumn.class) && column.getEntityField().isAnnotationPresent(ManyToOne.class)) {
//                    column.getEntityField().getJavaType()
                }
            }

            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.orderByDefault(entityClass));
            System.out.println(sql.toString());
            return sql.toString();
        }
    }
}
