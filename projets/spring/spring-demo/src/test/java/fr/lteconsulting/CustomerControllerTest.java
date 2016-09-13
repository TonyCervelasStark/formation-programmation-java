package fr.lteconsulting;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
@WebAppConfiguration
public class CustomerControllerTest
{
	private MediaType contentType = new MediaType( MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName( "utf8" ) );

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private List<Customer> customersList = new ArrayList<>();

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = webAppContextSetup( webApplicationContext ).build();

		customerRepository.deleteAll();

		customersList.add( customerRepository.save( new Customer( "Titi", "Toto" ) ) );
	}

	@Test
	public void readBookmarks() throws Exception
	{
		mockMvc.perform( get( "/customers/" ) )
				.andExpect( status().isOk() )
				.andExpect( content().contentType( contentType ) )
				.andExpect( jsonPath( "$", hasSize( 1 ) ) )
				.andExpect( jsonPath( "$[0].id", is( customersList.get( 0 ).getId().intValue() ) ) )
				.andExpect( jsonPath( "$[0].lastName", is( "Toto" ) ) )
				.andExpect( jsonPath( "$[0].firstName", is( "Titi" ) ) );
	}
}
