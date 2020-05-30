package iCrib11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;

import Entities.Crib;
import Entities.User;
import Firebase.FirestoreService;


@Path("/users")
public class UsersController {
	
	private FirestoreService firestore;
	
	public UsersController() {
		firestore = new FirestoreService();
	}
	
	
	@POST
	@Path("/register")
	@Consumes("text/plain")
	public Response register(String u) {
		
		Gson g = new Gson();
		User user = g.fromJson(u, User.class);
		
		User userAux = firestore.getUser(user.getEmail());
	      //Converting the Object to JSONString
	  
		System.out.println("AQUIIIIIIIIIIIII1\n");
		if(userAux == null) {
			System.out.println("AQUIIIIIIIIIIIII2\n");
				//adicionar a BD
			firestore.register(user);
			return Response.status(Status.OK).build();
		}
		else {
			System.out.println("AQUIIIIIIIIIIIII3\n");
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}	
	}
	
	@POST
	@Path("/login")
	@Consumes("text/plain")
	public Response login(String u) {

		System.out.println("AQUIIIIIIIIIIIII5\n");
		Gson g = new Gson();
		User user = g.fromJson(u, User.class);

		System.out.println("AQUIIIIIIIIIIIII6\n");
		
		System.out.println(user.getEmail()+"\n");
		User userAux = firestore.getUser(user.getEmail());


		System.out.println("AQUIIIIIIIIIIIII7\n");
		
		System.out.println("AQUIIIIIIIIIIIII1\n");
		if(userAux != null) {
			System.out.println("AQUIIIIIIIIIIIII2\n");
			
			//verificar palavra passe
			if(user.getPassword().equals(userAux.getPassword())) {

				firestore.login(userAux);
				System.out.println("AQUIIIIIIIIIIIII5555\n");
				return Response.status(Status.OK).build();
			}
			else {

				System.out.println("AQUIIIIIIIIIIIII4\n");
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			
		}
		else {
			System.out.println("AQUIIIIIIIIIIIII3\n");
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}	
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String temperatureChange() {
		
		// The topic name can be optionally prefixed with "/topics/".
		String topic = "123";

		// See documentation on defining a message payload.
		Message message = Message.builder().setNotification(new Notification("title", "text"))
		    .putData("temperature", "850")
		    .setTopic(topic)
		    .build();

		// Send a message to the devices subscribed to the provided topic.
		String response;
		try {
			response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Successfully sent message: " );
			
			return "Sent!";
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error when sending message" );
		}
		// Response is a message ID string.
	
		return "Oops";
	}
	

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCrib(@PathParam("id")String id) {
		
		System.out.println("AQUIIIIIIIIIIIII\n");
		Crib crib = firestore.getCrib(id);
		ObjectMapper mapper = new ObjectMapper();
	      //Converting the Object to JSONString
	    try {
			String jsonString = mapper.writeValueAsString(crib);

			System.out.println("AQUIIIIIIIIIIIII1\n");
			if(crib != null) {
				System.out.println("AQUIIIIIIIIIIIII2\n");
				return Response.status(Status.OK).entity(jsonString).type(MediaType.APPLICATION_JSON).build();
			}
			else {
				System.out.println("AQUIIIIIIIIIIIII3\n");
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("AQUIIIIIIIIIIIII4\n");
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();	
		}
	}

}
