package io.todolist.sample;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(SampleRestController.class)
public class SampleRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SampleService sampleService;
	@MockBean
	private SampleRepository sampleRepository;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private Sample sample;

	private static final String URL_SAMPLES = "/samples";

	@Before
	public void setUp() {
		sample = new Sample();
		sample.setId(1L);
		sample.setName("Sample");
	}

	@Test
	public void Sample정상생성시_created응답() throws Exception {
		// when
		when(sampleService.save("Sample")).thenReturn(sample);

		// then
		this.mockMvc.perform(post(URL_SAMPLES).param("name", "Sample")).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(sample.getId().intValue())))
				.andExpect(jsonPath("$.name", is(sample.getName())));
	}

	@Test
	public void 모든Samples조회() throws Exception {
		// given
		List<Sample> samples = new ArrayList<>();
		Sample sample1 = new Sample();
		Sample sample2 = new Sample();
		sample1.setId(1L);
		sample2.setId(2L);
		sample1.setName("Sample1");
		sample2.setName("Sample2");
		samples.add(sample1);
		samples.add(sample2);

		// when
		when(sampleService.findAll()).thenReturn(samples);

		// then
		this.mockMvc.perform(get(URL_SAMPLES)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(sample1.getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(sample1.getName())))
				.andExpect(jsonPath("$[1].id", is(sample2.getId().intValue())))
				.andExpect(jsonPath("$[1].name", is(sample2.getName())));
	}

	@Ignore()
	// SampleRestController.findOne(sample) 은 HandlerMethodArgumentResolver를
	// 사용해서 method를 호출하기 전에 미리 sample을 조회해오는데, @WebMvcTest은 이 부분을 테스트 할 수 없음.
	// 서버를 실행하고 직접 테스트를 하면 정상 작동을 확인할 수 있다.
	// 추후 테스트 가능한 방법이 생기면 업데이트.
	// http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests
	@Test
	public void SampleId로_Sample조회() throws Exception {
		// when
		when(sampleService.findOne(sample.getId())).thenReturn(sample);

		// then
		this.mockMvc.perform(get(URL_SAMPLES + "/" + sample)).andExpect(status().isOk());
	}

	@Ignore()
	// SampleRestController.findOne(sample) 은 HandlerMethodArgumentResolver를
	// 사용해서 method를 호출하기 전에 미리 sample을 조회해오는데, @WebMvcTest은 이 부분을 테스트 할 수 없음.
	// 서버를 실행하고 직접 테스트를 하면 정상 작동을 확인할 수 있다.
	// 추후 테스트 가능한 방법이 생기면 업데이트.
	// http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests
	@Test
	public void Sample업데이트() throws Exception {
		Sample savedSample = sampleService.save("Sample");
		String name = "Update Sample";
		savedSample.setName(name);

		// when
		when(sampleService.update(savedSample, name)).thenReturn(savedSample);

		// then
		this.mockMvc.perform(patch(URL_SAMPLES + "/" + savedSample.getId()).param("name", name))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(savedSample.getId().intValue())))
				.andExpect(jsonPath("$.name", is(savedSample.getName())));
	}

	 @Ignore()
	// SampleRestController.findOne(sample) 은 HandlerMethodArgumentResolver를
	// 사용해서 method를 호출하기 전에 미리 sample을 조회해오는데, @WebMvcTest은 이 부분을 테스트 할 수 없음.
	// 서버를 실행하고 직접 테스트를 하면 정상 작동을 확인할 수 있다.
	// 추후 테스트 가능한 방법이 생기면 업데이트.
	// http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests
	@Test
	public void Sample삭제() throws Exception {
		Sample savedSample = sampleService.save("Sample");

		// when

		// then
		this.mockMvc.perform(delete(URL_SAMPLES + "/" + savedSample.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
	}
}
