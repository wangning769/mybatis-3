/**
 *    Copyright 2009-2018 the original author or authors.
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

import java.sql.Connection;

/**
 * @author Clinton Begin
 * 事务隔离级别isolation
 * 
 * 
	事务隔离级别									脏读				不可重复读				幻读
	读未提交（read-uncommitted）	是							是							是
	读已提交（read-committed）		否							是							是
	可重复读（repeatable-read）		否							否							是
	串行化（serializable）					否							否							否

 * 
 */
public enum TransactionIsolationLevel {
	//不使用事务
  NONE(Connection.TRANSACTION_NONE),
  //读取已提交
  READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
  //读取未提交
  READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
  //可重复读
  REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
  //可序列化
  SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);
  private final int level;

  TransactionIsolationLevel(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }
}
