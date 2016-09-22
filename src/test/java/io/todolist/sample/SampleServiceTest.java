package io.todolist.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

	@Autowired
	private SampleService sampleService;

	@Test
	public void save() {
		// given
		String sampleName = "Sample";

		// when
		Sample savedSample = sampleService.save(sampleName);

		// then
		assertEquals(savedSample.getName(), sampleName);
	}

	@Test
	public void findAll() throws Exception {
		// given
		List<Sample> samples = new ArrayList<>();

		String sampleName1 = "Sample1";
		String sampleName2 = "Sample2";
		samples.add(sampleService.save(sampleName1));
		samples.add(sampleService.save(sampleName2));

		// when
		List<Sample> foundSamples = sampleService.findAll();

		// then
		assertEquals(foundSamples, samples);
	}

	@Test
	public void findOne() throws Exception {
		// given
		String sampleName = "Find One Sample";
		Sample savedSample = sampleService.save(sampleName);

		// when
		Sample foundSample = sampleService.findOne(savedSample.getId());

		// then
		assertEquals(foundSample, savedSample);
	}

	@Test
	public void update() throws Exception {
		// given
		String sampleName = "Sample";
		Sample savedSample = sampleService.save(sampleName);

		// when
		String updateName = "Update Sample";
		Sample updatedSample = sampleService.update(savedSample, updateName);

		// then
		assertEquals(updatedSample.getName(), updateName);
	}

	@Test
	public void delete() throws Exception {
		// given
		Sample savedSample = sampleService.save("Sample");

		// when
		sampleService.delete(savedSample);

		// then
		assertNull(sampleService.findOne(savedSample.getId()));
	}
}
