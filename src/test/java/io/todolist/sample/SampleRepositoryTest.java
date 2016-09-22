package io.todolist.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class SampleRepositoryTest {

	@Autowired
	private SampleRepository sampleRepository;

	@Test
	public void save() {
		// given
		Sample sample = new Sample();
		sample.setName("Save Sample");

		// when
		Sample savedSample = sampleRepository.save(sample);

		// then
		assertEquals(savedSample.getName(), sample.getName());
	}

	@Test
	public void findAll() throws Exception {
		// given
		Sample sample1 = new Sample();
		sample1.setName("Sample1");
		Sample sample2 = new Sample();
		sample2.setName("Sample2");

		List<Sample> samples = new ArrayList<>();
		samples.add(sample1);
		samples.add(sample2);
		sampleRepository.save(samples);

		// when
		List<Sample> savedSamples = sampleRepository.findAll();

		// then
		assertEquals(savedSamples.size(), samples.size());
	}

	@Test
	public void findOne() throws Exception {
		// given
		Sample sample = new Sample();
		sample.setName("Find One Sample");
		Sample savedSample = sampleRepository.save(sample);

		// when
		Sample foundSample = sampleRepository.findOne(savedSample.getId());

		// then
		assertEquals(foundSample, savedSample);
	}

	@Test
	public void saveAndFlush() throws Exception {
		// given
		Sample sample = new Sample();
		sample.setName("Sample");
		Sample savedSample = sampleRepository.save(sample);

		// when
		String newSampleName = "Change Sample";
		savedSample.setName(newSampleName);
		Sample changedSample = sampleRepository.saveAndFlush(savedSample);

		// then
		assertEquals(changedSample.getName(), newSampleName);
	}

	@Test
	public void delete() throws Exception {
		// given
		Sample sample = new Sample();
		sample.setName("Sample");
		Sample savedSample = sampleRepository.save(sample);

		// when
		sampleRepository.delete(savedSample);

		// then
		assertNull(sampleRepository.findOne(savedSample.getId()));
	}
}
