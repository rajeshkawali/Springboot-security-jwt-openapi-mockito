package com.rajeshkawali;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Rajesh_Kawali
 *
 */
@SpringBootTest
@ContextConfiguration
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WithMockUser(username="admin",roles={"USER","ADMIN"})
class SpringbootApplicationTests {

	@Test
	void contextLoads() {
	}

}
