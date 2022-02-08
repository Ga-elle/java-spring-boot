package com.openclassrooms.helloworld;

import com.openclassrooms.helloworld.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HelloworldApplicationTests {

	@Autowired
	private BusinessService bs;

	@Test
	void contextLoads() {
	}

	@Test
	void testGetHelloWorld(){
		String expected = "He, how are you doing?!";
		String result = bs.getHelloWorld().getValue();
		assertEquals(expected, result);
	}

}
