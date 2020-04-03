package read.mybatis.enter;

import org.apache.ibatis.io.Resources;

public class TestMain {

	public static void main(String[] args) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		System.out.println(classLoader);
		classLoader = classLoader.getParent().getSystemClassLoader();
		System.out.println(classLoader);
		
		

	}
}
