package read.mybatis.enter;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.TransactionalCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.parsing.TokenHandler;

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
