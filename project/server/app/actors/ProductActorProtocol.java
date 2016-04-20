package actors;

public class ProductActorProtocol {
	public static class SayHello {
        public final String name;

        public SayHello(String name) {
            this.name = name;
        }
    }
}
