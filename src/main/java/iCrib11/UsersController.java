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

				String token = firestore.login(userAux);
				System.out.println("AQUIIIIIIIIIIIII5555\n");
				return Response.status(Status.OK).entity(token).build();
			}
			else {

				System.out.println("AQUIIIIIIIIIIIII4\n");
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}

		}
		else {
			System.out.println("AQUIIIIIIIIIIIII3\n");
			return Response.status(Status.NOT_FOUND).build();
		}	
	}

	@POST
	@Path("/logout")
	@Consumes("text/plain")
	public Response logout(String u) {

		System.out.println("AQUIIIIIIIIIIIII5\n");
		Gson g = new Gson();
		User user = g.fromJson(u, User.class);

		System.out.println("AQUIIIIIIIIIIIII6\n");

		System.out.println("AQUIIIIIIIIIIIII2\n");

		//verificar palavra passe

		boolean result = firestore.logout(user);

		if(result) {
			System.out.println("AQUIIIIIIIIIIIII5555\n");
			return Response.status(Status.OK).build();
		}
		else {

			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}




	}


	@GET
	@Path("/session")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSession(String u) {

		System.out.println("AQUIIIIIIIIIIIII5\n");
		Gson g = new Gson();
		User user = g.fromJson(u, User.class);

		System.out.println("AQUIIIIIIIIIIIII6\n");

		User userAux = firestore.getUser(user.getEmail());

		System.out.println("AQUIIIIIIIIIIIII7\n");

		if(userAux != null) {
			System.out.println("AQUIIIIIIIIIIIII2\n");
			System.out.println(user.getToken() + "   " + userAux.getToken());

			//verificar palavra passe
			if(user.getToken().equals(userAux.getToken())) {

				return Response.status(Status.OK).build();
			}
			else {

				System.out.println("AQUIIIIIIIIIIIII4\n");
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}

		}
		else {
			System.out.println("AQUIIIIIIIIIIIII3\n");
			return Response.status(Status.NOT_FOUND).build();
		}	
	}

}
