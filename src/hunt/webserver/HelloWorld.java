package hunt.webserver;

import static spark.Spark.*;
import spark.*;

public class HelloWorld {

	public static void main(String[] args) 
	{
      
		get(new Route("/hello") 
		{
			@Override
			public Object handle(Request request, Response response) 
			{
				String firstName = (String) request.queryParams("fname");
				if (firstName == null)
					firstName = "Guest";
				return "Hello World!  " + firstName + " says";
			}
		});

	}

}