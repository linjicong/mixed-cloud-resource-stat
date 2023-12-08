package com.linjicong.cloud.stat.dao.mapper;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2023-02-20-18:13
 */
public class CommonSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertBatchSomeColumn());
        methodList.add(new InsertBatch());
        methodList.add(new DeleteByStatDate());
        methodList.add(new SelectByStatDate());
        methodList.add(new SelectByConfName());
        return methodList;
    }

    private static class InsertBatch extends AbstractMethod {
        protected InsertBatch() {
            super("insertBatch");
        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
            SqlMethod sqlMethod = SqlMethod.INSERT_ONE;
            List<TableFieldInfo> fieldList = tableInfo.getFieldList();
            String insertSqlColumn = tableInfo.getKeyInsertSqlColumn(true, "",false) +
                    this.filterTableFieldInfo(fieldList, null, i->{
                        if(i.getInsertStrategy()== FieldStrategy.NEVER){
                            return EMPTY;
                        }else{
                            return i.getInsertSqlColumn();
                        }
                    }, EMPTY);
            String columnScript = LEFT_BRACKET + insertSqlColumn.substring(0, insertSqlColumn.length() - 1) + RIGHT_BRACKET;
            String insertSqlProperty = tableInfo.getKeyInsertSqlProperty(true, ENTITY_DOT, false) +
                    this.filterTableFieldInfo(fieldList, null, i -> {
                        if(i.getInsertStrategy()== FieldStrategy.NEVER) {
                            return EMPTY;
                        }else{
                            return i.getInsertSqlProperty(ENTITY_DOT);
                        }
                    }, EMPTY);
            insertSqlProperty = LEFT_BRACKET + insertSqlProperty.substring(0, insertSqlProperty.length() - 1) + RIGHT_BRACKET;
            String valuesScript = SqlScriptUtils.convertForeach(insertSqlProperty, "list", null, ENTITY, COMMA);
            String keyProperty = null;
            String keyColumn = null;
            // 表包含主键处理逻辑,如果不包含主键当普通字段处理
            if (tableInfo.havePK()) {
                if (tableInfo.getIdType() == IdType.AUTO) {
                    /* 自增主键 */
                    keyGenerator = Jdbc3KeyGenerator.INSTANCE;
                    keyProperty = tableInfo.getKeyProperty();
                    // 去除转义符
                    keyColumn = SqlInjectionUtils.removeEscapeCharacter(tableInfo.getKeyColumn());
                } else {
                    if (null != tableInfo.getKeySequence()) {
                        keyGenerator = TableInfoHelper.genKeyGenerator(this.methodName, tableInfo, builderAssistant);
                        keyProperty = tableInfo.getKeyProperty();
                        keyColumn = tableInfo.getKeyColumn();
                    }
                }
            }
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return this.addInsertMappedStatement(mapperClass, modelClass, methodName, sqlSource, keyGenerator, keyProperty, keyColumn);

        }
    }

    private static class DeleteByStatDate extends AbstractMethod {
        protected DeleteByStatDate() {
            super("deleteByStatDate");
        }
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            return this.addDeleteMappedStatement(mapperClass, this.methodName, this.languageDriver.createSqlSource(this.configuration,"DELETE FROM " + tableInfo.getTableName() + " WHERE stat_date = #{statDate}",String.class));
        }
    }

    private static class SelectByStatDate extends AbstractMethod {
        protected SelectByStatDate() {
            super("selectByStatDate");
        }
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            return this.addSelectMappedStatementForOther(mapperClass, this.methodName, this.languageDriver.createSqlSource(this.configuration,"SELECT * FROM " + tableInfo.getTableName() + " WHERE stat_date = #{statDate}",String.class),String.class);
        }
    }

    private static class SelectByConfName extends AbstractMethod {
        protected SelectByConfName() {
            super("selectByConfName");
        }
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            return this.addSelectMappedStatementForOther(mapperClass, this.methodName, this.languageDriver.createSqlSource(this.configuration,"SELECT * FROM " + tableInfo.getTableName() + " WHERE conf_name = #{confName}",modelClass),modelClass);
        }
    }
}
