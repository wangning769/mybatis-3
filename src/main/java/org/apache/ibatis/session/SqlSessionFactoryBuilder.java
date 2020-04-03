/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

/**
 * Builds {@link SqlSession} instances.
 *
 * @author Clinton Begin
 */

/**
 * 先建造工厂，再生产加工
 * 流程:
 * 	(InputStream/Reader) -> XMLConfigBuilder -> ConfigBuilder -> DefaultSqlSessionFactory
 * 	
 * build方法，全是build方法
 * 
 * 	1)处理加载资源转换成Configuration
 * 		处理资源inputStream的build	inputStream -> XMLConfigBuilder -> ConfigBuilder
 * 		处理资源Reader的build					 Reader -> XMLConfigBuilder -> ConfigBuilder
 * 	 
 * 	2)处理Configuration 转换成 SqlSessionFactory
 * 		处理build的build
 * 			ConfigBuilder -> DefaultSqlSessionFactory
 * 
 * rethinking:
 *  1.工厂模式:
 * 	StringBuilder是jdk源码中典型的建造者模式
 * 		StringBuilder sb = new StringBuilder();		//一间毛坯房	
 *      	sb.append("厨房").append("厕所");		// “设计” 厨房 和 厕所，卧室呢？
 *      	sb.toString();	//开始装修->返回构建 String
 *    
 *     XMLConfigBuilder构建mybatis-config.xml配置文件
 *     	XMLConfigBuilder 
 *     		-> XMLConfigBuilder.build("数据库连接")
 *     		-> XMLConfigBuilder.build("管理Mapper.xml映射") 
 *     		-> XMLConfigBuilder.build("...")	//配置文件的中的所有属性
 *     		-> XMLConfigBuilder.build(); //开始构建 ->返回构建 Configuration
 *      		
 *      		->new DefaultSqlSessionFactory(Configuration);//修建好工厂可以生产SqlSession实例了
 *  
 *  2.XMLConfigBuilder实现是啥?
 *  		ConfigBuilder实现又是啥? 
 *    			DefaultSqlSessionFactory实现双是啥？
 *    
 * @author rethink
 *
 */
public class SqlSessionFactoryBuilder {

  public SqlSessionFactory build(Reader reader) {
    return build(reader, null, null);
  }

  public SqlSessionFactory build(Reader reader, String environment) {
    return build(reader, environment, null);
  }

  public SqlSessionFactory build(Reader reader, Properties properties) {
    return build(reader, null, properties);
  }

  /**
   * Reader -> XMLConfigBuilder -> ConfigBuilder -> DefaultSqlSessionFactory
   */
  public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
    try {
      XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        reader.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(InputStream inputStream) {
    return build(inputStream, null, null);
  }

  public SqlSessionFactory build(InputStream inputStream, String environment) {
    return build(inputStream, environment, null);
  }

  public SqlSessionFactory build(InputStream inputStream, Properties properties) {
    return build(inputStream, null, properties);
  }

  /**
   * inputStream -> XMLConfigBuilder -> ConfigBuilder -> DefaultSqlSessionFactory
   */
  public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
    try {
      XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        inputStream.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(Configuration config) {
    return new DefaultSqlSessionFactory(config);
  }

}
