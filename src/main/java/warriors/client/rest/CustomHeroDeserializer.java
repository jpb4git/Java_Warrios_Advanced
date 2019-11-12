package warriors.client.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import warriors.model.BaseHero;

import java.io.IOException;

public class CustomHeroDeserializer extends StdDeserializer<BaseHero> {

    public CustomHeroDeserializer() {
        this(null);
    }

    public CustomHeroDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BaseHero deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
       /* BaseHero bh = new BaseHero();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        // try catch block
        JsonNode colorNode = node.get("color");
        String color = colorNode.asText();
        car.setColor(color);
        return car;
        */
        return null;
    }
}
