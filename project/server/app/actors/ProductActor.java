package actors;

import actors.ProductActorProtocol.SayHello;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ProductActor extends UntypedActor {

	public static Props props = Props.create(ProductActor.class);
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof SayHello) {
            sender().tell("Hello, " + ((SayHello) msg).name, self());
        }
		
	}

}
