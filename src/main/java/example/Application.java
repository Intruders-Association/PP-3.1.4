package example;

import example.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class Application {
	private static final String URL = "http://94.198.50.185:7081/api/users";

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
		String sessionId = responseEntity.getHeaders().get("set-cookie").get(0);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.COOKIE, sessionId);

		User user = new User(3L, "James", "Brown", 73);

		HttpEntity<User> requestAdd = new HttpEntity<>(user, httpHeaders);
		ResponseEntity<String> addUser = restTemplate.exchange(URL, HttpMethod.POST, requestAdd, String.class);

		user.setName("Thomas");
		user.setLastName("Shelby");
		HttpEntity<User> requestUpdate = new HttpEntity<>(user, httpHeaders);
		ResponseEntity<String> updateUser = restTemplate.exchange(URL, HttpMethod.PUT, requestUpdate, String.class);

		ResponseEntity<String> deleteUser = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, requestAdd, String.class);
		String text = addUser.getBody() + updateUser.getBody() + deleteUser.getBody();
		System.out.println(text);
	}
}
