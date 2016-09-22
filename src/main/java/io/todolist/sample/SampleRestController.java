package io.todolist.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
public class SampleRestController {
	@Autowired
	private SampleService sampleService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Sample> createProject(@RequestParam String name) {
		Sample savedSample = sampleService.save(name);
		return new ResponseEntity<>(savedSample, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Sample>> findAll() {
		return new ResponseEntity<>(sampleService.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{sample}", method = RequestMethod.GET)
	public ResponseEntity<Sample> findOne(@PathVariable Sample sample) {
		return new ResponseEntity<>(sample, HttpStatus.OK);
	}

	@RequestMapping(value = "/{sample}", method = RequestMethod.PATCH)
	public ResponseEntity<Sample> update(@PathVariable Sample sample, @RequestParam String name) {
		return new ResponseEntity<>(sampleService.update(sample, name), HttpStatus.OK);
	}

	@RequestMapping(value = "/{sample}", method = RequestMethod.DELETE)
	public ResponseEntity<Sample> delete(@PathVariable Sample sample) {
		sampleService.delete(sample);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
