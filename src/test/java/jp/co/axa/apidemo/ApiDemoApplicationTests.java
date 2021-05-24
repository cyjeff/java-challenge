package jp.co.axa.apidemo;

import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiDemoApplicationTests {

	@Test
	public void a_contextLoads() {
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
	public void b_getAllEmployee() throws Exception {
		this.mockMvc.perform(get("/api/v1/employees"))
		.andExpect(status().isOk())
		.andExpect(content().json("[{'id': 1,'name': 'Jeff','salary': 100000,'department': 'IT'},{'id': 2,'name': 'Jack','salary': 120000,'department': 'HR'}]"));
	}

	// GET single
	@Test
	public void c_getOneEmployee() throws Exception {
		this.mockMvc.perform(get("/api/v1/employees/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1,'name': 'Jeff','salary': 100000,'department': 'IT'}"));
	}

	// POST
	// add one employee and check that exist
	@Test 
	public void d_postEmployee() throws Exception {
		this.mockMvc.perform(post("/api/v1/employees?name=Jay&salary=140000&department=Finance"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Employee Saved Successfully")));;
		this.mockMvc.perform(get("/api/v1/employees/3"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 3,'name': 'Jay','salary': 140000,'department': 'Finance'}"));
	}

	// DELETE
	// delete number 2 and check it does not exist
	@Test 
	public void e_deleteEmployee() throws Exception {
		this.mockMvc.perform(delete("/api/v1/employees/2"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Employee Deleted Successfully")));
		this.mockMvc.perform(get("/api/v1/employees"))
		.andExpect(status().isOk())
		.andExpect(content().json("[{'id': 1,'name': 'Jeff','salary': 100000,'department': 'IT'},{'id': 3,'name': 'Jay','salary': 140000,'department': 'Finance'}]"));
	}

	// delete and non-exstant employee and check the warning message
	@Test
	public void f_deleteEmployeeNotExist() throws Exception {
		this.mockMvc.perform(delete("/api/v1/employees/4"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Requested User Does Not Exist")));
		
	}

	// PUT
	// modify one employee and verify the change
	@Test
	public void g_putEmployee() throws Exception {
		this.mockMvc.perform(put("/api/v1/employees/1")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"salary\":200000}"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Employee Updated Successfully")));
		this.mockMvc.perform(get("/api/v1/employees/1"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'id': 1,'name': 'Jeff','salary': 200000,'department': 'IT'}"));
		
	}

	// modify an non-existant employee and check that a new one is added
	@Test
	public void g_putEmployeeNotExist() throws Exception {
		this.mockMvc.perform(put("/api/v1/employees/4")		
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"salary\":200000}"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Employee Saved Successfully")));
		this.mockMvc.perform(get("/api/v1/employees"))
		.andExpect(status().isOk())
		.andExpect(content().json("[{'id': 1,'name': 'Jeff','salary': 200000,'department': 'IT'},{'id': 3,'name': 'Jay','salary': 140000,'department': 'Finance'},{'id': 4,'name': null,'salary': 200000,'department': null}]"));
	}
}
