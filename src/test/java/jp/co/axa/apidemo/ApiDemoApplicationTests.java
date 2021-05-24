package jp.co.axa.apidemo;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	// setup mockMvc
	@Autowired
	private MockMvc mockMvc;

	// marker for seeding use
	private static boolean seeded = false;

	// seed data, not using BeforeClass as it requires static function
	@Before
	public void seedData() throws Exception {
		if (seeded == false) {
			this.mockMvc.perform(post("/api/v1/employees?name=Jeff&salary=100000&department=IT"));
			this.mockMvc.perform(post("/api/v1/employees?name=Jack&salary=120000&department=HR"));
			seeded = true;
		}
	}

	// GET all
	@Test
	public void getAllEmployee() throws Exception {
		this.mockMvc.perform(get("/api/v1/employees"))
		.andExpect(status().isOk())
		.andExpect(content().json("[{'id': 1,'name': 'Jeff','salary': 100000,'department': 'IT'},{'id': 2,'name': 'Jack','salary': 120000,'department': 'HR'}]"));;
	}

	// GET single
	@Test
	public void getOneEmployee() throws Exception {
		this.mockMvc.perform(get("/api/v1/employees/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1,'name': 'Jeff','salary': 100000,'department': 'IT'}"));;
	}

	// POST
	@Test
	public void postEmployee() throws Exception {
		this.mockMvc.perform(post("/api/v1/employees?name=Jay&salary=140000&department=Finance"))
		.andExpect(status().isOk());
		this.mockMvc.perform(get("/api/v1/employees/3"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 3,'name': 'Jay','salary': 140000,'department': 'Finance'}"));
		this.mockMvc.perform(delete("/api/v1/employees/3"));
	}
}
