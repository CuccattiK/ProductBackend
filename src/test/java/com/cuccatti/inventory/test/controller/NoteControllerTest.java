package com.cuccatti.inventory.test.controller;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cuccatti.inventory.controller.NoteController;
import com.cuccatti.inventory.model.Note;
import com.cuccatti.inventory.repository.NoteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoteControllerTest.class)
public class NoteControllerTest {

	final static String url = "http://localhost:8080";
	final static String endpoint = "/api/notes/";
	
	@MockBean
	private Note note;

	@MockBean
	private NoteRepository noteRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getNoteFailesWhenInvalidNoteId() {
		given().when().get(endpoint + "999").then().statusCode(404);
	}

	@Test
	public void getNoteSucceedsWhenValidNoteId() {

		given().when().get(endpoint + "1").then().statusCode(200);
	}

	@Test
	public void addValidNote() {
		Map<String, String> note = new HashMap<>();
		note.put("title", "Kyles Note");
		note.put("content", "Kyles Content");
		given().contentType("application/json").body(note).when().post("/api/notes").then().statusCode(200);
	}

	@Test
	public void addNoteFailsWhenInvalidJson() {
		Map<String, String> note = new HashMap<>();
		note.put("Atitle", "Kyles Note");
		note.put("SomeContent", "Kyles Content");
		given().contentType("application/json").body(note).when().post("/api/notes").then().statusCode(400);
	}	
	
	
}
