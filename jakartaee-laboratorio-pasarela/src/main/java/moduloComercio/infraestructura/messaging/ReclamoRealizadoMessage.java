package moduloComercio.infraestructura.messaging;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.io.StringReader;
import java.io.StringWriter;

public record ReclamoRealizadoMessage(
        String texto,
        String comercioId
) {
    public String toJson() {
        JsonObject json = Json.createObjectBuilder()
            .add("texto", texto)
            .add("comercioId", comercioId)
            .build();

        StringWriter sw = new StringWriter();
        try (JsonWriter writer = Json.createWriter(sw)) {
            writer.write(json);
        }
        return sw.toString();
    }

    public static ReclamoRealizadoMessage fromJson(String json) {
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject obj = reader.readObject();
            return new ReclamoRealizadoMessage(
                obj.getString("texto"),
                obj.getString("comercioId")
            );
        }
    }
}
