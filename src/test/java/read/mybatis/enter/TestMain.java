package read.mybatis.enter;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.TransactionalCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.logging.LogFactory;

public class TestMain {

	public static void main(String[] args) {

		
		LogFactory.useLog4J2Logging();
		
		//Cache
		
	    Cache cache = new PerpetualCache("default");
	    cache = new TransactionalCache(cache);
	    System.out.println(cache.getSize());
	    cache.putObject("wn", 123);
	    System.out.println(cache.getSize());
	    System.out.println(cache.getObject("wn123"));
	    ((TransactionalCache)cache).commit();
	    System.out.println(cache.getSize());
	    System.out.println(cache.getObject("wn"));

	}
}
