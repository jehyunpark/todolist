package io.todolist.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
	@Autowired
	private SampleRepository sampleRepository;

	public Sample save(String name) {
		Sample sample = new Sample();
		sample.setName(name);
		return sampleRepository.save(sample);
	}

	public List<Sample> findAll() {
		return sampleRepository.findAll();
	}

	public Sample findOne(Long id) {
		return sampleRepository.findOne(id);
	}

	public Sample update(Sample sample, String name) {
		sample.setName(name);
		return sampleRepository.saveAndFlush(sample);
	}

	public void delete(Sample sample) {
		sampleRepository.delete(sample);
	}
}
