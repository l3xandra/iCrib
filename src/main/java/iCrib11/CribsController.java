package iCrib11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import Entities.CribCreation;
import Entities.User;
import Firebase.FirestoreService;


@Path("/cribs")
public class CribsController {

	private FirestoreService firestore;
	private final static String TEMP_NOTIF_TITLE = "Room's temperature change!";
	private final static String BABY_IS = "The baby is ";

	private final static String TEMP_NOTIF_TEXT = "One of yout crib's room's temperature changed to ";

	public CribsController() {
		firestore = new FirestoreService();
	}

	@POST
	@Path("/inCrib")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isInCribUpdate(String c) {

		//update crib at DB
		Gson g = new Gson();
		Crib crib = g.fromJson(c, Crib.class);
		
		System.out.println("111");
		String result = firestore.updateIsInCrib(crib);

		System.out.println("222");
		if(result != null) {
			

			System.out.println("inside");
			// See documentation on defining a message payload.
			Message message = Message.builder().setNotification(new Notification(result, ""))
					.putData("code", crib.getCode())
					.setTopic(crib.getCode())
					.build();
	
			// Send a message to the devices subscribed to the provided topic.
			String response;
			try {
				response = FirebaseMessaging.getInstance().send(message);
				System.out.println("Successfully sent message: " );

				return Response.status(Status.OK).build();
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error when sending message" );
			}
			// Response is a message ID string.

		}
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/rocking")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cribRockingUpdate(String c) {

		//update crib at DB
		Gson g = new Gson();
		Crib crib = g.fromJson(c, Crib.class);
		
		System.out.println("111");
		boolean result = firestore.updateCribRocking(crib);

		System.out.println("222");
		if(result) {
			Message message = Message.builder().setNotification(new Notification("rocking", ""))
					.putData("value", Float.toString(crib.getCribRocking()))
					.setTopic("simulator"+crib.getCode())
					.build();
	
			// Send a message to the devices subscribed to the provided topic.
			String response;
			try {
				response = FirebaseMessaging.getInstance().send(message);
				System.out.println("Successfully sent message: " );

				return Response.status(Status.OK).build();
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error when sending message" );
			}
			System.out.println("333");
				return Response.status(Status.OK).build();
			
			// Response is a message ID string.

		}
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/toy")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response toyMovementUpdate(String c) {

		//update crib at DB
		Gson g = new Gson();
		Crib crib = g.fromJson(c, Crib.class);
		
		System.out.println("111");
		boolean result = firestore.updateToyMovement(crib);

		System.out.println("222");
		if(result) {
			Message message = Message.builder().setNotification(new Notification("toy", ""))
					.putData("value", Float.toString(crib.getToyMovement()))
					.setTopic("simulator"+crib.getCode())
					.build();
	
			// Send a message to the devices subscribed to the provided topic.
			String response;
			try {
				response = FirebaseMessaging.getInstance().send(message);
				System.out.println("Successfully sent message: " );

				return Response.status(Status.OK).build();
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error when sending message" );
			}

			System.out.println("333");
				return Response.status(Status.OK).build();
			
			// Response is a message ID string.

		}
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/temperature")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response temperatureUpdate(String c) {

		//update crib at DB
		Gson g = new Gson();
		Crib crib = g.fromJson(c, Crib.class);
		
		System.out.println("111");
		boolean result = firestore.updateTemperature(crib);

		System.out.println("222");
		if(result) {
			
			// See documentation on defining a message payload.
			Message message = Message.builder().setNotification(new Notification(TEMP_NOTIF_TITLE, TEMP_NOTIF_TEXT + crib.getRoomTemperature() + " ºC."))
					.setTopic(crib.getCode())
					.build();
	
			// Send a message to the devices subscribed to the provided topic.
			String response;
			try {
				response = FirebaseMessaging.getInstance().send(message);
				System.out.println("Successfully sent message: " );

				return Response.status(Status.OK).build();
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error when sending message" );
			}
			// Response is a message ID string.

		}
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/movement")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response movementUpdate(String c) {

		//update crib at DB
		Gson g = new Gson();
		Crib crib = g.fromJson(c, Crib.class);
		
		System.out.println("111");
		String result = firestore.updateMovement(crib);

		System.out.println("222");
		if(result != null) {

			System.out.println("333");
			// See documentation on defining a message payload.
			Message message = Message.builder().setNotification(new Notification(BABY_IS + result, ""))
					.putData("code", crib.getCode())
					.setTopic(crib.getCode())
					.build();
	
			// Send a message to the devices subscribed to the provided topic.
			String response;
			try {
				response = FirebaseMessaging.getInstance().send(message);
				System.out.println("Successfully sent message: " );

				return Response.status(Status.OK).build();
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error when sending message" );
			}
			// Response is a message ID string.

		}
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	
	@POST
	@Path("/sound")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response soundUpdate(String c) {

		//update crib at DB
		Gson g = new Gson();
		Crib crib = g.fromJson(c, Crib.class);
		
		System.out.println("111");
		String result = firestore.updateSound(crib);

		System.out.println("222");
		if(result != null) {
			
			// See documentation on defining a message payload.
			Message message = Message.builder().setNotification(new Notification(BABY_IS + result, ""))
					.putData("code", crib.getCode())
					.setTopic(crib.getCode())
					.build();
	
			// Send a message to the devices subscribed to the provided topic.
			String response;
			try {
				response = FirebaseMessaging.getInstance().send(message);
				System.out.println("Successfully sent message: " );

				return Response.status(Status.OK).build();
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error when sending message" );
			}
			// Response is a message ID string.

		}
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}


	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCrib(@PathParam("id")String id) {

		System.out.println("CRIIIB\n");
		Crib crib = firestore.getCrib(id);
		ObjectMapper mapper = new ObjectMapper();
		//Converting the Object to JSONString
		try {
			String jsonString = mapper.writeValueAsString(crib);

			System.out.println("CRIIIIB1\n");
			if(crib != null) {
				System.out.println("CRIBBB2\n");
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

	@GET
	@Path("/cribs")
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response getCribs(String u) {

		System.out.println("AQUIIIIIIIIIIIII\n");
		Gson g = new Gson();
		User user = g.fromJson(u, User.class);

		System.out.println(user.getEmail() + "XXXXXXXXX\n");
		Map<String, String> cribs = firestore.getCribs(user);

		System.out.println("AQUIIIIIIIIIIIII1\n");
		if(cribs != null) {

			System.out.println("AQUIIIIIIIIIIIII1222\n");

			String cribsJson = g.toJson(cribs);

			System.out.println("AQUIIIIIIIIIIIII2\n");
			return Response.status(Status.OK).entity(cribsJson).type(MediaType.APPLICATION_JSON).build();
		}
		else {
			System.out.println("AQUIIIIIIIIIIIII3\n");
			return Response.status(Status.NOT_FOUND).build();
		}

	}


	@POST
	@Path("/add")
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response addCrib(String c) {

		System.out.println("AQUIIIIIIIIIIIII\n");

		Gson g = new Gson();
		CribCreation cribCreation = g.fromJson(c, CribCreation.class);

		System.out.println("AQUIIIIIIIIIIIII2\n");
		Crib crib = firestore.getCrib(cribCreation.getCode());

		System.out.println("AQUIIIIIIIIIIIII3\n");
		//verify if crib exists
		if(crib != null) {

			System.out.println("AQUIIIIIIIIIIIII4\n");
			
			
			
			crib = firestore.addCrib(cribCreation.getEmail(), cribCreation.getCode(), crib, cribCreation.getName());

			System.out.println("AQUIIIIIIIIIIIII5\n");
			if(crib != null) {

				ObjectMapper mapper = new ObjectMapper();
				//Converting the Object to JSONString
				try {
					String jsonString = mapper.writeValueAsString(crib);

					System.out.println("AQUIIIIIIIIIIIII6\n");
					return Response.status(Status.OK).entity(jsonString).type(MediaType.APPLICATION_JSON).build();

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					System.out.println("AQUIIIIIIIIIIIII7\n");
					e.printStackTrace();
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();	
				}

			}
			else {

				System.out.println("AQUIIIIIIIIIIIII8\n");
				return Response.status(Status.CONFLICT).build();
			}
		}

		else {

			System.out.println("AQUIIIIIIIIIIIII9\n");
			return Response.status(Status.NOT_FOUND).build();
		}


	}

	@POST
	@Path("/update")
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response updateCribName(String c) {

		System.out.println("AQUIIIIIIIIIIIII\n");

		Gson g = new Gson();
		CribCreation crib = g.fromJson(c, CribCreation.class);

		System.out.println("AQUIIIIIIIIIIIII2\n");

		CribCreation cribResult = firestore.updateCribName(crib);

		System.out.println("AQUIIIIIIIIIIIII5\n");
		if(cribResult != null) {

			ObjectMapper mapper = new ObjectMapper();
			//Converting the Object to JSONString
			try {
				String jsonString = mapper.writeValueAsString(cribResult);

				System.out.println("AQUIIIIIIIIIIIII6\n");
				return Response.status(Status.OK).entity(jsonString).type(MediaType.APPLICATION_JSON).build();

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				System.out.println("AQUIIIIIIIIIIIII7\n");
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();	
			}

		}
		else {

			System.out.println("AQUIIIIIIIIIIIII8\n");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}



	}
	
	@POST
	@Path("/delete")
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response deleteCrib(String c) {

		System.out.println(c+"\n");

		Gson g = new Gson();
		CribCreation crib = g.fromJson(c, CribCreation.class);

		System.out.println("AQUIIIIIIIIIIIII2\n");

		boolean cribResult = firestore.deleteCrib(crib);

		System.out.println("AQUIIIIIIIIIIIII5\n");
		if(cribResult) {


				System.out.println("AQUIIIIIIIIIIIII6\n");
				return Response.status(Status.OK).build();


		}
		else {

			System.out.println("AQUIIIIIIIIIIIII8\n");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}



	}

}
