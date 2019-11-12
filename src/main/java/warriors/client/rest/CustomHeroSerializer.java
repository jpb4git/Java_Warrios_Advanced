package warriors.client.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import warriors.contracts.Hero;
import warriors.model.BaseHero;

import java.io.IOException;

public class CustomHeroSerializer extends StdSerializer<BaseHero> {


    public CustomHeroSerializer() {
        this(null);
    }

    public CustomHeroSerializer(Class<BaseHero> t) {
        super(t);
    }

    @Override
    public void serialize(BaseHero h, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {


        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", h.getName());
        jsonGenerator.writeStringField("attack", String.valueOf(h.getAttackLevel().asInt()));
        jsonGenerator.writeStringField("life", String.valueOf(h.getLife().asInt()));
        jsonGenerator.writeEndObject();

    }
}
